package parser.cartas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.lowagie.text.DocumentException;

import model.Agent;
import model.Operario;

public class TxtLetter extends Letter{
	private Writer writer;

	public void createLetter(Agent user) throws IOException{
		File letter = new File("cartas/txt/" + user.getIdentificador() + ".txt");
		writer = new FileWriter(letter);
		writer.write("Nombre: " + user.getNombre());
		writer.write(System.lineSeparator());
		writer.write("Identificador: " + user.getIdentificador());
		writer.write(System.lineSeparator());
		writer.write("Clave: " + user.getClave());
		writer.close();
	}

	@Override
	public void createLetter(Operario user) throws DocumentException, FileNotFoundException, IOException {
		File letter = new File("cartas/txt/" + user.getEmail() + ".txt");
		writer = new FileWriter(letter);
		writer.write("Email: " + user.getEmail());
		writer.write(System.lineSeparator());
		writer.write("Identificador/Clave: " + user.getPassword());
		writer.close();
		
	}
}
