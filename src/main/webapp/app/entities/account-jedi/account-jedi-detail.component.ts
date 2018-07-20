import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountJEDI } from 'app/shared/model/account-jedi.model';

@Component({
    selector: 'jhi-account-jedi-detail',
    templateUrl: './account-jedi-detail.component.html'
})
export class AccountJEDIDetailComponent implements OnInit {
    accountJEDI: IAccountJEDI;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ accountJEDI }) => {
            this.accountJEDI = accountJEDI;
        });
    }

    previousState() {
        window.history.back();
    }
}
