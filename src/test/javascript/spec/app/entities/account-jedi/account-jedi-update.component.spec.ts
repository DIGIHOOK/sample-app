/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SampleAppTestModule } from '../../../test.module';
import { AccountJEDIUpdateComponent } from 'app/entities/account-jedi/account-jedi-update.component';
import { AccountJEDIService } from 'app/entities/account-jedi/account-jedi.service';
import { AccountJEDI } from 'app/shared/model/account-jedi.model';

describe('Component Tests', () => {
    describe('AccountJEDI Management Update Component', () => {
        let comp: AccountJEDIUpdateComponent;
        let fixture: ComponentFixture<AccountJEDIUpdateComponent>;
        let service: AccountJEDIService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SampleAppTestModule],
                declarations: [AccountJEDIUpdateComponent]
            })
                .overrideTemplate(AccountJEDIUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AccountJEDIUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccountJEDIService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AccountJEDI(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.accountJEDI = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AccountJEDI();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.accountJEDI = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
