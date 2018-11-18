import { BaseEntity } from './../../shared';

export const enum TipoObjeto {
    'ALIMENTACIONBEBIDAS',
    'ARTEANTIGUEDADES',
    'VEHICULOS',
    'CASAHOGAR',
    'COLECCION',
    'DEPORTES',
    'EDUCACION',
    'IMAGENSONIDO',
    'INFORMATICAELECTRONICA',
    'INMOBILIARIA',
    'JOYERIA',
    'JUEGOSJUGUETES',
    'LIBROSREVISTASCOMICS',
    'MUSICA',
    'ROPACALZADO',
    'SALUDBELLEZA',
    'SERVICIOS',
    'MANOOBRA',
    'TELEFONIA',
    'TIEMPOLIBRE'
}

export class Oferta implements BaseEntity {
    constructor(
        public id?: number,
        public generarOferta?: string,
        public tipoObjeto?: TipoObjeto,
        public generaOfertaContentType?: string,
        public generaOferta?: any,
        public clienteId?: number,
        public estadoOfertas?: BaseEntity[],
    ) {
    }
}
