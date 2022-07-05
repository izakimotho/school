import { IClasses } from "./IClasses";


export interface ISchoolStream {
    school_stream_id: string;
    stream_name: string;
    abbr: string;
    description:  string; 
    class_capacity: number, 
    class_model: IClasses;
    organization:any;

     class_rep:any,
     class_teacher:any

    createdAt:  string; 
    deletedAt:  string;  

  }
 
 