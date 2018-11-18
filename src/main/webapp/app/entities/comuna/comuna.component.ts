import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Comuna } from './comuna.model';
import { ComunaService } from './comuna.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-comuna',
    templateUrl: './comuna.component.html'
})
export class ComunaComponent implements OnInit, OnDestroy {
comunas: Comuna[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private comunaService: ComunaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.comunaService.query().subscribe(
            (res: ResponseWrapper) => {
                this.comunas = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInComunas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Comuna) {
        return item.id;
    }
    registerChangeInComunas() {
        this.eventSubscriber = this.eventManager.subscribe('comunaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
