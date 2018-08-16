package JoaoVFG.com.github.entity.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmpresaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer pessoa;
	
	private Integer tipoEmpresa;
	
	private Integer transportadora;
	
	private Integer empresaMatriz;
	

}
