package dev.repositories.examen;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import dev.entites.Examen;
import dev.repositories.BaseRepositoryMemoire;

@Repository
public class ExamenBaseRepositoryMemoire extends BaseRepositoryMemoire<Examen> implements ExamenRepository {

	public ExamenBaseRepositoryMemoire(@Value("${jdd.examens}") String path) {
		super(path, Examen.class);
	}

}
