package br.com.rodolfo.pontointeligente.api.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * LancamentoDto
 */
public class LancamentoDto {

    private Optional<Long> id = Optional.empty();
    
    @NotEmpty(message = "Data não pode ser vazia.")
    private String data;
    private String tipo;
    private String descricao;
    private String localizacao;
    private Long funcionarioId;

    public LancamentoDto() {}
    
    /**
     * @return Optional<Long> return the id
     */
    public Optional<Long> getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Optional<Long> id) {
        this.id = id;
    }

    /**
     * @return String return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return String return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return String return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return String return the localizacao
     */
    public String getLocalizacao() {
        return localizacao;
    }

    /**
     * @param localizacao the localizacao to set
     */
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    /**
     * @return Long return the funcionarioId
     */
    public Long getFuncionarioId() {
        return funcionarioId;
    }

    /**
     * @param funcionarioId the funcionarioId to set
     */
    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    @Override
    public String toString() {
        return "LancamentoDto [ id = " + id + ", data = " + data + ", tipo = " + tipo + ", descricao = " + descricao + ", localizacao = " + localizacao + ", funcionarioId = " + funcionarioId + " ]";
    }

}