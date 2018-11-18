import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Valoracion } from './valoracion.model';
import { ValoracionPopupService } from './valoracion-popup.service';
import { ValoracionService } from './valoracion.service';

@Component({
    selector: 'jhi-valoracion-delete-dialog',
    templateUrl: './valoracion-delete-dialog.component.html'
})
export class ValoracionDeleteDialogComponent {

    valoracion: Valoracion;

    constructor(
        private valoracionService: ValoracionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.valoracionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'valoracionListModification',
                content: 'Deleted an valoracion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-valoracion-delete-popup',
    template: ''
})
export class ValoracionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private valoracionPopupService: ValoracionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.valoracionPopupService
                .open(ValoracionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
