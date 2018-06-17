package com.les.brouilles.planner.service.devis.json;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.les.brouilles.planner.service.devis.json.propriete.ProprietePrix;

public class JsonDevisMariageTest {

	@Test
	public void test() throws IOException {
		final String json = IOUtils.toString(this.getClass().getResourceAsStream("/json/devis-mariage.json"), "UTF-8");

		final JsonDevisMariage devisJson = mapperJsondevis().readValue(json, JsonDevisMariage.class);
		final ProprietePrix remiseTraiteurPlus70P = devisJson.getProprietes().get("remiseTraiteurPlus70P");

		// @formatter:off
		final ProprietePrix attendu = ProprietePrix.builder()
			.quantite(1)
			.prixUnitaireHT(BigDecimal.valueOf(-333.33))
			.prixUnitaireTTC(BigDecimal.valueOf(-400))
			.prixTotalHT(BigDecimal.valueOf(-333.33))
			.prixTotalTTC(BigDecimal.valueOf(-400))
			.build();
		// @formatter:on
		assertEquals(attendu, remiseTraiteurPlus70P);
	}

	private ObjectMapper mapperJsondevis() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		// Fail if unknown!
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

		mapper.registerModule(new JavaTimeModule());
		mapper.registerModule(new Jdk8Module());
		return mapper;
	}

}
