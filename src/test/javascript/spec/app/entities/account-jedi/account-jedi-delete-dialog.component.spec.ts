/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SampleAppTestModule } from '../../../test.module';
import { AccountJEDIDeleteDialogComponent } from 'app/entities/account-jedi/account-jedi-delete-dialog.component';
import { AccountJEDIService } from 'app/entities/account-jedi/account-jedi.service';

describe('Component Tests', () => {
    describe('AccountJEDI Management Delete Component', () => {
        let comp: AccountJEDIDeleteDialogComponent;
        let fixture: ComponentFixture<AccountJEDIDeleteDialogComponent>;
        let service: AccountJEDIService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SampleAppTestModule],
                declarations: [AccountJEDIDeleteDialogComponent]
            })
                .overrideTemplate(AccountJEDIDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AccountJEDIDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccountJEDIService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
