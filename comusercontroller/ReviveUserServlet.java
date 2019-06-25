package jp.kelonos.comusercontroller;

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
import jp.kelonos.dao.UserDao;
import jp.kelonos.dto.ComUserDto;
import jp.kelonos.dto.UserDto;

@WebServlet("/revive-user")
public class ReviveUserServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("comuser-login.html");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// セッションを取得
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("comUserDto") == null) {
			// チャンネル一覧画面に遷移する
			req.getRequestDispatcher("comuser-login.html").forward(req, resp);
			return;
		}
		
		//セッションからログインユーザ情報を取得
		ComUserDto comUser = (ComUserDto)session.getAttribute("comUser");

		// フォームのデータを取得
		req.setCharacterEncoding("UTF-8");
		UserDto userRestDto = new UserDto();
		userRestDto.setUserId(Integer.parseInt(req.getParameter("userId")));

		// コネクションを取得
		try (Connection conn = DataSourceManager.getConnection()) {

			// 休止理由を追加
			UserDao userRestDao = new UserDao(conn);
			userRestDao.statusOn(userRestDto);

			// メッセージをリクエストスコープに保持
			req.setAttribute("message", "処理が正常に行われました");

		} catch (SQLException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		}

		// 利用者一覧に遷移
		resp.sendRedirect("list-user");
	}
}
