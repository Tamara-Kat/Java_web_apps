INSERT INTO teachers (first_name, last_name, email, department) VALUES
                                                                    ('John', 'Smith', 'john.smith@university.ua', 'Computer Science'),
                                                                    ('Anna', 'Brown', 'anna.brown@university.ua', 'Mathematics'),
                                                                    ('Michael', 'Johnson', 'michael.johnson@university.ua', 'Physics');

INSERT INTO students (first_name, last_name, email, status, study_year) VALUES
                                                                            ('Alice', 'Cooper', 'alice.cooper@student.ua', 'ACTIVE', 1),
                                                                            ('Bob', 'Miller', 'bob.miller@student.ua', 'ACTIVE', 2),
                                                                            ('Clara', 'Wilson', 'clara.wilson@student.ua', 'ACTIVE', 3),
                                                                            ('David', 'Taylor', 'david.taylor@student.ua', 'INACTIVE', 2),
                                                                            ('Emma', 'Davis', 'emma.davis@student.ua', 'ACTIVE', 4),
                                                                            ('Frank', 'Moore', 'frank.moore@student.ua', 'GRADUATED', 4),
                                                                            ('Grace', 'Anderson', 'grace.anderson@student.ua', 'ACTIVE', 1),
                                                                            ('Henry', 'Thomas', 'henry.thomas@student.ua', 'EXPELLED', 3);

INSERT INTO courses (code, title, credits, teacher_id) VALUES
                                                           ('JAVA101', 'Java Programming Basics', 5, 1),
                                                           ('DB201', 'Databases and SQL', 4, 1),
                                                           ('MATH101', 'Linear Algebra', 3, 2),
                                                           ('PHY101', 'Physics Fundamentals', 4, 3),
                                                           ('WEB301', 'Spring Boot Web Development', 5, 1);

INSERT INTO enrollments (student_id, course_id, semester, paid, grade) VALUES
                                                                           (1, 1, '2026-SPRING', TRUE, 92),
                                                                           (1, 2, '2026-SPRING', FALSE, 85),
                                                                           (2, 1, '2026-SPRING', TRUE, 78),
                                                                           (2, 3, '2026-SPRING', TRUE, 88),
                                                                           (3, 2, '2026-SPRING', FALSE, 95),
                                                                           (3, 5, '2026-SPRING', TRUE, 90),
                                                                           (4, 4, '2026-SPRING', FALSE, NULL),
                                                                           (5, 5, '2026-SPRING', TRUE, 97),
                                                                           (6, 3, '2026-SPRING', TRUE, 91),
                                                                           (7, 1, '2026-SPRING', FALSE, 73);