package blog.utils;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import blog.dao.UserDao;
import blog.daoImpl.UserDaoImpl;
import blog.model.User;

public class LoginUtils {

	public static User login(HttpServletRequest request) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
			return null;

		UserDao dao = UserDaoImpl.getInstance();
		//开始登陆
		Logger log = Logger.getLogger("LoginUtils");
		User user = dao.login(username, password);
		if (user == null) {
			return null;
		} else {
			return user;
		}

	}

}
