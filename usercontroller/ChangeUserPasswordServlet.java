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


@WebServlet("/change_password")
public class ChangeUserPasswordServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// �g�b�v�y�[�W�ɑJ�ڂ���
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String oldPassword = request.getParameter("old_password");
		String newPassword = request.getParameter("new_password");
		String changePassword = request.getParameter("change_password");

		HttpSession session = request.getSession(true);
		UserDto user = (UserDto) session.getAttribute("user");
		String password = user.getUserPassword();
		String email = user.getUserEmail();

			try (Connection conn = DataSourceManager.getConnection()) {
				if (password.equals(oldPassword) && newPassword.equals(changePassword)) {
					// �V�K�p�X���[�h�o�^
					UserDao changePasswordDao = new UserDao(conn);
					changePasswordDao.updatePassword(email, newPassword);
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}else {
					String errormessage = "�p�X���[�h������������܂���";
					request.setAttribute("errormessage", errormessage);
					request.getRequestDispatcher("/WEB-INF/change_password.jsp").forward(request, response);
				}
		} catch (SQLException | NamingException e) {

//			 �V�X�e���G���[�ɑJ�ڂ���
			request.getRequestDispatcher("system-error.jsp").forward(request, response);
		}
	}
}