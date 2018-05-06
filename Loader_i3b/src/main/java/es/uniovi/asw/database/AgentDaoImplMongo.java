package es.uniovi.asw.database;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import es.uniovi.asw.parser.agents.AbstractAgent;
import es.uniovi.asw.parser.agents.EntityAgent;
import es.uniovi.asw.parser.agents.GeneralAgent;
import es.uniovi.asw.parser.agents.PersonAgent;
import es.uniovi.asw.parser.agents.SensorAgent;
import es.uniovi.asw.reportwriter.WriteReport;
import es.uniovi.asw.reportwriter.WriteReportDefault;

/**
 * DAO implementation for MongoDB database
 * 
 * @author Sergio Santano Álvarez - UO244858
 *
 */
public class AgentDaoImplMongo implements AgentDao {

	private final static String HOST = "localhost";
	private final static int PORT = 27017;
	private final static String DATABASE = "Agents";
	private final static String COLLECTION = "agentscollec";
	

	private MongoClient mongo;
	private DB db;
	private DBCollection users;
	private WriteReport reporter;
	private Properties properties;

	
	
	/**
	 * Default constructor that initializes the database from the constants
	 * specified above
	 */
	@SuppressWarnings("deprecation")
	public AgentDaoImplMongo() {
		
		this.reporter = new WriteReportDefault();
		this.mongo = new MongoClient(HOST, PORT);
		this.db = mongo.getDB(DATABASE);
		this.users = db.getCollection(COLLECTION);

//		users.createIndex(new BasicDBObject("id", 1), new BasicDBObject(
//				"unique", true));
	}

	
	/**
	 * This method is used in the test (for using the database for test)
	 * 
	 * 
	 * @param host
	 * @param port
	 * @param database
	 * @param collection
	 */
	@SuppressWarnings("deprecation")
	public AgentDaoImplMongo(String host, int port, String database,
			String collection) {
		this.reporter = new WriteReportDefault();
		this.mongo = new MongoClient(host, port);
		this.db = mongo.getDB(database);
		this.users = db.getCollection(collection);

//		users.createIndex(new BasicDBObject("id", 1), new BasicDBObject(
//				"unique", true));
	}

	/**
	 * 
	 * @param a
	 * 
	 *            Inserts a new document into the database with the citizen
	 *            passed as a parameter.
	 * 
	 */

	@Override
	public boolean insert(AbstractAgent a) {
		//Puede que el problema esté en ese basic object
		BasicDBObject document = new BasicDBObject();
		document.put("_id", a.getId());
		document.put("password", a.getPassword());
		document.put("name", a.getName());
		document.put("email", a.getEmail());
		document.put("location", a.getLocation());
		document.put("kind", a.getKind());
		document.put("kindCode", a.getKindCode());
		try {
			users.insert(document);
			reporter.logDatabaseInsertion(a);
			return true;
		} catch (DuplicateKeyException me) {
			reporter.report(me, "Error inserting in the database: "
					+ "The inserted Key is in the database");
		} catch (MongoException me) {
			reporter.report(me, "Error inserting in the database");
		}
		return false;

	}

	/**
	 * 
	 * @param ID
	 * 
	 *            Removes a document from the database.
	 * 
	 */

	@Override
	public void remove(String ID) {
		BasicDBObject document = new BasicDBObject();
		document.put("_id", ID);
		users.remove(document);
	}

	/**
	 * 
	 * @param ID
	 * 
	 *            Returns a document (citizen) from the database corresponding
	 *            to the id passed as a parameter.
	 * 
	 */

	@Override
	public AbstractAgent findById(String ID) {
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", ID);
		DBCursor cursor = users.find(whereQuery);
		AbstractAgent a = null;
		while (cursor.hasNext()) {
			DBObject user = cursor.next();
			a = createAgentByKind((String) user.get("name"), (String) user.get("location"), (String) user.get("email"), 
						(String) user.get("_id"),(String) user.get("kindCode"));
		}
		return a;
	}

	/**
	 * 
	 * Returns every document (citizen) in the databse.
	 * 
	 */

	@Override
	public List<AbstractAgent> findAll() {

		List<AbstractAgent> allAgents = new ArrayList<>();

		DBCursor cursor = users.find();
		while (cursor.hasNext()) {
			DBObject user = cursor.next();
			AbstractAgent a = createAgentByKind((String) user.get("name"), (String) user.get("location"), (String) user.get("email"), 
					(String) user.get("_id"), (String)user.get("kindCode"));
			allAgents.add(a);
		}

		return allAgents;
	}

	/**
	 * 
	 * Clears the database.
	 * 
	 */

	@Override
	public void cleanDatabase() {
		users.remove(new BasicDBObject());

	}
	
	/**
	 * 
	 * @param name
	 * @param location
	 * @param email
	 * @param identifier
	 * @param kind
	 * @return
	 * 
	 * 
	 * Create an Agent depending on its kind
	 * 
	 */
	private AbstractAgent createAgentByKind(String name, String location, String email, String identifier, String kindCode) {
		AbstractAgent a = null;
		int kind = Integer.valueOf(kindCode);
		switch (kind) {
		case 1:
			a = new PersonAgent(name, location, email, identifier, kindCode);
			break;
		case 2:
			a = new EntityAgent(name, location, email, identifier, kindCode);
			break;
		case 3: 
			a = new SensorAgent(name, location, email, identifier, kindCode);
			break;
		default:
			a = new GeneralAgent(name, location, email, identifier, kindCode);
		}
		return a;
	}

}
