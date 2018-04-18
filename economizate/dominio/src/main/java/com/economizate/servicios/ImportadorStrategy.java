package com.economizate.servicios;

import java.io.IOException;

public interface ImportadorStrategy {

	public String importarFile(String filename) throws IOException;
}
