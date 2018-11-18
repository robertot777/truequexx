import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Oferta } from './oferta.model';
import { OfertaPopupService } from './oferta-popup.service';
import { OfertaService } from './oferta.service';
import { User, UserService } from '../../shared';
import { RespuestaOferta, RespuestaOfertaService } from '../respuesta-oferta';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-oferta-dialog',
    templateUrl: './oferta-dialog.component.html'
})
export class OfertaDialogComponent implements OnInit {

    oferta: Oferta;
    isSaving: boolean;

    users: User[];

    respuestaofertas: RespuestaOferta[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private ofertaService: OfertaService,
        private userService: UserService,
        private respuestaOfertaService: RespuestaOfertaService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.respuestaOfertaService.query()
            .subscribe((res: ResponseWrapper) => { this.respuestaofertas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.oferta, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.oferta.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ofertaService.update(this.oferta));
        } else {
            this.subscribeToSaveResponse(
                this.ofertaService.create(this.oferta));
        }
    }

    private subscribeToSaveResponse(result: Observable<Oferta>) {
        result.subscribe((res: Oferta) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Oferta) {
        this.eventManager.broadcast({ name: 'ofertaListModification', content: 'OK'});
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

    trackRespuestaOfertaById(index: number, item: RespuestaOferta) {
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
    selector: 'jhi-oferta-popup',
    template: ''
})
export class OfertaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ofertaPopupService: OfertaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ofertaPopupService
                    .open(OfertaDialogComponent as Component, params['id']);
            } else {
                this.ofertaPopupService
                    .open(OfertaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
