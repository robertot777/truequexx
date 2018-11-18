import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { RespuestaOferta } from './respuesta-oferta.model';
import { RespuestaOfertaService } from './respuesta-oferta.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-respuesta-oferta',
    templateUrl: './respuesta-oferta.component.html'
})
export class RespuestaOfertaComponent implements OnInit, OnDestroy {
respuestaOfertas: RespuestaOferta[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private respuestaOfertaService: RespuestaOfertaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.respuestaOfertaService.query().subscribe(
            (res: ResponseWrapper) => {
                this.respuestaOfertas = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRespuestaOfertas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RespuestaOferta) {
        return item.id;
    }
    registerChangeInRespuestaOfertas() {
        this.eventSubscriber = this.eventManager.subscribe('respuestaOfertaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
