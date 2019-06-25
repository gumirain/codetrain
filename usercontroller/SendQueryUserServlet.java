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
import jp.kelonos.dao.MessageDao;

@WebServlet("/send-query")


public class SendQueryUserServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// �g�b�v�y�[�W�ɑJ�ڂ���
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		String title = request.getParameter("title");
		String body = request.getParameter("body");

		HttpSession session = request.getSession(true);
		UserDto user = (UserDto) session.getAttribute("user");
		int userId = user.getUserId();

		try (Connection conn = DataSourceManager.getConnection()) {

			MessageDao sendDao = new MessageDao(conn);
			sendDao.sendMessage(categoryId, courseId, title, body, userId);
			request.getRequestDispatcher("index.jsp").forward(request, response);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("system-error.jsp");
		}
	}
}
