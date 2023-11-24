package com.example.project.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

// Utility classes should not have public constructors (java:S1118)
@NoArgsConstructor(access = AccessLevel.NONE)
public class UtilHelper {

    private static final Random random = new Random();

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAddress = "Invalid IP:" + e.getMessage();
        }
        return ipAddress;
    }

    public static String getRandomNumber(int len) {
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String toSlugString(String input) {
        return input.toLowerCase()
                .replaceAll("[áàảạãăắằẳẵặâấầẩẫậäåæą]", "a")
                .replaceAll("[óòỏõọôốồổỗộơớờởỡợöœø]", "o")
                .replaceAll("[éèẻẽẹêếềểễệěėëę]", "e")
                .replaceAll("[úùủũụưứừửữự]", "u")
                .replaceAll("[iíìỉĩịïîį]", "i")
                .replaceAll("[ùúüûǘůűūų]", "u")
                .replaceAll("[ßşśšș]", "s")
                .replaceAll("[źžż]", "z")
                .replaceAll("[ýỳỷỹỵÿ]", "y")
                .replaceAll("[ǹńňñ]", "n")
                .replaceAll("[çćč]", "c")
                .replaceAll("[ğǵ]", "g")
                .replaceAll("[ŕř]", "r")
                .replaceAll("[·/_,:;]", "-")
                .replaceAll("[ťț]", "t")
                // "String#replace" should be preferred to "String#replaceAll" (java:S5361)
                .replace("ḧ", "h")
                .replace("ẍ", "x")
                .replace("ẃ", "w")
                .replace("ḿ", "m")
                .replace("ṕ", "p")
                .replace("ł", "l")
                .replace("đ", "d")
//                .replace("\\s+", "-")
                .replace("&", "-and-")
                .replaceAll("[^\\w\\-]+", "")
                .replaceAll("--+", "-")
                .replaceAll("^-+", "")
                .replaceAll("-+$", "");
    }

    public static <T> Page<T> getPage(List<T> searched, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), searched.size());
        return new PageImpl<>(searched.subList(start, end), pageable, searched.size());
    }
}
