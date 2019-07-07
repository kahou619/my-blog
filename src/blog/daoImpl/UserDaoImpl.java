package blog.daoImpl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.impl.Log4JLogger;

import blog.dao.TagDao;
import blog.dao.UserDao;
import blog.db.C3P0Connection;
import blog.model.User;
import blog.utils.DBUtils;

/**
 * 用户服务的具体实现类
 *
 */
public class UserDaoImpl implements UserDao {

	private Connection conn;

	private UserDaoImpl() {
		conn = C3P0Connection.getInstance().getConnection();
	}

	private static UserDao instance;

	public static final UserDao getInstance() {
		if (instance == null) {
			try {
				instance = new UserDaoImpl();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blog.daoImpl.UserDao#register(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean register(String username, String password) {
		// todo
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see blog.daoImpl.UserDao#login(java.lang.String, java.lang.String)
	 */
	@Override
	public User login(String username, String password) {

		User user = null;

		String sql = "select * from t_user where user_name=? and user_password=? ";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			// bean导入
			if (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				user = new User();
				map.put("user_name", rs.getString("user_name"));
				map.put("user_password", rs.getString("user_name"));
				map.put("user_id", rs.getString("user_id"));
				try {
					//用来将一些 key-value 的值（例如 hashmap）映射到 bean 中的属性。
					BeanUtils.populate(user, map);
				} catch (Exception e) {
					throw new RuntimeException();
				}
			}
//			DBUtils.Close(conn, ps, rs);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
}
