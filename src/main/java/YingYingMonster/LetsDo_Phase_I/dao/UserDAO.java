package YingYingMonster.LetsDo_Phase_I.dao;

import YingYingMonster.LetsDo_Phase_I.model.User;

public interface UserDAO {

	public boolean register(User user);
	public boolean login(String id,String psw);
	public boolean modify(User newUser);
	public User findById(String id);
}
