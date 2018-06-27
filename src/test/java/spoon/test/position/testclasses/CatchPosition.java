package spoon.test.position.testclasses;

import java.io.IOException;

public class CatchPosition {
	void method() {
		try {
			throw new IOException();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	};
}