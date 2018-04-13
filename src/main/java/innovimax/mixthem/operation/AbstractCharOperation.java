package innovimax.mixthem.operation;

import innovimax.mixthem.MixException;
import innovimax.mixthem.arguments.RuleParam;
import innovimax.mixthem.arguments.ParamValue;
import innovimax.mixthem.io.DefaultCharReader;
import innovimax.mixthem.io.DefaultCharWriter;
import innovimax.mixthem.io.IInputChar;
import innovimax.mixthem.io.IOutputChar;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
* <p>Abstract class for all line operation.</p>
* @see ILineOperation
* @author Innovimax
* @version 1.0
*/
public abstract class AbstractCharOperation extends AbstractOperation implements ICharOperation {

	/**
	* Constructor
 	* @param params The list of parameters (maybe empty)
	* @see innovimax.mixthem.arguments.RuleParam
	* @see innovimax.mixthem.arguments.ParamValue
	*/
	public AbstractCharOperation(Map<RuleParam, ParamValue> params) {
		super(params);
	}

	@Override
    	public void processFiles(File file1, File file2, OutputStream out) throws MixException, IOException {
        	IInputChar reader1 = new DefaultCharReader(file1);
        	IInputChar reader2 = new DefaultCharReader(file2);
        	IOutputChar writer = new DefaultCharWriter(out); 
        	while (reader1.hasCharacter() || reader2.hasCharacter()) {
			final int c1 = reader1.nextCharacter();
			final int c2 = reader2.nextCharacter();
			process(c1, c2).forEach(i -> {
				try {
					writer.writeCharacter(i);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}});
        	}
        	reader1.close();
        	reader2.close();
        	writer.close();
    	}

}
