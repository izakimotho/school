import { ISubCounty } from "./ISubCounty";

export interface IWard {
    ward_id: number;
    ward_name: string;
    ward_code: string;
    sub_county: ISubCounty;
  }