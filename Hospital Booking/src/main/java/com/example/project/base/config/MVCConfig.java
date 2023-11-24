package com.example.project.base.config;

import com.example.project.base.constant.Gender;
import com.example.project.base.constant.ServiceQuality;
import com.example.project.base.constant.ServiceType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

    private final Converter<String, Gender> stringToGenderConverter = new Converter<String, Gender>() {
        @Override
        public Gender convert(String source) {
            if (source.equalsIgnoreCase("MALE")) {
                return Gender.MALE;
            } else if (source.equalsIgnoreCase("FEMALE")) {
                return Gender.FEMALE;
            } else if (source.equalsIgnoreCase("OTHER")) {
                return Gender.OTHER;
            } else {
                throw new IllegalArgumentException("Invalid gender value: " + source);
            }
        }
    };
    private final Converter<String, ServiceType> stringToServiceTypeConverter = new Converter<String, ServiceType>() {
        @Override
        public ServiceType convert(String source) {
            if (source.equalsIgnoreCase("CONSULTATION")) {
                return ServiceType.CONSULTATION;
            } else if (source.equalsIgnoreCase("TREATMENT")) {
                return ServiceType.TREATMENT;
            } else {
                throw new IllegalArgumentException("Invalid gender value: " + source);
            }
        }
    };
    private final Converter<String, ServiceQuality> stringToServiceQualityConverter = new Converter<String, ServiceQuality>() {
        @Override
        public ServiceQuality convert(String source) {
            if (source.equalsIgnoreCase("PREMIUM")) {
                return ServiceQuality.PREMIUM;
            } else if (source.equalsIgnoreCase("STANDARD")) {
                return ServiceQuality.STANDARD;
            } else if (source.equalsIgnoreCase("FREE")) {
                return ServiceQuality.FREE;
            } else {
                throw new IllegalArgumentException("Invalid gender value: " + source);
            }
        }
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(new Locale("vi", "VN"));
        return resolver;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToGenderConverter);
        registry.addConverter(stringToServiceTypeConverter);
        registry.addConverter(stringToServiceQualityConverter);
//        registry.addConverter(stringNewsStatusConverter);
    }

//    private final Converter<String, News.NewsStatus> stringNewsStatusConverter = new Converter<String, News.NewsStatus>() {
//        @Override
//        public News.NewsStatus convert(String source) {
//            if (source.equalsIgnoreCase("DRAFT")) {
//                return News.NewsStatus.DRAFT;
//            } else if (source.equalsIgnoreCase("PUBLISHED")) {
//                return News.NewsStatus.PUBLISHED;
//            } else if (source.equalsIgnoreCase("HIDDEN")) {
//                return News.NewsStatus.HIDDEN;
//            } else {
//                throw new IllegalArgumentException("Invalid gender value: " + source);
//            }
//        }
//    };
}
