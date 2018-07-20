import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AccountJEDI } from 'app/shared/model/account-jedi.model';
import { AccountJEDIService } from './account-jedi.service';
import { AccountJEDIComponent } from './account-jedi.component';
import { AccountJEDIDetailComponent } from './account-jedi-detail.component';
import { AccountJEDIUpdateComponent } from './account-jedi-update.component';
import { AccountJEDIDeletePopupComponent } from './account-jedi-delete-dialog.component';
import { IAccountJEDI } from 'app/shared/model/account-jedi.model';

@Injectable({ providedIn: 'root' })
export class AccountJEDIResolve implements Resolve<IAccountJEDI> {
    constructor(private service: AccountJEDIService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((accountJEDI: HttpResponse<AccountJEDI>) => accountJEDI.body));
        }
        return of(new AccountJEDI());
    }
}

export const accountJEDIRoute: Routes = [
    {
        path: 'account-jedi',
        component: AccountJEDIComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sampleAppApp.accountJEDI.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'account-jedi/:id/view',
        component: AccountJEDIDetailComponent,
        resolve: {
            accountJEDI: AccountJEDIResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleAppApp.accountJEDI.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'account-jedi/new',
        component: AccountJEDIUpdateComponent,
        resolve: {
            accountJEDI: AccountJEDIResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleAppApp.accountJEDI.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'account-jedi/:id/edit',
        component: AccountJEDIUpdateComponent,
        resolve: {
            accountJEDI: AccountJEDIResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleAppApp.accountJEDI.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const accountJEDIPopupRoute: Routes = [
    {
        path: 'account-jedi/:id/delete',
        component: AccountJEDIDeletePopupComponent,
        resolve: {
            accountJEDI: AccountJEDIResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sampleAppApp.accountJEDI.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
