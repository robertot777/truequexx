import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Valoracion } from './valoracion.model';
import { ValoracionService } from './valoracion.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-valoracion',
    templateUrl: './valoracion.component.html'
})
export class ValoracionComponent implements OnInit, OnDestroy {
valoracions: Valoracion[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private valoracionService: ValoracionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.valoracionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.valoracions = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInValoracions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Valoracion) {
        return item.id;
    }
    registerChangeInValoracions() {
        this.eventSubscriber = this.eventManager.subscribe('valoracionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
