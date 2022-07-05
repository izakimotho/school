import {IExamType} from './IExamType';
import {IClasses} from '../school/IClasses';
import { ISchoolSubjects } from '../school/ISchoolSubjects';

export interface IExam {
    exam_schedule_id: string,
    exam_schedule_name: string,
    exam_type: IExamType,
    subject: ISchoolSubjects,
    exam_date: string,
    exam_time: string,
    class_model: IClasses,
    // organization: ISchool,
    // schedule_by: IStaff,
    createAt: string,
    deletedAt: string
}
