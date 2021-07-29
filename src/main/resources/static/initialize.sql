
INSERT INTO users (username,password,mentored_program_name)
VALUES ('user1','$2a$10$TtrQV4GbJMS.FIeuoS9B6eKnpiyeN4J/viNN.zCYNrtijs3PvrX86',null),
       ('user2','$2a$10$Z329QvVPCViUzQnlK5l/qOO3qc25YrlORlyJhc0xdruFurvzp8v0i',null),
       ('user3','$2a$10$gyZagwu.9vysY39jsEA0BeDwc8TDh.P8cHWkJQQe2oSvNZq/DO2Ou',null),
       ('user4','$2a$10$WSay9HiCLpFipIpoTY7ZNubzuKr5ljZ8ir24t8o3ZRm6IfY0YPAdm',null),
       ('user5','$2a$10$jhSeFixdHrZjuER4AXPkgu4OSTso0uciEX1MgLSrWYBN6QUdOGcfO',null),
       ('user6','$2a$10$egmzlCh/TfWQwmkqN3y.cOU6C6jkW6mYnMrmtIOIp1BUgT76sJIZa','subject-3,a')
ON CONFLICT DO NOTHING;

INSERT INTO admin (username,password)
VALUES ('admin1','$2a$10$/es0qNYiyEfN25DiArPAPerGg/UAAJ2sOF39Cq4Zj7S4JHc3JIDoO'),
       ('admin2','$2a$10$DiCiFafymzmdqaOfhlwxQu.yA79T25hwuCGL08HGwOFEneeWnRr3q')
ON CONFLICT DO NOTHING;


INSERT INTO Subject (subject_name,subsubject_name)
VALUES ('subject-1','a'),
       ('subject-1','b'),
       ('subject-1','c'),
       ('subject-2','a'),
       ('subject-2','b'),
       ('subject-2','c'),
       ('subject-3','a'),
       ('subject-3','b'),
       ('subject-4','a')
ON CONFLICT DO NOTHING;


INSERT INTO mentorship_application(applicant_username,subject_name,subsubject_name,experience,is_active)
VALUES ('user1','subject-2','a','user 1 experiences for subject 2,a',True),
       ('user2','subject-1','a','user 2 experiences for subject 1,a',True),
       ('user5','subject-2','b','user 5 experiences for subject 2,b',True),
       ('user4','subject-3','b','user 4 experiences for subject 3,b',True),
       ('user5','subject-1','c','user 5 experiences for subject 1,c',True),
       ('user3','subject-2','a','user 3 experiences for subject 2,a',True),
       ('user6','subject-2','a','user 6 experiences for subject 2,a',True),
       ('user5','subject-3','c','user 5 experiences for subject 3,c',True)
ON CONFLICT DO NOTHING;

INSERT INTO program(programname,startdate,enddate,status)
VALUES ('subject-1,b','1996-12-02','1997-12-02','ended'),
       ('subject-4,a','2006-10-03','2008-02-22','ended'),
       ('subject-3,a','2021-07-03',null,'phase2')
ON CONFLICT DO NOTHING;

INSERT INTO phase(phasename,program_name,point,startdate,enddate,experience)
VALUES ('phase1','subject-3,a',4,'2021-07-03','2021-07-04','phase 1 of program subject-3,a done'),
       ('phase2','subject-3,a',null,'2021-07-04',null,null)
ON CONFLICT DO NOTHING;

INSERT INTO enrollment(mentee_name,program_name,mentee_comment,mentor_comment,is_active)
VALUES ('user2','subject-1,b','was a good program','it was nice to work with these amazing mentees',False),
       ('user5','subject-1,b','was a good program2','it was nice to work with these amazing mentees',False),
       ('user5','subject-4,a','was a bad program','things didnt went well',False)
ON CONFLICT DO NOTHING;


