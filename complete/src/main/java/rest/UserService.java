package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UserDao;
import beans.User;

@Service
public class UserService {

	@Autowired
	UserDao userDao;
	
	public User login(User user) {
		user = userDao.login(user);
		return user;
	}

}
