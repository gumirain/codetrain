package jp.kelonos.usercontroller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.kelonos.DataSourceManager;
import jp.kelonos.dao.UserDao;
import jp.kelonos.dto.UserDto;

@WebServlet("/initial_password")
public class ChangeInitialPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// トップページに遷移する
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String initialPassword = request.getParameter("initial_password");
		String newPassword = request.getParameter("new_password");
		String changePassword = request.getParameter("change_password");
		
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("user");
		String email = userDto.getUserEmail();

		try (Connection conn = DataSourceManager.getConnection()) {
			if ("CodeTrain777".equals(initialPassword) && newPassword == changePassword) {
				// 新規パスワード登録
				UserDao changePasswordDao = new UserDao(conn);
				changePasswordDao.updatePassword(email, newPassword);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		} catch (SQLException | NamingException e) {

//			 システムエラーに遷移する
			request.getRequestDispatcher("system-error.jsp").forward(request, response);
		}
	}
}
