import { UserCategory } from "../userprofile/IUserCategory";
import { IUserType } from "../userprofile/IUserType";
import { IPosition } from "./IPositions";
import { ISchool } from "./ISchool"; 

export class IStaff {
    staff_id: string;
    user_id: string;
    organization: ISchool;
    positions: IPosition;
    type:IUserType;
    first_name: string;
    firstName: string;
    lastName: string;
    last_name: string;
    middle_name: string;
    surname: string;
    username: string;
    email: string;
    password: string;
    gender: string;
    title: string;
    phone_number: string; 
    marital_status: string;
    spouse_name: string;
    spouse_phone: string;
    employment_date: string;
    user_category: UserCategory;
    avatar: string;
    position_id;
    constructor() {
    }
  }
  
