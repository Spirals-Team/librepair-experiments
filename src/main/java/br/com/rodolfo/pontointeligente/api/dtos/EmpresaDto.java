package br.com.rodolfo.pontointeligente.api.dtos;

/**
 * EmpresaDto
 */
public class EmpresaDto {

    private Long id;
    private String razaoSocial;
    private String cnpj;

    public EmpresaDto() {}

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
     * @return String return the razaoSocial
     */
    public String getRazaoSocial() {
        return razaoSocial;
    }

    /**
     * @param razaoSocial the razaoSocial to set
     */
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    /**
     * @return String return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "EmpresaDto [ id = " + id + ", razaoSocial = " + razaoSocial + ", cnpj = " + cnpj + " ]";
    }

}