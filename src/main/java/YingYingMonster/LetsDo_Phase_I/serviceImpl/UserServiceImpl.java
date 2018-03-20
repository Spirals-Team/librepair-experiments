package YingYingMonster.LetsDo_Phase_I.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import YingYingMonster.LetsDo_Phase_I.dao.UserDAO;
import YingYingMonster.LetsDo_Phase_I.model.User;
import YingYingMonster.LetsDo_Phase_I.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDao;
	
	@Override
	public boolean register(User user) {
		// TODO Auto-generated method stub
		return userDao.register(user);
	}

	@Override
	public boolean login(String id, String pw) {
		// TODO Auto-generated method stub
		return userDao.login(id, pw);
	}

	@Override
	public boolean modify(User user) {
		// TODO Auto-generated method stub
		return userDao.modify(user);
	}

	@Override
	public User findUser(String id) {
		// TODO Auto-generated method stub
		return userDao.findById(id);
	}

}
