package br.net.itech.factories.dtos;

import org.springframework.stereotype.Component;

import br.net.itech.dtos.DiagnosisDto;
import br.net.itech.entities.Diagnosis;

@Component
public class DiagnosisDtoFactory {
	public DiagnosisDto getObject(Diagnosis diagnosis) {
		DiagnosisDto diagnosisDto = new DiagnosisDto();
		diagnosisDto.setId(String.valueOf(diagnosis.getId()));
		diagnosisDto.setDescription(diagnosis.getDescription());
		diagnosisDto.setDiagnosticStatus(diagnosis.getDiagnosticStatus().toString());
		return diagnosisDto;
	}
}