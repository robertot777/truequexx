import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OfertaComponent } from './oferta.component';
import { OfertaDetailComponent } from './oferta-detail.component';
import { OfertaPopupComponent } from './oferta-dialog.component';
import { OfertaDeletePopupComponent } from './oferta-delete-dialog.component';

export const ofertaRoute: Routes = [
    {
        path: 'oferta',
        component: OfertaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.oferta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'oferta/:id',
        component: OfertaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.oferta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ofertaPopupRoute: Routes = [
    {
        path: 'oferta-new',
        component: OfertaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.oferta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oferta/:id/edit',
        component: OfertaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.oferta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'oferta/:id/delete',
        component: OfertaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.oferta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
