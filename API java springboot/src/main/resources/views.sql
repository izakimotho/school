CREATE OR REPLACE VIEW view_report_card AS
SELECT s.student_id,
s.org_id,
CONCAT(s.first_name, '', s.surname) as student_name,
s.adm_number,
o.org_name,
g.grade,
sub.subject_name,
cm.class_name,
cm.class_id,
s.school_stream_id,
ss.stream_name,
CONCAT(st.first_name, '', st.surname) as class_teacher,
exam.terms,
g.remarks
FROM students s
JOIN organization o ON o.org_id = s.org_id
JOIN grade_postings g ON g.student_id = s.student_id
JOIN exam_schedules exam ON exam.exam_schedule_id = g.exam_schedule_id
JOIN school_streams ss ON ss.school_stream_id = s.school_stream_id
JOIN class_model cm ON cm.class_id = ss.class_id
LEFT JOIN staff st ON st.staff_id = ss.staff_id
JOIN subjects sub ON sub.subject_id = exam.subject_id;




CREATE OR REPLACE VIEW view_exam_time_table AS
SELECT es.exam_schedule_id,
es.created_at,
es.exam_date,
es.exam_schedule_name,
es.exam_time,
o.org_id,
es.school_stream_id,
et.exam_type_name,

t.name as term_name,

s.subject_abbr,
s.subject_name,
ss.class_id,

c.class_name,
o.org_name

FROM public.exam_schedules es

LEFT JOIN exam_types et ON et.exam_type_id = es.exam_type_id
LEFT JOIN tbl_terms t ON t.term_id = es.terms
JOIN school_streams ss ON ss.school_stream_id = es.school_stream_id
LEFT JOIN class_model c ON c.class_id = ss.class_id
LEFT JOIN organization o ON o.org_id = es.org_id
JOIN school_subjects ssb ON ssb.school_subject_id = es.school_subject_id
JOIN subjects s ON s.subject_id = ssb.subject_id

