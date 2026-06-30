INSERT INTO client (name) VALUES
                              ('Alice Johnson'),
                              ('Bob Smith'),
                              ('Charlie Brown'),
                              ('Diana Prince'),
                              ('Edward Stone'),
                              ('Fiona Green'),
                              ('George Miller'),
                              ('Helen Carter'),
                              ('Ivan Petrov'),
                              ('Julia White');

INSERT INTO planet (id, name) VALUES
                                  ('MERCURY', 'Mercury'),
                                  ('VEN', 'Venus'),
                                  ('EARTH', 'Earth'),
                                  ('MARS', 'Mars'),
                                  ('JUPITER', 'Jupiter');

INSERT INTO ticket (created_at, client_id, from_planet_id, to_planet_id) VALUES
                                                                             (TIMESTAMP '2026-01-10 10:00:00', 1, 'EARTH', 'MARS'),
                                                                             (TIMESTAMP '2026-01-11 11:30:00', 2, 'MARS', 'EARTH'),
                                                                             (TIMESTAMP '2026-01-12 09:15:00', 3, 'EARTH', 'VEN'),
                                                                             (TIMESTAMP '2026-01-13 14:45:00', 4, 'VEN', 'MARS'),
                                                                             (TIMESTAMP '2026-01-14 16:20:00', 5, 'MARS', 'JUPITER'),
                                                                             (TIMESTAMP '2026-01-15 08:10:00', 6, 'JUPITER', 'EARTH'),
                                                                             (TIMESTAMP '2026-01-16 19:00:00', 7, 'MERCURY', 'EARTH'),
                                                                             (TIMESTAMP '2026-01-17 12:40:00', 8, 'EARTH', 'MERCURY'),
                                                                             (TIMESTAMP '2026-01-18 07:50:00', 9, 'VEN', 'JUPITER'),
                                                                             (TIMESTAMP '2026-01-19 21:25:00', 10, 'JUPITER', 'MARS');