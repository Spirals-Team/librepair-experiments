package pl.edu.prz.kia.universityproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.prz.kia.universityproject.repository.UserAnswerRepository;

@Service
public class UserAnswerService {

    private UserAnswerRepository userAnswerRepository;

    @Autowired
    public UserAnswerService(UserAnswerRepository userAnswerRepository) {
        this.userAnswerRepository = userAnswerRepository;
    }

}
