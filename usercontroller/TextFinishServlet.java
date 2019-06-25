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
import jp.kelonos.dao.CourseDao;
import jp.kelonos.dto.CourseDto;
import jp.kelonos.dto.UserDto;

@WebServlet("/text-finish")
public class TextFinishServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int courseId = Integer.parseInt(request.getParameter("courseId"));

			request.setAttribute("courseId", courseId);

			// �p�X���[�h�ύX��ʂɑJ�ڂ���
			request.getRequestDispatcher("detail-course").forward(request, response);
		} 

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
