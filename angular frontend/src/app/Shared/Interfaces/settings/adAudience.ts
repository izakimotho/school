
import { Gender } from './gender';

export class AdAudience {
    id: number | null;
    name:string;
    startAge: Date;//-10
    endAge: Date;//65+
    latitude: number;
    longitude: number;
    radius: number;
    gender: Gender;
    subTopics:[];
    audienceName: string;//-10
    constructor() {
    }
}


