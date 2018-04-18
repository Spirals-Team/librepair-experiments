package com.economizate.batch;

import java.util.List;

public interface IVisitor {
	
	public void visitar(BackupArchivo backup);
	
	public void visitar(BackupDrive backup);
	
	public void visitar(List<IBackup> lista);

}
