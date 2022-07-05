import {Topic} from './topics';
export class SubTopic {
    id: string | null;
    name:string;
    followersCount:number = 0;
    createdAt: any;
    active:boolean= true;
    topic:Topic;
    constructor() {
    }
}
