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
import jp.kelonos.dao.ProgressDao;
import jp.kelonos.dao.CourseDao;
import jp.kelonos.dto.CourseDto;
import jp.kelonos.dto.UserDto;

@WebServlet("/text-complete")
public class TextCompleteServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		request.setAttribute("courseId", courseId);

		// �g�b�v�y�[�W�ɑJ�ڂ���
		response.sendRedirect("detail-course");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		int txtId = Integer.parseInt(request.getParameter("txtId"));
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		int articleId = Integer.parseInt(request.getParameter("articleId"));

		HttpSession session = request.getSession(true);
		UserDto user = (UserDto) session.getAttribute("user");
		int userId = user.getUserId();

		try (Connection conn = DataSourceManager.getConnection()) {
			ProgressDao completeDao = new ProgressDao(conn);
			completeDao.completeTxt(userId, txtId, articleId, courseId);

			request.setAttribute("courseId", courseId);

			request.getRequestDispatcher("detail-course").forward(request, response);
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
