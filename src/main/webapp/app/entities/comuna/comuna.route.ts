import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ComunaComponent } from './comuna.component';
import { ComunaDetailComponent } from './comuna-detail.component';
import { ComunaPopupComponent } from './comuna-dialog.component';
import { ComunaDeletePopupComponent } from './comuna-delete-dialog.component';

export const comunaRoute: Routes = [
    {
        path: 'comuna',
        component: ComunaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.comuna.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'comuna/:id',
        component: ComunaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.comuna.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const comunaPopupRoute: Routes = [
    {
        path: 'comuna-new',
        component: ComunaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.comuna.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'comuna/:id/edit',
        component: ComunaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.comuna.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'comuna/:id/delete',
        component: ComunaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'truequepApp.comuna.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
