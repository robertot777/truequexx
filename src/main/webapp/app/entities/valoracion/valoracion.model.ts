import { BaseEntity } from './../../shared';

export const enum TipoValoracion {
    'EXELENTE',
    'BUENA',
    'REGULAR',
    'POCOSERIO',
    'PESIMO'
}

export class Valoracion implements BaseEntity {
    constructor(
        public id?: number,
        public tipoValoracion?: TipoValoracion,
        public nombreId?: number,
    ) {
    }
}
