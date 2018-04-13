package guru.bonacci.oogway.auth.repositories;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import guru.bonacci.oogway.auth.models.User;

public interface UserRepository extends Repository<User, Long> {

	Optional<User> findByUsername(String username);

	Optional<User> findByApiKey(String apiKey);

	User save(User user);
}
