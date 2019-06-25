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
import jp.kelonos.dao.ExeDao;
import jp.kelonos.dto.CategoryDto;

@WebServlet("/delete-cateogry-send")
public class DeleteCategorySendServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session == null || session.getAttribute("adUserDto") == null) {
			// ログイン画面に遷移する
			req.getRequestDispatcher("aduser-login.html").forward(req, resp);
			return;
		}
		try (Connection conn = DataSourceManager.getConnection()) {

			CategoryDao categoryDao = new CategoryDao(conn);
			ArrayList<CategoryDto> categoryList = new ArrayList<CategoryDto>();
			categoryList = categoryDao.findAll();
			
			req.setAttribute("categoryList", categoryList);

			req.getRequestDispatcher("WEB-INF/delete-category.jsp").forward(req, resp);;
			
			
		} catch (SQLException | NamingException e) {
			// システムエラーに遷移する
			req.getRequestDispatcher("system-error.jsp").forward(req, resp);
		}
	}
}
