package YingYingMonster.LetsDo_Phase_I.daoImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import YingYingMonster.LetsDo_Phase_I.dao.UserDAO;
import YingYingMonster.LetsDo_Phase_I.model.User;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

@Component
public class UserDAOImpl implements UserDAO {

	private final static String basePath=System.getProperty("user.dir")
			.replaceAll("\\\\", "/");
	
	@Override
	public boolean register(User user) {
		// TODO 自动生成的方法存根
		
		String path=basePath+"/database/repository/"+user.getId();
		
		if(this.findById(user.getId())!=null)
			return false;
		else{
			//为新用户创建文件夹
			File newUser=new File(path);
			if(!newUser.exists())
				newUser.mkdirs();
			
			File f=new File(path+"/upload");
			f.mkdirs();
			f=new File(path+"/fork");
			f.mkdirs();
			List<String[]>list=null;
			try {
				list=readCSV();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[]attrs={user.getId(),user.getName(),user.getPw()};
			list.add(attrs);
			if(this.writeCSV(list))
				return true;
			else 
				return false;
		}
	}

	@Override
	public boolean login(String id, String psw) {
		if(findById(id)!=null){
			if(findById(id).getPw().equals(psw))
				return true;
			else
				return false;
		}
		else
			return false;
	}

	@Override
	public boolean modify(User newUser) {
		if(newUser.getId()==null||
				newUser.getName()==null||newUser.getPw()==null
				||findById(newUser.getId())==null)
			return false;
		
		String[]attrs={newUser.getId(),newUser.getName(),newUser.getPw()};
		
		List<String[]>list=null;
		try {
			list=readCSV();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(list==null)
			return false;
		else{
			Iterator<String[]>it=list.iterator();
			while(it.hasNext()){
				String[]token=it.next();
				if(token[0].equals(attrs[0])){
					it.remove();
					break;
				}
			}
			list.add(attrs);
			return writeCSV(list);
		}
		
	}

	@Override
	public User findById(String id) {
		// TODO 自动生成的方法存根
		List<String[]> users=null;
		try {			
			users=this.readCSV();						
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(users!=null)
			for(String[] userInfo: users){
				if(userInfo[0].equals(id)){
					User u=new User();
					u.setId(userInfo[0]);
					u.setName(userInfo[1]);
					u.setPw(userInfo[2]);
					return u;
				}
			}
		
		return null;
	}

	private List<String[]> readCSV() throws FileNotFoundException {
		
		String path=basePath+"/database/users.csv";
		File users=new File(path);
		
		CSVReader cr=new CSVReader(new FileReader(users));
		List<String[]> list=null;
		try {
			list = cr.readAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				cr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	private boolean writeCSV(List<String[]>val){
		String path=basePath+"/database/users.csv";
		File users=new File(path);
		CSVWriter cw=null;
		try {
			cw = new CSVWriter(new FileWriter(users,false),',');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cw.writeAll(val);
		try {
			cw.flush();
			cw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
