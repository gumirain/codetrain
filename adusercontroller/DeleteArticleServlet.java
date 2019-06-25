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

@WebServlet("/delete-article")
public class DeleteArticleServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session == null || session.getAttribute("adUserDto") == null) {
			// ログイン一覧画面に遷移する
			req.getRequestDispatcher("aduser-login.html").forward(req, resp);
			return;
		}

		String strArticleId = req.getParameter("articleId");
		int articleId = Integer.parseInt(strArticleId);

		try (Connection conn = DataSourceManager.getConnection()) {
			
			
			TxtDao txtDao = new TxtDao(conn);
			int articleCountOfTxt  = txtDao.countByArticleId(articleId);
			
			if (articleCountOfTxt > 0) {
				txtDao.deleteByArticleId(articleId);
			}
			
			ExeDao exeDao = new ExeDao(conn);
			int articleCountOfExe  = exeDao.countByArticleId(articleId);
			
			if (articleCountOfExe > 0) {
				exeDao.deleteByArticleId(articleId);
			}

			ArticleDao articleDao = new ArticleDao(conn);
		     articleDao.deleteByArticleId(articleId);
			
			
		
			resp.sendRedirect("menu-servlet");

		} catch (SQLException | NamingException e) {
			// システムエラーに遷移する
			req.getRequestDispatcher("delete-course-error.jsp").forward(req, resp);
		}
	}
}
