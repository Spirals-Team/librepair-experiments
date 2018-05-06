package es.uniovi.asw.parser.lettergenerators;

import es.uniovi.asw.parser.agents.AbstractAgent;

/**
 * 
 * @author Oriol
 * Default generator
 */
public class ConsoleLetterGenerator implements LetterGenerator{

	@Override
	public void generatePersonalLetter(AbstractAgent c) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n------ PERSONAL LETTER ------\n");
		sb.append("To: "+c.getEmail()+"\n");
		sb.append("Subject: Login data\n");
		sb.append("Mr/Mrs "+ c.getName() +",\n\n");
		sb.append("Your login data has been generated:\n");
		sb.append("\tUsername: "+c.getId()+"\n");
		sb.append("\tPassword: "+c.getPassword()+"\n");
		System.out.println(sb.toString());
		System.out.println(c.getId() +" letter sent.");
	}

}
