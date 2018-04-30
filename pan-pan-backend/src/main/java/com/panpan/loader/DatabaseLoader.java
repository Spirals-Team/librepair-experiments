package com.panpan.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.panpan.model.User;
import com.panpan.model.VerificationToken;
import com.panpan.model.Band;
import com.panpan.repository.UserRepository;
import com.panpan.repository.VerificationTokenRepository;
import com.panpan.repository.BandRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final UserRepository repository;
	private final VerificationTokenRepository repository2;
	private final BandRepository repository3;	

	@Autowired
	public DatabaseLoader(UserRepository repository, VerificationTokenRepository repository2, BandRepository repository3) {
		this.repository = repository;
		this.repository2 = repository2;
		this.repository3 = repository3;
	}

	@Override
	public void run(String... strings) throws Exception {
		User user1 = new User("Frodo", "Baggins");
		this.repository.save(user1);
		this.repository2.save(new VerificationToken(user1));
		this.repository3.save(new Band("TestName", "TestGenre"));
	}
}
