INSERT INTO admin (username, password, gmail)
VALUES ('admin1', '$2a$10$/es0qNYiyEfN25DiArPAPerGg/UAAJ2sOF39Cq4Zj7S4JHc3JIDoO', ''),
       ('admin2', '$2a$10$DiCiFafymzmdqaOfhlwxQu.yA79T25hwuCGL08HGwOFEneeWnRr3q', ''),
       ('Veyis Turgut', '$2a$10$TtrQV4GbJMS.FIeuoS9B6eKnpiyeN4J/viNN.zCYNrtijs3PvrX86', 'vturgut68@gmail.com')

ON CONFLICT DO NOTHING;

insert into users (username, gmail, password) values ('jpeche0', 'hrossbrooke0@gmail.com', 'yLmUIJ');
insert into users (username, gmail, password) values ('dallanson1', 'mmercey1@gmail.com', 'ehDp2FKL');
insert into users (username, gmail, password) values ('mhankard2', 'cdiperaus2@gmail.com', '8lv8YyXK0');
insert into users (username, gmail, password) values ('mhumbles3', 'sespino3@gmail.com', 'A4kVwA4Ek0XS');
insert into users (username, gmail, password) values ('skillcross4', 'ggrebert4@gmail.com', '4KsI3RYRmdZ');
insert into users (username, gmail, password) values ('jmeneely5', 'dmantha5@gmail.com', 'KtmcZCmEjO');
insert into users (username, gmail, password) values ('gyukhnevich6', 'sfeatherby6@gmail.com', 'YFRjRXt');
insert into users (username, gmail, password) values ('ewarboy7', 'bmachostie7@gmail.com', 'm0SkrZW11L');
insert into users (username, gmail, password) values ('jmiddleditch8', 'ekordas8@gmail.com', 'uLyaZzvANBj');
insert into users (username, gmail, password) values ('ugrindle9', 'toxe9@gmail.com', 'W1dtEIo7Mo');
INSERT INTO users (username, password, gmail) VALUES ('veyisTurgut', '$2a$10$TtrQV4GbJMS.FIeuoS9B6eKnpiyeN4J/viNN.zCYNrtijs3PvrX86', 'vturgut68@gmail.com')
ON CONFLICT DO NOTHING;

INSERT INTO Subject (subject_id, subject_name, subsubject_name)
VALUES (21, 'subject-1', 'a'),
       (22, 'subject-1', 'b'),
       (23, 'subject-1', 'c'),
       (24, 'subject-2', 'a'),
       (25, 'subject-2', 'b'),
       (26, 'subject-2', 'c'),
       (27, 'subject-3', 'a'),
       (28, 'subject-3', 'b'),
       (29, 'subject-4', 'a')
ON CONFLICT DO NOTHING;

INSERT INTO mentorship_application(applicant_username, subject_id, experience, status)
VALUES ('user1', 21, 'user 1 experiences for subject 2,a', 'open'),
       ('user2', 22, 'user 2 experiences for subject 1,a', 'open'),
       ('user5', 23, 'user 5 experiences for subject 2,b', 'open'),
       ('user4', 25, 'user 4 experiences for subject 3,b', 'open'),
       ('user5', 25, 'user 5 experiences for subject 1,c', 'open'),
       ('user3', 25, 'user 3 experiences for subject 2,a', 'open'),
       ('user6', 26, 'user 6 experiences for subject 2,a', 'open'),
       ('user5', 24, 'user 5 experiences for subject 3,c', 'open')
ON CONFLICT DO NOTHING;

INSERT INTO program(program_id, start_date, end_date, status, is_active, mentee_comment, mentor_comment,
                    mentor_username,
                    mentee_username, subject_id)
VALUES (7, '1996-12-02', '1997-12-02', 'ended', false, 'mentee_commentxo', 'mentor_commentxo', 'user5', 'user3', 23),
       (8, '2006-10-03', '2008-02-22', 'ended', false, 'mentee_commentxo', 'mentor_commentxo', 'user5', 'user2', 24),
       (9, '2021-07-03', null, 'phase2', true, 'mentee_commentxo', 'mentor_commentxo', 'user4', 'user2', 21)
ON CONFLICT DO NOTHING;

INSERT INTO phase(phase_id, program_id, mentee_point, mentor_point, start_date, end_date, mentee_experience,
                  mentor_experience)
VALUES (1, 8, 4, 2, '2021-07-03', '2021-07-04', 'mentee_expcommentxo', 'mentor_commentxoexp'),
       (1, 7, 2, 3, '2021-07-03', '2021-07-04', 'mentee_expcommentxo', 'mentor_commentxoexp'),
       (1, 9, 4, 5, '2021-07-03', '2021-07-04', 'mentee_coexpmmentxo', 'mentor_commentxoexp'),
       (2, 9, null, null, '2021-07-04', null, 'mentee_coexpmmentxo', null)
ON CONFLICT DO NOTHING;







