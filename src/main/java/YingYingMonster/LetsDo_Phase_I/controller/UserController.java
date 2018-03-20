package YingYingMonster.LetsDo_Phase_I.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import YingYingMonster.LetsDo_Phase_I.model.User;
import YingYingMonster.LetsDo_Phase_I.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Generated;

@Controller
@RequestMapping("/user")
@Api()
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	@ApiOperation(value = "访问用户注册界面")
	public String visitRegisterPage(Model model){
		
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/register")
	@ApiOperation(value = "注册新用户，注册成功后跳转至登录界面；失败则返回注册界面，显示错误信息")
	public String register(@ModelAttribute("user")User user){
		if(userService.register(user))
			return "redirect:/user/login";
		else
			return "register";
	}
	
	@GetMapping("/login")
	@ApiOperation(value = "访问用户登录界面")
	public String visitLoginPage(Model model){
		model.addAttribute("user", new User());
		return "login";
	}
	
	@PostMapping("/login")
	@ApiOperation(value = "用户登录，成功后返回用户工作界面；失败则返回登录界面，显示错误信息")
	public String login(@RequestParam("userId")String userId
			,@RequestParam("password")String password){
		if(userService.login(userId, password))
			return "redirect:/workSpace/"+userId;
		else
			return "login";
	}
	
	@GetMapping("/modify")
	@ApiOperation(value="访问修改信息页面",notes="一定要根据账号密码做身份验证，否则可能会修改到别的用户的数据！")
	public String visitModifyPage(Model model,
			@RequestParam(value="id",required=true)String id,
			@RequestParam(value="pw",required=true)String pw){
		
		User user=userService.findUser(id);
		model.addAttribute("user", user);
		if(user.getPw().equals(pw))
			return "workSpace";
		else
			return "error";//身份验证失败返回错误页
	}
	
	@PostMapping("/modify")
	@ApiOperation(value = "用户修改自身信息")
	public String modify(@ModelAttribute("user")User user){
		if(userService.modify(user))
			return "workSpace";
		else
			return "wrong";
	}
}
