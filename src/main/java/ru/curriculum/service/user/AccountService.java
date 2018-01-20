package ru.curriculum.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.curriculum.domain.admin.user.repository.UserRepository;
import ru.curriculum.service.teacher.dto.TeacherDTO;
import ru.curriculum.service.user.dto.UserDTO;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class AccountService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCRUDService userCRUDService;

    public Collection<UserDTO> getFreeAccounts() {
        Collection<UserDTO> accounts = new ArrayList<>();
        userRepository.findAllByTeacherIsNull().forEach(user ->
                accounts.add(new UserDTO(user)));

        return accounts;
    }

    public Collection<UserDTO> getFreeAccountsAndTeacherAccount(TeacherDTO teacherDTO) {
        Collection<UserDTO> accounts = getFreeAccounts();
        if(null != teacherDTO.getUserId()) {
            accounts.add(userCRUDService.getUser(teacherDTO.getUserId()));
        }

        return accounts;
    }
}
