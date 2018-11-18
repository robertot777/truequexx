import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RespuestaOfertaComponent } from './respuesta-oferta.component';
import { RespuestaOfertaDetailComponent } from './respuesta-oferta-detail.component';
import { RespuestaOfertaPopupComponent } from './respuesta-oferta-dialog.component';
import { RespuestaOfertaDeletePopupComponent } from './respuesta-oferta-delete-dialog.component';

export const respuestaOfertaRoute: Routes = [
    {
        path: 'respuesta-oferta',
        component: RespuestaOfertaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.respuestaOferta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'respuesta-oferta/:id',
        component: RespuestaOfertaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.respuestaOferta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const respuestaOfertaPopupRoute: Routes = [
    {
        path: 'respuesta-oferta-new',
        component: RespuestaOfertaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.respuestaOferta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'respuesta-oferta/:id/edit',
        component: RespuestaOfertaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.respuestaOferta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'respuesta-oferta/:id/delete',
        component: RespuestaOfertaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.respuestaOferta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
