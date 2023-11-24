DROP DATABASE IF EXISTS `HospitalBooking`;

CREATE DATABASE `HospitalBooking`;

USE `HospitalBooking`;


CREATE TABLE `homepage_info`
(
    `homepage_id`      BIGINT AUTO_INCREMENT PRIMARY KEY,
    `logo_url`         VARCHAR(255),
    `hospital_name`    VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `phone_number`     VARCHAR(20),
    `email`            VARCHAR(50),
    `address`          TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `about_us_img`     VARCHAR(255),
    `about_us_title`   TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `about_us_desc`    MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `department_title` TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `department_desc`  MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `doctor_title`     TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `doctor_desc`      MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `created_at`       DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `department`
(
    `department_id`         VARCHAR(5) PRIMARY KEY,
    `department_name`       VARCHAR(50),
    `department_short_desc` TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `department_full_desc`  MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `is_active`             BOOLEAN  DEFAULT TRUE,
    `created_at`            DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at`      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `department_id_unique` UNIQUE (`department_id`)
);

CREATE TABLE `role`
(
    `role_id`          VARCHAR(5) PRIMARY KEY,
    `role_name`        VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `role_desc`        VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `is_active`        BOOLEAN  DEFAULT TRUE,
    `created_at`       DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `role_name_unique` UNIQUE (`role_name`)
);

CREATE TABLE `user`
(
    `user_id`          BIGINT AUTO_INCREMENT PRIMARY KEY,
    `username`         VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci UNIQUE,
    `hashed_password`  VARCHAR(255),
    `email`            VARCHAR(50),
    `contact_number`   VARCHAR(20),
    `department_id`    VARCHAR(5)   NULL,
    `gender`           ENUM ('MALE', 'FEMALE', 'OTHER'),
    `first_name`       VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `last_name`        VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `address`          VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `date_of_birth`    DATE,
    `profile_picture`  VARCHAR(255) NULL,
    `profile_desc`     TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `created_at`       DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_locked`        BOOLEAN  DEFAULT FALSE,
    `role_id`          VARCHAR(5),
    FOREIGN KEY (`role_id`) REFERENCES role (`role_id`) ON DELETE CASCADE,
    FOREIGN KEY (`department_id`) REFERENCES department (`department_id`) ON DELETE CASCADE,
    CONSTRAINT `username_unique` UNIQUE (`username`),
    CONSTRAINT `email_unique` UNIQUE (`email`)
);

CREATE TABLE `token`
(
    `token_id`    BIGINT AUTO_INCREMENT PRIMARY KEY,
    `token_value` VARCHAR(255),
    `token_type`  ENUM ('REFRESH', 'VERIFY'),
    `user_id`     BIGINT,
    `is_revoked`  BOOLEAN,
    FOREIGN KEY (`user_id`) REFERENCES user (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `patient`
(
    `patient_id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `full_name`          VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `date_of_birth`      DATE,
    `gender`             ENUM ('MALE', 'FEMALE', 'OTHER'),
    `email`              VARCHAR(50),
    `national_id_card`   VARCHAR(50),
    `contact_number`     VARCHAR(20),
    `address`            TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `insurance_provider` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `created_at`         DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at`   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `medical_record`
(
    `record_id`        BIGINT AUTO_INCREMENT PRIMARY KEY,
    `patient_id`       BIGINT                                                         NOT NULL,
    `doctor_id`        BIGINT                                                         NOT NULL,
    `record_date`      DATE                                                           NOT NULL,
    `diagnosis`        TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NOT NULL,
    `treatment`        TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NOT NULL,
    `prescription`     TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NOT NULL,
    `document_path`    VARCHAR(255)                                                   NOT NULL,
    `created_at`       DATETIME DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    `last_modified_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE,
    FOREIGN KEY (`doctor_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `doctor_schedule`
(
    `schedule_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `doctor_id`   BIGINT,
    `time_start`  DATETIME,
    `time_end`    DATETIME,
    `created_at`  DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_update` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`doctor_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `doctor_service`
(
    `service_id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `service_name`       VARCHAR(255),
    `service_short_desc` TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `service_full_desc`  MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `service_type`       ENUM ('CONSULTATION', 'TREATMENT'),
    `service_quality`    ENUM ('FREE', 'STANDARD', 'PREMIUM'),
    `doctor_id`          BIGINT,
    `price`              DECIMAL(10, 2),
    `created_at`         DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at`   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`doctor_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `appointment`
(
    `appointment_id`     BIGINT AUTO_INCREMENT PRIMARY KEY,
    `patient_id`         BIGINT,
    `service_id`         BIGINT,
    `reason`             TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `appointment_time`   DATETIME,
    `appointment_status` ENUM ('PENDING', 'CONFIRMED', 'CANCELLED', 'DONE'),
    `created_at`         DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at`   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE,
    FOREIGN KEY (`service_id`) REFERENCES `doctor_service` (`service_id`) ON DELETE CASCADE
);

# CREATE TABLE `order`
# (
#     `order_id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
#     `appointment_id`   BIGINT,
#     `status`           ENUM ('PENDING', 'PAID'),
#     `created_at`       DATETIME DEFAULT CURRENT_TIMESTAMP,
#     `last_modified_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
#     FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`appointment_id`) ON DELETE CASCADE
# );

CREATE TABLE `payment`
(
    `payment_id`       INT AUTO_INCREMENT PRIMARY KEY,
    `appointment_id`   BIGINT,
    `amount`           DECIMAL(10, 2),
    `payment_status`   ENUM ('UNPAID', 'PAID', 'REFUND') DEFAULT 'PAID',
    `created_at`       DATETIME                          DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at` DATETIME                          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (appointment_id) REFERENCES appointment (appointment_id) ON DELETE CASCADE
);

# CREATE TABLE `payment`
# (
#     `payment_id`       INT AUTO_INCREMENT PRIMARY KEY,
#     `booking_id`       BIGINT,
#     `payment_method`   ENUM ('CREDIT_CARD', 'ONLINE', 'CASH'),
#     `amount`           DECIMAL(10, 2),
#     `payment_status`   ENUM ('DEPOSIT', 'PAID', 'REFUND') DEFAULT 'PAID',
#     `created_at`       DATETIME                           DEFAULT CURRENT_TIMESTAMP,
#     `last_modified_at` DATETIME                           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
#     FOREIGN KEY (booking_id) REFERENCES appointment (appointment_id) ON DELETE CASCADE
# );
# 
# CREATE TABLE `refund_request`
# (
#     `refund_id`        BIGINT AUTO_INCREMENT PRIMARY KEY,
#     `patient_id`       BIGINT,
#     `booking_id`       BIGINT,
#     `status`           ENUM ('PENDING', 'APPROVED', 'REJECTED', 'DONE'),
#     `reason`           TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
#     `created_at`       DATETIME DEFAULT CURRENT_TIMESTAMP,
#     `last_modified_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
#     FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE,
#     FOREIGN KEY (`booking_id`) REFERENCES `appointment` (`appointment_id`) ON DELETE CASCADE
# );
# 
CREATE TABLE `feedback`
(
    `review_id`        BIGINT AUTO_INCREMENT PRIMARY KEY,
    `appointment_id`   BIGINT,
    `service_id`       BIGINT,
    `comments`         TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `created_at`       DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`appointment_id`) ON DELETE CASCADE,
    FOREIGN KEY (`service_id`) REFERENCES `doctor_service` (`service_id`) ON DELETE CASCADE
);

CREATE TABLE `category`
(
    `category_id`      BIGINT AUTO_INCREMENT PRIMARY KEY,
    `category_name`    VARCHAR(255),
    `category_desc`    VARCHAR(255) NULL,
    `is_active`        BOOLEAN  DEFAULT TRUE,
    `created_at`       DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `article`
(
    `article_id`       BIGINT AUTO_INCREMENT PRIMARY KEY,
    `author_id`        BIGINT,
    `thumbnail_url`    VARCHAR(255),
    `description`      TEXT,
    `category_id`      BIGINT,
    `title`            TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `view_count`       INT,
    `home_pin`         BOOLEAN                               DEFAULT FALSE,
    `content`          MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `status`           ENUM ('DRAFT', 'PUBLISHED', 'HIDDEN') DEFAULT 'DRAFT',
    `created_at`       DATETIME                              DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at` DATETIME                              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`author_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE
);
CREATE TABLE `comment`
(
    `comment_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `article_id` BIGINT,
    `email`      VARCHAR(50),
    `full_name`  VARCHAR(255),
    `content`    TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE
);
