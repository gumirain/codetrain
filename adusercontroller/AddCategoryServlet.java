package jp.kelonos.adusercontroller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
import jp.kelonos.dao.MessageDao;
import jp.kelonos.dto.CategoryDto;
import jp.kelonos.dto.MessageDto;

@WebServlet("/add-category")
public class AddCategoryServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session == null || session.getAttribute("adUserDto") == null) {
			// ログイン画面に遷移する
			req.getRequestDispatcher("aduser-login.html").forward(req, resp);
			return;
		}
		req.setCharacterEncoding("UTF-8");
		String newCategory = req.getParameter("newCategory");
		try (Connection conn = DataSourceManager.getConnection()) {

			// categoryを作成
			CategoryDao categoryDao = new CategoryDao(conn);
		     categoryDao.createCategory(newCategory);

			// 学習機能管理画面に遷移する
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
