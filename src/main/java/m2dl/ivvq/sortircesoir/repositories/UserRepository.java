package m2dl.ivvq.sortircesoir.repositories;

import m2dl.ivvq.sortircesoir.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByLoginAndPassword(String login, String password);
    List<User> findUserByLogin(String login);
}
