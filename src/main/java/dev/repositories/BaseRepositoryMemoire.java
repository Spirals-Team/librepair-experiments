package dev.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import dev.entites.BaseEntite;

public abstract class BaseRepositoryMemoire<T extends BaseEntite> implements CrudRepository<T> {

	private List<T> entityList = new ArrayList<>();
	private String contextpath;
	private final Class<T> typeOfArray;
	protected static Long currentId = 3L;

	public BaseRepositoryMemoire(String contextpath, Class<T> typeOfArray) {
		this.contextpath = contextpath;
		this.typeOfArray = typeOfArray;
	}

	@PostConstruct
	protected void initialiser() {
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(contextpath)) {
			context.getBeansOfType(typeOfArray).forEach((k, v) -> entityList.add(v));
		}
	}

	@Override
	public List<T> findAll() {
		return entityList;
	}

	@Override
	public void save(T entite) {
		entite.setId(BaseRepositoryMemoire.currentId++);
		entityList.add(entite);

	}

	@Override
	public void update(T entityWithId) {
		boolean entityFound = false;
		int i = 0;
		while (i < entityList.size() && !entityFound) {
			if (entityList.get(i).getId().equals(entityWithId.getId())) {
				entityList.set(i, entityWithId);
				entityFound = true;
			}
			i++;
		}
	}

	@Override
	public void delete(T entite) {
		if (entityList.contains(entite)) {
			entityList.remove(entite);
		}
	}

	public List<T> getEntityList() {
		return entityList;
	}

	@Override
	public Optional<T> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
