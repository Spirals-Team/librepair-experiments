package com.les.brouilles.planner.service.constraint;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.les.brouilles.planner.persistence.model.devis.Devis.TypeDevis;
import com.les.brouilles.planner.service.devis.json.BaseJsonDevis;
import com.les.brouilles.planner.service.dto.DevisDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DevisJsonContentValidator {

	@Autowired
	private List<DevisJsonMapper> devisJsonMappers;

	@Autowired
	@Qualifier("mapperJsondevis")
	private ObjectMapper objectMapper;

	@Autowired
	private Validator validator;

	private Map<TypeDevis, DevisJsonMapper> mappersByType;

	@PostConstruct
	public void register() {
		log.info("Registering Json Devis mappers : {}", devisJsonMappers);

		mappersByType = new HashMap<>(devisJsonMappers.size());
		devisJsonMappers.forEach(m -> mappersByType.put(m.typeDevis(), m));
	}

	public boolean validate(final DevisDTO devis) {

		boolean valid = false;

		final DevisJsonMapper jsonMapper = mappersByType.get(TypeDevis.valueOf(devis.getTypeDevis()));

		if (jsonMapper != null) {
			try {

				final BaseJsonDevis devisJson = objectMapper.readValue(devis.getJson(), jsonMapper.mappedClass());
				final Set<ConstraintViolation<BaseJsonDevis>> violations = validator.validate(devisJson);

				// Validate json format
				if (!CollectionUtils.isEmpty(violations)) {
					valid = false;
					for (final ConstraintViolation<BaseJsonDevis> violation : violations) {
						log.error("Constraint violation on devis json {}. Error is {}", devis.getJson(),
								getFormattedMessage(violation));
					}
				} else {
					valid = true;
				}

				// @formatter:off
				
//				} else {
//					final Set<String> proprietesValides = jsonMapper.proprietesValides();
//					if (null != proprietesValides && !proprietesValides.isEmpty()) {
//
//						if (proprietesValides.containsAll(devisJson.getProprietes().keySet())) {
//							log.info("Devis json valid");
//							// Validate property
//							valid = true;
//						} else {
//							log.error("Constraint violation on devis json {}. Proprietes {} are not all valides",
//									devis.getJson(), devisJson.getProprietes());
//						}
//					} else {
//						log.error("Constraint violation on devis json {}. Proprietes valides is empty",
//								devis.getJson());
//					}
//
//				}

				// @formatter:on

			} catch (JsonMappingException | JsonParseException e) {
				log.error("Error checking json devis: ", e);
				valid = false;

			} catch (final IOException e) {
				log.error("Error checking json devis: ", e);
				valid = false;
			}
		}

		return valid;
	}

	private String getFormattedMessage(final ConstraintViolation<BaseJsonDevis> constraintViolation) {
		return MessageFormat.format("{0} property {1} {2}", constraintViolation.getPropertyPath(),
				constraintViolation.getInvalidValue(), constraintViolation.getMessage());
	}
}
