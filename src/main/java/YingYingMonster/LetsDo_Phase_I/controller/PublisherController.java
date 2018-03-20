package YingYingMonster.LetsDo_Phase_I.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import YingYingMonster.LetsDo_Phase_I.model.Requirement;
import YingYingMonster.LetsDo_Phase_I.service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/publisher")
@Api()
public class PublisherController {
	
	@Autowired
	private DataService dataService;
	
	@GetMapping("/upload")
	@ApiOperation(value="返回上传数据集的页面")
	public String visitUploadPage(Model model){
		model.addAttribute("requirement", new Requirement());
		return "uploadDataSet";
	}
	
	@PostMapping("/upload")
	@ApiOperation(value = "发布者上传数据集（一般是压缩文件）和要求")
	public void upload(@RequestParam("publisherId")String publisherId,
			@RequestParam("fileId")String fileId,
			@RequestParam("file")MultipartFile file,
			@ModelAttribute("requirement")Requirement requirement){
		
		try {
			dataService.uploadDataSet(publisherId, fileId, file, requirement);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
