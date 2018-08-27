package JoaoVFG.com.github.service.utils;

import java.util.Random;

public class GenerateRandom {

	private Random random = new Random();

	public String newRandom(Integer tamanho) {
		char[] vet = new char[tamanho];

		for (int i = 0; i < vet.length; i++) {
			vet[i] = randomChar();
		}

		return new String(vet);
	}

	private char randomChar() {
		int option = random.nextInt(3);

		if (option == 0) {// gera digito
			return (char) (random.nextInt(10) + 48);
		} else if (option == 1) {// gera letra Maiuscula
			return (char) (random.nextInt(26) + 65);
		} else {// gera letra minuscula
			return (char) (random.nextInt(26) + 97);
		}
	}
}
