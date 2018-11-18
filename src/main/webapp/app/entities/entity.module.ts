import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TruequepComunaModule } from './comuna/comuna.module';
import { TruequepRegionModule } from './region/region.module';
import { TruequepDireccionModule } from './direccion/direccion.module';
import { TruequepValoracionModule } from './valoracion/valoracion.module';
import { TruequepOfertaModule } from './oferta/oferta.module';
import { TruequepRespuestaOfertaModule } from './respuesta-oferta/respuesta-oferta.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TruequepComunaModule,
        TruequepRegionModule,
        TruequepDireccionModule,
        TruequepValoracionModule,
        TruequepOfertaModule,
        TruequepRespuestaOfertaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TruequepEntityModule {}
