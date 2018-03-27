/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VitrineTestModule } from '../../../test.module';
import { ProdutoComponent } from '../../../../../../main/webapp/app/entities/produto/produto.component';
import { ProdutoService } from '../../../../../../main/webapp/app/entities/produto/produto.service';
import { Produto } from '../../../../../../main/webapp/app/entities/produto/produto.model';

describe('Component Tests', () => {

    describe('Produto Management Component', () => {
        let comp: ProdutoComponent;
        let fixture: ComponentFixture<ProdutoComponent>;
        let service: ProdutoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VitrineTestModule],
                declarations: [ProdutoComponent],
                providers: [
                    ProdutoService
                ]
            })
            .overrideTemplate(ProdutoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProdutoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProdutoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Produto(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.produtos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
