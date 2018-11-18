import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Comuna } from './comuna.model';
import { ComunaPopupService } from './comuna-popup.service';
import { ComunaService } from './comuna.service';
import { Region, RegionService } from '../region';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-comuna-dialog',
    templateUrl: './comuna-dialog.component.html'
})
export class ComunaDialogComponent implements OnInit {

    comuna: Comuna;
    isSaving: boolean;

    regions: Region[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private comunaService: ComunaService,
        private regionService: RegionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.regionService.query()
            .subscribe((res: ResponseWrapper) => { this.regions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.comuna.id !== undefined) {
            this.subscribeToSaveResponse(
                this.comunaService.update(this.comuna));
        } else {
            this.subscribeToSaveResponse(
                this.comunaService.create(this.comuna));
        }
    }

    private subscribeToSaveResponse(result: Observable<Comuna>) {
        result.subscribe((res: Comuna) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Comuna) {
        this.eventManager.broadcast({ name: 'comunaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRegionById(index: number, item: Region) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-comuna-popup',
    template: ''
})
export class ComunaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private comunaPopupService: ComunaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.comunaPopupService
                    .open(ComunaDialogComponent as Component, params['id']);
            } else {
                this.comunaPopupService
                    .open(ComunaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
