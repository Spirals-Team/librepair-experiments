package br.com.reclameaqui.teste.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReclamantesResponseDTO {

	private int clientesQueReclamaram;
	
	private List<ClienteDTO> clientes;
}