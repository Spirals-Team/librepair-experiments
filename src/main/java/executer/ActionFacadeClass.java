package executer;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.lowagie.text.DocumentException;

import dbupdate.Insert;
import dbupdate.InsertP;
import model.Agent;
import model.Operario;

public class ActionFacadeClass implements ActionFacade {

	@Override
	public void saveData(Agent user) throws FileNotFoundException, DocumentException, IOException {
		Insert insert = new InsertP();
		insert.save(user);
			
	}
	
	@Override
	public void saveData(Operario oper) throws FileNotFoundException, DocumentException, IOException {
		Insert insert = new InsertP();
		insert.save(oper);
			
	}
}