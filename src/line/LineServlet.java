package line;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LineServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String method = request.getMethod();
		System.out.println("method:"+method);
	
		String requestURI = request.getRequestURI();
		StringBuffer requestURL = request.getRequestURL();
		System.out.println("uri:"+requestURI);
		System.out.println("url:"+requestURL);
		
		String contextPath = request.getContextPath();
		System.out.println("web”¶”√"+contextPath);
		
		String queryString = request.getQueryString();
		System.out.println(queryString);
		
		String remoteAddr = request.getRemoteAddr();
		System.out.println("IP:"+remoteAddr);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}