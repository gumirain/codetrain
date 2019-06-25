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
import jp.kelonos.dao.CategoryDao;
import jp.kelonos.dto.CategoryDto;

@WebServlet("/add-course-send")
public class AddCourseSendServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (Connection conn = DataSourceManager.getConnection()) {
			HttpSession session = req.getSession();
			if (session == null || session.getAttribute("adUserDto") == null) {
				// ログイン画面に遷移する
				req.getRequestDispatcher("aduser-login.html").forward(req, resp);
				return;
			}
			CategoryDao categoryDao = new CategoryDao(conn);
			ArrayList<CategoryDto> categoryList = new ArrayList<CategoryDto>();
			categoryList = categoryDao.findAll();

			req.setAttribute("categoryList", categoryList);
			req.getRequestDispatcher("WEB-INF/add-course.jsp").forward(req, resp);
			
		} catch (SQLException e) {

			resp.sendRedirect("system-error.jsp");
		} catch (NamingException e) {

			resp.sendRedirect("system-error.jsp");
		}
	}
}
