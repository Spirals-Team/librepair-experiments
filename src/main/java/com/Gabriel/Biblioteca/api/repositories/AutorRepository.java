package com.Gabriel.Biblioteca.api.repositories;



import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.Gabriel.Biblioteca.api.entities.Autor;

@Transactional (readOnly = true)
@NamedQueries({
	@NamedQuery(name = "AutorRepository.procuraPorCodigo", query = "SELECT * FROM Autor WHERE codigo = :codigoAU") })

public interface AutorRepository extends JpaRepository<Autor, Long> {

	Autor findByCodigo(@Param("codigoAU") String codigo);

}
