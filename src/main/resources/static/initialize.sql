INSERT INTO admin (username,password)
VALUES ('admin1','$2a$10$/es0qNYiyEfN25DiArPAPerGg/UAAJ2sOF39Cq4Zj7S4JHc3JIDoO'),
       ('admin2','$2a$10$DiCiFafymzmdqaOfhlwxQu.yA79T25hwuCGL08HGwOFEneeWnRr3q')
ON CONFLICT DO NOTHING;


INSERT INTO users (username,password)
VALUES ('user1','$2a$10$TtrQV4GbJMS.FIeuoS9B6eKnpiyeN4J/viNN.zCYNrtijs3PvrX86'),
       ('user2','$2a$10$Z329QvVPCViUzQnlK5l/qOO3qc25YrlORlyJhc0xdruFurvzp8v0i'),
       ('user3','$2a$10$gyZagwu.9vysY39jsEA0BeDwc8TDh.P8cHWkJQQe2oSvNZq/DO2Ou'),
       ('user4','$2a$10$WSay9HiCLpFipIpoTY7ZNubzuKr5ljZ8ir24t8o3ZRm6IfY0YPAdm'),
       ('user5','$2a$10$jhSeFixdHrZjuER4AXPkgu4OSTso0uciEX1MgLSrWYBN6QUdOGcfO'),
       ('user6','$2a$10$egmzlCh/TfWQwmkqN3y.cOU6C6jkW6mYnMrmtIOIp1BUgT76sJIZa')
ON CONFLICT DO NOTHING;

INSERT INTO Subject (subject_id,subject_name,subsubject_name)
VALUES (1,'subject-1','a'),
       (2,'subject-1','b'),
       (3,'subject-1','c'),
       (4,'subject-2','a'),
       (5,'subject-2','b'),
       (6,'subject-2','c'),
       (7,'subject-3','a'),
       (8,'subject-3','b'),
       (9,'subject-4','a')
ON CONFLICT DO NOTHING;

INSERT INTO mentorship_application(applicant_username,subject_id,experience,is_active)
VALUES ('user1',1,'user 1 experiences for subject 2,a',True),
       ('user2',2,'user 2 experiences for subject 1,a',True),
       ('user5',3,'user 5 experiences for subject 2,b',True),
       ('user4',5,'user 4 experiences for subject 3,b',True),
       ('user5',5,'user 5 experiences for subject 1,c',True),
       ('user3',5,'user 3 experiences for subject 2,a',True),
       ('user6',6,'user 6 experiences for subject 2,a',True),
       ('user5',4,'user 5 experiences for subject 3,c',True)
ON CONFLICT DO NOTHING;

INSERT INTO program(program_id,startdate,enddate,status)
VALUES (7,'1996-12-02','1997-12-02','ended'),
       (8,'2006-10-03','2008-02-22','ended'),
       (9,'2021-07-03',null,'phase2')
ON CONFLICT DO NOTHING;

INSERT INTO phase(phase_name,program_id,point,startdate,enddate)
VALUES ('phase1',8,4,'2021-07-03','2021-07-04'),
       ('phase1',7,4,'2021-07-03','2021-07-04'),
       ('phase1',9,4,'2021-07-03','2021-07-04'),
       ('phase2',9,null,'2021-07-04',null)
ON CONFLICT DO NOTHING;







