package br.com.vitrine.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.vitrine.service.ProdutoService;
import br.com.vitrine.web.rest.errors.BadRequestAlertException;
import br.com.vitrine.web.rest.util.HeaderUtil;
import br.com.vitrine.web.rest.util.PaginationUtil;
import br.com.vitrine.service.dto.ProdutoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Produto.
 */
@RestController
@RequestMapping("/api")
public class ProdutoResource {

    private final Logger log = LoggerFactory.getLogger(ProdutoResource.class);

    private static final String ENTITY_NAME = "produto";

    private final ProdutoService produtoService;

    public ProdutoResource(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    /**
     * POST  /produtos : Create a new produto.
     *
     * @param produtoDTO the produtoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produtoDTO, or with status 400 (Bad Request) if the produto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/produtos")
    @Timed
    public ResponseEntity<ProdutoDTO> createProduto(@Valid @RequestBody ProdutoDTO produtoDTO) throws URISyntaxException {
        log.debug("REST request to save Produto : {}", produtoDTO);
        if (produtoDTO.getId() != null) {
            throw new BadRequestAlertException("A new produto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProdutoDTO result = produtoService.save(produtoDTO);
        return ResponseEntity.created(new URI("/api/produtos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produtos : Updates an existing produto.
     *
     * @param produtoDTO the produtoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produtoDTO,
     * or with status 400 (Bad Request) if the produtoDTO is not valid,
     * or with status 500 (Internal Server Error) if the produtoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/produtos")
    @Timed
    public ResponseEntity<ProdutoDTO> updateProduto(@Valid @RequestBody ProdutoDTO produtoDTO) throws URISyntaxException {
        log.debug("REST request to update Produto : {}", produtoDTO);
        if (produtoDTO.getId() == null) {
            return createProduto(produtoDTO);
        }
        ProdutoDTO result = produtoService.save(produtoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, produtoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produtos : get all the produtos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of produtos in body
     */
    @GetMapping("/produtos")
    @Timed
    public ResponseEntity<List<ProdutoDTO>> getAllProdutos(Pageable pageable) {
        log.debug("REST request to get a page of Produtos");
        Page<ProdutoDTO> page = produtoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/produtos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /produtos/:id : get the "id" produto.
     *
     * @param id the id of the produtoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produtoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/produtos/{id}")
    @Timed
    public ResponseEntity<ProdutoDTO> getProduto(@PathVariable Long id) {
        log.debug("REST request to get Produto : {}", id);
        ProdutoDTO produtoDTO = produtoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(produtoDTO));
    }

    /**
     * DELETE  /produtos/:id : delete the "id" produto.
     *
     * @param id the id of the produtoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/produtos/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        log.debug("REST request to delete Produto : {}", id);
        produtoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/produtos?query=:query : search for the produto corresponding
     * to the query.
     *
     * @param query the query of the produto search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/produtos")
    @Timed
    public ResponseEntity<List<ProdutoDTO>> searchProdutos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Produtos for query {}", query);
        Page<ProdutoDTO> page = produtoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/produtos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
