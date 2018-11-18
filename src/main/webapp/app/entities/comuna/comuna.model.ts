import { BaseEntity } from './../../shared';

export class Comuna implements BaseEntity {
    constructor(
        public id?: number,
        public nombreComuna?: string,
        public regionId?: number,
    ) {
    }
}
