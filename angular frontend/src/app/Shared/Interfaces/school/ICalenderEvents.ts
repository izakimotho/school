import { ICalender } from "./ICalender";

export interface ICalenderEvents {
    calendar_event_id: string;
    calendar_event: string;
    event_date: string;
    calendar_item: ICalender;
    createAt: string;
    deletedAt: string;
}