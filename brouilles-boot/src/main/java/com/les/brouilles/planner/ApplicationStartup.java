package com.les.brouilles.planner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import com.les.brouilles.planner.service.bean.CompteurService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	private static final String BANNER_PATH = "demarrage/banniere-demarrage.txt";

	@Autowired
	private CompteurService compteurService;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		// Insert compteur
		compteurService.insertCurrentAndNextYearCompteurIfNeeded();

		String content = "";

		content = getBanner(BANNER_PATH);

		log.info("\n" + content);

	}

	private String getBanner(final String name) {

		String content = "";
		final ClassPathResource cpr = new ClassPathResource(name);
		try {
			final byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
			content = new String(bdata, StandardCharsets.UTF_8);
		} catch (final IOException e) {
			log.error("Error à la lecture du fichier banner", e);
		}

		return content;
	}

}