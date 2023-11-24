package com.example.project.util.token;

import com.example.project.base.constant.TokenType;
import com.example.project.admin.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private final String secretKey;
    private final long accessExpiration;
    private final long refreshExpiration;
    private final long confirmExpiration;
    private final TokenRepository tokenRepository;

    public JwtUtil(JwtProperties jwtProperties, TokenRepository tokenRepository) {
        this.secretKey = jwtProperties.getSecretKey();
        this.accessExpiration = jwtProperties.getAccessExpiration();
        this.refreshExpiration = jwtProperties.getRefreshExpiration();
        this.confirmExpiration = jwtProperties.getConfirmExpiration();
        this.tokenRepository = tokenRepository;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public TokenType extractTokenType(String token) {
        return TokenType.valueOf(extractClaim(token, claims -> claims.get("token_type", String.class)));
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(getRolesClaims(userDetails), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, accessExpiration, TokenType.BEARER);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(getRolesClaims(userDetails), userDetails, refreshExpiration, TokenType.REFRESH);
    }

    public String generateConfirmToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, confirmExpiration, TokenType.VERIFY);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration, TokenType tokenType) {
        long currentTime = System.currentTimeMillis();
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setClaims(Map.of("token_type", tokenType))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new InvalidBearerTokenException("Invalid JWT token: " + e.getMessage());
        }
    }

    private Map<String, Object> getRolesClaims(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(role -> role.startsWith("ROLE_"))
                .toList();
        return Map.of("roles", roles);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void saveUserToken(User user, String jwtToken, TokenType tokenType) {
        Token token = Token.builder()
                .user(user)
                .value(jwtToken)
                .tokenType(tokenType)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> token.setRevoked(true));
        tokenRepository.saveAll(validUserTokens);
    }
}
