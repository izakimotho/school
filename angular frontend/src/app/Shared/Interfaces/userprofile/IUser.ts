import { IRole } from "./IRole";

export interface IUser  {
    id: null | number;
    phone_number: null | number;
    email:string;
    password: string; 
    username: string;
    firstName:string;
    lastName:string;
    avatar: string; 
    user_level: any;  
    organization: any;
    active:boolean;
    role:IRole;
    
  }