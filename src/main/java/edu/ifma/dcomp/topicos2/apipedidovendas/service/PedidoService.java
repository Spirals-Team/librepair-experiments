package edu.ifma.dcomp.topicos2.apipedidovendas.service;


import edu.ifma.dcomp.topicos2.apipedidovendas.model.Pedido;
import edu.ifma.dcomp.topicos2.apipedidovendas.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

   private final PedidoRepository produtoRepository;

   private final GenericoService<Pedido> genericoService;

    public PedidoService(PedidoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
        this.genericoService = new GenericoService<>(produtoRepository);
    }


    @Transactional
    public Pedido salva(Pedido pedido) {
       return genericoService.salva(pedido );
    }


    @Transactional(readOnly = true)
    public List<Pedido> todos() {
        return genericoService.buscaTodasAsEntities();
    }


    @Transactional
    public Pedido atualiza(Integer id, Pedido pedido) {
       return genericoService.atualiza(pedido, id);
    }


}
