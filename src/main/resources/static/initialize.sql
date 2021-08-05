INSERT INTO admin (username, password)
VALUES ('admin1', '$2a$10$/es0qNYiyEfN25DiArPAPerGg/UAAJ2sOF39Cq4Zj7S4JHc3JIDoO'),
       ('admin2', '$2a$10$DiCiFafymzmdqaOfhlwxQu.yA79T25hwuCGL08HGwOFEneeWnRr3q')
ON CONFLICT DO NOTHING;


INSERT INTO users (username, password)
VALUES ('user1', '$2a$10$TtrQV4GbJMS.FIeuoS9B6eKnpiyeN4J/viNN.zCYNrtijs3PvrX86'),
       ('user2', '$2a$10$Z329QvVPCViUzQnlK5l/qOO3qc25YrlORlyJhc0xdruFurvzp8v0i'),
       ('user3', '$2a$10$gyZagwu.9vysY39jsEA0BeDwc8TDh.P8cHWkJQQe2oSvNZq/DO2Ou'),
       ('user4', '$2a$10$WSay9HiCLpFipIpoTY7ZNubzuKr5ljZ8ir24t8o3ZRm6IfY0YPAdm'),
       ('user5', '$2a$10$jhSeFixdHrZjuER4AXPkgu4OSTso0uciEX1MgLSrWYBN6QUdOGcfO'),
       ('user6', '$2a$10$egmzlCh/TfWQwmkqN3y.cOU6C6jkW6mYnMrmtIOIp1BUgT76sJIZa')
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

INSERT INTO program(program_id, startdate, enddate, status, is_active, mentee_comment, mentor_comment, mentor_username,
                    mentee_username, subject_id)
VALUES (7, '1996-12-02', '1997-12-02', 'ended', false, 'mentee_commentxo', 'mentor_commentxo', 'user5', 'user3', 23),
       (8, '2006-10-03', '2008-02-22', 'ended', false, 'mentee_commentxo', 'mentor_commentxo', 'user5', 'user2', 24),
       (9, '2021-07-03', null, 'phase2', true, 'mentee_commentxo', 'mentor_commentxo', 'user4', 'user2', 21)
ON CONFLICT DO NOTHING;

INSERT INTO phase(phase_id, program_id, mentee_point, mentor_point, startdate, enddate, mentee_experience,
                  mentor_experience)
VALUES (1, 8, 4, 2, '2021-07-03', '2021-07-04', 'mentee_expcommentxo', 'mentor_commentxoexp'),
       (1, 7, 2, 3, '2021-07-03', '2021-07-04', 'mentee_expcommentxo', 'mentor_commentxoexp'),
       (1, 9, 4, 5, '2021-07-03', '2021-07-04', 'mentee_coexpmmentxo', 'mentor_commentxoexp'),
       (2, 9, null, null, '2021-07-04', null, 'mentee_coexpmmentxo', null)
ON CONFLICT DO NOTHING;







