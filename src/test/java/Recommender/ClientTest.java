package Recommender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import recommender.Client;

public class ClientTest {

	private String output;
	private File recommenderDir;
	private File recoOutput;
	
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void testRun() throws FileNotFoundException {
		
		recommenderDir = new File(System.getProperty("user.home")+File.separator+"Recommender");
		output = System.getProperty("user.home")+File.separator+"Recommender"+File.separator+"OutputTest";
		recoOutput = new File(output);
			
		if(!recommenderDir.exists()) {
			recommenderDir.mkdir();
		}
		
		System.out.println(recommenderDir.getAbsolutePath());
		
		if(!recoOutput.exists()) {
			recoOutput.mkdir();
		}
		
		System.out.println(recoOutput.getAbsolutePath());
		
		String[] args = {"src/test/java/Recommender","src/test/java/Recommender/TestCollections",output,"-e"};
		Client.main(args);

		assertTrue(recoOutput.exists());
		assertTrue(recoOutput.isDirectory());
		File[] files = recoOutput.listFiles();
		assertEquals(files.length,1);
	}
	
	@After
	public void tearDown() {

		for(File dir : recoOutput.listFiles()) {
			dir.delete();
		}
		recoOutput.delete();
	}
	
}
