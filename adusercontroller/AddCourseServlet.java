package jp.kelonos.adusercontroller;

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
import jp.kelonos.dao.CategoryDao;
import jp.kelonos.dao.CourseDao;
import jp.kelonos.dto.CourseDto;

@WebServlet("/add-course")
public class AddCourseServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session == null || session.getAttribute("adUserDto") == null) {
			// ログイン画面に遷移する
			req.getRequestDispatcher("aduser-login.html").forward(req, resp);
			return;
		}
		req.setCharacterEncoding("UTF-8");
		String strcategoryId = req.getParameter("category");
		String courseName = req.getParameter("courseName");
		String courseProperty = req.getParameter("courseProperty");
		String coursePremise = req.getParameter("coursePremise");
		String courseGoal = req.getParameter("courseGoal");
		String courseTime = req.getParameter("time");
		String free = req.getParameter("free");
		int categoryId = Integer.parseInt(strcategoryId);
       int freeFlg = 0;
		if (free == null) {
			freeFlg = 0;
		}else {
			freeFlg = 1;
		}
		
		CourseDto courseDto = new CourseDto();
		courseDto.setCourseName(courseName);
		courseDto.setCourseProperty(courseProperty);
		courseDto.setCoursePremise(coursePremise);
		courseDto.setCourseGoal(courseGoal);
		courseDto.setCourseTime(courseTime);
		courseDto.setCategoryId(categoryId);
		courseDto.setCourseFreeFlag(freeFlg);
		
		try (Connection conn = DataSourceManager.getConnection()) {

			// message情報を取得
			CourseDao courseDao = new CourseDao(conn);
			courseDao.insert(courseDto);

			// メッセージ一覧画面に遷移する
			resp.sendRedirect("menu-servlet");
		} catch (SQLException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		}
	}
}
