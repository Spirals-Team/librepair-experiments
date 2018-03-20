package YingYingMonster.LetsDo_Phase_I.DAO;

import static org.junit.Assert.*;

import org.junit.Test;

import YingYingMonster.LetsDo_Phase_I.dao.UserDAO;
import YingYingMonster.LetsDo_Phase_I.daoImpl.UserDAOImpl;
import YingYingMonster.LetsDo_Phase_I.model.User;

public class UserDAOTest {
	
	@Test
	public void testRegister(){
		User user=new User();
		user.setId("11115");
		user.setName("admin1");
		user.setPw("12345");
		UserDAO userImpl=new UserDAOImpl();
//		assertEquals(userImpl.register(user),true);
		assertEquals(userImpl.register(user),false);
	}
	
	@Test
	public void testModify(){
		User user=new User();
		user.setId("11111");
		user.setName("name1");
		user.setPw("pw1");
		UserDAO userImpl=new UserDAOImpl();
		assertEquals(userImpl.modify(user),true);
		
		user=userImpl.findById("11111");
		assertNotNull(user);
		assertEquals("name1",user.getName());
		assertEquals("pw1",user.getPw());
	}
	
	@Test
	public void testFind(){
		UserDAO userImpl=new UserDAOImpl();
		User user=userImpl.findById("17678");
		assertNull(user);
		
		user=userImpl.findById("id");
		assertNotNull(user);
		assertEquals("name",user.getName());
		assertEquals("pw",user.getPw());
	}
}
