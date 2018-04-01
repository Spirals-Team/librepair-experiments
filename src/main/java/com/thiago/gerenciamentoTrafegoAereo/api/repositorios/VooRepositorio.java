package com.thiago.gerenciamentoTrafegoAereo.api.repositorios;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Voo;

@Transactional(readOnly = true)
@NamedQueries({ @NamedQuery(
		name = "VooRepositorio.findByCidadeDestinoAndCidadeOrigem",
		query = "SELECT v FROM Voo v WHERE v.cidadeOrigem.codigo = :cidadeOrigem AND v.cidadeDestino.codigo = :cidadeDestino") })
public interface VooRepositorio extends JpaRepository<Voo, Long>
{
	public Voo findByCodigo(Long id);

	public List<Voo> findByCidadeOrigemCodigoAndCidadeDestinoCodigo(@Param("cidadeOrigem") Long cidadeOrigem, @Param("cidadeDestino") Long cidadeDestino);
	
	public List<Voo> findByCidadeOrigemCodigoAndCidadeDestinoCodigo(@Param("cidadeOrigem") Long cidadeOrigem, @Param("cidadeDestino") Long cidadeDestino, Pageable paginacao);
}
