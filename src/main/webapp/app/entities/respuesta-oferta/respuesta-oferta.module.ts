import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TruequepSharedModule } from '../../shared';
import { TruequepAdminModule } from '../../admin/admin.module';
import {
    RespuestaOfertaService,
    RespuestaOfertaPopupService,
    RespuestaOfertaComponent,
    RespuestaOfertaDetailComponent,
    RespuestaOfertaDialogComponent,
    RespuestaOfertaPopupComponent,
    RespuestaOfertaDeletePopupComponent,
    RespuestaOfertaDeleteDialogComponent,
    respuestaOfertaRoute,
    respuestaOfertaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...respuestaOfertaRoute,
    ...respuestaOfertaPopupRoute,
];

@NgModule({
    imports: [
        TruequepSharedModule,
        TruequepAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RespuestaOfertaComponent,
        RespuestaOfertaDetailComponent,
        RespuestaOfertaDialogComponent,
        RespuestaOfertaDeleteDialogComponent,
        RespuestaOfertaPopupComponent,
        RespuestaOfertaDeletePopupComponent,
    ],
    entryComponents: [
        RespuestaOfertaComponent,
        RespuestaOfertaDialogComponent,
        RespuestaOfertaPopupComponent,
        RespuestaOfertaDeleteDialogComponent,
        RespuestaOfertaDeletePopupComponent,
    ],
    providers: [
        RespuestaOfertaService,
        RespuestaOfertaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TruequepRespuestaOfertaModule {}
