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
import jp.kelonos.dao.ExeDao;

@WebServlet("/delete-exe")
public class DeleteExeServlet extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	HttpSession session = req.getSession();
	if (session == null || session.getAttribute("adUserDto") == null) {
		// ログイン画面に遷移する
		req.getRequestDispatcher("aduser-login.html").forward(req, resp);
		return;
	}
	String strArticleId = req.getParameter("articlelId");
	int articleId = Integer.parseInt(strArticleId);
	
	try (Connection conn = DataSourceManager.getConnection()) {

		ExeDao exeDao = new ExeDao(conn);
		exeDao.deleteByArticleId(articleId);

		resp.sendRedirect("menu-servlet");
	} catch (SQLException | NamingException e) {
		// システムエラーに遷移する
		req.getRequestDispatcher("system-error.jsp").forward(req, resp);
	}
}
	
}

