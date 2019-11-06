package register;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;

import utils.DataSourceUtils;


@WebServlet("/regist")
public class Regist extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//	response.getWriter().write("asdfasdf");
		//完成注册页面的后台管理
		//1.拿到用户输入的注册信息。还要解决用户的编码问题
		//解决编码问题,这种方式只能解决表单提交方式为post提交，但是当表单提交方式为get时需要使用其他的方式解决乱码问题，但是开发中很少使用get提交方式
		request.setCharacterEncoding("UTF-8");
		//拿到用户注册的信息
		//为了解决每次只能拿到一个数据这里使用的是request.getParameterMap()的方式拿到所有信息
		Map<String, String[]> parameterMap = request.getParameterMap();
		//为了把全部信息分装到User对象中，使用一个工具Beanutils，使用前需要导入beanutils包
		User user = new User();
		
		
			try {
				BeanUtils.populate(user, parameterMap);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			} 
		//现在这个位置 user对象已经封装好了
	    //手动封装uid----uuid---随机不重复的字符串32位--java代码生成后是36位
		user.setUid(UUID.randomUUID().toString());
		//---------------上面代码完成了将用户信息分装到了User对象中，接下来完成将数据存放到数据库中------------------
		//将对数据的操作单独做成一个函数,单数需要传入参数user
		try {
			regist(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//如果注册成功后就让当前页面重定向到登录页面
		response.sendRedirect(request.getContextPath()+"/login.jsp");
		
		//request.getRequestDispatcher("").forward(request, response);
		
	}
	
	public  void regist(User user) throws SQLException {
		//使用工具类
		QueryRunner runnable = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		runnable.update(sql,user.getUid(),user.getUsername(),user.getPassword(),user.getName(),
				user.getEmail(),null,user.getBirthday(),user.getSex(),null,null);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}



















