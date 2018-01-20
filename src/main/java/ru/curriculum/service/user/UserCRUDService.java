package ru.curriculum.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.curriculum.domain.admin.user.entity.User;
import ru.curriculum.domain.admin.user.repository.UserRepository;
import ru.curriculum.domain.teacher.entity.Teacher;
import ru.curriculum.domain.teacher.repository.TeacherRepository;
import ru.curriculum.service.user.dto.UserDTO;

import java.util.ArrayList;
import java.util.Collection;


@Component
public class UserCRUDService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    public Collection<UserDTO> findAllUsers() {
        Collection<UserDTO> userDTOs = new ArrayList<>();
        userRepository.findAll().forEach(user ->
                userDTOs.add(new UserDTO(user)));

        return userDTOs;
    }

    public UserDTO getUser(Integer userId) {
        // TODO: либо применять get, либо проверять на null
        User user = userRepository.findOne(userId);

        return new UserDTO(user);
    }

    public void create(UserDTO userDTO) {
        User newUser = new User(userDTO);
        userRepository.save(newUser);
    }

    public void update(UserDTO userDTO) {
        User user = userRepository.findOne(userDTO.getId());
        user.updatePrincipal(userDTO);
        userRepository.save(user);
    }

    public void delete(Integer userId) {
        Teacher teacher = teacherRepository.findByUserId(userId);
        if(null != teacher) {
            teacher.deleteUserAccount();
            teacherRepository.save(teacher);
        }
        userRepository.delete(userId);
    }
}
