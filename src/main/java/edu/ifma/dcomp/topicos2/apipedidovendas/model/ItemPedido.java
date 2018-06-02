package edu.ifma.dcomp.topicos2.apipedidovendas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "item_pedido")
public class ItemPedido {

    @EmbeddedId
    private ItemPedidoPK id;

    private Integer quantidade;
    private BigDecimal valor;
    private BigDecimal desconto;

    @JsonIgnore
    public Pedido getPedido() {
        return id.getPedido();
    }


    public Produto getProduto() {
        return id.getProduto();
    }


    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return this.valor;
    }


// explicar por que não precisamos deste método
/*
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

*/
    @PrePersist
    private void prePersist() {
        this.valor = id.getProduto().getPreco();
    }

    public BigDecimal getSubTotal() {

        return valor
                .multiply(new BigDecimal(quantidade))
                .subtract(desconto );

    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemPedido)) return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
