package br.com.vitrine.service;

import br.com.vitrine.service.dto.ProdutoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Produto.
 */
public interface ProdutoService {

    /**
     * Save a produto.
     *
     * @param produtoDTO the entity to save
     * @return the persisted entity
     */
    ProdutoDTO save(ProdutoDTO produtoDTO);

    /**
     * Get all the produtos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProdutoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" produto.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProdutoDTO findOne(Long id);

    /**
     * Delete the "id" produto.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the produto corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProdutoDTO> search(String query, Pageable pageable);
}
