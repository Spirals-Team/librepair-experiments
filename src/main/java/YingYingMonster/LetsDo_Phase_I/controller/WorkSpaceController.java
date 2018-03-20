package YingYingMonster.LetsDo_Phase_I.controller;

import java.util.ArrayList;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import YingYingMonster.LetsDo_Phase_I.model.User;
import YingYingMonster.LetsDo_Phase_I.service.DataService;
import YingYingMonster.LetsDo_Phase_I.service.RepositoryService;
import YingYingMonster.LetsDo_Phase_I.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/workSpace")
@Api()
public class WorkSpaceController {

	@Autowired
	private RepositoryService repoService;
	@Autowired
	private DataService dataService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	@ApiOperation(value="返回用户工作间页面",notes="工作间页面上有其他按钮可以处理更多事务")
	public String visitWorkSpace(@PathVariable("id")String id,Model model){
		
		User user=userService.findUser(id);
		model.addAttribute("user", user);
		return "workSpace";
	}
	
	
	@GetMapping("/projects/{id}")
	@ApiOperation(value="查看用户的project",notes="根据String列表显示文件夹结构")
	public String viewProjects(Model model,@PathVariable("id") String id){
		
		ArrayList<String> files=null;
		try {
			files = repoService.readDirs(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(files==null)
			//返回报错页面
		
		model.addAttribute("files", files);
		return "workSpace";
	}
	
	@GetMapping("/projects/{id}/**")
	@ApiOperation(value="返回对应用户对应目录下的文件夹名称")
	@ResponseBody
	public String viewSubDirs(@PathVariable("id")String id,
			HttpServletRequest request,Model model){
		
		String path=request.getPathInfo();
		
		ArrayList<String> files=null;
		try {
			files = repoService.readDirs(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(files==null){
			//错误处理			
		}
		
		model.addAttribute("files", files);
		return "workSpace";
	}
}
