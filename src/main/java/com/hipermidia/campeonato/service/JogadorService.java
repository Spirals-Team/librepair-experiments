package com.hipermidia.campeonato.service;

import com.hipermidia.campeonato.model.Jogador;
import com.hipermidia.campeonato.repository.JogadorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void excluir(Integer id) {
        jogadorRepository.deleteById(id);
    }

    @Transactional (readOnly = true)
    public List<Jogador> todos() {
        return jogadorRepository.findAll();
    }

    public Jogador buscaPor(Integer id) {
        return jogadorRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Transactional
    public Jogador atualiza(Integer id, Jogador jogador) {
        Jogador jogadorManager = this.buscaPor(id);

        BeanUtils.copyProperties(jogador, jogadorManager, "id");

        this.salva(jogadorManager);

        return jogadorManager;
    }

    @Transactional
    public void atualizarAtributoAtivo(Integer id, Boolean ativo) {
        Jogador jogador = this.buscaPor(id);
        jogador.setAtivo(ativo);
        jogadorRepository.save(jogador);
    }
}
