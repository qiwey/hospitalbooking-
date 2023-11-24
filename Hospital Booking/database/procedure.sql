USE HospitalBooking;

DROP PROCEDURE IF EXISTS GetStaff;
DELIMITER //
CREATE PROCEDURE GetStaff()
BEGIN
    SELECT u.user_id                               AS id,
           CONCAT(u.last_name, ' ', u.first_name,) AS fullName,
           u.email,
           u.contact_number                        AS contactNumber,
           r.role_name                             AS roleName,
           d.department_name                       AS departmentName,
           u.is_locked                             AS lockedStatus
    FROM user u
             LEFT JOIN role r ON u.role_id = r.role_id
             LEFT JOIN department d ON u.department_id = d.department_id;
END //
DELIMITER ;


# CALL GetStaff();

SELECT u.user_id                              AS userId,
       CONCAT(u.last_name, ' ', u.first_name) AS fullName,
       u.email,
       u.contact_number                       AS contactNumber,
       r.role_name                            AS roleName,
       d.department_name                      AS departmentName,
       u.is_locked                            AS lockedStatus
FROM HospitalBooking.user u
         LEFT JOIN
     HospitalBooking.role r
     ON u.role_id = r.role_id
         LEFT JOIN
     HospitalBooking.department d
     ON u.department_id = d.department_id
limit 10