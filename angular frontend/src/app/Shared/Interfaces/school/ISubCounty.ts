import { ICounty } from "./ICounty";

export interface ISubCounty {
    sub_county_id: number;
    sub_county_name: string;
    sub_code: string;
    county: ICounty;
  }