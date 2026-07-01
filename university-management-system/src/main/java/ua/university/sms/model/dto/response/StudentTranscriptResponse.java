package ua.university.sms.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class StudentTranscriptResponse {

    private Long studentId;

    private String studentName;

    private String email;

    private Double gpa;

    private List<EnrollmentResponse> enrollments;
}
