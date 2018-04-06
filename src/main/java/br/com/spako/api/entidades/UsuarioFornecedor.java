package br.com.spako.api.entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "usuario_fornecedor")
public class UsuarioFornecedor implements Serializable {

    private static final long serialVersionUID = 475918891428093041L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telefone;
    @Column
    private String celular;
//    @JoinColumn(name = "cidade", referencedColumnName = "id")
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Cidade cidade;
    private String cidade;
    private String estado;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    private String endereco;
    @Column(nullable = false)
    private String numero;
    @Column(nullable = false)
    private String cep;
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Usuario usuario;
    @Column(nullable = false)
    private String cnpj;
    @Column(nullable = false)
    private String razao;
    @Column(nullable = false)
    private String nome;
    @Column(name = "atendimento_seg_sex", nullable = false)
    private String atendimentoSegSex;
    @Column(name = "atendimento_sab", nullable = false)
    private String atendimentoSab;
    @Column(length = 500)
    private String descricao;
    @Column()
    private String logo;
    @Column()
    private String email;

    public UsuarioFornecedor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAtendimentoSegSex() {
        return atendimentoSegSex;
    }

    public void setAtendimentoSegSex(String atendimentoSegSex) {
        this.atendimentoSegSex = atendimentoSegSex;
    }

    public String getAtendimentoSab() {
        return atendimentoSab;
    }

    public void setAtendimentoSab(String atendimentoSab) {
        this.atendimentoSab = atendimentoSab;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioFornecedor other = (UsuarioFornecedor) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
