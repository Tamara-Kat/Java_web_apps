package ua.university.sms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.university.sms.exception.ResourceNotFoundException;
import ua.university.sms.model.dto.request.CourseRequest;
import ua.university.sms.model.dto.response.CourseResponse;
import ua.university.sms.model.entity.Course;
import ua.university.sms.model.entity.Teacher;
import ua.university.sms.repository.CourseRepository;
import ua.university.sms.repository.TeacherRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public CourseResponse create(CourseRequest request) {

        if (courseRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException(
                    "Course with code already exists: " + request.getCode()
            );
        }

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher with id " + request.getTeacherId() + " not found"
                        ));

        Course course = Course.builder()
                .code(request.getCode().trim())
                .title(request.getTitle().trim())
                .credits(request.getCredits())
                .teacher(teacher)
                .build();

        return toResponse(courseRepository.save(course));
    }

    @Transactional(readOnly = true)
    public CourseResponse getById(Long id) {
        return toResponse(findCourse(id));
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getAll(Long teacherId, Integer credits) {

        List<Course> courses;

        if (teacherId != null && credits != null) {
            courses = courseRepository.findByTeacherIdAndCredits(teacherId, credits);
        } else if (teacherId != null) {
            courses = courseRepository.findByTeacherId(teacherId);
        } else if (credits != null) {
            courses = courseRepository.findByCredits(credits);
        } else {
            courses = courseRepository.findAll();
        }

        return courses.stream()
                .map(this::toResponse)
                .toList();
    }

    public CourseResponse update(Long id, CourseRequest request) {

        Course course = findCourse(id);

        courseRepository.findByCode(request.getCode())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                            "Course with code already exists: " + request.getCode()
                    );
                });

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher with id " + request.getTeacherId() + " not found"
                        ));

        course.setCode(request.getCode().trim());
        course.setTitle(request.getTitle().trim());
        course.setCredits(request.getCredits());
        course.setTeacher(teacher);

        return toResponse(courseRepository.save(course));
    }

    public void delete(Long id) {
        courseRepository.delete(findCourse(id));
    }

    private Course findCourse(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course with id " + id + " not found"
                        ));
    }

    private CourseResponse toResponse(Course course) {

        return CourseResponse.builder()
                .id(course.getId())
                .code(course.getCode())
                .title(course.getTitle())
                .credits(course.getCredits())
                .teacherId(course.getTeacher().getId())
                .teacherName(
                        course.getTeacher().getFirstName()
                                + " "
                                + course.getTeacher().getLastName()
                )
                .build();
    }
}
