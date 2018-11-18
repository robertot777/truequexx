import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Oferta } from './oferta.model';
import { OfertaPopupService } from './oferta-popup.service';
import { OfertaService } from './oferta.service';

@Component({
    selector: 'jhi-oferta-delete-dialog',
    templateUrl: './oferta-delete-dialog.component.html'
})
export class OfertaDeleteDialogComponent {

    oferta: Oferta;

    constructor(
        private ofertaService: OfertaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ofertaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ofertaListModification',
                content: 'Deleted an oferta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-oferta-delete-popup',
    template: ''
})
export class OfertaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ofertaPopupService: OfertaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ofertaPopupService
                .open(OfertaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
