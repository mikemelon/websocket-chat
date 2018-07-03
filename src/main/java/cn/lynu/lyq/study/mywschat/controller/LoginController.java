package cn.lynu.lyq.study.mywschat.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.lynu.lyq.study.mywschat.entity.User;
import cn.lynu.lyq.study.mywschat.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {
	
	@Resource
	private UserService userService;
	
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password,
			HttpSession session){
		User user = userService.findByNameAndPassword(username,password);
		
		if(user!=null){
			log.info(user.toString());
			session.setAttribute("USER_INFO", user);
			return "main";
		}else{
			log.error("user not exist");
			return "login";
		}
	}
	
}
