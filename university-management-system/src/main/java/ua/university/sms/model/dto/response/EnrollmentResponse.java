package ua.university.sms.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class EnrollmentResponse {

    private Long id;

    private Long studentId;

    private String studentName;

    private Long courseId;

    private String courseTitle;

    private String semester;

    private Boolean paid;

    private Double grade;
}