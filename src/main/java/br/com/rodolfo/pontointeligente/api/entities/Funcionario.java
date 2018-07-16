package br.com.rodolfo.pontointeligente.api.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.rodolfo.pontointeligente.api.enums.PerfilEnum;

/**
 * Funcionario
 */
@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable{

	private static final long serialVersionUID = -2020484172424836670L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "valor_hora", nullable = false)
    private BigDecimal valorHora = new BigDecimal(0);

    @Column(name = "qtd_horas_trabalho_dia", nullable = false)
    private Float qtdHorasTrabalhoDia = Float.valueOf(0);

    @Column(name = "qtd_horas_almoco", nullable = false)
    private Float qtdHorasAlmoco = Float.valueOf(0);

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    private PerfilEnum perfil;

    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private Date dataAtualizacao;

    //Eager --> sempre que carregar os funcionários já carregar a empresa
    @ManyToOne(fetch = FetchType.EAGER)
    private Empresa empresa;

    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lancamento> lancamentos;

    public Funcionario() {}
    
    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return String return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return String return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Transient
    public Optional<BigDecimal> getValorHoraOpt() {

        return Optional.ofNullable(valorHora);
    }

    /**
     * @return BigDecimal return the valorHora
     */
    public BigDecimal getValorHora() {
        return valorHora;
    }

    /**
     * @param valorHora the valorHora to set
     */
    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    @Transient
    public Optional<Float> getQtdHorasTrabalhoDiaOpt() {

        return Optional.ofNullable(qtdHorasTrabalhoDia);
    }

    /**
     * @return Float return the qtdHorasTrabalhoDia
     */
    public Float getQtdHorasTrabalhoDia() {
        return qtdHorasTrabalhoDia;
    }

    /**
     * @param qtdHorasTrabalhoDia the qtdHorasTrabalhoDia to set
     */
    public void setQtdHorasTrabalhoDia(Float qtdHorasTrabalhoDia) {
        this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
    }

    @Transient
    public Optional<Float> getQtdHorasAlmocoOpt() {
        
        return Optional.ofNullable(qtdHorasAlmoco);
    }

    /**
     * @return Float return the qtdHorasAlmoco
     */
    public Float getQtdHorasAlmoco() {
        return qtdHorasAlmoco;
    }

    /**
     * @param qtdHorasAlmoco the qtdHorasAlmoco to set
     */
    public void setQtdHorasAlmoco(Float qtdHorasAlmoco) {
        this.qtdHorasAlmoco = qtdHorasAlmoco;
    }

    /**
     * @return PerfilEnum return the perfil
     */
    public PerfilEnum getPerfil() {
        return perfil;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(PerfilEnum perfil) {
        this.perfil = perfil;
    }

    /**
     * @return Date return the dataCriacao
     */
    public Date getDataCriacao() {
        return dataCriacao;
    }

    /**
     * @param dataCriacao the dataCriacao to set
     */
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    /**
     * @return Date return the dataAtualizacao
     */
    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    /**
     * @param dataAtualizacao the dataAtualizacao to set
     */
    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    /**
     * @return Empresa return the empresa
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /**
     * @return List<Lancamento> return the lancamentos
     */
    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    /**
     * @param lancamentos the lancamentos to set
     */
    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    @PreUpdate
    public void preUpdate() {
        
        dataAtualizacao = new Date();
    }

    @PrePersist
    public void prePersist() {
        
        final Date atual = new Date();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }

    @Override
    public String toString() {
        return "Funcionario [ id = " + id + ", nome = " + nome + ", email = " + email + ", senha = " + senha + ", cpf = " + cpf + ", valorHora = " + valorHora + ", qtdHorasTrabalhoDia = " + qtdHorasTrabalhoDia + ", qtdHorasAlmoco = " + qtdHorasAlmoco + ", perfil = " + perfil + ", dataCriacao = " + dataCriacao + ", dataAtualizacao = " + dataAtualizacao + ", empresa = " + empresa + " ]";
    }

}