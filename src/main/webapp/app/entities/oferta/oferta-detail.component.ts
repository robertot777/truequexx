import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Oferta } from './oferta.model';
import { OfertaService } from './oferta.service';

@Component({
    selector: 'jhi-oferta-detail',
    templateUrl: './oferta-detail.component.html'
})
export class OfertaDetailComponent implements OnInit, OnDestroy {

    oferta: Oferta;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private ofertaService: OfertaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOfertas();
    }

    load(id) {
        this.ofertaService.find(id).subscribe((oferta) => {
            this.oferta = oferta;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOfertas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ofertaListModification',
            (response) => this.load(this.oferta.id)
        );
    }
}
