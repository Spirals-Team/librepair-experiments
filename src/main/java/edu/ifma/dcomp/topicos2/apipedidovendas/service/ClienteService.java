package edu.ifma.dcomp.topicos2.apipedidovendas.service;

import edu.ifma.dcomp.topicos2.apipedidovendas.model.Cliente;
import edu.ifma.dcomp.topicos2.apipedidovendas.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    private final GenericoService<Cliente> genericoService;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;

        this.genericoService = new GenericoService<Cliente>(clienteRepository );
    }


    public Optional<Cliente> buscaPor(String nome) {
        return Optional.ofNullable( clienteRepository.findByNome(nome ) );
    }


    public Cliente buscaPor(Integer id) {
        // ...
        return this.genericoService.buscaPor(id );
    }


    @Transactional
    public Cliente salva(Cliente cliente ) {
       return this.genericoService.salva(cliente );
    }


    @Transactional(readOnly = true)
    public List<Cliente> obterTodosClientes() {

        return genericoService.buscaTodasAsEntities();
    }


    @Transactional
    public void excluir(Integer id) {

        this.genericoService.excluir(id );
    }


    @Transactional
    public Cliente atualiza(Integer id, Cliente cliente) {
        Cliente clienteManager = this.buscaPor(id );

        BeanUtils.copyProperties(cliente, clienteManager, "id" );
        this.salva(clienteManager );

        return clienteManager;
    }
}
