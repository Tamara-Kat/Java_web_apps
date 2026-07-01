package ua.university.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.university.sms.model.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCode(String code);

    boolean existsByCode(String code);

    List<Course> findByTeacherId(Long teacherId);

    List<Course> findByCredits(Integer credits);

    List<Course> findByTeacherIdAndCredits(Long teacherId, Integer credits);

    List<Course> findByTitleContainingIgnoreCase(String title);
}
