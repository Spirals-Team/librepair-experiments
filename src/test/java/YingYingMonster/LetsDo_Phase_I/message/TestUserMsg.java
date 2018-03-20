package YingYingMonster.LetsDo_Phase_I.message;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import YingYingMonster.LetsDo_Phase_I.model.User;

public class TestUserMsg {

	UserMsg um;
	
	@Before
	public void setUp() throws Exception {
		um=new UserMsg();
		User user=new User();
		user.setId("id");
		user.setName("name");
		user.setPw("pw");
		um.setUser(user);
		um.setResults(Results.FAIL);
		um.setReasons(Reasons.WRONG_PASSWORD);
	}

	@Test
	public void test() {
		System.out.println(um.getResults());
	}

}
