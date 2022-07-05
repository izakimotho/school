--
-- PostgreSQL database counties
--

insert into user_level(user_level, category_name)
values (0, 'Super Admin') ON CONFLICT (user_level) DO NOTHING;
insert into user_level(user_level, category_name)
values (1, 'School Admin') ON CONFLICT (user_level) DO NOTHING;
insert into user_level(user_level, category_name)
values (2, 'Staff') ON CONFLICT (user_level) DO NOTHING;
insert into user_level(user_level, category_name)
values (3, 'User') ON CONFLICT (user_level) DO NOTHING;
insert into user_level(user_level, category_name)
values (4, 'Student') ON CONFLICT (user_level) DO NOTHING;
insert into user_level(user_level, category_name)
values (5, 'Parent') ON CONFLICT (user_level) DO NOTHING;
insert into user_level(user_level, category_name)
values (6, 'Support Staff') ON CONFLICT (user_level) DO NOTHING;

insert into user_type(user_type_id, user_type_name)values (1, 'TEACHING STAFF');
insert into user_type(user_type_id, user_type_name)values (2, 'SUPPORT STAFF');
insert into user_type(user_type_id, user_type_name)values (3, 'STUDENT');
insert into user_type(user_type_id, user_type_name)values (4, 'PARENT');



insert into school_type (school_type_id, school_type_name, description)
values ('fbd3278b-1832-4462-a1a1-a0d34dbdafe1', 'Day School ', '')ON CONFLICT (school_type_id) DO NOTHING;
insert into school_type (school_type_id, school_type_name, description)
values ('b9c868f4-2541-4a2a-a885-7d480fd53df4', 'Boarding School', '')ON CONFLICT (school_type_id) DO NOTHING;

insert into school_category (school_category_id, school_category_name)
values ('aa66d1ff-2b95-4bdd-b8bf-6afff05376ad', 'National school')ON CONFLICT (school_category_id) DO NOTHING;
insert into school_category (school_category_id, school_category_name)
values ('e122dc52-a6bb-4b0c-a2e4-4dc09bfa6c42', 'County school')ON CONFLICT (school_category_id) DO NOTHING;

insert into education_system (education_system_id, education_system_name)
values ('c3b94634-42e0-4cd0-989f-640b80fdc5e9', 'CBC')ON CONFLICT (education_system_id) DO NOTHING;
insert into education_system (education_system_id, education_system_name)
values ('c7ba56fb-edb6-4ff0-bdd5-d9a78d5e1aea', 'IGCSE')ON CONFLICT (education_system_id) DO NOTHING;

insert into school_gender (school_gender_id, school_gender_name)
values ('9a755c10-9dc6-47b1-8a2f-95dd3d6a5a09', 'Boys School')ON CONFLICT (school_gender_id) DO NOTHING;
insert into school_gender (school_gender_id, school_gender_name)
values ('4870d77c-7959-44eb-8386-e4c6f1b6d5f4', 'Girls School')ON CONFLICT (school_gender_id) DO NOTHING;
insert into school_gender (school_gender_id, school_gender_name)
values ('5285e3db-29e6-4872-a4ca-09518088aeb2', 'Mixed School')ON CONFLICT (school_gender_id) DO NOTHING;

insert into school_cluster (cluster_id, cluster_name)
values ('2548cfa7-a8e9-45c8-a758-bf1d3fc6278a', 'C1')ON CONFLICT (cluster_id) DO NOTHING;
insert into school_cluster (cluster_id, cluster_name)
values ('9a309b40-cac8-4d8c-8f4d-853d64a6163e', 'C2')ON CONFLICT (cluster_id) DO NOTHING;

insert into school_level (school_level_id, school_level_name)
values ('92755473-f28b-40b1-bdf7-41f1542ea7aa', 'Secondary')ON CONFLICT (school_level_id) DO NOTHING;
insert into school_level (school_level_id, school_level_name)
values ('e5c8cd05-a550-4f0e-a732-2d64b85a5eb2', 'Primary')ON CONFLICT (school_level_id) DO NOTHING;
insert into school_level (school_level_id, school_level_name)
values ('07d7d790-76c5-468d-b16e-44834e0e7321', 'Kindergarten')ON CONFLICT (school_level_id) DO NOTHING;

insert into school_sponsor (school_sponsor_id, school_sponsor_name)
values ('dcf687d3-32de-4784-9c67-98130b509877', 'Private')ON CONFLICT (school_sponsor_id) DO NOTHING;
insert into school_sponsor (school_sponsor_id, school_sponsor_name)
values ('116deb95-2829-40e5-9eb4-739fbca4b936', 'DEB')ON CONFLICT (school_sponsor_id) DO NOTHING;
insert into school_sponsor (school_sponsor_id, school_sponsor_name)
values ('91c67503-fb9c-4a15-a974-fc1c2cf9a3b4', 'Roman Catholic')ON CONFLICT (school_sponsor_id) DO NOTHING;

insert into organization (org_id, org_name, code, school_type_id, county_id, ward_id, school_level_id,
                          education_system_id, school_category_id,
                          school_gender_id, school_sponsor_id)
values ('85cd1ef1-859a-4dad-9423-aec71a8c234e', 'Lunna', 'LNN',
        'fbd3278b-1832-4462-a1a1-a0d34dbdafe1', 1, 2, '07d7d790-76c5-468d-b16e-44834e0e7321',
        'c3b94634-42e0-4cd0-989f-640b80fdc5e9', 'aa66d1ff-2b95-4bdd-b8bf-6afff05376ad',
        '9a755c10-9dc6-47b1-8a2f-95dd3d6a5a09', 'dcf687d3-32de-4784-9c67-98130b509877')ON CONFLICT (org_id) DO NOTHING;

insert into roles (role_id, role_name, org_id)
values ('5260d6c6-8425-4082-90b2-44ed90d05845', 'Super Admin' ,'85cd1ef1-859a-4dad-9423-aec71a8c234e'),
('e441b182-1cee-41ae-81c0-ccd47f5ed8bc', 'Administration','85cd1ef1-859a-4dad-9423-aec71a8c234e'),
('2624260a-b30b-4aea-9bef-b25c6674981c', 'User', '85cd1ef1-859a-4dad-9423-aec71a8c234e')ON CONFLICT (role_id) DO NOTHING;

insert into permissions (permission_id, permission_name, description, user_level)
values (1, 'can_create_role', 'Create Role', 1),
       (2, 'can_edit_role', 'Edit Role', 1),
       (3, 'can_delete_role', 'Delete Role', 1),

       (4, 'can_add_calendar_event', 'Add Calendar Event', 1),
       (5, 'can_edit_calendar_event', 'Edit Calendar Event', 1),
       (6, 'can_delete_calendar_event', 'Delete Calendar Event', 1),
       (7, 'can_view_calendar_event', 'Show Calendar Events', 1),

       (8, 'can_view_calendar_items', ' Show Calendar Items', 1),
       (9, 'can_add_calendar_items', 'Add Calendar Item', 1),
       (10, 'can_edit_calendar_items', 'Edit Calendar Item', 1),
       (11, 'can_delete_calendar_items', 'Delete Calendar Item', 1),

       (12, 'can_view_class_model', 'Show Classes', 1),
       (13, 'can_add_class_model', ' Add Class', 1),
       (14, 'can_edit_class_model', 'Edit Class', 1),
       (15, 'can_delete_class_model', 'Delete Class', 1),

       (16, 'can_view_education_system', 'Show Education Systems', 1),
       (17, 'can_add_education_system', 'Add Education System', 1),
       (18, 'can_edit_education_system', 'Edit Education System', 1),
       (19, 'can_delete_education_system', 'Delete Education System', 1),

       (20, 'can_view_exam_schedule', 'Show Exam Schedules', 1),
       (21, 'can_add_exam_schedule', 'Add Exam Schedule', 1),
       (22, 'can_edit_exam_schedule', 'Edit Exam Schedule', 1),
       (23, 'can_delete_exam_schedule', 'Delete Exam Schedule', 1),

       (24, 'can_view_exam_type', 'Show Exam Types', 1),
       (25, 'can_add_exam_type', 'Add Exam Type', 1),
       (26, 'can_edit_exam_type', 'Edit Exam Type', 1),
       (27, 'can_delete_exam_type', 'Delete Exam Type', 1),

       (28, 'can_view_grade_posting', 'Show Grade Postings', 1),
       (29, 'can_add_grade_posting', 'Add Grade Posting', 1),
       (30, 'can_edit_grade_posting', 'Edit Grade Posting', 1),
       (31, 'can_delete_grade_posting', 'Delete Grade Posting', 1),

       (32, 'can_view_grades', 'Show Grades', 1),
       (33, 'can_add_grades', 'Add Grade', 1),
       (34, 'can_edit_grades', 'Edit Grade', 1),
       (35, 'can_delete_grades', 'Delete Grade', 1),

       (36, 'can_view_hostels', 'Show Hostels', 1),
       (37, 'can_add_hostels', 'Add Hostel', 1),
       (38, 'can_edit_hostels', 'Edit Hostel', 1),
       (39, 'can_delete_hostels', 'Delete Hostel', 1),

       (40, 'can_view_organization', 'Show Schools', 1),
       (41, 'can_add_organization', 'Add School', 1),
       (42, 'can_edit_organization', 'Edit School', 1),
       (43, 'can_delete_organization', 'Delete School', 1),

       (44, 'can_view_parents', 'Show Parents', 1),
       (45, 'can_add_parents', 'Add Parent', 1),
       (46, 'can_edit_parents', 'Edit Parent', 1),
       (47, 'can_delete_parents', 'Delete Parent', 1),

       (48, 'can_view_positions', 'Show Positions', 1),
       (49, 'can_add_positions', 'Add Position', 1),
       (50, 'can_edit_positions', 'Edit Position', 1),
       (51, 'can_delete_positions', 'Delete Position', 1),

       (52, 'can_view_roles', 'Show Roles', 1),
--       (53, 'can_add_roles', '', 1),
--       (54, 'can_edit_roles', '', 1),
--       (55, 'can_delete_roles', 'Delete Roles', 1),

       (56, 'can_view_role_permission', 'Show Role Permissions', 1),
       (57, 'can_add_role_permission', 'Add Role Permission', 1),
       (58, 'can_edit_role_permission', 'Edit Role Permission', 1),
       (59, 'can_delete_role_permission', 'Delete Role Permission', 1),

       (60, 'can_view_school_category', 'Show School Categories', 1),
       (61, 'can_add_school_category', 'Add School Category', 1),
       (62, 'can_edit_school_category', 'Edit School Category', 1),
       (63, 'can_delete_school_category', 'Delete School Category', 1),

       (64, 'can_view_school_stream_students', 'Show School Stream Students', 1),
       (65, 'can_add_school_stream_rep', 'Add School Stream Rep', 1),
       (66, 'can_edit_school_stream_class', 'Edit School Class', 1),
       (67, 'can_delete_school_class', 'Delete School Class', 1),

       (68, 'can_view_school_cluster', 'Show School Clusters', 1),
       (69, 'can_add_school_cluster', 'Add School Cluster', 1),
       (70, 'can_edit_school_cluster', 'Edit School Cluster', 1),
       (71, 'can_delete_school_cluster', 'Delete School Cluster', 1),

       (72, 'can_view_school_gender', 'Show School Genders', 1),
       (73, 'can_add_school_gender', 'Add School Gender', 1),
       (74, 'can_edit_school_gender', 'Edit School Gender', 1),
       (75, 'can_delete_school_gender', 'Delete School Gender', 1),

       (76, 'can_view_school_level', 'Show School Levels', 1),
       (77, 'can_add_school_level', 'Add School Level', 1),
       (78, 'can_edit_school_level', 'Edit School Level', 1),
       (79, 'can_delete_school_level', 'Delete School Level', 1),

       (80, 'can_view_school_sponsor', 'Show School Sponsors', 1),
       (81, 'can_add_school_sponsor', 'Add School Sponsor', 1),
       (82, 'can_edit_school_sponsor', 'Edit School Sponsor', 1),
       (83, 'can_delete_school_sponsor', 'Delete School Sponsor', 1),

       (84, 'can_view_school_stream', 'Show School Streams', 1),
       (85, 'can_add_school_stream', 'Add School Stream', 1),
       (86, 'can_edit_school_stream', 'Edit School Stream', 1),
       (87, 'can_delete_school_stream', 'Delete School Stream', 1),

       (88, 'can_view_school_type', 'Show School Types', 1),
       (89, 'can_add_school_type', 'Add School Type', 1),
       (90, 'can_edit_school_type', 'Edit School Type', 1),
       (91, 'can_delete_school_type', 'Delete School Type', 1),

       (92, 'can_view_staff', 'Show Staffs', 1),
       (93, 'can_add_staff', 'Add Staff', 1),
       (94, 'can_edit_staff', 'Edit Staff', 1),
       (95, 'can_delete_staff', 'Delete Staff', 1),

       (96, 'can_view_stream_details', 'Show Stream Details', 1),
       (97, 'can_add_stream_details', 'Add Stream Detail', 1),
       (98, 'can_edit_stream_details', 'Edit Stream Detail', 1),
       (99, 'can_delete_stream_details', 'Delete Stream Detail', 1),

       (100, 'can_view_student', 'Show Students', 1),
       (101, 'can_add_student', 'Add Student', 1),
       (102, 'can_edit_student', 'Edit Student', 1),
       (103, 'can_delete_student', 'Delete Student', 1),

       (104, 'can_view_subject', 'Show Subjects', 1),
       (105, 'can_add_subject', 'Add Subject', 1),
       (106, 'can_edit_subject', 'Edit Subject', 1),
       (107, 'can_delete_subject', 'Delete Subject', 1),

       (108, 'can_view_terms', 'Show Terms', 1),
       (109, 'can_add_terms', 'Add Term', 1),
       (110, 'can_edit_terms', 'Edit Term', 1),
       (111, 'can_delete_terms', 'Delete Term', 1),

       (112, 'can_view_user', 'Show User', 1),
       (113, 'can_add_user', 'Add User', 1),
       (114, 'can_edit_user', 'Edit User', 1),
       (115, 'can_delete_user', 'Delete User', 1),

       (116, 'can_view_user_category', 'Show User Categories', 1),
       (117, 'can_add_user_category', 'Add User Category', 1),
       (118, 'can_edit_user_category', 'Edit User Category', 1),
       (119, 'can_delete_user_category', 'Delete User Category', 1),

       (120, 'can_view_user_role', 'Show User Roles', 1),
       (121, 'can_add_user_role', 'Add User Role', 1),
       (122, 'can_edit_user_role', 'Edit User Role', 1),
       (123, 'can_delete_user_role', 'Delete  User Role', 1),

       (124, 'can_view_user_type', 'Show User Type', 1),
       (125, 'can_add_user_type', 'Add User Type', 1),
       (126, 'can_edit_user_type', 'Edit User Type', 1),
       (127, 'can_delete_user_type', 'Delete User Type', 1),

       (128, 'can_view_user_type_specific', 'Show User Type Specific', 1),
       (129, 'can_add_user_type_specific', 'Add User Type Specific', 1),
       (130, 'can_edit_user_type_specific', 'Edit User Type Specific', 1),
       (131, 'can_delete_user_type_specific', 'Delete User Type Specific', 1),

       (132,'can_view_schools_module', 'Show School Modules', 1),
       (133,'can_view_school_dashboard_module', 'Show School Dashboard Module', 1),
       (134,'can_view_students_module', 'Show Students Module', 1),
       (135,'can_view_school_admin_module', 'Show School Admin MOdule', 1),
       (136,'can_view_school_academics_module', 'Show School Academics Module', 1),
       (137,'can_view_users_module','User management module',1);


INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (138, 'can_add_school_subject', 'Create School Subject', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (139, 'can_view_school_subject', 'Show School Subject', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (140, 'can_edit_school_subject', 'Edit School Subject', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (141, 'can_delete_school_subject', 'Create School Subject', 1);



INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (142, 'can_add_academic_year', 'Create Academic Year', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (143, 'can_view_academic_year', 'Show Academic Year', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (144, 'can_edit_academic_year', 'Edit Academic Year', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (145, 'can_delete_academic_year', 'Create Academic Year', 1);


insert into users (user_id, username, first_name, last_name, email, password, user_level, org_id)
values ('adafdef2-c824-40fc-a6d9-2ce6cf258b77', 'root@localhost', 'Super', 'Admin', 'root@localhost',
    '$2a$10$z5OD8sYSdpl9niLYZIBPCef5f5LM3wcjdzHfOU8aBLNOPwPWHaUem',
    0, '85cd1ef1-859a-4dad-9423-aec71a8c234e')
ON CONFLICT (user_id) DO NOTHING;

insert into user_roles (user_id, role_id)
values('adafdef2-c824-40fc-a6d9-2ce6cf258b77','5260d6c6-8425-4082-90b2-44ed90d05845');

insert into role_permissions (permission_id, role_id)
values(1,'5260d6c6-8425-4082-90b2-44ed90d05845'),
    (2,'5260d6c6-8425-4082-90b2-44ed90d05845'),
    (3,'5260d6c6-8425-4082-90b2-44ed90d05845'),
    (4,'5260d6c6-8425-4082-90b2-44ed90d05845'),
    (5,'5260d6c6-8425-4082-90b2-44ed90d05845'),
    (6, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (7, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (8, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (9, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (10, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (11, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (12, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (13, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (14, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (15, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (16, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (17, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (18, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (19, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (20, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (21, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (22, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (23, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (24, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (25, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (26, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (27, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (28, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (29, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (30, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (31, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (32, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (33, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (34, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (35, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (36, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (37, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (38, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (39, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (40, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (41, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (42, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (43, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (44, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (45, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (46, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (47, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (48, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (49, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (50, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (51, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (52, '5260d6c6-8425-4082-90b2-44ed90d05845'),
--       (53, '5260d6c6-8425-4082-90b2-44ed90d05845'),
--       (54, '5260d6c6-8425-4082-90b2-44ed90d05845'),
--       (55, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (56, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (57, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (58, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (59, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (60, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (61, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (62, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (63, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (64, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (65, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (66, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (67, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (68, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (69, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (70, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (71, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (72, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (73, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (74, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (75, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (76, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (77, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (78, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (79, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (80, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (81, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (82, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (83, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (84, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (85, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (86, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (87, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (88, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (89, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (90, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (91, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (92, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (93, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (94, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (95, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (96, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (97, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (98, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (99, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (100, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (101, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (102, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (103, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (104, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (105, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (106, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (107, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (108, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (109, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (110, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (111, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (112, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (113, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (114, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (115, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (116, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (117, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (118, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (119, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (120, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (121, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (122, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (123, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (124, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (125, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (126, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (127, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (128, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (129, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (130, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (131, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (132,'5260d6c6-8425-4082-90b2-44ed90d05845'),
       (133,'5260d6c6-8425-4082-90b2-44ed90d05845'),
       (134,'5260d6c6-8425-4082-90b2-44ed90d05845'),
       (135, '5260d6c6-8425-4082-90b2-44ed90d05845'),
       (136,'5260d6c6-8425-4082-90b2-44ed90d05845'),
       (137,'5260d6c6-8425-4082-90b2-44ed90d05845' ),


       (138,'5260d6c6-8425-4082-90b2-44ed90d05845'),
       (139,'5260d6c6-8425-4082-90b2-44ed90d05845'),
       (140,'5260d6c6-8425-4082-90b2-44ed90d05845'),
       (141, '5260d6c6-8425-4082-90b2-44ed90d05845'),

       (142,'5260d6c6-8425-4082-90b2-44ed90d05845'),
       (143,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (144,'5260d6c6-8425-4082-90b2-44ed90d05845'),
       (145,'5260d6c6-8425-4082-90b2-44ed90d05845' ),

       (1,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
        (2,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
        (3,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
        (4,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
        (5,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
        (6, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (7, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (8, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (9, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (10, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (11, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (12, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (13, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (14, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (15, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (16, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (17, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (18, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (19, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (20, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (21, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (22, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (23, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (24, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (25, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (26, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (27, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (28, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (29, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (30, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (31, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (32, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (33, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (34, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (35, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (36, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (37, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (38, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (39, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (40, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (41, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (42, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (43, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (44, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (45, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (46, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (47, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (48, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (49, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (50, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (51, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (52, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
--       (53, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
--       (54, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
--       (55, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (56, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (57, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (58, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (59, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (60, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (61, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (62, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (63, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (64, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (65, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (66, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (67, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (68, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (69, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (70, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (71, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (72, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (73, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (74, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (75, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (76, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (77, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (78, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (79, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (80, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (81, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (82, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (83, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (84, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (85, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (86, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (87, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (88, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (89, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (90, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (91, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (92, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (93, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (94, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (95, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (96, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (97, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (98, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (99, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (100, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (101, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (102, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (103, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (104, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (105, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (106, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (107, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (108, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (109, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (110, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (111, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (112, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (113, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (114, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (115, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (116, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (117, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (118, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (119, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (120, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (121, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (122, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (123, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (124, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (125, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (126, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (127, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (128, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (129, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (130, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (131, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),


       (132,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (133,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (134,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (135, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (136,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (137,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' ),

       (138,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (139,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (140,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (141, 'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),

       (142,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (143,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' ),
       (144,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc'),
       (145,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );

--  18-06-2022

-- term details

INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (146,  'can_view_term_details', 'Create View Terms/Session Types Details', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (147, 'can_add_terms_details', 'View Terms/Session Types Details', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (148, 'can_edit_terms_details', 'Edit Terms/Session Types Details', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (149, 'can_delete_terms_details', 'Create Terms/Session Types Details', 1);

insert into role_permissions (permission_id, role_id)
values (146,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (146,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (147,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (147,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (148,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (148,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (149,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (149,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );


-- Educations System


INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (150,  'can_view_educations_system', 'Create View Educations System', 0);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (151, 'can_add_educations_system', 'View Educations System', 0);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (152, 'can_edit_educations_system', 'Edit Educations System', 0);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (153, 'can_delete_educations_system', 'Create Educations System', 0);



insert into role_permissions (permission_id, role_id)
values (150,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (150,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (151,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (151,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (152,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (152,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (153,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (153,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );


-- 25-06-2022
-- School Subject


INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (154,  'can_view_school-subject', 'Create View School Subject', 0);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (155, 'can_add_school-subject', 'View School Subject', 0);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (156, 'can_edit_school-subject', 'Edit School Subject', 0);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (157, 'can_delete_school-subject', 'Create School Subject', 0);

-- Fee Vote Head Permissions

INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (158,  'can_add_fee_vote', 'Create Fee Vote Heads Details', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (159, 'can_view_fee_vote', 'View Fee Vote Heads Details', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (160, 'can_edit_fee_vote', 'Edit Fee Vote Heads Details', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (161, 'can_delete_fee_vote', 'Delete Fee Vote Heads Details', 1);

INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (162,  'can_add_fee_structure', 'Create Fee structure Details', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (163, 'can_view_fee_structure', 'View Fee structure Details', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (164, 'can_edit_fee_structure', 'Edit Fee structure Details', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (165, 'can_delete_fee_structure', 'Delete Fee structure Details', 1);

INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (166,  'can_add_student_fee', 'Create Student Fee Details', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (167, 'can_view_student_fee', 'View FStudent Fee Details', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (168, 'can_edit_student_fee', 'Edit Student Fee Details', 1);
INSERT INTO permissions (permission_id, permission_name, description, user_level)
values (169, 'can_delete_student_fee', 'Delete Student Fee Details', 1);



insert into role_permissions (permission_id, role_id)
values (154,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (154,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (155,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (155,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (156,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (156,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (157,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (157,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );

insert into role_permissions (permission_id, role_id)
values (158,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (158,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (159,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (159,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (160,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (160,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (161,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (161,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );

insert into role_permissions (permission_id, role_id)
values (162,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (162,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (163,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (163,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (164,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (164,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (165,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (165,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );

insert into role_permissions (permission_id, role_id)
values (166,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (166,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (167,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (167,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (168,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (168,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );
insert into role_permissions (permission_id, role_id)
values (169,'5260d6c6-8425-4082-90b2-44ed90d05845' ),
       (169,'e441b182-1cee-41ae-81c0-ccd47f5ed8bc' );