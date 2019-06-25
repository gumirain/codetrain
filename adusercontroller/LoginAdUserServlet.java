package jp.kelonos.adusercontroller;

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
import jp.kelonos.dao.AdUserDao;
import jp.kelonos.dto.AdUserDto;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = { "/login-aduser" }, initParams = {
		@WebInitParam(name = "password", value = "CodeTrain777") })
public class LoginAdUserServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("aduserid");
		String password = request.getParameter("aduserpassword");
		
		HttpSession session = request.getSession(true);
		AdUserDto adUserDto = checkUser(id, password);

		if (adUserDto != null) {
			session.setAttribute("adUserDto", adUserDto);
			if (getInitParameter("password").equals(password)) {
				request.getRequestDispatcher("WEB-INF/change-intial-aduserpassword.jsp").forward(request, response);
				return;
			} else {
				response.sendRedirect("/CodeTrain/view-aduser");				
			}
		} else {
			response.sendRedirect("/CodeTrain/aduser-login.html");
		}
	}

	private AdUserDto checkUser(String id, String password) {

		try (Connection con = DataSourceManager.getConnection()) {
			AdUserDao adUserLoginDao = new AdUserDao(con);
			AdUserDto adUserDto = adUserLoginDao.findByIdAndPassword(id, password);
			return adUserDto;
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
