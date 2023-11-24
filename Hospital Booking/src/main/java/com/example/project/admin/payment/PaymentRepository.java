package com.example.project.admin.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByAppointmentIdIn(List<Long> appointmentIds);

    @Query("""
            SELECT SUM(p.amount) FROM Payment p where month(p.createdAt) = :month
            """)
    Double findTotalInMonth(int month);

    @Query(value = """
            SELECT
                  COALESCE(SUM(p.amount), 0) AS total_amount
              FROM
                  (SELECT 1 AS month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION
                   SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12) m
                      LEFT JOIN
                  payment p ON MONTH(p.created_at) = m.month AND YEAR(p.created_at) = :year
              GROUP BY
                  m.month
              ORDER BY
                  m.month;
                    """,
            nativeQuery = true)
    List<Double> findAmountInYear(int year);
}
