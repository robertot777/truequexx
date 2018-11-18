import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Valoracion } from './valoracion.model';
import { ValoracionPopupService } from './valoracion-popup.service';
import { ValoracionService } from './valoracion.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-valoracion-dialog',
    templateUrl: './valoracion-dialog.component.html'
})
export class ValoracionDialogComponent implements OnInit {

    valoracion: Valoracion;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private valoracionService: ValoracionService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.valoracion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.valoracionService.update(this.valoracion));
        } else {
            this.subscribeToSaveResponse(
                this.valoracionService.create(this.valoracion));
        }
    }

    private subscribeToSaveResponse(result: Observable<Valoracion>) {
        result.subscribe((res: Valoracion) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Valoracion) {
        this.eventManager.broadcast({ name: 'valoracionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-valoracion-popup',
    template: ''
})
export class ValoracionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private valoracionPopupService: ValoracionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.valoracionPopupService
                    .open(ValoracionDialogComponent as Component, params['id']);
            } else {
                this.valoracionPopupService
                    .open(ValoracionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
