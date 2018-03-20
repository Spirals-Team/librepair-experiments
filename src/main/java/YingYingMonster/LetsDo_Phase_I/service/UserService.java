package YingYingMonster.LetsDo_Phase_I.service;

import YingYingMonster.LetsDo_Phase_I.model.User;

public interface UserService {

	public boolean register(User user);
	
	public boolean login(String id,String pw);
	
	public boolean modify(User user);
	
	public User findUser(String id);
}
