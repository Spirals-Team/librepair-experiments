package es.uniovi.asw.database;

public class MongoPersistanceFactory {

	public static AgentDao getAgentDAO() {
		return new AgentDaoImplMongo();
	}

}
