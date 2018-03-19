package com.jairosousa.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jairosousa.cursomc.domain.Cliente;
import com.jairosousa.cursomc.repository.ClienteRepository;
import com.jairosousa.cursomc.services.exceptios.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder bCrypt;

	@Autowired
	private EmailService emailService;

	private Random rand = new Random();

	public void sendNewPassword(String email) {

		Cliente cliente = clienteRepository.findByEmail(email);

		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado!");
		}

		String newPassword = newPassword();
		cliente.setSenha(bCrypt.encode(newPassword));

		clienteRepository.save(cliente);

		emailService.sendNewPasswordEmail(cliente, newPassword);

	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < vet.length; i++) {
			vet[i] = ramdomChar();
		}
		return new String(vet);
	}

	private char ramdomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) {// Gera um digito
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) {// Gera letra Maiuscula
			return (char) (rand.nextInt(26) + 65);
		} else {// Gera letra Minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}

}
