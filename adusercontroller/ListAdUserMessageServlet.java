package jp.kelonos.adusercontroller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import jp.kelonos.dao.MessageDao;
import jp.kelonos.dto.AdUserDto;
import jp.kelonos.dto.CategoryDto;
import jp.kelonos.dto.CourseDto;
import jp.kelonos.dto.MessageDto;

@WebServlet("/aduser-list-message")
public class ListAdUserMessageServlet extends HttpServlet{

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		// セッションを取得する
		HttpSession session = request.getSession(true);
		if (session == null || session.getAttribute("adUserDto") == null) {
			request.getRequestDispatcher("aduser-login.html").forward(request, response);
			return;
		}
		
		AdUserDto adUser = (AdUserDto) session.getAttribute("adUserDto");

		try (Connection conn = DataSourceManager.getConnection()) {

			// message情報を取得
			MessageDao adUserMessageDao = new MessageDao(conn);
			List<MessageDto> adUserMessageList = adUserMessageDao.selectAll();
			request.setAttribute("messages", adUserMessageList);
			
			CourseDao coursedao = new CourseDao(conn);
			ArrayList<CourseDto> courseList = coursedao.selectAll();
			request.setAttribute("courseList", courseList);
			
			CategoryDao categorydao = new CategoryDao(conn);
			ArrayList<CategoryDto> categoryList = categorydao.findAll();
			request.setAttribute("categoryList", categoryList);

			// メッセージ一覧画面に遷移する
			request.getRequestDispatcher("WEB-INF/aduser-list-message.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			e.printStackTrace();
			response.sendRedirect("system-error.jsp");
		}
	}
}
