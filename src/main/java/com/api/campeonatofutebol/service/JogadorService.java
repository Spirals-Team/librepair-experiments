package com.api.campeonatofutebol.service;

import com.api.campeonatofutebol.model.Jogador;
import com.api.campeonatofutebol.repository.JogadorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;

    @Autowired
    public JogadorService(JogadorRepository jogadorRepository) {
        this.jogadorRepository = jogadorRepository;
    }

    @Transactional
    public Jogador salva(Jogador jogador) {
        return jogadorRepository.save(jogador);
    }

    @Transactional (readOnly = true)
    public List<Jogador> todos() {
        return jogadorRepository.findAll();
    }

    @Transactional (readOnly = true)
    public List<Jogador> listarPor(String nome) {
        return  jogadorRepository.findByNomeContaining(nome )
                .orElse(new ArrayList<>() );
    }

    @Transactional (readOnly = true)
    public Jogador buscarPor(String nome) {
        return jogadorRepository.findByNome(nome);
    }

    @Transactional (readOnly = true)
    public Jogador buscaPor(Integer id) {
        return jogadorRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Transactional
    public void excluir(Integer id) {
        jogadorRepository.deleteById(id);
    }

    @Transactional
    public Jogador atualiza(Integer id, Jogador jogador) {
        Jogador jogadorManager = this.buscaPor(id);

        BeanUtils.copyProperties(jogador, jogadorManager, "id", "instanteDaCriacao");

        this.salva(jogador);

        return jogadorManager;
    }

    @Transactional
    public void atualizarAtributoAtivo(Integer id, boolean ativo) {
        Jogador jogador = this.buscaPor(id);

        jogador.setAtivo(ativo);

        jogadorRepository.save(jogador);
    }
}
