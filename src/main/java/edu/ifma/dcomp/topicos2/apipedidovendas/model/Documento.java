package edu.ifma.dcomp.topicos2.apipedidovendas.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
abstract class Documento {

    @Column(name = "doc_receita_federal")
    private String numero;

    private TipoDocumento tipo;



}
