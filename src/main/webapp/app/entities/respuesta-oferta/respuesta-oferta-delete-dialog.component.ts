import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RespuestaOferta } from './respuesta-oferta.model';
import { RespuestaOfertaPopupService } from './respuesta-oferta-popup.service';
import { RespuestaOfertaService } from './respuesta-oferta.service';

@Component({
    selector: 'jhi-respuesta-oferta-delete-dialog',
    templateUrl: './respuesta-oferta-delete-dialog.component.html'
})
export class RespuestaOfertaDeleteDialogComponent {

    respuestaOferta: RespuestaOferta;

    constructor(
        private respuestaOfertaService: RespuestaOfertaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.respuestaOfertaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'respuestaOfertaListModification',
                content: 'Deleted an respuestaOferta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-respuesta-oferta-delete-popup',
    template: ''
})
export class RespuestaOfertaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private respuestaOfertaPopupService: RespuestaOfertaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.respuestaOfertaPopupService
                .open(RespuestaOfertaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
