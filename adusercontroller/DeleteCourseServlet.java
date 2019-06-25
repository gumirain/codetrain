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
import jp.kelonos.dao.ArticleDao;
import jp.kelonos.dao.CourseDao;
import jp.kelonos.dao.ExeDao;
import jp.kelonos.dao.TxtDao;

@WebServlet("/delete-course")
public class DeleteCourseServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session == null || session.getAttribute("adUserDto") == null) {
			// ログイン一覧画面に遷移する
			req.getRequestDispatcher("aduser-login.html").forward(req, resp);
			return;
		}

		String strCourseId = req.getParameter("courseId");
		int courseId = Integer.parseInt(strCourseId);

		try (Connection conn = DataSourceManager.getConnection()) {
			
			TxtDao txtDao = new TxtDao(conn);
			int courseCountOfTxt  = txtDao.countByCourseId(courseId);
			
			if (courseCountOfTxt > 0) {
				txtDao.deleteByCourseId(courseId);
			}
			
			ExeDao exeDao = new ExeDao(conn);
			int courseCountOfExe  = exeDao.countByCourseId(courseId);
			
			if (courseCountOfExe > 0) {
				exeDao.deleteByCourseId(courseId);
			}

			ArticleDao articleDao = new ArticleDao(conn);
			int courseCountOfArticle =  articleDao.countByCourseId(courseId);
			
			if (courseCountOfArticle > 0) {
				articleDao.deleteByCourseId(courseId);
			}
			
			
			
			CourseDao courseDao = new CourseDao(conn);
			courseDao.deleteByCourseId(courseId);
		

			resp.sendRedirect("menu-servlet");

		} catch (SQLException | NamingException e) {
			// システムエラーに遷移する
			req.getRequestDispatcher("delete-course-error.jsp").forward(req, resp);
		}
	}
}
