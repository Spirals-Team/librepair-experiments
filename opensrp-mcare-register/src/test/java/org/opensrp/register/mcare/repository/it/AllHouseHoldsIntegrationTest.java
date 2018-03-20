package org.opensrp.register.mcare.repository.it;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.Before;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.scheduler.repository.AllActions;
import org.springframework.beans.factory.annotation.Autowired;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register-mcare.xml")*/
public class AllHouseHoldsIntegrationTest {
	
	@Autowired
	private AllHouseHolds allHouseHolds;
	
	@Autowired
	private AllElcos allElcos;
	
	private CouchDbInstance dbInstance;
	
	private StdCouchDbConnector stdCouchDbConnector;
	
	private StdCouchDbConnector stdCouchDbConnectorForm;
	
	@Autowired
	private AllFormSubmissions allFormSubmissions;
	
	@Autowired
	private AllMothers allMothers;
	
	@Autowired
	private AllChilds allChilds;
	
	@Autowired
	private AllActions allActions;
	
	@Before
	public void setUp() throws Exception {
		
		HttpClient httpClient = new StdHttpClient.Builder()
		//.host("localhost") 
		        .host("localhost").port(5984).username("Admin").password("mPower@1234").socketTimeout(1000).build();
		dbInstance = new StdCouchDbInstance(httpClient);
		
		stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());
		stdCouchDbConnectorForm = new StdCouchDbConnector("opensrp-form", dbInstance, new StdObjectMapperFactory());
		stdCouchDbConnector.createDatabaseIfNotExists();
		allHouseHolds = new AllHouseHolds(2, stdCouchDbConnector);
		allElcos = new AllElcos(2, stdCouchDbConnector);
		allFormSubmissions = new AllFormSubmissions(stdCouchDbConnectorForm);
		allMothers = new AllMothers(2, stdCouchDbConnector);
		allChilds = new AllChilds(2, stdCouchDbConnector);
		allActions = new AllActions(stdCouchDbConnector);
		//initMocks(this);
	}
	
	/* @Ignore@Test
	 public void updateHouseHold(){
	   List<HouseHold> houseHolds = allHouseHolds.getAll();
	   int i=0;
	   for (HouseHold houseHold : houseHolds) {
		houseHold.setTimeStamp(System.currentTimeMillis());
		allHouseHolds.update(houseHold);
		i++;
		System.err.println("I::"+i);
	   }
	   
	 }
	
	
	
	*/
	
}
