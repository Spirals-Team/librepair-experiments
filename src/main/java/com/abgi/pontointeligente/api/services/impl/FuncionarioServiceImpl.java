package com.abgi.pontointeligente.api.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abgi.pontointeligente.api.entities.Funcionario;
import com.abgi.pontointeligente.api.repositories.FuncionarioRepository;
import com.abgi.pontointeligente.api.services.FuncionarioService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FuncionarioServiceImpl implements FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Override
	public Funcionario persistir(Funcionario funcionario) {
		log.info("Persistindo funcionario: {}", funcionario);
		return this.funcionarioRepository.save(funcionario);
	}

	@Override
	public Optional<Funcionario> buscarPorCpf(String cpf) {
		log.info("Buscando funcionario pelo cpf {}", cpf);
		return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> buscarPorEmail(String email) {
		log.info("Buscando funcionario pelo email {}", email);
		return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
	}

	@Override
	public Optional<Funcionario> buscarPorId(Long id) {
		log.info("Buscando funcionário pelo IDl {}", id);
		return Optional.ofNullable(this.funcionarioRepository.getOne(id));
	}

}
