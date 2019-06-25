package jp.kelonos.comusercontroller;

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
import jp.kelonos.dao.ComUserDao;
import jp.kelonos.dto.AdUserDto;
import jp.kelonos.dto.ComUserDto;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = { "/login-comuser" }, initParams = {
		@WebInitParam(name = "password", value = "CodeTrain777") })
public class LoginComUserServlet extends HttpServlet {

	/**
<<<<<<< .mine
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		String id = request.getParameter("id");
		String password = request.getParameter("password");

		HttpSession session = request.getSession(true);
		ComUserDto comUserDto = checkUser(id, password);

		if (comUserDto != null) {
			session.setAttribute("comUserDto", comUserDto);
			if (getInitParameter("password").equals(password)) {
				request.getRequestDispatcher("WEB-INF/change-intial-comuserpassword.jsp").forward(request, response);
				return;
			} else {
				response.sendRedirect("/CodeTrain/list-user");				
			}
		} else {
			response.sendRedirect("/CodeTrain/comuser-login.html");
		}
	}

	private ComUserDto checkUser(String id, String password) {

		try (Connection con = DataSourceManager.getConnection()) {
			ComUserDao comUserLoginDao = new ComUserDao(con);
			ComUserDto comUserDto = comUserLoginDao.findByIdAndPassword(id, password);
			return comUserDto;
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
}