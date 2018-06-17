package com.les.brouilles.planner.service.mapper.evenement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.les.brouilles.planner.persistence.model.evenement.CommonEvenement;
import com.les.brouilles.planner.service.converter.HourMinuteConverter;
import com.les.brouilles.planner.service.dto.CommonEvenementDTO;
import com.les.brouilles.planner.service.mapper.Mapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Mapper de date pour gérer les heures et date stockées dans le même champs en
 * BDD mais en champs séparés dans le Json/Angular
 *
 * @author julien
 *
 */
@Slf4j
public abstract class EvenementMapper<EntityT extends CommonEvenement, DtoT extends CommonEvenementDTO>
		implements Mapper<EntityT, DtoT> {

	@Autowired
	private ModelMapper modelMapper;

	// --------------------- public methods ------------------------

	@Override
	public EntityT convertToEntity(final DtoT dto) {

		log.debug("Conversion du dto evenement {} en entity", dto);

		final EntityT entity = modelMapper.map(dto, getEntityClass());

		updateEntityDates(dto, entity);

		log.debug("Dto convertit en entity {}", entity);

		return entity;
	}

	@Override
	public DtoT convertToDTO(final EntityT entity) {

		log.debug("Conversion de l'entity evenement {} en dto", entity);

		final DtoT dto = modelMapper.map(entity, getDtoClass());

		updateDTODates(entity, dto);

		log.debug("Entity convertit en dto {}", dto);

		return dto;
	}

	// --------------------- abstract methods ------------------------

	protected abstract Class<EntityT> getEntityClass();

	protected abstract Class<DtoT> getDtoClass();

	// --------------------- protected methods ------------------------

	protected void updateEntityDates(final DtoT dto, final EntityT entity) {

		entity.setDebut(HourMinuteConverter.convertirDateEtHeureEnDate(dto.getDebut(), dto.getHeureDebut()));
		entity.setFin(HourMinuteConverter.convertirDateEtHeureEnDate(dto.getFin(), dto.getHeureFin()));
	}

	protected void updateDTODates(final EntityT entity, final DtoT dto) {

		dto.setHeureDebut(HourMinuteConverter.extraireHeureDeDate(entity.getDebut()));
		dto.setHeureFin(HourMinuteConverter.extraireHeureDeDate(entity.getFin()));
	}
}
