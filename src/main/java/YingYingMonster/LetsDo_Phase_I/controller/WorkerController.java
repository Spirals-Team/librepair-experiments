package YingYingMonster.LetsDo_Phase_I.controller;

import java.io.File;
import java.util.zip.ZipFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import YingYingMonster.LetsDo_Phase_I.dao.FileDAO;
import YingYingMonster.LetsDo_Phase_I.dao.UserDAO;
import YingYingMonster.LetsDo_Phase_I.daoImpl.FileDAOImpl;
import YingYingMonster.LetsDo_Phase_I.daoImpl.UserDAOImpl;
import YingYingMonster.LetsDo_Phase_I.model.Tag;
import YingYingMonster.LetsDo_Phase_I.service.DataService;
import YingYingMonster.LetsDo_Phase_I.serviceImpl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/worker")
@Api()
public class WorkerController {

//	@PostMapping("/chooseProject")

	@Autowired
	private DataService dataService;
	
	@PostMapping("/upload")
	@ApiOperation(value = "工人上传一张图片的标签（Tag）",notes="用于在线作业，返回值未定")
	public void uploadTag(@RequestParam ("workerId")String workerId,
			@ModelAttribute("tag")Tag tag){
		dataService.uploadTag(workerId, tag);
	}
	
	@PostMapping("/download/all")
	@ApiOperation(value = "下载整个数据集")
	public File downloadDataSet(@RequestParam ("workerId")String workerId
			,@RequestParam ("dataSetId")String dataSetId){
		return dataService.downloadDataSet(workerId, dataSetId);
	}
	
	@PostMapping("/download/one")
	@ApiOperation(value="下载单个数据")
	public File downloadData(@RequestParam("workerId")String workerId,
			@RequestParam("dataSetId")String dataSetId,
			@RequestParam("dataId")String dataId){
		return dataService.downloadData(workerId, dataId,dataSetId);
	}
}
