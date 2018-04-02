package spoon.test.position.testclasses;

import java.util.ArrayList;
import java.util.List;

public class CastVariableRead {
	void m() {
		Object s = new ArrayList<>();
		List x = (List<?>) s;
	}
}