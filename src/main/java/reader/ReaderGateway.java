package reader;

public interface ReaderGateway {

	String readLine();

	int getLineNumber();

	void setLineNumber(int lineNumber);

}