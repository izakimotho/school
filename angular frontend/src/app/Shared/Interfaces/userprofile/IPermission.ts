
export interface IPermission  {
    permission_id:  number;
    description:string; 
    permission_name:string;  
    user_level:  number;  
    createdAt:  null |string;       
    deletedAt:  null |string;
    checked: boolean; 
 }