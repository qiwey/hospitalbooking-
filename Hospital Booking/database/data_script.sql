USE `HospitalBooking`;

INSERT INTO `homepage_info` (homepage_id, logo_url, hospital_name, phone_number, email, address, about_us_img,
                             about_us_title, about_us_desc, department_title, department_desc, doctor_title,
                             `doctor_desc`)
    VALUE (1, NULL, 'VINMEC CENTRAL PARK', '1900 636 699', 'sample@email.com',
           '208 Nguyễn Hữu Cảnh, Phường 22, Q.Bình Thạnh, Hồ Chí Minh',
           '07_03_2020_03_49_30_910711.png',
           'VINMEC CENTRAL PARK - YÊN TÂM VỀ SỨC KHỎE, HÀI LÒNG VỀ DỊCH VỤ',
           'Kế thừa uy tín và chất lượng cao của thương hiệu Y tế Vinmec, Bệnh viện Đa khoa Quốc tế Vinmec Central Park là bệnh viện thứ 3 trong hệ thống chính thức đi vào hoạt động. Với tổng nguồn vốn hơn 1.200 tỷ đồng, Vinmec Central Park được xây dựng trong khuôn viên có diện tích mặt bằng rộng 38.643 m2, với 7 tầng nổi, 3 tầng hầm, quy mô 178 giường, 16 chuyên khoa cùng một số trung tâm hỗ trợ chuyên ngành. Toàn bộ hệ thống hoạt động đều được đầu tư các trang thiết bị hiện đại, cao cấp  theo tiêu chuẩn quốc tế.',
           'Phòng khám Đa khoa Quốc tế Vinmec Times City bao gồm 7 chuyên khoa',
           'Trung tâm Thẩm mỹ Vinmec-View (Tầng 1). Khoa Chẩn đoán hình ảnh, khoa Nội soi Tiêu hóa và Trung tâm khám Sức khỏe Cao cấp (Tầng 2). Trung tâm khám Sức khỏe Tổng quát (Tầng 3). Trung tâm Vacxin và Trung tâm Sản phụ khoa (Tầng 4). Trung tâm Hỗ trợ Sinh Sản - IVF (Tầng 5).',
           'Đội ngũ bác sĩ',
           'Phòng khám quy tụ đội ngũ bác sĩ và chuyên gia y tế giàu kinh nghiệm của Việt Nam và quốc tế, có trình độ chuyên môn cao, hướng đến mục tiêu trở thành bệnh viện lớn tại Việt Nam cả về chuyên môn và công nghệ, đồng thời đạt tiêu chuẩn quốc tế về quản lý chất lượng và an toàn cho bệnh nhân, đội ngũ thư ký chuyên môn hỗ trợ ngôn ngữ (Tiếng Anh, Tiếng Pháp, Tiếng Nhật, Tiếng Hàn) cho khách hàng trong quá trình khám chữa bệnh.');

INSERT INTO department (department_id, department_name, department_short_desc, is_active, department_full_desc)
VALUES ('IM', 'Nội khoa', 'Khoa nội khám chữa bệnh nội khoa', 1,
        'Khoa Nội khám chữa các bệnh lý về tim mạch, hô hấp, tiêu hóa, thận, nội tiết, huyết học, nhiễm trùng và các bệnh nội khoa khác. Bác sĩ ở khoa Nội sẽ thăm khám, chẩn đoán và điều trị cho bệnh nhân mắc các bệnh về nội khoa.'),
       ('SU', 'Ngoại khoa', 'Khoa ngoại khám chữa bệnh ngoại khoa', 1,
        'Khoa Ngoại khám và điều trị các bệnh lý, tổn thương về da liễu, chấn thương chỉnh hình, gãy xương, viêm ruột thừa, sỏi mật, u bướu, ung thư và các bệnh phẫu thuật khác. Bác sĩ khoa Ngoại sẽ thực hiện các thủ thuật phẫu thuật để điều trị cho bệnh nhân.'),
       ('NU', 'Nhi khoa', 'Khoa nhi khám chữa bệnh cho trẻ em', 1,
        'Khoa Nhi dành cho trẻ em từ sơ sinh đến 18 tuổi, chuyên khám và điều trị các bệnh lý ở trẻ như sốt, ho, viêm phổi, tiêu chảy, táo bón, dị ứng, bệnh truyền nhiễm, rối loạn dinh dưỡng, bệnh bẩm sinh, chậm phát triển và các bệnh nhi khoa khác.'),
       ('OB', 'Sản khoa', 'Khoa sản phụ khoa khám chữa bệnh cho phụ nữ mang thai', 1,
        'Khoa Sản phụ khoa chăm sóc sức khỏe sinh sản cho phụ nữ, từ khám thai, chăm sóc thai kỳ, siêu âm thai, khám phụ khoa, điều trị vô sinh, đến hỗ trợ sinh nở, chăm sóc sơ sinh và hậu sản.'),
       ('OP', 'Nhãn khoa', 'Khoa nhãn khám chữa bệnh về mắt', 1,
        'Khoa Nhãn khám, chẩn đoán và điều trị các bệnh lý về mắt như cận thị, viêm kết mạc, glôcôm, thoái hóa điểm vàng, lão thị, mờ đục thủy tinh thể và các bệnh về mắt khác. Các bác sĩ nhãn khoa sẽ tiến hành thủ thuật phẫu thuật mắt khi cần thiết.'),
       ('DE', 'Răng Hàm Mặt', 'Khoa răng hàm mặt khám chữa bệnh về răng miệng', 1,
        'Khoa Răng Hàm Mặt chuyên khám, chữa trị các bệnh về răng miệng như sâu răng, viêm nướu, loét lợi, nha chu, các bệnh tủy răng, chấn thương răng, hàm mặt và các tổn thương tại khoang miệng.'),
       ('PS', 'Tâm thần', 'Khoa tâm thần khám chữa bệnh về tâm thần', 1,
        'Khoa Tâm thần chuyên khám, chẩn đoán và điều trị các rối loạn về tâm thần như trầm cảm, lo âu, rối loạn tâm trí, mất ngủ, căng thẳng thần kinh, rối loạn hoang tưởng... Bác sĩ tâm thần sẽ đưa ra phác đồ điều trị phù hợp cho từng bệnh nhân.'),
       ('ON', 'Ung bướu', 'Khoa ung bướu khám chữa bệnh ung thư', 1,
        'Khoa Ung bướu chuyên khám, tầm soát, phát hiện sớm, điều trị các bệnh lý ung thư. Bác sĩ ung bướu sẽ xác định giai đoạn bệnh và đưa ra phương pháp điều trị phù hợp nhất cho bệnh nhân như hóa trị, xạ trị, phẫu thuật, miễn dịch trị liệu...'),
       ('AL', 'Dị ứng', 'Khoa dị ứng khám chữa các bệnh dị ứng', 1,
        'Khoa Dị ứng khám, xét nghiệm và điều trị các bệnh lý viêm da dị ứng, mày đay, phù Quincke, hen suyễn, viêm mũi dị ứng, sốc phản vệ... Bác sĩ dị ứng sẽ tư vấn cách phòng tránh và điều trị triệu chứng dị ứng hiệu quả.');

INSERT INTO `role` (role_id, role_name, role_desc)
VALUES ('ADMIN', 'Quản trị viên', 'Vai trò quản trị với quyền truy cập đầy đủ.'),
       ('DOC', 'Bác sĩ', 'Vai trò bác sĩ với quyền truy cập vào hồ sơ bệnh án.'),
       ('NURSE', 'Y tá', 'Vai trò y tá với quyền truy cập vào dữ liệu chăm sóc bệnh nhân.'),
       ('ASSIS', 'Trợ lí', 'Vai trò trợ lý với quyền truy cập vào lịch hẹn.'),
       ('RECEP', 'Tiếp tân', 'Vai trò lễ tân với quyền truy cập lên lịch hẹn.'),
       ('MEDIA', 'Truyền thông', 'Vai trò lễ tân với quyền truy cập lên lịch hẹn.');

INSERT INTO `category` (category_id, category_name, category_desc, is_active)
VALUES (1, 'Bài viết y tế', 'Danh mục chứa các bài viết về y tế và sức khỏe', 1),
       (2, 'Thông tin bệnh viện', 'Danh mục chứa thông tin về các bệnh viện và cơ sở y tế', 1),
       (3, 'Dinh dưỡng', 'Bài viết về dinh dưỡng và cách duy trì một chế độ ăn lành mạnh', 1),
       (4, 'Chăm sóc sức khỏe', 'Bài viết về cách chăm sóc sức khỏe cá nhân và gia đình', 1),
       (5, 'Các bệnh lý phổ biến', 'Thông tin về các bệnh lý thường gặp và cách điều trị', 1),
       (6, 'Y học cổ truyền', 'Bài viết về y học cổ truyền và phương pháp chữa bệnh truyền thống', 1),
       (7, 'Sản phẩm y tế', 'Thông tin về sản phẩm và dịch vụ y tế hiện có trên thị trường', 1);

# all password is khanh.ha
INSERT INTO `user` (user_id, username, hashed_password, email, contact_number, department_id, gender, first_name,
                    last_name, address, date_of_birth, profile_picture, role_id)
VALUES (1, 'duykhanh', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK',
        'ninggiangboy@gmail.com', '0368260323', NULL, 'MALE', 'Khanh', 'Ha',
        'Hải Dương', '2003-03-26', NULL, 'ADMIN'),
       (2, 'doctor1', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK',
        'doctor1@gmail.com', '0368260324', 'IM', 'MALE', 'Khanh', 'Ha',
        'Hải Dương', '2003-03-26', NULL, 'DOC'),
       (3, 'doctor2', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK',
        'doctor2@gmail.com', '0368260324', 'SU', 'MALE', 'Khanh', 'Ha',
        'Hải Dương', '2003-03-26', NULL, 'DOC'),
       (4, 'doctor3', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK',
        'doctor3@gmail.com', '0368260324', 'NU', 'MALE', 'Khanh', 'Ha',
        'Hải Dương', '2003-03-26', NULL, 'DOC'),
       (5, 'nurse', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK',
        'nurse@gmail.com', '0368260324', 'NU', 'FEMALE', 'Khanh', 'Ha',
        'Hải Dương', '2003-03-26', NULL, 'NURSE'),
       (6, 'assistant1', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK',
        'assistant1@gmail.com', '0368260324', 'IM', 'MALE', 'Khanh', 'Ha',
        'Hải Dương', '2003-03-26', NULL, 'ASSIS'),
       (7, 'assistant2', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK',
        'assistant2@gmail.com', '0368260324', 'SU', 'MALE', 'Khanh', 'Ha',
        'Hải Dương', '2003-03-26', NULL, 'ASSIS'),
       (8, 'assistant3', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK',
        'assistant3@gmail.com', '0368260324', 'NU', 'MALE', 'Khanh', 'Ha',
        'Hải Dương', '2003-03-26', NULL, 'ASSIS'),
       (9, 'reception', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK',
        'reception@gmail.com', '0368260324', NULL, 'MALE', 'Khanh', 'Ha',
        'Hải Dương', '2003-03-26', NULL, 'RECEP'),
       (10, 'media', '$2a$10$o0QV8g1o73wqT9HTcX9nl.VRNci1V9MBRM4vLnQ3AY86fXW82TjyK',
        'media@gmail.com', '0368260324', NULL, 'MALE', 'Khanh', 'Ha',
        'Hải Dương', '2003-03-26', NULL, 'MEDIA');


INSERT INTO `patient` (full_name, date_of_birth, gender, email, national_id_card, contact_number, address,
                       insurance_provider)
VALUES ('Trần Văn An', '1992-03-15', 'MALE', 'vungocchungfpt@gmail.com', '1234567890', '0123456789', 'Hà Nội', 'HA_NOI'),
       ('Nguyễn Thị Cẩm', '1985-07-20', 'FEMALE', 'nguyencam@email.com', '9876543210', '0987654321', 'Hồ Chí Minh',
        'HO_CHI_MINH'),
       ('Lê Thanh Dương', '1990-12-10', 'FEMALE', 'leduong@email.com', '4567891230', '0369852147', 'Cao Bằng',
        'CAO_BANG'),
       ('Võ Văn Hải', '1987-05-25', 'MALE', 'vohai@email.com', '7896541230', '0246897531', 'Hải Phòng', 'HAI_PHONG'),
       ('Phạm Thị Hương', '1994-08-05', 'FEMALE', 'phamhuong@email.com', '1597534680', '0765432189', 'Đà Nẵng',
        'DA_NANG'),
       ('Hoàng Văn Đức', '1998-02-28', 'MALE', 'hoangduc@email.com', '1234567891', '0123456781', 'Bắc Ninh',
        'BAC_NINH'),
       ('Mai Thị Trang', '1991-04-10', 'FEMALE', 'maitrang@email.com', '9876543211', '0987654321', 'Nghệ An',
        'NGHE_AN'),
       ('Nguyễn Văn Tú', '1986-09-15', 'MALE', 'nguyONu@email.com', '4567891231', '0369852141', 'Thanh Hóa',
        'THANH_HOA'),
       ('Lê Thị Hoa', '1993-11-25', 'FEMALE', 'lehoa@email.com', '7896541231', '0246897531', 'Quảng Ninh',
        'QUANG_NINH'),
       ('Trần Văn Hoàng', '1997-01-05', 'MALE', 'tranhoang@email.com', '1597534681', '0765432181', 'Ninh Bình',
        'NINH_BINH'),
       ('Phạm Thị Lan', '1990-06-20', 'FEMALE', 'phamlan@email.com', '1234567892', '0123456782', 'Vĩnh Phúc',
        'VINH_PHUC'),
       ('Đỗ Văn Nam', '1985-03-10', 'MALE', 'donam@email.com', '9876543212', '0987654322', 'Bắc Giang', 'BAC_GIANG'),
       ('Lý Thị Ánh', '1996-02-05', 'FEMALE', 'lyanh@email.com', '4567891232', '0369852142', 'Ninh Thuận',
        'NINH_THUAN'),
       ('Nguyễn Thanh Thảo', '1991-12-15', 'FEMALE', 'nguyONthao@email.com', '7896541232', '0246897532', 'Gia Lai',
        'GIA_LAI'),
       ('Hà Văn Đông', '1994-10-25', 'MALE', 'hadong@email.com', '1597534682', '0765432182', 'Bình Định', 'BINH_DINH'),
       ('Trịnh Văn Long', '1998-05-05', 'MALE', 'trinhlong@email.com', '1234567893', '0123456783', 'Hà Tĩnh',
        'HA_TINH'),
       ('Vũ Thị Hòa', '1990-07-20', 'FEMALE', 'vuhoa@email.com', '9876543213', '0987654323', 'Hòa Bình', 'HOA_BINH'),
       ('Nguyễn Văn Hùng', '1986-09-10', 'MALE', 'nguyenhung@email.com', '4567891233', '0369852143', 'Quảng Bình',
        'QUANG_BINH'),
       ('Phan Thị Ngọc', '1993-04-25', 'FEMALE', 'phanngoc@email.com', '7896541233', '0246897533', 'Quảng Trị',
        'QUANG_TRI'),
       ('Bùi Thị Linh', '1997-08-05', 'FEMALE', 'builinh@email.com', '1597534683', '0765432183', 'Thừa Thiên-Huế',
        'THUA_THIEN_HUE'),
       ('Lương Văn Tuấn', '1990-01-10', 'MALE', 'luongtuan@email.com', '1234567894', '0123456784', 'Quảng Nam',
        'QUANG_NAM'),
       ('Ngô Thị Thảo', '1985-03-15', 'FEMALE', 'ngotheo@email.com', '9876543214', '0987654324', 'Kon Tum', 'KON_TUM'),
       ('Trần Văn Dũng', '1996-02-20', 'MALE', 'trandung@email.com', '4567891234', '0369852144', 'Quảng Ngãi',
        'QUANG_NGAI'),
       ('Lê Thị Hoài', '1991-12-10', 'FEMALE', 'lehoai@email.com', '7896541234', '0246897534', 'Bình Định',
        'BINH_DINH'),
       ('Võ Văn Khôi', '1994-10-25', 'MALE', 'vokhoi@email.com', '1597534684', '0765432184', 'Phú Yên', 'PHU_YEN'),
       ('Phạm Thị Thu', '1998-05-05', 'FEMALE', 'phamthu@email.com', '1234567895', '0123456785', 'Khánh Hòa',
        'KHANH_HOA'),
       ('Nguyễn Văn Hòa', '1990-07-20', 'MALE', 'nguyenhoa@email.com', '9876543215', '0987654325', 'Lâm Đồng',
        'LAM_DONG'),
       ('Lê Thị Hoàng', '1986-09-10', 'FEMALE', 'lehoang@email.com', '4567891235', '0369852145', 'Bà Rịa-Vũng Tàu',
        'BA_RIA_VUNG_TAU'),
       ('Trần Văn Quý', '1993-04-25', 'MALE', 'tranquy@email.com', '7896541235', '0246897535', 'Long An', 'LONG_AN'),
       ('Vũ Văn Sơn', '1997-08-05', 'MALE', 'vuson@email.com', '1597534685', '0765432185', 'Tây Ninh', 'TAY_NINH');


INSERT INTO `medical_record` (`patient_id`, `doctor_id`, `record_date`, `diagnosis`, `treatment`, `prescription`,
                              `document_path`)
VALUES (1, 2, '2023-01-15', 'Sốt cao và ho khan', 'Dùng Paracetamol', 'Paracetamol 500mg, uống 3 lần/ngày',
        'FCNN.pdf; CV-1.pdf');
INSERT INTO `medical_record` (`patient_id`, `doctor_id`, `record_date`, `diagnosis`, `treatment`, `prescription`,
                              `document_path`)
VALUES (1, 4, '2023-02-20', 'Đau bên phải hông', 'Thăm khám chuyên gia', 'N/A', 'CV-1.pdf');
INSERT INTO `medical_record` (`patient_id`, `doctor_id`, `record_date`, `diagnosis`, `treatment`, `prescription`,
                              `document_path`)
VALUES (1, 4, '2023-03-25', 'Cảm cúm', 'Nghỉ ngơi, uống nhiều nước', 'N/A', 'FCNN.pdf');
INSERT INTO `medical_record` (`patient_id`, `doctor_id`, `record_date`, `diagnosis`, `treatment`, `prescription`,
                              `document_path`)
VALUES (2, 2, '2023-04-10', 'Đau rát họng', 'Sử dụng viên sủi họng', 'Viên sủi họng StreDEils', 'FCNN.pdf');
INSERT INTO `medical_record` (`patient_id`, `doctor_id`, `record_date`, `diagnosis`, `treatment`, `prescription`,
                              `document_path`)
VALUES (3, 2, '2023-05-05', 'Tiêu chảy', 'Uống nước muối, tránh thực phẩm nhiễm khuẩn',
        'Nước muối 0,9%, uống mỗi 2 tiếng', 'FCNN.pdf');
INSERT INTO `appointment` (`patient_id`, `service_id`, `reason`, `appointment_time`, `appointment_status`)
VALUES
    (1, 1, 'Kiểm tra định kỳ', '2023-10-31 08:00:00', 'CONFIRMED'),
    (2, 2, 'Vệ sinh răng', '2023-11-15 10:30:00', 'CONFIRMED'),
    (3, 1, 'Cuộc hẹn theo dõi', '2023-11-05 09:45:00', 'PENDING'),
    (1, 2, 'Khám mắt', '2023-11-20 11:15:00', 'PENDING');

INSERT INTO payment (booking_id, payment_method, amount, payment_status)
VALUES
    (1, 'CREDIT_CARD', 150.00, 'PAID'),
    (2, 'ONLINE', 200.50, 'DEPOSIT'),
    (3, 'CASH', 100.75, 'PAID'),
    (4, 'CREDIT_CARD', 150.00, 'DEPOSIT');


select * from medical_record
