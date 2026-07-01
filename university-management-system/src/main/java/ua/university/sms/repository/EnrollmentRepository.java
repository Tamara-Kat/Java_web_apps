package ua.university.sms.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.university.sms.model.entity.Enrollment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByCourseId(Long courseId);

    List<Enrollment> findBySemester(String semester);

    List<Enrollment> findByPaidFalse();

    Optional<Enrollment> findByStudentIdAndCourseIdAndSemester(
            Long studentId,
            Long courseId,
            String semester
    );

    @Query("""
            SELECT AVG(e.grade)
            FROM Enrollment e
            WHERE e.student.id = :studentId
              AND e.grade IS NOT NULL
            """)
    BigDecimal calculateAverageGradeByStudentId(Long studentId);

    @Query("""
            SELECT AVG(e.grade)
            FROM Enrollment e
            WHERE e.course.id = :courseId
              AND e.grade IS NOT NULL
            """)
    BigDecimal calculateAverageGradeByCourseId(Long courseId);

    @Query("""
            SELECT AVG(e.grade)
            FROM Enrollment e
            WHERE e.semester = :semester
              AND e.grade IS NOT NULL
            """)
    BigDecimal calculateAverageGradeBySemester(String semester);

    @Query("""
            SELECT e
            FROM Enrollment e
            WHERE e.paid = false
            """)
    List<Enrollment> findUnpaidEnrollments();

    @Query("""
            SELECT
                e.student.id,
                e.student.firstName,
                e.student.lastName,
                e.student.email,
                AVG(e.grade)
            FROM Enrollment e
            WHERE e.grade IS NOT NULL
            GROUP BY e.student.id, e.student.firstName, e.student.lastName, e.student.email
            ORDER BY AVG(e.grade) DESC
            """)
    List<Object[]> findTopStudentsByGpa(Pageable pageable);
}