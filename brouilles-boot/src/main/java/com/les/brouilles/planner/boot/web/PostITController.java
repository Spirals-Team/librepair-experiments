package com.les.brouilles.planner.boot.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.les.brouilles.planner.persistence.model.PostIT;
import com.les.brouilles.planner.service.bean.PostITService;
import com.les.brouilles.planner.service.dto.PostITDTO;
import com.les.brouilles.planner.service.exception.BrouillesPostItNonTrouveException;
import com.les.brouilles.planner.service.mapper.postit.PostITMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/postit")
public class PostITController {

	@Autowired
	private PostITService service;

	@Autowired
	private PostITMapper mapper;

	@GetMapping()
	public List<PostITDTO> getPostits() {

		// FIXME : faire une recherche par page pour cause de perfs!!

		final List<PostIT> postits = service.getPostIts();

		// @formatter:off
		return postits.stream()
				.map(e -> mapper.convertToDTO(e))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	PostITDTO create(@RequestBody @Valid final PostITDTO postITDTO) {

		log.info("Creation de l'evenement postITDTO:{}", postITDTO);

		// FIXME : gérer le cas où il manque des champs!!
		final PostIT postIT = mapper.convertToEntity(postITDTO);

		final PostIT created = service.create(postIT);

		return mapper.convertToDTO(created);
	}

	@GetMapping("/jour")
	public List<PostITDTO> getPostItsDuJour() {

		final Calendar todayMinuit = Calendar.getInstance();
		todayMinuit.set(Calendar.HOUR_OF_DAY, 0);
		todayMinuit.set(Calendar.MINUTE, 0);
		todayMinuit.set(Calendar.SECOND, 0);

		final Date dateMin = todayMinuit.getTime();

		final Calendar todaySoir = Calendar.getInstance();
		todaySoir.set(Calendar.HOUR_OF_DAY, 23);
		todaySoir.set(Calendar.MINUTE, 59);
		todaySoir.set(Calendar.SECOND, 59);
		final Date dateMax = todaySoir.getTime();

		log.info("Recherche des post-it du jour (entre {} et {})", dateMin, dateMax);

		final List<PostIT> evenements = service.getPostItWithDateInRange(dateMin, dateMax);

		// @formatter:off
		return evenements.stream()
				.map(e -> mapper.convertToDTO(e))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@PutMapping
	public PostITDTO update(@RequestBody @Valid final PostITDTO postItDTO) {

		log.info("Mise à jour du post-it:{}", postItDTO);

		final PostIT postIt = service.getPostItById(postItDTO.getId());
		if (null == postIt) {
			throw new BrouillesPostItNonTrouveException(postItDTO.getId());
		}

		final PostIT withUpdate = mapper.convertToEntity(postItDTO);

		return mapper.convertToDTO(service.update(withUpdate));

	}

	@GetMapping("/{id}")
	public PostITDTO getById(@PathVariable(value = "id") final Long id) {

		final PostIT postit = service.getPostItById(id);
		if (null == postit) {
			throw new BrouillesPostItNonTrouveException(id);
		}

		return mapper.convertToDTO(postit);
	}

	@DeleteMapping("{id}")
	void delete(@PathVariable(value = "id") final Long id) {

		log.info("Suppression du post-it avec Id:{}", id);

		service.delete(id);
	}
}