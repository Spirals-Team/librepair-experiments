import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Produto } from './produto.model';
import { ProdutoService } from './produto.service';

@Injectable()
export class ProdutoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private produtoService: ProdutoService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.produtoService.find(id)
                    .subscribe((produtoResponse: HttpResponse<Produto>) => {
                        const produto: Produto = produtoResponse.body;
                        if (produto.dataCadastro) {
                            produto.dataCadastro = {
                                year: produto.dataCadastro.getFullYear(),
                                month: produto.dataCadastro.getMonth() + 1,
                                day: produto.dataCadastro.getDate()
                            };
                        }
                        this.ngbModalRef = this.produtoModalRef(component, produto);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.produtoModalRef(component, new Produto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    produtoModalRef(component: Component, produto: Produto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.produto = produto;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
