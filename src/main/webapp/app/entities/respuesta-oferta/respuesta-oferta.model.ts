import { BaseEntity } from './../../shared';

export const enum OfertaRespuesta {
    'ACEPTA',
    'NOACEPTA',
    'NODISPONIBLE'
}

export class RespuestaOferta implements BaseEntity {
    constructor(
        public id?: number,
        public aceptar?: OfertaRespuesta,
        public generaRespustaId?: number,
        public ofertas?: BaseEntity[],
    ) {
    }
}
