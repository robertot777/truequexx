import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TruequepSharedModule } from '../../shared';
import { TruequepAdminModule } from '../../admin/admin.module';
import {
    ValoracionService,
    ValoracionPopupService,
    ValoracionComponent,
    ValoracionDetailComponent,
    ValoracionDialogComponent,
    ValoracionPopupComponent,
    ValoracionDeletePopupComponent,
    ValoracionDeleteDialogComponent,
    valoracionRoute,
    valoracionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...valoracionRoute,
    ...valoracionPopupRoute,
];

@NgModule({
    imports: [
        TruequepSharedModule,
        TruequepAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ValoracionComponent,
        ValoracionDetailComponent,
        ValoracionDialogComponent,
        ValoracionDeleteDialogComponent,
        ValoracionPopupComponent,
        ValoracionDeletePopupComponent,
    ],
    entryComponents: [
        ValoracionComponent,
        ValoracionDialogComponent,
        ValoracionPopupComponent,
        ValoracionDeleteDialogComponent,
        ValoracionDeletePopupComponent,
    ],
    providers: [
        ValoracionService,
        ValoracionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TruequepValoracionModule {}
