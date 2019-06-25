package jp.kelonos.usercontroller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.kelonos.DataSourceManager;
import jp.kelonos.dao.UserDao;
import jp.kelonos.dto.UserDto;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns={"/login-user"}, initParams={@WebInitParam(name="password", value="CodeTrain777")})
public class LoginUserServlet extends HttpServlet {


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	
		String loginId = request.getParameter("id");
		String loginPassword = request.getParameter("password");
		String uri = request.getParameter("uri");

	
		HttpSession session = request.getSession(true);

		
		try (Connection conn = DataSourceManager.getConnection()) {

		
			UserDao loginDao = new UserDao(conn);
			UserDto userDto = loginDao.findByIdAndPassword(loginId, loginPassword);
			
			
			session.setAttribute("user", userDto);
			session.removeAttribute("message");

		
			if (userDto == null ) {
				session.setAttribute("message", "　　　*ログイン失敗");
				response.sendRedirect("list-category");
				return;
			}

	
			if (getInitParameter("password").equals(loginPassword)) {
				request.getRequestDispatcher("WEB-INF/change-password.jsp").forward(request, response);
				return;
			}
						
			response.sendRedirect(uri);
			
		} catch (SQLException | NamingException e) {
						
	
			request.getRequestDispatcher("system-error.jsp").forward(request, response);
		}
	}
}
