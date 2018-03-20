package YingYingMonster.LetsDo_Phase_I.dao;

import java.io.File;
import java.util.zip.ZipFile;

import YingYingMonster.LetsDo_Phase_I.model.Requirement;
import YingYingMonster.LetsDo_Phase_I.model.Tag;

public interface FileDAO {
	
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
	 * 把数据集添加到用户仓库
	 * @param workerId 工人id
	 * @param dataSetId 数据集id（用户输入）
	 * @param sourcePath 源文件路径
	 * @return
	 */
	public boolean forkDataSetToAccount(String workerId,String dataSetId,String sourcePath);
	
	/**
	 * 下载整个数据集到客户端
	 * @param workerId 工人id
	 * @param dataSetId 数据集id
	 * @return zip形式数据集
	 */
	public File downloadDataSet(String workerId,String dataSetId);
	
	/**
	 * 单个下载数据到客户端
	 * @param workerId 工人id
	 * @param dataId 数据id
	 * @param dataSetId 数据集id
	 * @return 数据
	 */
	public File downloadData(String workerId,String dataId,String dataSetId);

	/**
	 * 
	 * @param publisherId 发布者id
	 * @param fileId 文件id
	 * @param bytes 文件内容
	 * @param requirement 要求说明
	 */
	public void uploadDataSet(String publisherId, String fileId, byte[] bytes, Requirement requirement);
}
