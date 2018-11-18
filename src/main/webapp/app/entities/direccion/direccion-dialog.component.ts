import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Direccion } from './direccion.model';
import { DireccionPopupService } from './direccion-popup.service';
import { DireccionService } from './direccion.service';
import { Comuna, ComunaService } from '../comuna';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-direccion-dialog',
    templateUrl: './direccion-dialog.component.html'
})
export class DireccionDialogComponent implements OnInit {

    direccion: Direccion;
    isSaving: boolean;

    comunas: Comuna[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private direccionService: DireccionService,
        private comunaService: ComunaService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.comunaService
            .query({filter: 'direccion-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.direccion.comunaId) {
                    this.comunas = res.json;
                } else {
                    this.comunaService
                        .find(this.direccion.comunaId)
                        .subscribe((subRes: Comuna) => {
                            this.comunas = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.direccion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.direccionService.update(this.direccion));
        } else {
            this.subscribeToSaveResponse(
                this.direccionService.create(this.direccion));
        }
    }

    private subscribeToSaveResponse(result: Observable<Direccion>) {
        result.subscribe((res: Direccion) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Direccion) {
        this.eventManager.broadcast({ name: 'direccionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackComunaById(index: number, item: Comuna) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-direccion-popup',
    template: ''
})
export class DireccionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private direccionPopupService: DireccionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.direccionPopupService
                    .open(DireccionDialogComponent as Component, params['id']);
            } else {
                this.direccionPopupService
                    .open(DireccionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
