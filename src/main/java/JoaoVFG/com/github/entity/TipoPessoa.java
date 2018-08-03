package JoaoVFG.com.github.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;

@Getter
@Entity
public class TipoPessoa implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	
	private String descricao;
	
	
}
