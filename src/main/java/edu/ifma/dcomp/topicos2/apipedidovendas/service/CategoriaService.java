package edu.ifma.dcomp.topicos2.apipedidovendas.service;

import edu.ifma.dcomp.topicos2.apipedidovendas.model.Categoria;
import edu.ifma.dcomp.topicos2.apipedidovendas.repository.CategoriaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
      this.categoriaRepository = categoriaRepository;
    }


    Optional<Categoria> buscaPor(String nome) {
        return Optional.ofNullable( categoriaRepository.findByNome(nome) );
    }


    public Categoria buscaPor(Integer id) {

        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id );

        return optionalCategoria
                .orElseThrow( () ->new EmptyResultDataAccessException(1 ) );
    }


    @Transactional
    public Categoria salva(Categoria categoria ) {
       return this.categoriaRepository.save(categoria );
    }


    @Transactional(readOnly = true)
    public List<Categoria> obterTodasCategorias() {
        return categoriaRepository.findAll();
    }


    @Transactional
    public void excluir(Integer id) {
        categoriaRepository.deleteById(id );
    }


    @Transactional
    public Categoria atualiza(Integer id, Categoria categoria) {
        Categoria categoriaManager = this.buscaPor(id );

        if (categoriaManager == null) {
            throw new EmptyResultDataAccessException(1 );
        }

        BeanUtils.copyProperties(categoria, categoriaManager, "id" );
        this.salva(categoriaManager );

        return categoriaManager;
    }
}
