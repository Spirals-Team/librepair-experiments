package com.jairosousa.cursomc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jairosousa.cursomc.domain.Categoria;
import com.jairosousa.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias,
			Pageable pageRequest);
	
	/**
	 * O Metodo faz a mesma consulta que o metodo serch, mais usando o 
	 * padrão de nomes do Spring Data.
	 * @param nome
	 * @param categorias
	 * @param pageRequest
	 * @return List<Produtos>
	 */
	@Transactional(readOnly=true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias,
			Pageable pageRequest);
}
