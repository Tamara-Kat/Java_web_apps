package ua.university.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.university.sms.model.entity.Student;
import ua.university.sms.model.entity.StudentStatus;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Student> findByStatus(StudentStatus status);

    List<Student> findByStudyYear(Integer studyYear);

    List<Student> findByStatusAndStudyYear(StudentStatus status, Integer studyYear);

    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String firstName,
            String lastName,
            String email
    );

    @Query("""
            SELECT DISTINCT e.student
            FROM Enrollment e
            WHERE e.paid = false
            """)
    List<Student> findStudentsWithUnpaidCourses();
}