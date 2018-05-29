package dev.paie.entite;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import dev.paie.util.PaieUtils;

@Entity
public class Grade {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String code;
	private BigDecimal nbHeuresBase;
	private BigDecimal tauxBase;
	
	@OneToMany(mappedBy="grade")
	private List<RemunerationEmploye> remuneration;
	
	@Transient
	PaieUtils pa = new PaieUtils();
	
	public String toString() {
		String codeaff =getCode();
		codeaff=codeaff.substring(codeaff.length() - 1);
		String result = "Code "+codeaff +" - "+ pa.formaterBigDecimal(getTauxBase().multiply(getNbHeuresBase()).multiply(new BigDecimal(12)))+" €";
		
		return result;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BigDecimal getNbHeuresBase() {
		return nbHeuresBase;
	}
	public void setNbHeuresBase(BigDecimal nbHeuresBase) {
		this.nbHeuresBase = nbHeuresBase;
	}
	public BigDecimal getTauxBase() {
		return tauxBase;
	}
	public void setTauxBase(BigDecimal tauxBase) {
		this.tauxBase = tauxBase;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
