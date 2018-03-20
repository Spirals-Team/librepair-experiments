package YingYingMonster.LetsDo_Phase_I.service;

import java.util.ArrayList;

/**
 * 处理用户工作时的一些事物
 * @author 17678
 *
 */
public interface RepositoryService {

	/**
	 * 
	 * @param currentPath 当前文件夹名字
	 * @return 当前文件夹的子文件夹列表(要全路径的！！！)
	 */
	public ArrayList<String>readDirs(String currentPath)throws Exception;
}
