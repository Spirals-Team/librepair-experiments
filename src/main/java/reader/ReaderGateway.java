package reader;

import java.util.List;

public interface ReaderGateway {

	List<String> readLine();

	int getLineNumber();

	void setLineNumber(int lineNumber);

}