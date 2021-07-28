
INSERT INTO users (id,username,password,mentored_program_id)
VALUES (1,'user1','user1234',null),
       (2,'user2','user2345',null),
       (33,'user3','user3456',null),
       (34,'user4','user4567',null),
       (35,'user5','user5678',null),
       (36,'user6','user6789',37)
ON CONFLICT DO NOTHING;

INSERT INTO admin (id,username,password)
VALUES (3,'admin1','admin1234'),
       (4,'admin2','admin2345')
ON CONFLICT DO NOTHING;

INSERT INTO Subject (id,subject_name)
VALUES (5,'subject2'),
       (6,'subject1')
ON CONFLICT DO NOTHING;

INSERT INTO Subsubject (id,subject_id,subsubject_name)
VALUES (7,5,'subsubject2-1'),
       (8,5,'subsubject2-2'),
       (9,5,'subsubject2-3'),
       (10,5,'subsubject2-4'),
       (11,5,'subsubject2-5'),
       (12,5,'subsubject2-6'),
       (13,6,'subsubject1-1'),
       (14,6,'subsubject1-2'),
       (15,6,'subsubject1-3'),
       (16,6,'subsubject1-4')
ON CONFLICT DO NOTHING;

INSERT INTO application_subsubjects(id,application_id,subsubject_id)
VALUES (17,25,7),
       (18,26,13),
       (19,27,8),
       (20,28,11),
       (21,29,15),
       (22,30,10),
       (23,31,10),
       (24,32,9)
ON CONFLICT DO NOTHING;

INSERT INTO mentorship_application(id,applicant_id,experience,is_active)
VALUES (25,1,'user 1 experiences for subject 2-1',True),
       (26,1,'user 1 experiences for subject 1-1',True),
       (27,2,'user 2 experiences for subject 2-2',True),
       (28,2,'user 2 experiences for subject 2-5',True),
       (29,1,'user 1 experiences for subject 1-3',True),
       (30,1,'user 1 experiences for subject 2-4',True),
       (31,2,'user 2 experiences for subject 2-4',True),
       (32,2,'user 2 experiences for subject 2-3',True)
ON CONFLICT DO NOTHING;

INSERT INTO program(id,programname,startdate,enddate,status)
VALUES (37,'program1','1996-12-02','1997-12-02','ended'),
       (38,'program2','2006-10-03','2008-02-22','ended'),
       (42,'program3','2021-07-03',null,'phase2')
ON CONFLICT DO NOTHING;

INSERT INTO phase(id,phasename,program_id,point,startdate,enddate,experience,is_active)
VALUES (43,'phase1',42,null,'2021-07-03','2021-07-04','phase 1 of program 3 done',False),
       (44,'phase2',42,null,'2021-07-04',null,null,True),
       (45,'phase3',42,null,null,null,null,False)
ON CONFLICT DO NOTHING;

INSERT INTO enrollment(id,mentee_id,program_id,menteecomment,mentorcomment,is_active)
VALUES (39,33,37,'was a good program','it was nice to work with these amazing mentees',False),
       (40,34,37,'was a good program2','it was nice to work with these amazing mentees',False),
       (41,33,38,'was a bad program','things didnt went well',False)
ON CONFLICT DO NOTHING;



