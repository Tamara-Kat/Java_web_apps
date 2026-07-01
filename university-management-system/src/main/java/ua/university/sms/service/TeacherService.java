package ua.university.sms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.university.sms.exception.ResourceNotFoundException;
import ua.university.sms.model.dto.request.TeacherRequest;
import ua.university.sms.model.dto.response.TeacherResponse;
import ua.university.sms.model.entity.Teacher;
import ua.university.sms.repository.TeacherRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherResponse create(TeacherRequest request) {
        if (teacherRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Teacher with email already exists: " + request.getEmail());
        }

        Teacher teacher = Teacher.builder()
                .firstName(request.getFirstName().trim())
                .lastName(request.getLastName().trim())
                .email(request.getEmail().trim())
                .department(request.getDepartment().trim())
                .build();

        Teacher savedTeacher = teacherRepository.save(teacher);

        return toResponse(savedTeacher);
    }

    @Transactional(readOnly = true)
    public TeacherResponse getById(Long id) {
        Teacher teacher = findTeacherById(id);
        return toResponse(teacher);
    }

    @Transactional(readOnly = true)
    public List<TeacherResponse> getAll(String department) {
        List<Teacher> teachers;

        if (department != null && !department.isBlank()) {
            teachers = teacherRepository.findByDepartmentContainingIgnoreCase(department.trim());
        } else {
            teachers = teacherRepository.findAll();
        }

        return teachers.stream()
                .map(this::toResponse)
                .toList();
    }

    public TeacherResponse update(Long id, TeacherRequest request) {
        Teacher teacher = findTeacherById(id);

        teacherRepository.findByEmail(request.getEmail())
                .filter(existingTeacher -> !existingTeacher.getId().equals(id))
                .ifPresent(existingTeacher -> {
                    throw new IllegalArgumentException("Teacher with email already exists: " + request.getEmail());
                });

        teacher.setFirstName(request.getFirstName().trim());
        teacher.setLastName(request.getLastName().trim());
        teacher.setEmail(request.getEmail().trim());
        teacher.setDepartment(request.getDepartment().trim());

        Teacher updatedTeacher = teacherRepository.save(teacher);

        return toResponse(updatedTeacher);
    }

    public void deleteById(Long id) {
        Teacher teacher = findTeacherById(id);
        teacherRepository.delete(teacher);
    }

    private Teacher findTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher with id " + id + " not found"));
    }

    private TeacherResponse toResponse(Teacher teacher) {
        return TeacherResponse.builder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .email(teacher.getEmail())
                .department(teacher.getDepartment())
                .build();
    }
}