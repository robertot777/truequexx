import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ValoracionComponent } from './valoracion.component';
import { ValoracionDetailComponent } from './valoracion-detail.component';
import { ValoracionPopupComponent } from './valoracion-dialog.component';
import { ValoracionDeletePopupComponent } from './valoracion-delete-dialog.component';

export const valoracionRoute: Routes = [
    {
        path: 'valoracion',
        component: ValoracionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.valoracion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'valoracion/:id',
        component: ValoracionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.valoracion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const valoracionPopupRoute: Routes = [
    {
        path: 'valoracion-new',
        component: ValoracionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.valoracion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'valoracion/:id/edit',
        component: ValoracionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.valoracion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'valoracion/:id/delete',
        component: ValoracionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.valoracion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
