package com.example.project.util.token;

import com.example.project.base.constant.TokenType;
import com.example.project.admin.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    public TokenType tokenType;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;
    @Column(name = "token_value", unique = true)
    private String value;
    @Column(name = "is_revoked")
    private boolean revoked;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
