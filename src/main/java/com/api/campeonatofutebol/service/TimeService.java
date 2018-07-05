package com.api.campeonatofutebol.service;

import com.api.campeonatofutebol.model.Time;
import com.api.campeonatofutebol.repository.TimeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    @Autowired
    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @Transactional
    public Time salva(Time time) {
        return this.timeRepository.save(time);
    }

    @Transactional (readOnly = true)
    public List<Time> todos() {
        return this.timeRepository.findAll();
    }

    @Transactional (readOnly = true)
    public Time buscarPor(String nome) {
        return this.timeRepository.findByNome(nome);
    }

    @Transactional (readOnly = true)
    public Time buscarPor(Integer id) {
        return this.timeRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Transactional (readOnly = true)
    public List<Time> listarPor(String nome) {
        return this.timeRepository.findByNomeContaining(nome)
                .orElse(new ArrayList<>());
    }

    @Transactional
    public void excluir(Integer id) {
        timeRepository.deleteById(id);
    }

    @Transactional
    public Time atualiza(Integer id, Time time) {
      Time timeManager  = this.buscarPor(id);

        BeanUtils.copyProperties(time, timeManager,"id");

        this.salva(time);

        return timeManager;
    }

    @Transactional
    public void atualizarAtributoAtivo(Integer id, boolean ativo) {
        Time time = this.buscarPor(id);

        time.setAtivo(ativo);

        timeRepository.save(time);
    }

}
