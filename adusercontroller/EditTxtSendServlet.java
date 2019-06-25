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
import jp.kelonos.dao.TxtDao;
import jp.kelonos.dto.TxtDto;

@WebServlet("/edit-txt-send")
public class EditTxtSendServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session == null || session.getAttribute("adUserDto") == null) {
			// ログイン画面に遷移する
			req.getRequestDispatcher("aduser-login.html").forward(req, resp);
			return;
		}
		req.setCharacterEncoding("UTF-8");
		String strArtileId = req.getParameter("articleId");
		int articleId = Integer.parseInt(strArtileId);
		TxtDto txtDto = new TxtDto();
		try (Connection conn = DataSourceManager.getConnection()) {
                TxtDao txtDao = new TxtDao(conn);
                txtDto = txtDao.findByArticleId2(articleId);
                req.setAttribute("txt", txtDto);
                
                req.getRequestDispatcher("WEB-INF/add-txt.jsp").forward(req, resp);;
		} catch (SQLException | NamingException e) {
			// システムエラーに遷移する
			req.getRequestDispatcher("system-error.jsp").forward(req, resp);
		}
	}
}
