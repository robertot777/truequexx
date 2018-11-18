import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RespuestaOferta } from './respuesta-oferta.model';
import { RespuestaOfertaPopupService } from './respuesta-oferta-popup.service';
import { RespuestaOfertaService } from './respuesta-oferta.service';
import { User, UserService } from '../../shared';
import { Oferta, OfertaService } from '../oferta';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-respuesta-oferta-dialog',
    templateUrl: './respuesta-oferta-dialog.component.html'
})
export class RespuestaOfertaDialogComponent implements OnInit {

    respuestaOferta: RespuestaOferta;
    isSaving: boolean;

    users: User[];

    ofertas: Oferta[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private respuestaOfertaService: RespuestaOfertaService,
        private userService: UserService,
        private ofertaService: OfertaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.ofertaService.query()
            .subscribe((res: ResponseWrapper) => { this.ofertas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.respuestaOferta.id !== undefined) {
            this.subscribeToSaveResponse(
                this.respuestaOfertaService.update(this.respuestaOferta));
        } else {
            this.subscribeToSaveResponse(
                this.respuestaOfertaService.create(this.respuestaOferta));
        }
    }

    private subscribeToSaveResponse(result: Observable<RespuestaOferta>) {
        result.subscribe((res: RespuestaOferta) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: RespuestaOferta) {
        this.eventManager.broadcast({ name: 'respuestaOfertaListModification', content: 'OK'});
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

    trackOfertaById(index: number, item: Oferta) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-respuesta-oferta-popup',
    template: ''
})
export class RespuestaOfertaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private respuestaOfertaPopupService: RespuestaOfertaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.respuestaOfertaPopupService
                    .open(RespuestaOfertaDialogComponent as Component, params['id']);
            } else {
                this.respuestaOfertaPopupService
                    .open(RespuestaOfertaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
