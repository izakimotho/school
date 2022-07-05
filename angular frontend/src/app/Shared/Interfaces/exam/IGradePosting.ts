import { IStudent } from "../student/IStudent"
import { IExam } from "./IExam"
 
export interface IGradePosting {
    exam_schedule: IExam,
    student: IStudent, 
    grade: string,
    remarks: string, 
}



 
 