import { IPermission } from "./IPermission";

export interface IRole  {
  role_id: null | number;
  role_name: null | number;
  description:string;  
  permissions: IPermission[];   
    
  }
  

