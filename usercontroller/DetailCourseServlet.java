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
import javax.servlet.http.HttpSession;

import jp.kelonos.DataSourceManager;
import jp.kelonos.dao.ArticleDao;
import jp.kelonos.dao.CategoryDao;
import jp.kelonos.dao.CourseDao;
import jp.kelonos.dao.ProgressDao;
import jp.kelonos.dao.TxtDao;
import jp.kelonos.dto.ArticleDto;
import jp.kelonos.dto.CategoryDto;
import jp.kelonos.dto.CourseDto;
import jp.kelonos.dto.UserDto;

/**
 * Servlet implementation class ThreadListServlet
 */
@WebServlet("/detail-course")
public class DetailCourseServlet extends HttpServlet {


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {



		try (Connection conn = DataSourceManager.getConnection()) {

			HttpSession session = request.getSession(true);
			session.removeAttribute("queries");


			CourseDao coursedao = new CourseDao(conn);
			ArrayList<CourseDto> courseList = coursedao.selectAll();
			request.setAttribute("courseList", courseList);
			
			CategoryDao categorydao = new CategoryDao(conn);
			ArrayList<CategoryDto> categoryList = categorydao.findAll();
			request.setAttribute("categoryList", categoryList);
			
		   int courseId = Integer.parseInt(request.getParameter("courseId"));
           request.setAttribute("courseId", courseId);
          
       	ArticleDao articledao = new ArticleDao(conn);
		ArrayList<ArticleDto> articleList = articledao.findByCategoryIdCourseId(courseId);
		request.setAttribute("articleList", articleList);
		
		if(session.getAttribute("user") != null) {
			UserDto user = (UserDto) session.getAttribute("user");
			int userId = user.getUserId();
			
			ProgressDao progressdao = new ProgressDao(conn);
			float countProgressTxt = progressdao.countTxtByUserIdAndCourseId( userId,  courseId);

			

			float countArticle = articledao.countArticleByCourseId(courseId);
			
			int progress = (int)(countProgressTxt / countArticle * 100);
			request.setAttribute("progress", progress);
			
			int countarticleId = articledao.countArticleByCourseId(courseId);

			int articleBeginningId = articleList.get(0).getArticleId();
			request.setAttribute("articleBeginningId", articleBeginningId);

				ProgressDao progressarticle = new ProgressDao(conn);
				ArrayList<Integer> ProgressArticleList = progressarticle.findArticleIdFromProgress(courseId, userId);

				int countprogressarticle = progressarticle.countArticleByUserIdAndCourseId(userId, courseId);

				int a;
				
		
				for (int i = 0; i < countarticleId; i++) {
					 a = articleList.get(i).getArticleId();
				
					if(ProgressArticleList.contains(a)) {
						continue;
					}else {

								request.setAttribute("ProgressArticleId", a);
								request.setAttribute("uri", request.getRequestURI() + "?courseId=" + courseId);
								request.getRequestDispatcher("WEB-INF/detail-course.jsp").forward(request, response);
								return;
						}
					}
			}
			
		// URIをリクエストに保持する
			request.setAttribute("uri", request.getRequestURI() + "?courseId=" + courseId);

			request.getRequestDispatcher("WEB-INF/detail-course.jsp").forward(request, response);
		} catch (SQLException | NamingException e) {

			request.getRequestDispatcher("system-error.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
