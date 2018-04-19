package br.net.itech.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.net.itech.controllers.crud.Create;
import br.net.itech.dtos.DiagnosisDto;
import br.net.itech.entities.Diagnosis;
import br.net.itech.enums.DiagnosticStatus;
import br.net.itech.factories.dtos.DiagnosisDtoFactory;
import br.net.itech.factories.entities.DiagnosisFactory;
import br.net.itech.response.Response;
import br.net.itech.services.DiagnosisService;

@RestController
@RequestMapping(value = "/api/diagnosis")
public class DiagnosisController implements Create<DiagnosisDto> {
	@Autowired
	DiagnosisService diagnosisService;

	@Autowired
	DiagnosisDtoFactory diagnosisDtoFactory;

	@Autowired
	DiagnosisFactory diagnosisFactory;

	private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosisController.class);

	@PostMapping
	@Override
	public ResponseEntity<Response<DiagnosisDto>> create(@Valid @RequestBody DiagnosisDto objectDto,
			BindingResult bindingResult) throws NoSuchAlgorithmException {
		LOGGER.info("Recebendo Diagnostico: {}", objectDto.toString());
		Response<DiagnosisDto> response = new Response<DiagnosisDto>();
		Diagnosis diagnosis = diagnosisFactory.getDiagnosis(objectDto);
		// validadeEnum(diagnosis, objectDto);
		diagnosis = diagnosisService.create(diagnosis);
		objectDto = diagnosisDtoFactory.getObject(diagnosis);
		response.setData(objectDto);
		return ResponseEntity.ok(response);
	}

	private void validadeEnum(Diagnosis diagnosis, DiagnosisDto diagnosisDto) {
		if (EnumUtils.isValidEnum(DiagnosticStatus.class, diagnosisDto.getDiagnosticStatus())) {
			diagnosis.setDiagnosticStatus(DiagnosticStatus.valueOf(diagnosisDto.getDiagnosticStatus()));
		}
	}
}