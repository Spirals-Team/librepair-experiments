package YingYingMonster.LetsDo_Phase_I.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import YingYingMonster.LetsDo_Phase_I.dao.FileDAO;
import YingYingMonster.LetsDo_Phase_I.model.Requirement;
import YingYingMonster.LetsDo_Phase_I.model.Tag;
import YingYingMonster.LetsDo_Phase_I.service.DataService;

@Component
public class DataServiceImpl implements DataService {

	//@Autowired
	private FileDAO fileDao;
	
	

	@Override
	public void uploadTag(String workerId, Tag tag) {
		// TODO Auto-generated method stub
		fileDao.uploadTag(workerId, tag);
	}

	@Override
	public void uploadTags(String workerId, File zipFile) {
		// TODO Auto-generated method stub
		fileDao.uploadTags(workerId, zipFile);
	}

	@Override
	public File downloadDataSet(String workerId, String dataSetId) {
		// TODO Auto-generated method stub
		return fileDao.downloadDataSet(workerId, dataSetId);
	}

	@Override
	public void uploadDataSet(String publisherId, String fileId, MultipartFile file, Requirement requirement) throws IOException {
		// TODO Auto-generated method stub
		fileDao.uploadDataSet(publisherId, fileId, file.getBytes(), requirement);
	}

	@Override
	public File downloadData(String workerId, String dataId, String dataSetId) {
		// TODO Auto-generated method stub
		return fileDao.downloadData(workerId, dataId, dataSetId);
	}

}
