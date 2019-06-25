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
import jp.kelonos.dao.CategoryDao;
import jp.kelonos.dao.CourseDao;
import jp.kelonos.dao.ExeDao;
import jp.kelonos.dao.TxtDao;

@WebServlet("/delete-category")
public class DeleteCategorySevlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session == null || session.getAttribute("adUserDto") == null) {
			// ログイン一覧画面に遷移する
			req.getRequestDispatcher("aduser-login.html").forward(req, resp);
			return;
		}
		
		String strCategoryId = req.getParameter("categoryId");
		int categoryId = Integer.parseInt(strCategoryId);
		
		try (Connection conn = DataSourceManager.getConnection()) {
           
			TxtDao txtDao = new TxtDao(conn);
			int categoryCountOfTxt  = txtDao.countByCategoryId(categoryId);
			
			if (categoryCountOfTxt > 0) {
				txtDao.deleteByCategoryId(categoryId);
			}
			
			ExeDao exeDao = new ExeDao(conn);
			int categoryCountOfExe  = exeDao.countByCategoryId(categoryId);
			
			if (categoryCountOfExe > 0) {
				exeDao.deleteByCategoryId(categoryId);
			}
			
			ArticleDao articleDao = new ArticleDao(conn);
			int categoryCountOfArticle =  articleDao.countByCategoryId(categoryId);
			
			if (categoryCountOfArticle > 0) {
				articleDao.deleteByCategoryId(categoryId);
			}
			
			CourseDao courseDao = new CourseDao(conn);
			int categoryCountOfCourse = courseDao.countByCategoryId(categoryId);
			
			if (categoryCountOfCourse > 0) {
				courseDao.deleteByCategoryId(categoryId);
			}
			
			CategoryDao categoryDao = new CategoryDao(conn);
			categoryDao.deleteByCategoryId(categoryId);
			
			resp.sendRedirect("menu-servlet");

		} catch (SQLException | NamingException e) {
			// システムエラーに遷移する
			req.getRequestDispatcher("system-error.jsp").forward(req, resp);
		}
	}
}
