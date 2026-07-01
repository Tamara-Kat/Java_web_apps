CREATE TABLE students (
                          id BIGSERIAL PRIMARY KEY,
                          first_name VARCHAR(100) NOT NULL,
                          last_name VARCHAR(100) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          status VARCHAR(30) NOT NULL,
                          study_year INTEGER NOT NULL,

                          CONSTRAINT students_status_check
                              CHECK (status IN ('ACTIVE', 'INACTIVE', 'GRADUATED', 'EXPELLED')),

                          CONSTRAINT students_study_year_check
                              CHECK (study_year BETWEEN 1 AND 6)
);

CREATE TABLE teachers (
                          id BIGSERIAL PRIMARY KEY,
                          first_name VARCHAR(100) NOT NULL,
                          last_name VARCHAR(100) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          department VARCHAR(150) NOT NULL
);

CREATE TABLE courses (
                         id BIGSERIAL PRIMARY KEY,
                         code VARCHAR(30) NOT NULL UNIQUE,
                         title VARCHAR(255) NOT NULL,
                         credits INTEGER NOT NULL,
                         teacher_id BIGINT NOT NULL,

                         CONSTRAINT courses_credits_check
                             CHECK (credits BETWEEN 1 AND 10),

                         CONSTRAINT fk_courses_teacher
                             FOREIGN KEY (teacher_id)
                                 REFERENCES teachers(id)
                                 ON DELETE RESTRICT
);

CREATE TABLE enrollments (
                             id BIGSERIAL PRIMARY KEY,
                             student_id BIGINT NOT NULL,
                             course_id BIGINT NOT NULL,
                             semester VARCHAR(30) NOT NULL,
                             paid BOOLEAN NOT NULL DEFAULT FALSE,
                             grade NUMERIC(5,2),

                             CONSTRAINT enrollments_grade_check
                                 CHECK (grade IS NULL OR grade BETWEEN 0 AND 100),

                             CONSTRAINT fk_enrollments_student
                                 FOREIGN KEY (student_id)
                                     REFERENCES students(id)
                                     ON DELETE CASCADE,

                             CONSTRAINT fk_enrollments_course
                                 FOREIGN KEY (course_id)
                                     REFERENCES courses(id)
                                     ON DELETE CASCADE,

                             CONSTRAINT unique_student_course_semester
                                 UNIQUE (student_id, course_id, semester)
);