import { ILeadershipType } from "./ILeadershipType";

export interface IPosition {
    position_id: string;
    position_name: string;
    position_holder: ILeadershipType;
    description:  string;    
  }
  
 