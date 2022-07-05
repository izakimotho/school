
import { IEducationSystem } from "./IEducationSystem";
import { ISchoolLevel } from "./ISchoolLevel";

export interface ISubject {
    subject_id: string;
    subject_name: string;
    subject_abbr: string;
    description: string;
    education_system: IEducationSystem;
    school_level: ISchoolLevel;
    createdAt:  string; 
    deletedAt:  string;  
  } 