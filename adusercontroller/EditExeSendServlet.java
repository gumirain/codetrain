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
import jp.kelonos.dao.TxtDao;
import jp.kelonos.dto.ExeDto;
import jp.kelonos.dto.TxtDto;

@WebServlet("/edit-exe-send")
public class EditExeSendServlet extends HttpServlet {
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
		ExeDto exeDto = new ExeDto();
		try (Connection conn = DataSourceManager.getConnection()) {
			ExeDao exeDao = new ExeDao(conn);
			exeDto = exeDao.findByArticleId2(articleId);
			req.setAttribute("exe", exeDto);

			req.getRequestDispatcher("WEB-INF/add-exe.jsp").forward(req, resp);
		} catch (SQLException | NamingException e) {
			// システムエラーに遷移する
			req.getRequestDispatcher("system-error.jsp").forward(req, resp);
		}
	}
}
