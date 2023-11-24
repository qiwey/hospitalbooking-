DROP DATABASE IF EXISTS `HospitalBooking`;
CREATE DATABASE IF NOT EXISTS `HospitalBooking` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION = 'N' */;
USE `HospitalBooking`;
-- MySQL dump 10.13  Distrib 8.0.34, for Linux (x86_64)
--
-- Host: localhost    Database: HospitalBooking
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment`
(
    `appointment_id`     bigint NOT NULL AUTO_INCREMENT,
    `patient_id`         bigint                                         DEFAULT NULL,
    `service_id`         bigint                                         DEFAULT NULL,
    `reason`             varchar(255)                                   DEFAULT NULL,
    `appointment_time`   datetime(6)                                    DEFAULT NULL,
    `appointment_status` enum ('CONFIRMED','DONE','PENDING','REJECTED') DEFAULT NULL,
    `created_at`         datetime(6)                                    DEFAULT NULL,
    `last_modified_at`   datetime(6)                                    DEFAULT NULL,
    PRIMARY KEY (`appointment_id`),
    KEY `patient_id` (`patient_id`),
    KEY `service_id` (`service_id`),
    CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE,
    CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`service_id`) REFERENCES `doctor_service` (`service_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 22
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment`
    DISABLE KEYS */;
INSERT INTO `appointment`
VALUES (15, 1, 1, 'ốm', '2023-11-16 00:30:00.000000', 'DONE', '2023-11-15 16:37:42.356291',
        '2023-11-15 16:59:50.449383'),
       (16, 1, 1, 'ốm', '2023-11-17 01:00:00.000000', 'PENDING', '2023-11-16 00:26:54.520971',
        '2023-11-16 00:26:54.520983'),
       (17, 1, 1, 'Đau đầu', '2023-11-16 02:30:00.000000', 'DONE', '2023-11-16 00:41:20.232223',
        '2023-11-16 01:03:09.013157'),
       (18, 1, 1, 'Đau lưng ', '2023-11-17 00:30:00.000000', 'PENDING', '2023-11-16 00:42:49.831776',
        '2023-11-16 00:42:49.831778'),
       (19, 1, 3, 'Mệt', '2023-11-18 02:30:00.000000', 'PENDING', '2023-11-16 00:43:36.481055',
        '2023-11-16 00:43:36.481060'),
       (20, 1, 1, 'Ung thư', '2023-11-17 04:00:00.000000', 'PENDING', '2023-11-16 00:44:30.836962',
        '2023-11-16 00:44:30.836963'),
       (21, 1, 1, 'ốm', '2023-10-16 03:00:00.000000', 'PENDING', '2023-11-16 00:57:15.086909',
        '2023-11-16 00:57:15.086911');
/*!40000 ALTER TABLE `appointment`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article`
(
    `article_id`       bigint NOT NULL AUTO_INCREMENT,
    `author_id`        bigint                              DEFAULT NULL,
    `thumbnail_url`    varchar(255)                        DEFAULT NULL,
    `description`      mediumtext,
    `category_id`      bigint                              DEFAULT NULL,
    `title`            varchar(255)                        DEFAULT NULL,
    `view_count`       int                                 DEFAULT NULL,
    `home_pin`         tinyint(1)                          DEFAULT '0',
    `content`          longtext,
    `status`           enum ('DRAFT','HIDDEN','PUBLISHED') DEFAULT NULL,
    `created_at`       datetime(6)                         DEFAULT NULL,
    `last_modified_at` datetime(6)                         DEFAULT NULL,
    PRIMARY KEY (`article_id`),
    KEY `author_id` (`author_id`),
    KEY `category_id` (`category_id`),
    CONSTRAINT `article_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
    CONSTRAINT `article_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article`
    DISABLE KEYS */;
INSERT INTO `article`
VALUES (6, 1, '57ed08032c6744e3ad1b7325329aa93c-20231106_040558_928936_HOT-PROMOTION_1000X1000.width-800.jpg',
        'Trung tâm Hỗ trợ sinh sản IVF Vimmec Times City dành tặng 20 suất khám miễn phí cho khách hàng nữ thăm khám lần đầu và 20 suất miễn phí xét nghiệm tinh dịch đồ cho khách hàng nam trong tháng 11.2023',
        2, 'Miễn phí khám & xét nghiệm tinh dịch đồ tại IVF Vinmec Times City', 3, NULL,
        '<p>Đặt lịch ngay tại hotline: 0243 9743 556</p><p>Trung tâm Hỗ trợ sinh sản IVF Vinmec đã giúp hàng ngàn gia đình hiếm muộn thành công đón con yêu với những con số ấn tượng:</p><ul><li>Tỷ lệ thành công sau chuyển phôi đạt 60,1%</li><li>Tỉ lệ thai lâm sàng đạt 51.6%</li><li>Tỉ lệ thai diễn tiến đạt 53.1%</li></ul><p>IVF Vinmec - Tự hào là trung tâm hỗ trợ sinh sản đạt tiêu chuẩn quốc tế</p><ul><li>Là trung tâm DUY NHẤT tại Việt Nam đạt đồng thời 3 chứng chỉ khắt khe nhất trên thế giới: RTAC, JCI, CAP;</li><li>Đội ngũ bác sĩ, chuyên gia gồm các chuyên gia trong và ngoài nước luôn sẵn sàng hội chẩn cho các ca bệnh phức tạp;</li><li>Trang thiết bị hiện đại, kỹ thuật tiên tiến, đạt tiêu chuẩn “vàng” về chất lượng, tiên phong trong ứng dụng xét nghiệm Matrice Lab, phát hiện nguyên nhân thất bại nhiều lần của các cặp đôi.</li></ul><p>Thăm khám kịp thời, làm các xét nghiệm, siêu âm cận lâm sàng là điều cần thiết để các bác sĩ đưa ra chẩn đoán chính xác nhất.</p><p>Liên hệ ngay với IVF Vinmec để nhận ưu đãi và được thăm khám chính xác ngay từ hôm nay!</p>',
        'PUBLISHED', '2023-11-15 09:49:42.923392', '2023-11-15 17:39:27.406161'),
       (7, 1,
        '44e1e7914666400284a4a40061789098-Amoxicillin+-+Uses-+Side+Effects-+Composition-+Indications+and+Price.jpg',
        'Dị ứng amoxicillin là một tình trạng xảy ra ở nhiều người cả người lớn và trẻ em. Đây là thuốc kháng sinh phổ rộng thường được điều trị những tình trạng nhiễm trùng. Tuy nhiên, tình trạng dị ứng khi dùng thuốc có thể xảy ra với những dấu hiệu như nổi mẩn, phát ban,... tình trạng nghiêm trọng có thể dẫn tới shock phản vệ.',
        1, 'Tìm hiểu về dị ứng kháng sinh amoxicillin', 1, NULL,
        '<h2>1. Thuốc kháng sinh amoxicillin là thuốc gì?</h2><p>&nbsp;</p><p><a href=\"https://www.vinmec.com/thong-tin-duoc/su-dung-thuoc-toan/cong-dung-thuoc-amoxicillin-250/\"><strong>Thuốc kháng sinh amoxicillin</strong></a> là một loại thuốc <a href=\"https://www.vinmec.com/thong-tin-duoc/su-dung-thuoc-toan/khang-sinh-pho-rong-la-gi/\"><strong>kháng sinh phổ rộng</strong></a> được sử dụng trong điều trị các bệnh nhiễm khuẩn như viêm xoang, viêm tai giữa, viêm đường hô hấp gây ra bởi tụ cầu, liên cầu, phế cầu,... viêm dạ dày ruột, <a href=\"https://www.vinmec.com/vi/benh/viem-duong-tiet-nieu-2998/\"><strong>viêm đường tiết niệu</strong></a>, nhiễm khuẩn da,... Bên cạnh đó, thuốc kháng sinh amoxicillin có thể sử dụng đơn lẻ hoặc kết hợp với những loại thuốc kháng sinh khác để tăng cường khả năng điều trị các bệnh nhiễm khuẩn.</p><p>Trong quá trình sử dụng thuốc kháng sinh amoxicillin sẽ không tránh khỏi các tác dụng không mong muốn như buồn nôn, nôn, tiêu chảy, đau bụng, <a href=\"https://www.vinmec.com/suc-khoe-tong-quat/tu-van-bac-si/dau-dau-chong-mat-sau-nhin-co-sao-khong/\"><strong>đau đầu, chóng mặt</strong></a>, tăng men gan nhẹ,... trong đó có phản ứng dị ứng.</p><h2>2. Dị ứng kháng sinh amoxicillin có biểu hiện gì?</h2><p>&nbsp;</p><p><strong>Dị ứng thuốc kháng sinh amoxicillin</strong> là một trong những tình trạng không phải hiếm gặp, gây nên những thay đổi trên da có thể làm cho trẻ nổi mẩn, ngứa hay còn được gọi là phát ban. Phản ứng có thể xảy ra ngay lập tức trong một đến hai giờ hoặc chậm hơn vài giờ hoặc vài ngày. Thuốc kháng sinh amoxicillin thuộc họ penicillin là một trong những loại kháng sinh dễ gây dị ứng. Biểu hiện của dị ứng thuốc amoxicillin bao gồm:</p><ul><li><a href=\"https://www.vinmec.com/tin-tuc/thong-tin-suc-khoe/suc-khoe-tong-quat/noi-me-day-do-lanh/\"><strong>Nổi mề đay</strong></a>: người bệnh bị nổi mề đay với những dấu hiệu như xuất hiện vết mẩn ngứa ngoài da có màu trắng hoặc đỏ, sần nổi trên bề mặt của da và người bệnh có thể cảm nhận được khi chạm vào. Tình trạng này có thể xuất hiện ngay sau khi dùng từ 1-2 liều thuốc kháng sinh.</li><li>Nổi ban đỏ: tình trạng nổi ban đỏ thường xuất hiện muộn hơn so với nổi mề đay khoảng 3-10 ngày sau khi dùng thuốc kháng sinh amoxicillin. Tuy nhiên, trong một số trường hợp ban đỏ cũng có thể xuất hiện ở bất kỳ thời điểm nào trong thời gian điều trị. Các ban đỏ giống như những mảng phẳng màu đỏ trên da không nổi sần nên trên bề mặt. Những ban này thường nhỏ, nhạt màu hơn và có xen lẫn với những đốm mẩn đỏ, dạng mẩn ngứa thường sẽ tự khỏi sau khi ngừng dùng thuốc amoxicillin.</li></ul><h2>3. Dị ứng thuốc kháng sinh amoxicillin có nguy hiểm không?</h2><p>&nbsp;</p><p>Đa số trường hợp <a href=\"https://vinmecdr.com/huong-dan-thuc-hien-quy-trinh-chuyen-mon-test-da-voi-thuoc-hoac-vaccine/\"><strong>dị ứng thuốc</strong></a><strong> kháng sinh amoxicillin</strong> không quá nguy hiểm và chỉ xuất hiện những triệu chứng nhẹ trên da và đường hô hấp. Tuy nhiên, một số ít trường hợp có thể là shock phản vệ, đe dọa tới tính mạng người bệnh với biểu hiện ban đầu như phù nề vùng mặt, cổ họng, khó thở,... Vì vậy, khi xuất hiện những triệu chứng của dị ứng thuốc kháng sinh thì cần:</p><ul><li>Dừng sử dụng thuốc ngay lập tức;</li><li>Đặt người bệnh nằm ngửa, chân cao, đầu thấp;</li><li>Nghiêng người bệnh nhân sang một bên nếu có dấu hiệu nôn;</li><li>Liên hệ y tế để được hỗ trợ.</li></ul><p>Tóm lại, tình trạng <strong>dị ứng kháng sinh amoxicillin</strong> rất phổ biến đặc biệt là ở trẻ em. Dị ứng xuất hiện với những dấu hiệu như nổi mẩn, phát ban,... tình trạng nghiêm trọng có thể dẫn tới shock phản vệ đe dọa tới tính mạng người bệnh. Vì vậy, khi có những biểu hiện như nổi mẩn, phát ban, sưng, phù nề thì cần liên hệ y tế ngay lập tức để được xử trí kịp thời.</p><p>Theo dõi website <a href=\"https://vinmec.com/vi/danh-sach/ca-nuoc/coso-benh-vien-v-phong-kham/?location=all\"><strong>Bệnh viện Đa khoa Quốc tế Vinmec</strong></a> để nắm thêm nhiều thông tin sức khỏe, dinh dưỡng, làm đẹp để bảo vệ sức khỏe cho bản thân và những người thân yêu trong gia đình.</p>',
        'PUBLISHED', '2023-11-15 09:56:43.923874', '2023-11-15 09:57:57.591758'),
       (8, 1, 'b7510738860e475e90e7117a50f92468-20230831_150327_937881_Banner_Final_Booking-min.jpg',
        'Vinmec là Hệ thống Y tế hàn lâm do Vingroup - Tập đoàn kinh tế tư nhân hàng đầu Việt Nam đầu tư phát triển với sứ mệnh “Chăm sóc bằng tài năng, y đức và sự thấu cảm\". Hiện Vinmec vận hành 7 bệnh viện đa khoa và 5 phòng khám trên toàn quốc.',
        2, 'Thông tin liên hệ của các bệnh viện và phòng khám Vinmec trên toàn quốc', 49, 1,
        '<p>Vinmec là Hệ thống Y tế hàn lâm do Vingroup - Tập đoàn kinh tế tư nhân hàng đầu Việt Nam đầu tư phát triển với sứ mệnh “Chăm sóc bằng tài năng, y đức và sự thấu cảm\". Hiện Vinmec vận hành 7 bệnh viện đa khoa và 5 phòng khám trên toàn quốc.</p><p>Thông tin liên hệ:</p><h4><strong>Công ty Cổ phần Bệnh viện Đa khoa Quốc tế Vinmec</strong></h4><p>Địa chỉ: Số 458, phố Minh Khai, Phường Vĩnh Tuy, Quận Hai Bà Trưng, Hà Nội.</p><p>Hotline: 0243 975 0028</p><p>Email: info@vinmec.com</p><p>&nbsp;</p><p><strong>Hệ thống bệnh viện và phòng khám Vinmec trên toàn quốc</strong></p><p><img src=\"https://vinmec-prod.s3.amazonaws.com/images/20181207_065316_502734_2._He_thong_Benh_vi.max-1800x1800.png\" alt=\"vinmec.com-he-thong-y-te-vinmec\"></p>',
        'PUBLISHED', '2023-11-15 10:00:57.059320', '2023-11-16 01:07:38.321546');
/*!40000 ALTER TABLE `article`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category`
(
    `category_id`      bigint NOT NULL AUTO_INCREMENT,
    `category_name`    varchar(255) DEFAULT NULL,
    `category_desc`    varchar(255) DEFAULT NULL,
    `is_active`        tinyint(1)   DEFAULT '1',
    `created_at`       datetime(6)  DEFAULT NULL,
    `last_modified_at` datetime(6)  DEFAULT NULL,
    PRIMARY KEY (`category_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category`
    DISABLE KEYS */;
INSERT INTO `category`
VALUES (1, 'Bài viết y tế', 'Danh mục chứa các bài viết về y tế và sức khỏe', 1, '2023-10-27 14:37:20.000000',
        '2023-10-27 14:37:20.000000'),
       (2, 'Thông tin bệnh viện', 'Danh mục chứa thông tin về các bệnh viện và cơ sở y tế', 1,
        '2023-10-27 14:37:20.000000', '2023-10-27 14:37:20.000000'),
       (7, 'Sản phẩm y tế', 'Thông tin về sản phẩm và dịch vụ y tế hiện có trên thị trường', 1,
        '2023-10-27 14:37:20.000000', '2023-10-27 14:37:20.000000');
/*!40000 ALTER TABLE `category`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment`
(
    `comment_id` bigint NOT NULL AUTO_INCREMENT,
    `article_id` bigint       DEFAULT NULL,
    `email`      varchar(50)  DEFAULT NULL,
    `full_name`  varchar(255) DEFAULT NULL,
    `content`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `created_at` datetime(6)  DEFAULT NULL,
    PRIMARY KEY (`comment_id`),
    KEY `article_id` (`article_id`),
    CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment`
    DISABLE KEYS */;
INSERT INTO `comment`
VALUES (7, 8, 'haduykhanh.hs@gmail.com', 'Nguyễn Văn A', 'thông tin liên hệ', '2023-11-15 17:38:12.642009'),
       (8, 8, 'haduykhanh.hs@gmail.com', 'Nguyễn Văn A', 'Vingroup - Tập đoàn kinh tế tư nhân hàng đầu Việt Nam',
        '2023-11-15 17:38:41.980964'),
       (9, 8, 'haduykhanh.hs@gmail.com', 'Nguyễn Văn A', 'Chăm sóc bằng tài năng, y đức và sự thấu cảm',
        '2023-11-15 17:49:35.684579'),
       (10, 8, 'haduykhanh.hs@gmail.com', 'Nguyễn Văn A',
        'Số 458, phố Minh Khai, Phường Vĩnh Tuy, Quận Hai Bà Trưng, Hà Nội.', '2023-11-15 17:49:52.898332'),
       (11, 8, 'haduykhanh.hs@gmail.com', 'Nguyễn Văn A', '5 phòng khám trên toàn quốc', '2023-11-15 17:50:06.791233'),
       (12, 8, 'haduykhanh.hs@gmail.com', ' Nguyễn Văn A', 'Vinmec là Hệ thống Y tế hàn lâm',
        '2023-11-15 17:50:22.542846'),
       (13, 8, 'tuanballboo@gmail.com', 'Hoang Tuan Anh', 'Hay quá', '2023-11-16 01:05:01.021272');
/*!40000 ALTER TABLE `comment`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department`
(
    `department_id`         int        NOT NULL AUTO_INCREMENT,
    `department_code`       varchar(5) NOT NULL,
    `department_name`       varchar(50) DEFAULT NULL,
    `department_short_desc` text,
    `department_full_desc`  longtext,
    `is_active`             tinyint(1)  DEFAULT '1',
    `created_at`            datetime(6) DEFAULT NULL,
    `last_modified_at`      datetime(6) DEFAULT NULL,
    PRIMARY KEY (`department_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department`
    DISABLE KEYS */;
INSERT INTO `department`
VALUES (1, 'AL', 'Dị ứng', 'Khoa dị ứng khám chữa các bệnh dị ứng',
        'Khoa Dị ứng khám, xét nghiệm và điều trị các bệnh lý viêm da dị ứng, mày đay, phù Quincke, hen suyễn, viêm mũi dị ứng, sốc phản vệ... Bác sĩ dị ứng sẽ tư vấn cách phòng tránh và điều trị triệu chứng dị ứng hiệu quả.',
        1, '2023-10-27 14:37:20.000000', '2023-10-27 14:37:20.000000'),
       (2, 'DE', 'Răng Hàm Mặt', 'Khoa răng hàm mặt khám chữa bệnh về răng miệng',
        'Khoa Răng Hàm Mặt chuyên khám, chữa trị các bệnh về răng miệng như sâu răng, viêm nướu, loét lợi, nha chu, các bệnh tủy răng, chấn thương răng, hàm mặt và các tổn thương tại khoang miệng.',
        1, '2023-10-27 14:37:20.000000', '2023-10-27 14:37:20.000000'),
       (3, 'IM', 'Nội khoa', 'Khoa nội khám chữa bệnh nội khoa',
        'Khoa Nội khám chữa các bệnh lý về tim mạch, hô hấp, tiêu hóa, thận, nội tiết, huyết học, nhiễm trùng và các bệnh nội khoa khác. Bác sĩ ở khoa Nội sẽ thăm khám, chẩn đoán và điều trị cho bệnh nhân mắc các bệnh về nội khoa.',
        1, '2023-10-27 14:37:20.000000', '2023-10-27 14:37:20.000000'),
       (4, 'NU', 'Nhi khoa', 'Khoa nhi khám chữa bệnh cho trẻ em',
        'Khoa Nhi dành cho trẻ em từ sơ sinh đến 18 tuổi, chuyên khám và điều trị các bệnh lý ở trẻ như sốt, ho, viêm phổi, tiêu chảy, táo bón, dị ứng, bệnh truyền nhiễm, rối loạn dinh dưỡng, bệnh bẩm sinh, chậm phát triển và các bệnh nhi khoa khác.',
        1, '2023-10-27 14:37:20.000000', '2023-10-27 14:37:20.000000'),
       (9, 'SU', 'Ngoại khoa', 'Khoa ngoại khám chữa bệnh ngoại khoa',
        'Khoa Ngoại khám và điều trị các bệnh lý, tổn thương về da liễu, chấn thương chỉnh hình, gãy xương, viêm ruột thừa, sỏi mật, u bướu, ung thư và các bệnh phẫu thuật khác. Bác sĩ khoa Ngoại sẽ thực hiện các thủ thuật phẫu thuật để điều trị cho bệnh nhân.',
        1, '2023-10-27 14:37:20.000000', '2023-10-27 14:37:20.000000');
/*!40000 ALTER TABLE `department`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor_schedule`
--

DROP TABLE IF EXISTS `doctor_schedule`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_schedule`
(
    `schedule_id` bigint    NOT NULL AUTO_INCREMENT,
    `doctor_id`   bigint         DEFAULT NULL,
    `time_start`  datetime(6)    DEFAULT NULL,
    `time_end`    datetime(6)    DEFAULT NULL,
    `created_at`  timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`schedule_id`),
    KEY `doctor_id` (`doctor_id`),
    CONSTRAINT `doctor_schedule_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 282
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_schedule`
--

LOCK TABLES `doctor_schedule` WRITE;
/*!40000 ALTER TABLE `doctor_schedule`
    DISABLE KEYS */;
INSERT INTO `doctor_schedule`
VALUES (90, 12, '2023-11-08 00:30:00.000000', '2023-11-08 02:30:00.000000', '2023-11-03 16:34:58',
        '2023-11-03 16:34:58'),
       (91, 12, '2023-11-09 00:30:00.000000', '2023-11-09 02:30:00.000000', '2023-11-03 16:34:58',
        '2023-11-03 16:34:58'),
       (92, 12, '2023-11-10 00:30:00.000000', '2023-11-10 02:30:00.000000', '2023-11-03 16:34:58',
        '2023-11-03 16:34:58'),
       (93, 12, '2023-11-08 02:30:00.000000', '2023-11-08 05:00:00.000000', '2023-11-03 16:34:58',
        '2023-11-03 16:34:58'),
       (94, 12, '2023-11-09 02:30:00.000000', '2023-11-09 05:00:00.000000', '2023-11-03 16:34:58',
        '2023-11-03 16:34:58'),
       (95, 12, '2023-11-10 02:30:00.000000', '2023-11-10 05:00:00.000000', '2023-11-03 16:34:58',
        '2023-11-03 16:34:58'),
       (96, 12, '2023-11-08 06:00:00.000000', '2023-11-08 08:00:00.000000', '2023-11-03 16:34:58',
        '2023-11-03 16:34:58'),
       (97, 12, '2023-11-09 06:00:00.000000', '2023-11-09 08:00:00.000000', '2023-11-03 16:34:58',
        '2023-11-03 16:34:58'),
       (98, 12, '2023-11-10 06:00:00.000000', '2023-11-10 08:00:00.000000', '2023-11-03 16:34:58',
        '2023-11-03 16:34:58'),
       (99, 12, '2023-11-08 08:00:00.000000', '2023-11-08 10:30:00.000000', '2023-11-03 16:34:58',
        '2023-11-03 16:34:58'),
       (100, 12, '2023-11-09 08:00:00.000000', '2023-11-09 10:30:00.000000', '2023-11-03 16:34:58',
        '2023-11-03 16:34:58'),
       (101, 12, '2023-11-10 08:00:00.000000', '2023-11-10 10:30:00.000000', '2023-11-03 16:34:58',
        '2023-11-03 16:34:58'),
       (130, 2, '2023-11-07 00:30:00.000000', '2023-11-07 02:30:00.000000', '2023-11-09 08:43:13',
        '2023-11-09 08:43:13'),
       (131, 2, '2023-11-08 00:30:00.000000', '2023-11-08 02:30:00.000000', '2023-11-09 08:43:13',
        '2023-11-09 08:43:13'),
       (132, 2, '2023-11-09 00:30:00.000000', '2023-11-09 02:30:00.000000', '2023-11-09 08:43:13',
        '2023-11-09 08:43:13'),
       (133, 2, '2023-11-10 00:30:00.000000', '2023-11-10 02:30:00.000000', '2023-11-09 08:43:13',
        '2023-11-09 08:43:13'),
       (134, 2, '2023-11-07 02:30:00.000000', '2023-11-07 05:00:00.000000', '2023-11-09 08:43:13',
        '2023-11-09 08:43:13'),
       (135, 2, '2023-11-08 02:30:00.000000', '2023-11-08 05:00:00.000000', '2023-11-09 08:43:13',
        '2023-11-09 08:43:13'),
       (136, 2, '2023-11-09 02:30:00.000000', '2023-11-09 05:00:00.000000', '2023-11-09 08:43:13',
        '2023-11-09 08:43:13'),
       (137, 2, '2023-11-07 06:00:00.000000', '2023-11-07 08:00:00.000000', '2023-11-09 08:43:13',
        '2023-11-09 08:43:13'),
       (138, 2, '2023-11-08 06:00:00.000000', '2023-11-08 08:00:00.000000', '2023-11-09 08:43:13',
        '2023-11-09 08:43:13'),
       (139, 2, '2023-11-09 06:00:00.000000', '2023-11-09 08:00:00.000000', '2023-11-09 08:43:13',
        '2023-11-09 08:43:13'),
       (140, 2, '2023-11-07 08:00:00.000000', '2023-11-07 10:30:00.000000', '2023-11-09 08:43:13',
        '2023-11-09 08:43:13'),
       (141, 2, '2023-11-08 08:00:00.000000', '2023-11-08 10:30:00.000000', '2023-11-09 08:43:13',
        '2023-11-09 08:43:13'),
       (142, 2, '2023-11-09 08:00:00.000000', '2023-11-09 10:30:00.000000', '2023-11-09 08:43:13',
        '2023-11-09 08:43:13'),
       (143, 2, '2023-11-10 08:00:00.000000', '2023-11-10 10:30:00.000000', '2023-11-09 08:43:13',
        '2023-11-09 08:43:13'),
       (247, 2, '2023-11-14 00:30:00.000000', '2023-11-14 02:30:00.000000', '2023-11-15 18:36:53',
        '2023-11-15 18:36:53'),
       (248, 2, '2023-11-16 00:30:00.000000', '2023-11-16 02:30:00.000000', '2023-11-15 18:36:53',
        '2023-11-15 18:36:53'),
       (249, 2, '2023-11-17 00:30:00.000000', '2023-11-17 02:30:00.000000', '2023-11-15 18:36:53',
        '2023-11-15 18:36:53'),
       (250, 2, '2023-11-18 00:30:00.000000', '2023-11-18 02:30:00.000000', '2023-11-15 18:36:53',
        '2023-11-15 18:36:53'),
       (251, 2, '2023-11-14 02:30:00.000000', '2023-11-14 05:00:00.000000', '2023-11-15 18:36:53',
        '2023-11-15 18:36:53'),
       (252, 2, '2023-11-16 02:30:00.000000', '2023-11-16 05:00:00.000000', '2023-11-15 18:36:53',
        '2023-11-15 18:36:53'),
       (253, 2, '2023-11-17 02:30:00.000000', '2023-11-17 05:00:00.000000', '2023-11-15 18:36:53',
        '2023-11-15 18:36:53'),
       (254, 2, '2023-11-16 06:00:00.000000', '2023-11-16 08:00:00.000000', '2023-11-15 18:36:53',
        '2023-11-15 18:36:53'),
       (255, 2, '2023-11-17 06:00:00.000000', '2023-11-17 08:00:00.000000', '2023-11-15 18:36:53',
        '2023-11-15 18:36:53'),
       (256, 11, '2023-11-13 00:30:00.000000', '2023-11-13 02:30:00.000000', '2023-11-16 00:33:00',
        '2023-11-16 00:33:00'),
       (257, 11, '2023-11-14 00:30:00.000000', '2023-11-14 02:30:00.000000', '2023-11-16 00:33:00',
        '2023-11-16 00:33:00'),
       (258, 11, '2023-11-15 00:30:00.000000', '2023-11-15 02:30:00.000000', '2023-11-16 00:33:00',
        '2023-11-16 00:33:00'),
       (259, 11, '2023-11-16 00:30:00.000000', '2023-11-16 02:30:00.000000', '2023-11-16 00:33:00',
        '2023-11-16 00:33:00'),
       (260, 11, '2023-11-17 00:30:00.000000', '2023-11-17 02:30:00.000000', '2023-11-16 00:33:00',
        '2023-11-16 00:33:00'),
       (261, 11, '2023-11-18 00:30:00.000000', '2023-11-18 02:30:00.000000', '2023-11-16 00:33:00',
        '2023-11-16 00:33:00'),
       (262, 11, '2023-11-13 02:30:00.000000', '2023-11-13 05:00:00.000000', '2023-11-16 00:33:00',
        '2023-11-16 00:33:00'),
       (263, 11, '2023-11-14 02:30:00.000000', '2023-11-14 05:00:00.000000', '2023-11-16 00:33:00',
        '2023-11-16 00:33:00'),
       (264, 11, '2023-11-15 02:30:00.000000', '2023-11-15 05:00:00.000000', '2023-11-16 00:33:00',
        '2023-11-16 00:33:00'),
       (265, 11, '2023-11-16 02:30:00.000000', '2023-11-16 05:00:00.000000', '2023-11-16 00:33:00',
        '2023-11-16 00:33:00'),
       (266, 11, '2023-11-17 02:30:00.000000', '2023-11-17 05:00:00.000000', '2023-11-16 00:33:00',
        '2023-11-16 00:33:00'),
       (267, 11, '2023-11-18 02:30:00.000000', '2023-11-18 05:00:00.000000', '2023-11-16 00:33:00',
        '2023-11-16 00:33:00'),
       (268, 11, '2023-11-16 06:00:00.000000', '2023-11-16 08:00:00.000000', '2023-11-16 00:33:00',
        '2023-11-16 00:33:00'),
       (269, 11, '2023-11-17 06:00:00.000000', '2023-11-17 08:00:00.000000', '2023-11-16 00:33:00',
        '2023-11-16 00:33:00'),
       (270, 11, '2023-11-18 06:00:00.000000', '2023-11-18 08:00:00.000000', '2023-11-16 00:33:00',
        '2023-11-16 00:33:00'),
       (271, 12, '2023-11-13 00:30:00.000000', '2023-11-13 02:30:00.000000', '2023-11-16 00:38:22',
        '2023-11-16 00:38:22'),
       (272, 12, '2023-11-14 00:30:00.000000', '2023-11-14 02:30:00.000000', '2023-11-16 00:38:22',
        '2023-11-16 00:38:22'),
       (273, 12, '2023-11-15 00:30:00.000000', '2023-11-15 02:30:00.000000', '2023-11-16 00:38:22',
        '2023-11-16 00:38:22'),
       (274, 12, '2023-11-16 00:30:00.000000', '2023-11-16 02:30:00.000000', '2023-11-16 00:38:22',
        '2023-11-16 00:38:22'),
       (275, 12, '2023-11-17 00:30:00.000000', '2023-11-17 02:30:00.000000', '2023-11-16 00:38:22',
        '2023-11-16 00:38:22'),
       (276, 12, '2023-11-18 00:30:00.000000', '2023-11-18 02:30:00.000000', '2023-11-16 00:38:22',
        '2023-11-16 00:38:22'),
       (277, 12, '2023-11-14 02:30:00.000000', '2023-11-14 05:00:00.000000', '2023-11-16 00:38:22',
        '2023-11-16 00:38:22'),
       (278, 12, '2023-11-16 02:30:00.000000', '2023-11-16 05:00:00.000000', '2023-11-16 00:38:22',
        '2023-11-16 00:38:22'),
       (279, 12, '2023-11-17 02:30:00.000000', '2023-11-17 05:00:00.000000', '2023-11-16 00:38:22',
        '2023-11-16 00:38:22'),
       (280, 12, '2023-11-18 02:30:00.000000', '2023-11-18 05:00:00.000000', '2023-11-16 00:38:22',
        '2023-11-16 00:38:22'),
       (281, 12, '2023-11-17 06:00:00.000000', '2023-11-17 08:00:00.000000', '2023-11-16 00:38:22',
        '2023-11-16 00:38:22');
/*!40000 ALTER TABLE `doctor_schedule`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor_service`
--

DROP TABLE IF EXISTS `doctor_service`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_service`
(
    `service_id`         bigint NOT NULL AUTO_INCREMENT,
    `service_name`       varchar(255)                       DEFAULT NULL,
    `service_short_desc` text,
    `service_full_desc`  longtext,
    `service_type`       enum ('CONSULTATION','TREATMENT')  DEFAULT NULL,
    `service_quality`    enum ('FREE','PREMIUM','STANDARD') DEFAULT NULL,
    `doctor_id`          bigint                             DEFAULT NULL,
    `price`              double                             DEFAULT NULL,
    `created_at`         datetime(6)                        DEFAULT NULL,
    `last_modified_at`   datetime(6)                        DEFAULT NULL,
    PRIMARY KEY (`service_id`),
    KEY `doctor_id` (`doctor_id`),
    CONSTRAINT `doctor_service_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_service`
--

LOCK TABLES `doctor_service` WRITE;
/*!40000 ALTER TABLE `doctor_service`
    DISABLE KEYS */;
INSERT INTO `doctor_service`
VALUES (1, 'Dịch vụ khám tổng quát',
        'Gói khám theo yêu cầu tại Trung tâm Khám Sức khỏe định kỳ - Bệnh viện Trung ương Quân đội 108',
        '<p>Trung tâm khám sức khỏe định kỳ - Bệnh viện Trung ương Quân đội 108 là đơn vị đầu ngành thực hiện chức năng khám sức khỏe cho các cá nhân, nhóm, cơ quan. Hiện nay có khá ít bệnh viện công tuyến trung ương triển khai dịch vụ gói khám một cách chuyên nghiệp như Trung tâm khám sức khỏe định kỳ.&nbsp;</p><p>Trung tâm thực hiện các dịch vụ:</p><ul><li>Khám theo yêu cầu với Bác sĩ Chuyên khoa.</li><li>Khám theo các gói khám có sẵn hoặc linh hoạt xây dựng gói khám theo nhu cầu cá nhân.&nbsp;</li></ul><p><strong>Ưu điểm:</strong></p><ul><li>Được thăm khám với các Giáo sư, Phó giáo sư, Tiến sỹ, Bác sĩ chuyên khoa có trình độ cao và đội ngũ điều dưỡng chuyên nghiệp cùng hệ thống trang thiết bị đồng bộ, hiện đại hàng đầu Việt Nam.</li><li>Được khám xong trong ngày, quy trình khép kín, không phải di chuyển nhiều.&nbsp;</li><li>Được tự thiết kế những danh mục trong gói khám theo mong muốn và tư vấn của bác sĩ.</li><li>Khu vực khám riêng tư, lịch sự.&nbsp;</li><li>Được lấy máu xét nghiệm tại khu riêng, giảm thiểu thời gian chờ đợi.</li><li>Tất cả các gói khám tổng quát đều bao gồm suất ăn miễn phí cho khách hàng tại Bệnh viện sau khi lấy mẫu máu xét nghiệm.</li></ul><p><strong>Lưu ý:</strong></p><p>Đặt khám tại Trung tâm khám sức khỏe định kỳ qua BookingCare để được ưu tiên thăm khám, đăng ký trước thông tin tránh chờ đợi, đông đúc.</p>',
        'CONSULTATION', 'STANDARD', 2, 2000000, '2023-10-28 00:25:25.000000', '2023-11-16 00:31:02.481078'),
       (2, 'Khám ngoại Khoa',
        'Phẫu thuật là kỹ thuật mổ xẻ để lấy bỏ đi hoặc sửa chữa lại những cơ quan trong cơ thể bị hư hỏng.',
        '<p><strong>1. Bệnh ngoại khoa là gì ?</strong></p><p>Bệnh xảy ra do sự rối loạn hoạt động hay thay đổi cấu trúc của các cơ quan trong cơ thể. Hầu hết những thay đổi này cần được điều chỉnh lại bằng thuốc men, nhưng một số lại cần phải được sửa chữa, điều chỉnh bằng phẫu thuật – các bệnh này còn được gọi là bệnh ngoại khoa.</p><p>Phẫu thuật là kỹ thuật mổ xẻ để lấy bỏ đi hoặc sửa chữa lại những cơ quan trong cơ thể bị hư hỏng, với mục đích đưa cơ thể hoạt động trở lại bình thường hoặc gần như bình thường. Thực hiện kỹ này là những bác sĩ hiểu rõ về cơ thể con người, quá trình bệnh lý và hơn hết là họ đã được huấn luyện kỹ thuật thao tác cắt - xẻ, may - vá trên những cơ quan của con người.</p><p>Vì thế Ngoại khoa là phương pháp điều trị rất hiệu quả và nhanh chóng cho những bệnh mà thuốc men không chữa được, nhưng cũng là một phương pháp mang nhiều nguy hiểm tiềm ẩn trong quá trình điều trị cho bệnh nhân.</p><p><strong>2. Chuẩn bị gì khi đi mổ ?</strong></p><p>Khi bác sĩ của bạn thông báo cho bạn hay là bệnh cần thiết phải phẫu thuật thì bạn cần phải chuẩn bị như thế nào?</p><p>• Đối với những trường hợp mổ cấp cứu bạn sẽ được chuẩn bị và kiểm tra mọi thứ ngay tại bệnh viện. Bác sĩ sẽ làm xét nghiệm máu để xem các chỉ số hoạt động của gan, thận, các chỉ số về chức năng đông máu và số lượng các tế bào máu. Chụp x-quang tim phổi và điện tâm đồ để xem tim phổi hoạt động bình thường không ? nếu cần sẽ được kiểm tra thêm siêu âm tim. Sau đó Bác sĩ gây mê sẽ khám qua để đánh giá công việc gây mê để phẫu thuật. Việc của bạn phải làm là thông báo cho các bác sĩ biết những bệnh lý gì mà mình mắc phải, có đang dùng những thuốc gì không, dị ứng với thuốc gì không ? và không quên hỏi kỹ bác sĩ phẫu thuật của mình về tất cả những gì liên quan đến cuộc mổ mà bạn muốn biết thêm. Không được ăn uống gì và bình tâm chờ đợi đến khi vào phòng mổ.<br><br>• Đối với những bệnh không cấp cứu, bạn nên ăn nhẹ vào đêm trước mổ, ngưng các thuốc kháng viêm hay chống đông máu trước mổ khoảng 1 tuần. Có thể tiếp tục uống thuốc tim mạch, tiểu đường nhưng ngưng thuốc trong ngày phẫu thuật. Vào ngày mổ bạn nên nhịn ăn uống hoàn toàn, tắm rửa sạch sẽ, đại tiểu tiện trước khi vào phòng mổ. Trong những trường hợp có bệnh tim mạch như cao huyết áp, thiếu máu cơ tim, bệnh phổi hay tiểu đường chưa ổn định, có thể bác sĩ sẽ cho bạn điều trị nội khoa trước khi quyết định phẫu thuật. Một số bệnh đại trực tràng cần phải rửa ruột hay uống thuốc xổ để làm sạch thì bác sĩ sẽ hướng dẫn thực hiện như thế nào cho nhập viện trước vài ngày.</p><p><br>• Một số bệnh phẫu thuật khác như bướu giáp, sỏi túi mật, thoát vị bẹn ,…bác sĩ có thể cho thực hiện các xét nghiệm tầm soát buối sáng và phẫu thuật vào buổi trưa nếu mọi thông số cho phép.</p><p><strong>3. Chăm sóc sau mổ ?</strong></p><p>• Khi hồi tỉnh sau mổ, bác sĩ sẽ dùng thuốc để bạn không thấy đau và không bị ói. Bạn nên ngồi dậy sớm và tập đi lại ngay khi có thể (trong vòng 24 giờ đầu), đi vệ sinh và đi quanh giường bệnh, điều này có thể tránh biến chứng sau mổ.</p><p><br>• Nếu không có căn dặn gì đặc biệt, bạn có thể uống nước hay ăn cháo khi thấy đói, ăn uống trở lại bình thường sau 2-3 ngày, xuất viện sau 1 vài ngày. Đau vết mổ sẽ giảm dần mổi ngày và đau nhiều hơn khi vận động, đây là điều bình thường. Khoảng 7 đến 10 ngày bạn không còn cảm giác đau nữa.</p><p><br>• Thông báo với bác sĩ nếu bạn có một trong những dấu hiệu sau: đau bụng hay đau vết thương liên tục và dữ dội, ói và bụng chướng căng, sốt cao – lạnh run, chảy dịch qua vết mổ…</p>',
        'CONSULTATION', 'FREE', 3, 20000000, '2023-10-29 14:38:04.674329', '2023-11-16 00:34:05.952664'),
       (3, 'Dịch vụ khám Dị ứng',
        'Phẫu thuật là kỹ thuật mổ xẻ để lấy bỏ đi hoặc sửa chữa lại những cơ quan trong cơ thể bị hư hỏng.',
        '<p><strong>1. Bệnh ngoại khoa là gì ?</strong></p><p>Bệnh xảy ra do sự rối loạn hoạt động hay thay đổi cấu trúc của các cơ quan trong cơ thể. Hầu hết những thay đổi này cần được điều chỉnh lại bằng thuốc men, nhưng một số lại cần phải được sửa chữa, điều chỉnh bằng phẫu thuật – các bệnh này còn được gọi là bệnh ngoại khoa.</p><p>Phẫu thuật là kỹ thuật mổ xẻ để lấy bỏ đi hoặc sửa chữa lại những cơ quan trong cơ thể bị hư hỏng, với mục đích đưa cơ thể hoạt động trở lại bình thường hoặc gần như bình thường. Thực hiện kỹ này là những bác sĩ hiểu rõ về cơ thể con người, quá trình bệnh lý và hơn hết là họ đã được huấn luyện kỹ thuật thao tác cắt - xẻ, may - vá trên những cơ quan của con người.</p><p>Vì thế Ngoại khoa là phương pháp điều trị rất hiệu quả và nhanh chóng cho những bệnh mà thuốc men không chữa được, nhưng cũng là một phương pháp mang nhiều nguy hiểm tiềm ẩn trong quá trình điều trị cho bệnh nhân.</p><p><strong>2. Chuẩn bị gì khi đi mổ ?</strong></p><p>Khi bác sĩ của bạn thông báo cho bạn hay là bệnh cần thiết phải phẫu thuật thì bạn cần phải chuẩn bị như thế nào?</p><p>• Đối với những trường hợp mổ cấp cứu bạn sẽ được chuẩn bị và kiểm tra mọi thứ ngay tại bệnh viện. Bác sĩ sẽ làm xét nghiệm máu để xem các chỉ số hoạt động của gan, thận, các chỉ số về chức năng đông máu và số lượng các tế bào máu. Chụp x-quang tim phổi và điện tâm đồ để xem tim phổi hoạt động bình thường không ? nếu cần sẽ được kiểm tra thêm siêu âm tim. Sau đó Bác sĩ gây mê sẽ khám qua để đánh giá công việc gây mê để phẫu thuật. Việc của bạn phải làm là thông báo cho các bác sĩ biết những bệnh lý gì mà mình mắc phải, có đang dùng những thuốc gì không, dị ứng với thuốc gì không ? và không quên hỏi kỹ bác sĩ phẫu thuật của mình về tất cả những gì liên quan đến cuộc mổ mà bạn muốn biết thêm. Không được ăn uống gì và bình tâm chờ đợi đến khi vào phòng mổ.<br><br>• Đối với những bệnh không cấp cứu, bạn nên ăn nhẹ vào đêm trước mổ, ngưng các thuốc kháng viêm hay chống đông máu trước mổ khoảng 1 tuần. Có thể tiếp tục uống thuốc tim mạch, tiểu đường nhưng ngưng thuốc trong ngày phẫu thuật. Vào ngày mổ bạn nên nhịn ăn uống hoàn toàn, tắm rửa sạch sẽ, đại tiểu tiện trước khi vào phòng mổ. Trong những trường hợp có bệnh tim mạch như cao huyết áp, thiếu máu cơ tim, bệnh phổi hay tiểu đường chưa ổn định, có thể bác sĩ sẽ cho bạn điều trị nội khoa trước khi quyết định phẫu thuật. Một số bệnh đại trực tràng cần phải rửa ruột hay uống thuốc xổ để làm sạch thì bác sĩ sẽ hướng dẫn thực hiện như thế nào cho nhập viện trước vài ngày.</p><p><br>• Một số bệnh phẫu thuật khác như bướu giáp, sỏi túi mật, thoát vị bẹn ,…bác sĩ có thể cho thực hiện các xét nghiệm tầm soát buối sáng và phẫu thuật vào buổi trưa nếu mọi thông số cho phép.</p><p><strong>3. Chăm sóc sau mổ ?</strong></p><p>• Khi hồi tỉnh sau mổ, bác sĩ sẽ dùng thuốc để bạn không thấy đau và không bị ói. Bạn nên ngồi dậy sớm và tập đi lại ngay khi có thể (trong vòng 24 giờ đầu), đi vệ sinh và đi quanh giường bệnh, điều này có thể tránh biến chứng sau mổ.</p><p><br>• Nếu không có căn dặn gì đặc biệt, bạn có thể uống nước hay ăn cháo khi thấy đói, ăn uống trở lại bình thường sau 2-3 ngày, xuất viện sau 1 vài ngày. Đau vết mổ sẽ giảm dần mổi ngày và đau nhiều hơn khi vận động, đây là điều bình thường. Khoảng 7 đến 10 ngày bạn không còn cảm giác đau nữa.</p><p><br>• Thông báo với bác sĩ nếu bạn có một trong những dấu hiệu sau: đau bụng hay đau vết thương liên tục và dữ dội, ói và bụng chướng căng, sốt cao – lạnh run, chảy dịch qua vết mổ…</p>',
        'TREATMENT', 'STANDARD', 12, 20000000, '2023-10-31 06:41:18.840764', '2023-11-16 00:29:53.392445'),
       (4, 'Dịch vụ khám răng hàm mặt BS Đông',
        'Bệnh xảy ra do sự rối loạn hoạt động hay thay đổi cấu trúc của các cơ quan trong cơ thể.',
        '<p><strong>1. Bệnh ngoại khoa là gì ?</strong></p><p>Bệnh xảy ra do sự rối loạn hoạt động hay thay đổi cấu trúc của các cơ quan trong cơ thể. Hầu hết những thay đổi này cần được điều chỉnh lại bằng thuốc men, nhưng một số lại cần phải được sửa chữa, điều chỉnh bằng phẫu thuật – các bệnh này còn được gọi là bệnh ngoại khoa.</p><p>Phẫu thuật là kỹ thuật mổ xẻ để lấy bỏ đi hoặc sửa chữa lại những cơ quan trong cơ thể bị hư hỏng, với mục đích đưa cơ thể hoạt động trở lại bình thường hoặc gần như bình thường. Thực hiện kỹ này là những bác sĩ hiểu rõ về cơ thể con người, quá trình bệnh lý và hơn hết là họ đã được huấn luyện kỹ thuật thao tác cắt - xẻ, may - vá trên những cơ quan của con người.</p><p>Vì thế Ngoại khoa là phương pháp điều trị rất hiệu quả và nhanh chóng cho những bệnh mà thuốc men không chữa được, nhưng cũng là một phương pháp mang nhiều nguy hiểm tiềm ẩn trong quá trình điều trị cho bệnh nhân.</p><p><strong>2. Chuẩn bị gì khi đi mổ ?</strong></p><p>Khi bác sĩ của bạn thông báo cho bạn hay là bệnh cần thiết phải phẫu thuật thì bạn cần phải chuẩn bị như thế nào?</p><p>• Đối với những trường hợp mổ cấp cứu bạn sẽ được chuẩn bị và kiểm tra mọi thứ ngay tại bệnh viện. Bác sĩ sẽ làm xét nghiệm máu để xem các chỉ số hoạt động của gan, thận, các chỉ số về chức năng đông máu và số lượng các tế bào máu. Chụp x-quang tim phổi và điện tâm đồ để xem tim phổi hoạt động bình thường không ? nếu cần sẽ được kiểm tra thêm siêu âm tim. Sau đó Bác sĩ gây mê sẽ khám qua để đánh giá công việc gây mê để phẫu thuật. Việc của bạn phải làm là thông báo cho các bác sĩ biết những bệnh lý gì mà mình mắc phải, có đang dùng những thuốc gì không, dị ứng với thuốc gì không ? và không quên hỏi kỹ bác sĩ phẫu thuật của mình về tất cả những gì liên quan đến cuộc mổ mà bạn muốn biết thêm. Không được ăn uống gì và bình tâm chờ đợi đến khi vào phòng mổ.<br><br>• Đối với những bệnh không cấp cứu, bạn nên ăn nhẹ vào đêm trước mổ, ngưng các thuốc kháng viêm hay chống đông máu trước mổ khoảng 1 tuần. Có thể tiếp tục uống thuốc tim mạch, tiểu đường nhưng ngưng thuốc trong ngày phẫu thuật. Vào ngày mổ bạn nên nhịn ăn uống hoàn toàn, tắm rửa sạch sẽ, đại tiểu tiện trước khi vào phòng mổ. Trong những trường hợp có bệnh tim mạch như cao huyết áp, thiếu máu cơ tim, bệnh phổi hay tiểu đường chưa ổn định, có thể bác sĩ sẽ cho bạn điều trị nội khoa trước khi quyết định phẫu thuật. Một số bệnh đại trực tràng cần phải rửa ruột hay uống thuốc xổ để làm sạch thì bác sĩ sẽ hướng dẫn thực hiện như thế nào cho nhập viện trước vài ngày.</p><p><br>• Một số bệnh phẫu thuật khác như bướu giáp, sỏi túi mật, thoát vị bẹn ,…bác sĩ có thể cho thực hiện các xét nghiệm tầm soát buối sáng và phẫu thuật vào buổi trưa nếu mọi thông số cho phép.</p><p><strong>3. Chăm sóc sau mổ ?</strong></p><p>• Khi hồi tỉnh sau mổ, bác sĩ sẽ dùng thuốc để bạn không thấy đau và không bị ói. Bạn nên ngồi dậy sớm và tập đi lại ngay khi có thể (trong vòng 24 giờ đầu), đi vệ sinh và đi quanh giường bệnh, điều này có thể tránh biến chứng sau mổ.</p><p><br>• Nếu không có căn dặn gì đặc biệt, bạn có thể uống nước hay ăn cháo khi thấy đói, ăn uống trở lại bình thường sau 2-3 ngày, xuất viện sau 1 vài ngày. Đau vết mổ sẽ giảm dần mổi ngày và đau nhiều hơn khi vận động, đây là điều bình thường. Khoảng 7 đến 10 ngày bạn không còn cảm giác đau nữa.</p><p><br>• Thông báo với bác sĩ nếu bạn có một trong những dấu hiệu sau: đau bụng hay đau vết thương liên tục và dữ dội, ói và bụng chướng căng, sốt cao – lạnh run, chảy dịch qua vết mổ…</p>',
        'CONSULTATION', 'FREE', 3, 300000, '2023-11-16 00:31:59.359990', '2023-11-16 00:31:59.359991');
/*!40000 ALTER TABLE `doctor_service`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback`
(
    `review_id`        bigint NOT NULL AUTO_INCREMENT,
    `appointment_id`   bigint   DEFAULT NULL,
    `comments`         text,
    `created_at`       datetime DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `service_id`       bigint   DEFAULT NULL,
    PRIMARY KEY (`review_id`),
    KEY `appointment_id` (`appointment_id`),
    CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`appointment_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback`
    DISABLE KEYS */;
INSERT INTO `feedback`
VALUES (3, 15, 'dịch vụ tốt', '2023-11-15 17:00:15', '2023-11-15 17:00:15', NULL),
       (4, 17, 'Dịch vụ tốt', '2023-11-16 01:03:30', '2023-11-16 01:03:30', NULL);
/*!40000 ALTER TABLE `feedback`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `homepage_info`
--

DROP TABLE IF EXISTS `homepage_info`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `homepage_info`
(
    `homepage_id`      bigint NOT NULL AUTO_INCREMENT,
    `logo_url`         varchar(255)                                                  DEFAULT NULL,
    `hospital_name`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `phone_number`     varchar(255)                                                  DEFAULT NULL,
    `email`            varchar(255)                                                  DEFAULT NULL,
    `address`          varchar(255)                                                  DEFAULT NULL,
    `about_us_img`     varchar(255)                                                  DEFAULT NULL,
    `about_us_title`   varchar(255)                                                  DEFAULT NULL,
    `about_us_desc`    mediumtext,
    `department_title` varchar(255)                                                  DEFAULT NULL,
    `department_desc`  mediumtext,
    `doctor_title`     varchar(255)                                                  DEFAULT NULL,
    `doctor_desc`      mediumtext,
    `created_at`       bigint                                                        DEFAULT NULL,
    `last_modified_at` bigint                                                        DEFAULT NULL,
    PRIMARY KEY (`homepage_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `homepage_info`
--

LOCK TABLES `homepage_info` WRITE;
/*!40000 ALTER TABLE `homepage_info`
    DISABLE KEYS */;
INSERT INTO `homepage_info`
VALUES (1, '4845123fd514478a8cb292d9faa19667-pngtree-medical-logo-vector-png-image_6713322.png', 'Vinmec Central Park',
        '1900636699', 'benhvienVinmec@email.com', '208 Nguyễn Hữu Cảnh, Phường 22, Q.Bình Thạnh, Hồ Chí Minh',
        'f81a1fb1b24542c4bc92a9bde38e4c7b-about-us-1.jpg',
        'Vinmec Central Park - Yên tâm về sức khỏe, hài lòng về dịch vụ',
        'Kế thừa uy tín và chất lượng cao của thương hiệu Y tế Vinmec, Bệnh viện Đa khoa Quốc tế Vinmec Central Park là bệnh viện thứ 3 trong hệ thống chính thức đi vào hoạt động. Với tổng nguồn vốn hơn 1.200 tỷ đồng, Vinmec Central Park được xây dựng trong khuôn viên có diện tích mặt bằng rộng 38.643 m2, với 7 tầng nổi, 3 tầng hầm, quy mô 178 giường, 16 chuyên khoa cùng một số trung tâm hỗ trợ chuyên ngành. Toàn bộ hệ thống hoạt động đều được đầu tư các trang thiết bị hiện đại, cao cấp  theo tiêu chuẩn quốc tế.',
        'Phòng khám Đa khoa Quốc tế Vinmec Central Park bao gồm nhiều chuyên khoa',
        'Trung tâm Thẩm mỹ Vinmec-View (Tầng 1). Khoa Chẩn đoán hình ảnh, khoa Nội soi Tiêu hóa và Trung tâm khám Sức khỏe Cao cấp (Tầng 2). Trung tâm khám Sức khỏe Tổng quát (Tầng 3). Trung tâm Vacxin và Trung tâm Sản phụ khoa (Tầng 4). Trung tâm Hỗ trợ Sinh Sản - IVF (Tầng 5).',
        'Đội ngũ bác sĩ',
        'Phòng khám quy tụ đội ngũ bác sĩ và chuyên gia y tế giàu kinh nghiệm của Việt Nam và quốc tế, có trình độ chuyên môn cao, hướng đến mục tiêu trở thành bệnh viện lớn tại Việt Nam cả về chuyên môn và công nghệ, đồng thời đạt tiêu chuẩn quốc tế về quản lý chất lượng và an toàn cho bệnh nhân, đội ngũ thư ký chuyên môn hỗ trợ ngôn ngữ (Tiếng Anh, Tiếng Pháp, Tiếng Nhật, Tiếng Hàn) cho khách hàng trong quá trình khám chữa bệnh.',
        20231027143720, 1700066736361);
/*!40000 ALTER TABLE `homepage_info`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical_record`
--

DROP TABLE IF EXISTS `medical_record`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medical_record`
(
    `record_id`        bigint      NOT NULL AUTO_INCREMENT,
    `patient_id`       bigint       DEFAULT NULL,
    `doctor_id`        bigint       DEFAULT NULL,
    `record_date`      date         DEFAULT NULL,
    `diagnosis`        varchar(255) DEFAULT NULL,
    `treatment`        tinytext,
    `prescription`     varchar(255) DEFAULT NULL,
    `created_at`       datetime(6) NOT NULL,
    `last_modified_at` datetime(6) NOT NULL,
    `document`         varchar(255) DEFAULT NULL,
    PRIMARY KEY (`record_id`),
    KEY `patient_id` (`patient_id`),
    KEY `doctor_id` (`doctor_id`),
    CONSTRAINT `medical_record_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE,
    CONSTRAINT `medical_record_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_record`
--

LOCK TABLES `medical_record` WRITE;
/*!40000 ALTER TABLE `medical_record`
    DISABLE KEYS */;
INSERT INTO `medical_record`
VALUES (1, 1, 2, '2023-01-15', 'Sốt cao và ho khan', 'Dùng Paracetamol', 'Paracetamol 500mg, uống 3 lần/ngày',
        '2021-03-26 00:00:00.000000', '2021-03-26 00:00:00.000000', 'FCNN.pdf; CV-1.pdf'),
       (2, 1, 2, '2023-02-20', 'Đau bên phải hông', 'Thăm khám chuyên gia', 'N/A', '2021-03-26 00:00:00.000000',
        '2021-03-26 00:00:00.000000', 'CV-1.pdf'),
       (3, 1, 3, '2023-03-25', 'Cảm cúm', 'Nghỉ ngơi, uống nhiều nước', 'N/A', '2021-03-26 00:00:00.000000',
        '2021-03-26 00:00:00.000000', 'FCNN.pdf'),
       (4, 2, 3, '2023-04-10', 'Đau rát họng', 'Sử dụng viên sủi họng', 'Viên sủi họng StreDEils',
        '2021-03-26 00:00:00.000000', '2021-03-26 00:00:00.000000', 'FCNN.pdf'),
       (5, 3, 2, '2023-05-05', 'Tiêu chảy', 'Uống nước muối, tránh thực phẩm nhiễm khuẩn',
        'Nước muối 0,9%, uống mỗi 2 tiếng', '2021-03-26 00:00:00.000000', '2021-03-26 00:00:00.000000', 'FCNN.pdf'),
       (7, 1, 2, NULL, 'chưa rõ', 'chưa rõ', 'thuốc cảm', '2023-11-15 17:31:22.227812', '2023-11-15 17:31:22.227822',
        NULL),
       (8, 1, 2, NULL, 'chưa rõ', 'chưa rõ', 'thuốc cảm', '2023-11-15 23:29:10.458413', '2023-11-15 23:29:10.458425',
        'org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@289af62c'),
       (9, 1, 2, NULL, 'đau đầu', 'ngủ', 'thuốc cảm', '2023-11-15 23:29:50.451523', '2023-11-15 23:29:50.451532',
        'org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@2aaeeb76');
/*!40000 ALTER TABLE `medical_record`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient`
(
    `patient_id`         bigint                         NOT NULL AUTO_INCREMENT,
    `full_name`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `date_of_birth`      date                                                          DEFAULT NULL,
    `gender`             enum ('FEMALE','MALE','OTHER') NOT NULL,
    `email`              varchar(50)                                                   DEFAULT NULL,
    `national_id_card`   varchar(255)                   NOT NULL,
    `contact_number`     varchar(20)                                                   DEFAULT NULL,
    `address`            varchar(255)                   NOT NULL,
    `insurance_provider` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `created_at`         datetime(6)                    NOT NULL,
    `last_modified_at`   datetime(6)                    NOT NULL,
    `verification_code`  varchar(6)                                                    DEFAULT NULL,
    PRIMARY KEY (`patient_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 33
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient`
    DISABLE KEYS */;
INSERT INTO `patient`
VALUES (1, 'Trần Văn An', '1992-03-15', 'MALE', 'ninggiangboy@gmail.com', '1234567890', '0368260323', 'Hà Nội',
        'HA_NOI', '2023-10-27 14:37:21.000000', '2023-11-16 00:27:18.576898', NULL),
       (2, 'Nguyễn Thị Cẩm', '1985-07-20', 'FEMALE', 'nguyencam@email.com', '9876543210', '0987654321', 'Hồ Chí Minh',
        'HO_CHI_MINH', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (3, 'Lê Thanh Dương', '1990-12-10', 'FEMALE', 'leduong@email.com', '4567891230', '0369852147', 'Cao Bằng',
        'CAO_BANG', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (4, 'Võ Văn Hải', '1987-05-25', 'MALE', 'vohai@email.com', '7896541230', '0246897531', 'Hải Phòng', 'HAI_PHONG',
        '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (5, 'Phạm Thị Hương', '1994-08-05', 'FEMALE', 'phamhuong@email.com', '1597534680', '0765432189', 'Đà Nẵng',
        'DA_NANG', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (6, 'Hoàng Văn Đức', '1998-02-28', 'MALE', 'hoangduc@email.com', '1234567891', '0123456781', 'Bắc Ninh',
        'BAC_NINH', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (7, 'Mai Thị Trang', '1991-04-10', 'FEMALE', 'maitrang@email.com', '9876543211', '0987654321', 'Nghệ An',
        'NGHE_AN', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (8, 'Nguyễn Văn Tú', '1986-09-15', 'MALE', 'nguyONu@email.com', '4567891231', '0369852141', 'Thanh Hóa',
        'THANH_HOA', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (9, 'Lê Thị Hoa', '1993-11-25', 'FEMALE', 'lehoa@email.com', '7896541231', '0246897531', 'Quảng Ninh',
        'QUANG_NINH', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (10, 'Trần Văn Hoàng', '1997-01-05', 'MALE', 'tranhoang@email.com', '1597534681', '0765432181', 'Ninh Bình',
        'NINH_BINH', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (11, 'Phạm Thị Lan', '1990-06-20', 'FEMALE', 'phamlan@email.com', '1234567892', '0123456782', 'Vĩnh Phúc',
        'VINH_PHUC', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (12, 'Đỗ Văn Nam', '1985-03-10', 'MALE', 'donam@email.com', '9876543212', '0987654322', 'Bắc Giang', 'BAC_GIANG',
        '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (13, 'Lý Thị Ánh', '1996-02-05', 'FEMALE', 'lyanh@email.com', '4567891232', '0369852142', 'Ninh Thuận',
        'NINH_THUAN', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (14, 'Nguyễn Thanh Thảo', '1991-12-15', 'FEMALE', 'nguyONthao@email.com', '7896541232', '0246897532', 'Gia Lai',
        'GIA_LAI', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (15, 'Hà Văn Đông', '1994-10-25', 'MALE', 'hadong@email.com', '1597534682', '0765432182', 'Bình Định',
        'BINH_DINH', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (16, 'Trịnh Văn Long', '1998-05-05', 'MALE', 'trinhlong@email.com', '1234567893', '0123456783', 'Hà Tĩnh',
        'HA_TINH', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (17, 'Vũ Thị Hòa', '1990-07-20', 'FEMALE', 'vuhoa@email.com', '9876543213', '0987654323', 'Hòa Bình', 'HOA_BINH',
        '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (18, 'Nguyễn Văn Hùng', '1986-09-10', 'MALE', 'nguyenhung@email.com', '4567891233', '0369852143', 'Quảng Bình',
        'QUANG_BINH', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (19, 'Phan Thị Ngọc', '1993-04-25', 'FEMALE', 'phanngoc@email.com', '7896541233', '0246897533', 'Quảng Trị',
        'QUANG_TRI', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (20, 'Bùi Thị Linh', '1997-08-05', 'FEMALE', 'builinh@email.com', '1597534683', '0765432183', 'Thừa Thiên-Huế',
        'THUA_THIEN_HUE', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (21, 'Lương Văn Tuấn', '1990-01-10', 'MALE', 'luongtuan@email.com', '1234567894', '0123456784', 'Quảng Nam',
        'QUANG_NAM', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (22, 'Ngô Thị Thảo', '1985-03-15', 'FEMALE', 'ngotheo@email.com', '9876543214', '0987654324', 'Kon Tum',
        'KON_TUM', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (23, 'Trần Văn Dũng', '1996-02-20', 'MALE', 'trandung@email.com', '4567891234', '0369852144', 'Quảng Ngãi',
        'QUANG_NGAI', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (24, 'Lê Thị Hoài', '1991-12-10', 'FEMALE', 'lehoai@email.com', '7896541234', '0246897534', 'Bình Định',
        'BINH_DINH', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (25, 'Võ Văn Khôi', '1994-10-25', 'MALE', 'vokhoi@email.com', '1597534684', '0765432184', 'Phú Yên', 'PHU_YEN',
        '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (26, 'Phạm Thị Thu', '1998-05-05', 'FEMALE', 'phamthu@email.com', '1234567895', '0123456785', 'Khánh Hòa',
        'KHANH_HOA', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (27, 'Nguyễn Văn Hòa', '1990-07-20', 'MALE', 'nguyenhoa@email.com', '9876543215', '0987654325', 'Lâm Đồng',
        'LAM_DONG', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (28, 'Lê Thị Hoàng', '1986-09-10', 'FEMALE', 'lehoang@email.com', '4567891235', '0369852145', 'Bà Rịa-Vũng Tàu',
        'BA_RIA_VUNG_TAU', '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (29, 'Trần Văn Quý', '1993-04-25', 'MALE', 'tranquy@email.com', '7896541235', '0246897535', 'Long An', 'LONG_AN',
        '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL),
       (30, 'Vũ Văn Sơn', '1997-08-05', 'MALE', 'vuson@email.com', '1597534685', '0765432185', 'Tây Ninh', 'TAY_NINH',
        '2023-10-27 14:37:21.000000', '2023-10-27 14:37:21.000000', NULL);
/*!40000 ALTER TABLE `patient`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment`
(
    `payment_id`       bigint NOT NULL AUTO_INCREMENT,
    `appointment_id`   bigint                            DEFAULT NULL,
    `amount`           double                            DEFAULT NULL,
    `payment_status`   enum ('PAID','REFUNDED','UNPAID') DEFAULT NULL,
    `created_at`       datetime(6)                       DEFAULT NULL,
    `last_modified_at` datetime(6)                       DEFAULT NULL,
    PRIMARY KEY (`payment_id`),
    KEY `appointment_id` (`appointment_id`),
    CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`appointment_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment`
    DISABLE KEYS */;
INSERT INTO `payment`
VALUES (4, 15, 200000, 'PAID', '2023-11-15 16:37:42.371364', '2023-11-15 16:43:36.880743'),
       (5, 16, 200000, 'UNPAID', '2023-11-16 00:26:54.538073', '2023-11-16 00:26:54.538085'),
       (6, 17, 300000, 'UNPAID', '2023-11-16 00:41:20.233471', '2023-11-16 00:41:20.233474'),
       (7, 18, 300000, 'UNPAID', '2023-11-16 00:42:49.833132', '2023-11-16 00:42:49.833134'),
       (8, 19, 300000, 'UNPAID', '2023-11-16 00:43:36.482135', '2023-11-16 00:43:36.482136'),
       (9, 20, 200000, 'UNPAID', '2023-11-16 00:44:30.837775', '2023-11-16 00:44:30.837776'),
       (10, 21, 2000000, 'UNPAID', '2023-10-16 00:57:15.088104', '2023-11-16 00:57:15.088106');
/*!40000 ALTER TABLE `payment`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role`
(
    `role_id`          varchar(5) NOT NULL,
    `role_name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  DEFAULT NULL,
    `role_desc`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `is_active`        tinyint(1)                                                    DEFAULT '1',
    `created_at`       datetime                                                      DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at` datetime                                                      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`role_id`),
    UNIQUE KEY `role_name_unique` (`role_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role`
    DISABLE KEYS */;
INSERT INTO `role`
VALUES ('ADMIN', 'Quản trị viên', 'Vai trò quản trị với quyền truy cập đầy đủ.', 1, '2023-10-27 14:37:20',
        '2023-10-27 14:37:20'),
       ('ASSIS', 'Trợ lí', 'Vai trò trợ lý với quyền truy cập vào lịch hẹn.', 1, '2023-10-27 14:37:20',
        '2023-10-27 14:37:20'),
       ('DOC', 'Bác sĩ', 'Vai trò bác sĩ với quyền truy cập vào hồ sơ bệnh án.', 1, '2023-10-27 14:37:20',
        '2023-10-27 14:37:20'),
       ('MEDIA', 'Truyền thông', 'Vai trò lễ tân với quyền truy cập lên lịch hẹn.', 1, '2023-10-27 14:37:20',
        '2023-10-27 14:37:20'),
       ('NURSE', 'Y tá', 'Vai trò y tá với quyền truy cập vào dữ liệu chăm sóc bệnh nhân.', 1, '2023-10-27 14:37:20',
        '2023-10-27 14:37:20'),
       ('RECEP', 'Tiếp tân', 'Vai trò lễ tân với quyền truy cập lên lịch hẹn.', 1, '2023-10-27 14:37:20',
        '2023-10-27 14:37:20');
/*!40000 ALTER TABLE `role`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token`
(
    `token_id`    bigint NOT NULL AUTO_INCREMENT,
    `token_value` varchar(255)                       DEFAULT NULL,
    `token_type`  enum ('BEARER','REFRESH','VERIFY') DEFAULT NULL,
    `user_id`     bigint                             DEFAULT NULL,
    `is_revoked`  tinyint(1)                         DEFAULT NULL,
    PRIMARY KEY (`token_id`),
    KEY `user_id` (`user_id`),
    CONSTRAINT `token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `token`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user`
(
    `user_id`          bigint NOT NULL AUTO_INCREMENT,
    `username`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  DEFAULT NULL,
    `hashed_password`  varchar(255)                                                  DEFAULT NULL,
    `email`            varchar(50)                                                   DEFAULT NULL,
    `contact_number`   varchar(20)                                                   DEFAULT NULL,
    `department_id`    int                                                           DEFAULT NULL,
    `gender`           enum ('FEMALE','MALE','OTHER')                                DEFAULT NULL,
    `first_name`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  DEFAULT NULL,
    `last_name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  DEFAULT NULL,
    `address`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `date_of_birth`    date                                                          DEFAULT NULL,
    `profile_picture`  varchar(255)                                                  DEFAULT NULL,
    `created_at`       datetime                                                      DEFAULT CURRENT_TIMESTAMP,
    `last_modified_at` datetime                                                      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_locked`        tinyint(1)                                                    DEFAULT '0',
    `role_id`          varchar(5)                                                    DEFAULT NULL,
    `profile_desc`     varchar(255)                                                  DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `username` (`username`),
    UNIQUE KEY `username_unique` (`username`),
    UNIQUE KEY `email_unique` (`email`),
    KEY `role_id` (`role_id`),
    KEY `department_id` (`department_id`),
    CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE,
    CONSTRAINT `user_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user`
    DISABLE KEYS */;
INSERT INTO `user`
VALUES (1, 'duykhanh', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK', 'ninggiangboy@gmail.com',
        '0368260323', NULL, 'MALE', 'Khanh', 'Ha', 'Hải Dương', '2003-03-26', NULL, '2023-10-27 14:37:20',
        '2023-10-27 14:37:20', 0, 'ADMIN', NULL),
       (2, 'doctor1', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK', 'doctor1@gmail.com', '0368260324',
        1, 'MALE', 'Nam', 'Đỗ Văn', 'Hải Dương', '2003-03-26',
        '9e1a94bfcc3d4bedb89377ff9238fca8-dr-vo-trieu-dat-2020.jpg', '2023-10-27 14:37:20', '2023-11-15 10:06:46', 0,
        'DOC', 'Nguyên Trưởng khoa lâm sàng, Bệnh tâm thần Thành phố Hồ Chí Minh'),
       (3, 'doctor2', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK', 'doctor2@gmail.com', '0368260324',
        2, 'MALE', 'Đông', 'Hà Văn ', 'Hải Dương', '2003-03-26', 'c56fa356a66f44ba82a884da56c5e101-bg-block-bacsi.png',
        '2023-10-27 14:37:20', '2023-11-15 10:06:46', 0, 'DOC',
        'Chuyên gia trên 35 năm kinh nghiệm trong lĩnh vực bệnh lý Tiêu hóa'),
       (4, 'doctor3', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK', 'doctor3@gmail.com', '0368260324',
        3, 'MALE', 'Long', 'Trịnh Văn', 'Hải Dương', '2003-03-26',
        'feb1d119366b45ea9b3b41ebef0bcf92-BS-Vo-Van-Dinh-1-scaled.jpg', '2023-10-27 14:37:20', '2023-11-15 10:06:46', 0,
        'DOC', 'Nguyên Trưởng khoa Tai mũi họng trẻ em, Bệnh viện Tai Mũi Họng Trung ương'),
       (5, 'nurse', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK', 'nurse@gmail.com', '0368260324', 1,
        'FEMALE', 'Khanh', 'Ha', 'Hải Dương', '2003-03-26', NULL, '2023-10-27 14:37:20', '2023-10-27 14:37:20', 0,
        'NURSE', NULL),
       (6, 'assistant1', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK', 'assistant1@gmail.com',
        '0368260324', 1, 'MALE', 'Khanh', 'Ha', 'Hải Dương', '2003-03-26', NULL, '2023-10-27 14:37:20',
        '2023-10-27 14:37:20', 0, 'ASSIS', NULL),
       (7, 'assistant2', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK', 'assistant2@gmail.com',
        '0368260324', 2, 'MALE', 'Khanh', 'Ha', 'Hải Dương', '2003-03-26', NULL, '2023-10-27 14:37:20',
        '2023-10-27 14:37:20', 0, 'ASSIS', NULL),
       (8, 'assistant3', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK', 'assistant3@gmail.com',
        '0368260324', 3, 'MALE', 'Khanh', 'Ha', 'Hải Dương', '2003-03-26', NULL, '2023-10-27 14:37:20',
        '2023-10-27 14:37:20', 0, 'ASSIS', NULL),
       (9, 'reception', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK', 'reception@gmail.com',
        '0368260324', NULL, 'MALE', 'Khanh', 'Ha', 'Hải Dương', '2003-03-26', NULL, '2023-10-27 14:37:20',
        '2023-10-27 14:37:20', 0, 'RECEP', NULL),
       (10, 'media', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK', 'media@gmail.com', '0368260324',
        NULL, 'MALE', 'Khanh', 'Ha', 'Hải Dương', '2003-03-26', NULL, '2023-10-27 14:40:39', '2023-10-27 14:40:39', 0,
        'MEDIA', NULL),
       (11, 'khanh5926', '$2a$10$4E0IYDXLMsUi7UFbV1vFKeNMS6k30xl0.9TksmTvoKU3g80PDYoRi', 'khanhhdhe170607@fpt.edu.vn',
        '900808908908', 3, 'MALE', 'Hùng', 'Nguyễn Văn', 'Thành phố Hồ Chí Minh', '1988-10-31',
        '9d842624a94e4b6489621f441a15cfda-BS_Nguyen_Thanh_Nam.jpg', '2023-10-30 19:39:36', '2023-11-16 00:59:49', 0,
        'DOC', 'Nguyên Trưởng khoa lâm sàng, Bệnh tâm thần Thành phố Hồ Chí Minh'),
       (12, 'giang0664', '$2a$10$I/HD2Rkgn6Dbzyczd4/iDupkv7G9gv4qNV6UTR5f4.0dYyXjkE6oG', 'ashdasd@fmail.com',
        '02255555555', 3, 'FEMALE', 'Linh', 'Bùi Thị', 'hai duong', '1995-10-31',
        '48108e2f52354d128b1f7cb466475b37-photo-1-16417925518511739175705.jpg', '2023-10-31 06:37:46',
        '2023-11-15 10:19:07', 0, 'DOC', 'Nguyên Trưởng khoa Tai mũi họng trẻ em, Bệnh viện Tai Mũi Họng Trung ương');
/*!40000 ALTER TABLE `user`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2023-11-16  8:15:09
