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
		//���ע��ҳ��ĺ�̨����
		//1.�õ��û������ע����Ϣ����Ҫ����û��ı�������
		//�����������,���ַ�ʽֻ�ܽ�����ύ��ʽΪpost�ύ�����ǵ����ύ��ʽΪgetʱ��Ҫʹ�������ķ�ʽ����������⣬���ǿ����к���ʹ��get�ύ��ʽ
		request.setCharacterEncoding("UTF-8");
		//�õ��û�ע�����Ϣ
		//Ϊ�˽��ÿ��ֻ���õ�һ����������ʹ�õ���request.getParameterMap()�ķ�ʽ�õ�������Ϣ
		Map<String, String[]> parameterMap = request.getParameterMap();
		//Ϊ�˰�ȫ����Ϣ��װ��User�����У�ʹ��һ������Beanutils��ʹ��ǰ��Ҫ����beanutils��
		User user = new User();
		
		
			try {
				BeanUtils.populate(user, parameterMap);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			} 
		//�������λ�� user�����Ѿ���װ����
	    //�ֶ���װuid----uuid---������ظ����ַ���32λ--java�������ɺ���36λ
		user.setUid(UUID.randomUUID().toString());
		//---------------�����������˽��û���Ϣ��װ����User�����У���������ɽ����ݴ�ŵ����ݿ���------------------
		//�������ݵĲ�����������һ������,������Ҫ�������user
		try {
			regist(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//���ע��ɹ�����õ�ǰҳ���ض��򵽵�¼ҳ��
		response.sendRedirect(request.getContextPath()+"/login.jsp");
		
		//request.getRequestDispatcher("").forward(request, response);
		
	}
	
	public  void regist(User user) throws SQLException {
		//ʹ�ù�����
		QueryRunner runnable = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		runnable.update(sql,user.getUid(),user.getUsername(),user.getPassword(),user.getName(),
				user.getEmail(),null,user.getBirthday(),user.getSex(),null,null);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}



















