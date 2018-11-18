import { BaseEntity } from './../../shared';

export class Direccion implements BaseEntity {
    constructor(
        public id?: number,
        public direccion?: string,
        public numero?: string,
        public adicional?: string,
        public codigoPostal?: string,
        public comunaId?: number,
        public tipoId?: number,
    ) {
    }
}
