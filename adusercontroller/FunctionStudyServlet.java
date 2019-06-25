
package jp.kelonos.adusercontroller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
import jp.kelonos.dto.ArticleDto;
import jp.kelonos.dto.CategoryDto;
import jp.kelonos.dto.CourseDto;
import jp.kelonos.dto.ExeDto;
import jp.kelonos.dto.TxtDto;

@WebServlet("/menu-servlet")
public class FunctionStudyServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		if (session == null || session.getAttribute("adUserDto") == null) {
			// ログイン画面に遷移する
			req.getRequestDispatcher("aduser-login.html").forward(req, resp);
			return;
		}
		// コネクションを取得する
		
		try (Connection conn = DataSourceManager.getConnection()) {
			
			// チャンネルを追加する
			
			CategoryDao categoryDao = new CategoryDao(conn);
			ArrayList<CategoryDto> categoryList = new ArrayList<CategoryDto>();
			categoryList = categoryDao.findAll();
			
			CourseDao courseDao = new CourseDao(conn);
			ArrayList<CourseDto> courseList = new ArrayList<CourseDto>();
			courseList = courseDao.selectAll();
			
			ArticleDao articledao = new ArticleDao(conn);
			ArrayList<ArticleDto> articleList = new ArrayList<ArticleDto>();
			articleList = articledao.findAll();
			
			TxtDao txtDao = new TxtDao(conn);
			ArrayList<TxtDto> txtList = new ArrayList<>();
			txtList = txtDao.findArticleTitles();
			
			ExeDao exeDao = new ExeDao(conn);
			ArrayList<ExeDto> exeList = new ArrayList<>();
			exeList = exeDao.findArticleTitles();
			
			// チャンネル名をリクエストスコープに保持する
			
			req.setAttribute("categoryList", categoryList);
			req.setAttribute("courseList", courseList);
			req.setAttribute("txtList", txtList);
			req.setAttribute("exeList", exeList);
			req.setAttribute("articleList", articleList);

			req.getRequestDispatcher("WEB-INF/menu.jsp").forward(req, resp);
			
		} catch (SQLException | NamingException e) {
			 // システムエラーに遷移する
			req.getRequestDispatcher("system-error.jsp").forward(req, resp);
		}

	}
}
