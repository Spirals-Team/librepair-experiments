/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VitrineTestModule } from '../../../test.module';
import { ProdutoDetailComponent } from '../../../../../../main/webapp/app/entities/produto/produto-detail.component';
import { ProdutoService } from '../../../../../../main/webapp/app/entities/produto/produto.service';
import { Produto } from '../../../../../../main/webapp/app/entities/produto/produto.model';

describe('Component Tests', () => {

    describe('Produto Management Detail Component', () => {
        let comp: ProdutoDetailComponent;
        let fixture: ComponentFixture<ProdutoDetailComponent>;
        let service: ProdutoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VitrineTestModule],
                declarations: [ProdutoDetailComponent],
                providers: [
                    ProdutoService
                ]
            })
            .overrideTemplate(ProdutoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProdutoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProdutoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Produto(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.produto).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
