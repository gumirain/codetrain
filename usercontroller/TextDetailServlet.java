package jp.kelonos.usercontroller;

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

import jp.kelonos.DataSourceManager;
import jp.kelonos.dao.ArticleDao;
import jp.kelonos.dao.TxtDao;
import jp.kelonos.dto.ArticleDto;
import jp.kelonos.dto.TxtDto;

@WebServlet("/txt-detail")
public class TextDetailServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


//		遷移元からテキスト情報を取得
		int articleId = Integer.parseInt(request.getParameter("articleId"));
		
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		
		
	

		try (Connection conn = DataSourceManager.getConnection()) {
			
		   	ArticleDao articledao = new ArticleDao(conn);
					ArrayList<ArticleDto> articleList = articledao.findByCategoryIdCourseId( courseId);
					request.setAttribute("articleList", articleList);

			// テキスト情報を取得
			TxtDao txtDao = new TxtDao(conn);
			TxtDto text = txtDao.findByArticleId(articleId);
			
//			リクエストスコープ
			request.setAttribute("uri", request.getRequestURI() + "?articleId=" + articleId + "&courseId=" + courseId);
			request.setAttribute("courseId", courseId);
			request.setAttribute("text", text);
			request.getRequestDispatcher("WEB-INF/text-detail.jsp").forward(request, response);
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	response.sendRedirect("system-error.jsp");
} catch (NamingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	response.sendRedirect("system-error.jsp");
}}
}
