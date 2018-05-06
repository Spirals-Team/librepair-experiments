package alltests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import es.uniovi.asw.LoadUsers;

public class LoadUsersTest {

	@Before
	public void clearDatabase() {
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase db = mongoClient.getDatabase("Agents");
		db.getCollection("agents").deleteMany(new Document());
	}

	@Test
	public void testRunInsert() {		// Clears the database before the test.
		clearDatabase();

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		// Tests how the data is inserted correctly into the database for the
		// first time.
		System.setOut(new PrintStream(outContent));
		LoadUsers.main("src/test/resources/masterTest.csv", "src/test/resources/test.xlsx");
		assertTrue(outContent.toString().contains("id1 letter sent."));

	}

	@Test
	public void testNoFileError() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setErr(new PrintStream(outContent));
		LoadUsers.main();
		assertTrue(outContent.toString().contains("Input the name of the file."));
	}

}
