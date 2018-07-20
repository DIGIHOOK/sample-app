import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAccountJEDI } from 'app/shared/model/account-jedi.model';
import { AccountJEDIService } from './account-jedi.service';

@Component({
    selector: 'jhi-account-jedi-update',
    templateUrl: './account-jedi-update.component.html'
})
export class AccountJEDIUpdateComponent implements OnInit {
    private _accountJEDI: IAccountJEDI;
    isSaving: boolean;

    constructor(private accountJEDIService: AccountJEDIService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ accountJEDI }) => {
            this.accountJEDI = accountJEDI;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.accountJEDI.id !== undefined) {
            this.subscribeToSaveResponse(this.accountJEDIService.update(this.accountJEDI));
        } else {
            this.subscribeToSaveResponse(this.accountJEDIService.create(this.accountJEDI));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAccountJEDI>>) {
        result.subscribe((res: HttpResponse<IAccountJEDI>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get accountJEDI() {
        return this._accountJEDI;
    }

    set accountJEDI(accountJEDI: IAccountJEDI) {
        this._accountJEDI = accountJEDI;
    }
}
