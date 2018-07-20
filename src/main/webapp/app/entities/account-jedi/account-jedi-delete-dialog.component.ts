import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountJEDI } from 'app/shared/model/account-jedi.model';
import { AccountJEDIService } from './account-jedi.service';

@Component({
    selector: 'jhi-account-jedi-delete-dialog',
    templateUrl: './account-jedi-delete-dialog.component.html'
})
export class AccountJEDIDeleteDialogComponent {
    accountJEDI: IAccountJEDI;

    constructor(
        private accountJEDIService: AccountJEDIService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.accountJEDIService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'accountJEDIListModification',
                content: 'Deleted an accountJEDI'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-account-jedi-delete-popup',
    template: ''
})
export class AccountJEDIDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ accountJEDI }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AccountJEDIDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.accountJEDI = accountJEDI;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
