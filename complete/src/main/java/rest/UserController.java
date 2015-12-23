package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import beans.User;

@RestController
@RequestMapping("/gym-agenda/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST , produces={"application/xml", "application/json"})
	public @ResponseBody User login(@RequestBody User user) throws Throwable{
		
		try{
			user = userService.login(user);
		}catch(Throwable t){
			throw new Throwable("Login failed", t);
		}
		return user;
	}
	
}
