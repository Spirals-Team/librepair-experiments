package br.com.reclameaqui.teste.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReclamantesRequestDTO {

	private String empresa;
	
	private String cidade;
	
}