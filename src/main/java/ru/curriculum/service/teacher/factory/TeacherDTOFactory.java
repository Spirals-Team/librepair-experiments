package ru.curriculum.service.teacher.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.curriculum.service.teacher.dto.TeacherDTO;
import ru.curriculum.service.user.dto.UserDTO;
import ru.curriculum.service.user.UserCRUDService;

@Component
public class TeacherDTOFactory {
    @Autowired
    private UserCRUDService userCRUDService;

    public TeacherDTO createTeacherDTOBasedOnUser(Integer userId) {
        UserDTO userDTO = userCRUDService.getUser(userId);
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setUserId(userDTO.getId());
        teacherDTO.setUsername(userDTO.getUsername());
        teacherDTO.setSurname(userDTO.getSurname());
        teacherDTO.setFirstName(userDTO.getFirstName());
        teacherDTO.setPatronymic(userDTO.getPatronymic());

        return teacherDTO;
    }
}
