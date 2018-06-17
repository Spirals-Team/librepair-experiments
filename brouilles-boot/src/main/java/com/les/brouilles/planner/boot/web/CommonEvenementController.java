package com.les.brouilles.planner.boot.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.les.brouilles.planner.persistence.model.evenement.CommonEvenement;
import com.les.brouilles.planner.persistence.model.evenement.enums.TypeEvenement;
import com.les.brouilles.planner.service.bean.ClientService;
import com.les.brouilles.planner.service.bean.CommonEvenementService;
import com.les.brouilles.planner.service.dto.CommonEvenementDTO;
import com.les.brouilles.planner.service.exception.BrouillesFormatDateInvalideException;
import com.les.brouilles.planner.service.mapper.evenement.CommonEvenementMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/evenements")
public class CommonEvenementController {

	@Autowired
	private CommonEvenementService service;

	@Autowired
	private ClientService clientService;

	@Autowired
	private CommonEvenementMapper mapper;

	@GetMapping()
	public List<CommonEvenementDTO> getEvenements() {

		// FIXME : faire une recherche par page pour cause de perfs!!

		final List<CommonEvenement> evenements = service.getEvenements();

		// @formatter:off
		return evenements.stream()
				.map(e -> mapper.convertToDTO(e))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@GetMapping("avecInfosClient")
	public List<CommonEvenementDTO> getEvenementsAvecInfoClient() {

		// FIXME : faire une recherche par page pour cause de perfs!!

		final List<CommonEvenement> evenements = service.findAllWithClientFetch();

		// @formatter:off
		return evenements.stream()
				.map(mapWithClientInfos())
				.collect(Collectors.toList());
		// @formatter:on
	}

	@GetMapping("/client/{clientId}")
	public List<CommonEvenementDTO> getEvenementByClientId(@PathVariable(value = "clientId") final Long clientId) {

		log.info("Recherche des événements du client {}", clientId);

		// FIXME : faire une recherche par page pour cause de perfs!!

		final List<CommonEvenement> evenements = service.getEvenementsByClientId(clientId);

		// @formatter:off
		return evenements.stream()
				.map(e -> mapper.convertToDTO(e))
				.collect(Collectors.toList());
		// @formatter:on
	}

	// @formatter:off
	@GetMapping("/range")
	public List<CommonEvenementDTO> getEvenementsInRange(@RequestParam("debut") final String debutAsStr,
			@RequestParam("fin") final String finAsStr) {

		// @formatter:on
		final DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		Date dateMin = null;
		Date dateMax = null;
		try {
			dateMin = formatter.parse(debutAsStr);

		} catch (final ParseException e) {
			throw new BrouillesFormatDateInvalideException(debutAsStr);
		}

		try {
			dateMax = formatter.parse(finAsStr);

		} catch (final ParseException e) {
			throw new BrouillesFormatDateInvalideException(finAsStr);
		}

		dateMax.setHours(23);
		dateMax.setMinutes(59);
		dateMax.setSeconds(59);

		log.info("Recherche des évenements entre {} et {}", dateMin, dateMax);

		final List<CommonEvenement> evenements = service.getEvenementsInRange(dateMin, dateMax);

		// @formatter:off
		return evenements.stream()
				.map(e -> mapper.convertToDTO(e))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@GetMapping("/semaine")
	public List<CommonEvenementDTO> getEvenementsInCurrentWeek() {

		final Calendar calendarLundi = Calendar.getInstance();
		calendarLundi.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendarLundi.set(Calendar.HOUR_OF_DAY, 0);
		calendarLundi.set(Calendar.MINUTE, 0);
		calendarLundi.set(Calendar.SECOND, 0);

		final Date lundi = calendarLundi.getTime();

		final Calendar calendarDimanche = Calendar.getInstance();
		calendarDimanche.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		calendarDimanche.set(Calendar.HOUR_OF_DAY, 23);
		calendarDimanche.set(Calendar.MINUTE, 59);
		calendarDimanche.set(Calendar.SECOND, 59);
		final Date dimanche = calendarDimanche.getTime();

		log.info("Recherche des évenements de la semaine entre lundi {} et dimanche {}", lundi, dimanche);

		final List<CommonEvenement> evenements = service.getEvenementsInRange(lundi, dimanche);

		// @formatter:off
		return evenements.stream()
				.map(e -> mapper.convertToDTO(e))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@GetMapping("/jour")
	public List<CommonEvenementDTO> getEvenementsInCurrentDay() {

		final Calendar todayMinuit = Calendar.getInstance();
		todayMinuit.set(Calendar.HOUR_OF_DAY, 0);
		todayMinuit.set(Calendar.MINUTE, 0);
		todayMinuit.set(Calendar.SECOND, 0);

		final Date debut = todayMinuit.getTime();

		final Calendar todaySoir = Calendar.getInstance();
		todaySoir.set(Calendar.HOUR_OF_DAY, 23);
		todaySoir.set(Calendar.MINUTE, 59);
		todaySoir.set(Calendar.SECOND, 59);
		final Date fin = todaySoir.getTime();

		log.info("Recherche des événements du jour (entre {} et {})", debut, fin);

		final List<CommonEvenement> evenements = service.getEvenementsInRange(debut, fin);

		// @formatter:off
		return evenements.stream()
				.map(e -> mapper.convertToDTO(e))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@GetMapping("/date/{day}")
	public List<CommonEvenementDTO> getEvenementsInDay(@PathVariable(value = "day") final String dayAsStr,
			@RequestParam(value = "types", required = true) final List<TypeEvenement> typeEvenements) {

		final DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		Date debutJournee = null;
		Date finJournee = null;
		try {
			debutJournee = formatter.parse(dayAsStr);
			finJournee = formatter.parse(dayAsStr);

		} catch (final ParseException e) {
			throw new BrouillesFormatDateInvalideException(dayAsStr);
		}

		finJournee.setHours(23);
		finJournee.setMinutes(59);
		finJournee.setSeconds(59);

		log.info("Recherche des événements {} du jour {} ", typeEvenements, debutJournee);

		final List<CommonEvenement> evenements = service.getEvenementsDuJourAvecTypes(debutJournee, finJournee,
				typeEvenements);

		// @formatter:off
		return evenements.stream()
				.map(e -> mapper.convertToDTO(e))
				.collect(Collectors.toList());
		// @formatter:on
	}

	private Function<? super CommonEvenement, ? extends CommonEvenementDTO> mapWithClientInfos() {
		return e -> {
			final CommonEvenementDTO dto = mapper.convertToDTO(e);
			dto.setInfosClient(
					e.getClient().getNumeroClient() + " - " + e.getClient().getNom() + " " + e.getClient().getPrenom());
			return dto;
		};
	}
}