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
import jp.kelonos.dto.ArticleDto;
import jp.kelonos.dto.CategoryDto;
import jp.kelonos.dto.CourseDto;
import jp.kelonos.dto.UserDto;

/**
 * Servlet implementation class ThreadListServlet
 */
@WebServlet("/view-course")
public class ViewCourseServlet extends HttpServlet {


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {



		try (Connection conn = DataSourceManager.getConnection()) {

			HttpSession session = request.getSession(true);
			session.removeAttribute("queries");
			
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));

		
			
			CategoryDao categorydao = new CategoryDao(conn);
			ArrayList<CategoryDto> categoryList = categorydao.findAll();
			request.setAttribute("categoryList", categoryList);
			
			
           
         
    	
        
          	
          
          	if(session.getAttribute("user") != null) {
          	
          	UserDto user = (UserDto) session.getAttribute("user");
            int userId = user.getUserId();
         	ArticleDao articledao = new ArticleDao(conn);
        	ArrayList<CourseDto> courseList = new ArrayList<>();
        	CourseDao coursedao = new CourseDao(conn);
       // 	int countCourse = coursedao.findCountCourse();
        	int maxcountId = coursedao.findMaxCourseId();
            for(int i = 1; i <= maxcountId; i++) { 
            ProgressDao progressdao = new ProgressDao(conn);
        	float countProgressTxt = progressdao.countTxtByUserIdAndCourseId( userId, i);
    		float countArticle = articledao.countArticleByCourseId(i);
    		int progress = (int)(countProgressTxt / countArticle * 100);
    		CourseDto courseDto = coursedao.findByCourseIdAndSetProgress( i, progress);
    		courseList.add(courseDto);
            }
            request.setAttribute("courseList", courseList);
          	} else {
          		CourseDao coursedao = new CourseDao(conn);
          		ArrayList<CourseDto> courseList = coursedao.selectAll();
          		request.setAttribute("courseList", courseList);
          	}
            
			request.setAttribute("uri", request.getRequestURI() + "?categoryId=" + categoryId);
			request.setAttribute("categoryId", categoryId);

			request.getRequestDispatcher("WEB-INF/view-course.jsp").forward(request, response);
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
