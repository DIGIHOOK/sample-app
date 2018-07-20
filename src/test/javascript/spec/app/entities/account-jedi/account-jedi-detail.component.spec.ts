/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SampleAppTestModule } from '../../../test.module';
import { AccountJEDIDetailComponent } from 'app/entities/account-jedi/account-jedi-detail.component';
import { AccountJEDI } from 'app/shared/model/account-jedi.model';

describe('Component Tests', () => {
    describe('AccountJEDI Management Detail Component', () => {
        let comp: AccountJEDIDetailComponent;
        let fixture: ComponentFixture<AccountJEDIDetailComponent>;
        const route = ({ data: of({ accountJEDI: new AccountJEDI(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SampleAppTestModule],
                declarations: [AccountJEDIDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AccountJEDIDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AccountJEDIDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.accountJEDI).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
