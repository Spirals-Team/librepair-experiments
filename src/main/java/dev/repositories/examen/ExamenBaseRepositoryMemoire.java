package dev.repositories.examen;

import org.springframework.beans.factory.annotation.Value;

import dev.entites.Examen;
import dev.repositories.BaseRepositoryMemoire;

public class ExamenBaseRepositoryMemoire extends BaseRepositoryMemoire<Examen> implements ExamenRepository {

	public ExamenBaseRepositoryMemoire(@Value("${jdd.examens}") String path) {
		super(path, Examen.class);
	}

}
