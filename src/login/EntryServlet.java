package login;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import register.User;
import utils.DataSourceUtils;


@WebServlet("/entry")
public class EntryServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    User user = null;
	    try {
			user = login(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    if(user!=null) {
	    	response.sendRedirect(request.getContextPath());
	    }else {
	    	request.setAttribute("loginInfo", "用户名或密码错误");
	    	//如果登录失败，就需要转发到当前页面，并且提示用户信息
	    	request.getRequestDispatcher("/login.jsp").forward(request, response);
	    }
	}
	public User login(String username,String password) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		User user = runner.query(sql, new BeanHandler<User>(User.class),username,password);
		return user;
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
