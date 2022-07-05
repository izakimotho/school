import { ISchool } from "src/app/container/agent-admin/interface/ISchool"
import { IStudent } from "../student/IStudent"
import { IClasses } from "./IClasses"
import { ISchoolStream } from "./ISchoolStream"
import { IStaff } from "./IStaff"

export interface ISchoolClass {
    school_class_id: string;
    description:  string; 
    class_capacity: number;
    class_rep:IStudent;
    class_model: IClasses;
    organization: ISchool;   
    school_stream: ISchoolStream;
    class_teacher: IStaff;  
    studentList: []    
    createAt:  string; 
    deletedAt:  string; 
  }
 