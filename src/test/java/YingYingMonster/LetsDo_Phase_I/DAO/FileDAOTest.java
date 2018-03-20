package YingYingMonster.LetsDo_Phase_I.DAO;

import org.junit.Test;

import YingYingMonster.LetsDo_Phase_I.dao.FileDAO;
import YingYingMonster.LetsDo_Phase_I.daoImpl.FileDAOImpl;

public class FileDAOTest {
	
	FileDAO fileImpl=new FileDAOImpl();

	@Test
	public void TestDownloadDS(){
		fileImpl.downloadDataSet("11112","11111_test");
	}
	
//	@Test
//	public void TestUploadDS(){
//		Requirement re=new Requirement();
//		re.setShape(1);
//		re.setThickness(2);
//		ArrayList<Integer> color=new ArrayList<>();
//		color.add(1);
//		re.setColor(color);
//		File input=new File("C:/Users/TF/Desktop/文件/test2.zip");
//		ByteArrayOutputStream bos=null;
//		BufferedInputStream bis=null;
//		bos=new ByteArrayOutputStream((int)input.length());
//		try {
//			bis=new BufferedInputStream(new FileInputStream(input));
//			byte[] buffer=new byte[10240];
//			int len=0;
//			while((len=bis.read(buffer, 0, 10240))!=-1)
//				bos.write(buffer,0,len);
//		} catch (IOException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//		fileImpl.uploadDataSet("11112", "test2", bos.toByteArray(), re);
//	}
}
