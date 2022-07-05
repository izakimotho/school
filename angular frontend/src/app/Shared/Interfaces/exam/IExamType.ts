import {ISchool} from '../school/ISchool';

export interface IExamType {
    exam_type_id: string,
    exam_type_name: string,
    description: string,
    organization: ISchool,
    createAt: string,
    deletedAt: string,
}
