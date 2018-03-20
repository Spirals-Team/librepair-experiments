package YingYingMonster.LetsDo_Phase_I.daoImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Component;

import YingYingMonster.LetsDo_Phase_I.LetsDoPhaseIApplication;
import YingYingMonster.LetsDo_Phase_I.dao.FileDAO;
import YingYingMonster.LetsDo_Phase_I.model.Requirement;
import YingYingMonster.LetsDo_Phase_I.model.Tag;
import au.com.bytecode.opencsv.CSVWriter;

@Component
public class FileDAOImpl implements FileDAO {

	@Override
	public void uploadTag(String workerId, Tag tag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uploadTags(String workerId, File zipFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public File downloadDataSet(String workerId, String dataSetId) {
		
		String[] name=dataSetId.split("_");
		File downloadDataSet=new File("src/main/resources/stock/projects/"
				+dataSetId+"/"+name[1]+".zip");
		File workerNewFile=new File("src/main/resources/stock/users/"
				+workerId+"/"+dataSetId+"/imcomplete");
		if(workerNewFile.exists())
			return null;
		workerNewFile.mkdirs();
			
		//解压
		ZipArchiveInputStream is;
		try {
			is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(downloadDataSet)));
			ZipArchiveEntry entry;
			while ((entry = is.getNextZipEntry()) != null) {
		        if (entry.isDirectory()) {
		        	File directory = new File("src/main/resources/stock/users/"+workerId+"/"+dataSetId+"/imcomplete", entry.getName());
		           	directory.mkdirs();
		        }else {
		        	OutputStream ops = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/stock/users/"+workerId+"/"+dataSetId+"/imcomplete", entry.getName())));
		           	IOUtils.copy(is, ops);
		            IOUtils.closeQuietly(ops);
		        }
			}	
		} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
		}
					
		//requirement移动
		File requirement=new File("src/main/resources/stock/projects/"+dataSetId+"/requirement.csv");
		File newRe=new File("src/main/resources/stock/users/"+workerId+"/"+dataSetId+"/requirement.csv");
		try {
			copyFile(requirement,newRe);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return requirement;
	}

	@Override
	public void uploadDataSet(String publisherId, String fileId, byte[] bytes, Requirement requirement) {
		// TODO Auto-generated method stub
		String newFilePath="src/main/resources/stock/projects/"+publisherId+"_"+fileId;
		File newDisk=new File(newFilePath);
		newDisk.mkdirs();
		File newFile=new File(newFilePath+"/"+fileId+".zip");
		try {
			//保存
			OutputStream os=new FileOutputStream(newFile);
			BufferedOutputStream bos=new BufferedOutputStream(os);
			bos.write(bytes);
			bos.close();
			
			//解压
			ZipArchiveInputStream is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(newFile), 1024));
			ZipArchiveEntry entry = null;
			while ((entry = is.getNextZipEntry()) != null) {
	            if (entry.isDirectory()) {
	            	File directory = new File(newFile.getParent(), entry.getName());
	            	directory.mkdirs();
	            }else {
	            	OutputStream ops = new BufferedOutputStream(new FileOutputStream(new File(newFile.getParent(), entry.getName())), 1024);
	            	IOUtils.copy(is, ops);
	            }
			}		
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		File requirementTxt=new File(newFilePath+"/requirement.csv");
		try {
			FileWriter fw=new FileWriter(requirementTxt);
			CSVWriter cw=new CSVWriter(fw);
			cw.writeNext(requirement.toStringCSV());
			cw.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/*
	 * 复制文件
	 */
	private void copyFile(File fromFile,File toFile) throws IOException{
        FileInputStream ins = new FileInputStream(fromFile);
        FileOutputStream out = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int n=0;
        while((n=ins.read(b))!=-1){
            out.write(b, 0, n);
        }
        
        ins.close();
        out.close();
    }

	@Override
	public File downloadData(String workerId, String dataId, String dataSetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean forkDataSetToAccount(String workerId, String dataSetId, String sourcePath) {
		// TODO Auto-generated method stub
		return false;
	}

}
