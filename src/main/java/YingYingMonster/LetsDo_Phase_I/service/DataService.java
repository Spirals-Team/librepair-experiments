package YingYingMonster.LetsDo_Phase_I.service;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

import org.springframework.web.multipart.MultipartFile;

import YingYingMonster.LetsDo_Phase_I.model.Requirement;
import YingYingMonster.LetsDo_Phase_I.model.Tag;

public interface DataService {

	/**
	 * 上传数据集
	 * @param publisherId 发布人id
	 * @param fileId 
	 * @param zipFile zip形式的数据集
	 * @param requirement 要求说明书
	 * @throws IOException 读取文件内容失败
	 */
	public void uploadDataSet(String publisherId,String fileId, MultipartFile file,Requirement requirement) throws IOException;
	
	/**
	 * 上传单张做好的标记
	 * @param workerId 工人id
	 * @param tag 标记
	 */
	public void uploadTag(String workerId,Tag tag);
	
	/**
	 * 批量上传做好的标记
	 * @param workerId 工人id
	 * @param zipFile 标记
	 */
	public void uploadTags(String workerId,File zipFile);
	
	/**
	 * 下载整个数据集
	 * @param workerId 工人id
	 * @param dataSetId 数据集id
	 * @return zip形式数据集
	 */
	public File downloadDataSet(String workerId,String dataSetId);
	
	/**
	 * 单个下载数据
	 * @param workerId 工人id
	 * @param dataId 数据id
	 * @param dataSetId 数据集id
	 * @return 数据
	 */
	public File downloadData(String workerId,String dataId,String dataSetId);
}
