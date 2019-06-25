package jp.kelonos.comusercontroller;

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
import jp.kelonos.dao.MessageDao;
import jp.kelonos.dto.ComUserDto;
import jp.kelonos.dto.MessageDto;

@WebServlet("/comuser-detail-message")
public class ComUserDetailMessageServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("UTF-8");

		//セッションを取得
		HttpSession session = req.getSession(true);
		if (session == null || session.getAttribute("comUserDto") == null) {
			req.getRequestDispatcher("comuser-login.html").forward(req, resp);
			return;
		}
		
		// セッションからログインユーザ情報を取得
		Object comUser = session.getAttribute("comUserDto");

		// カンパニーIDを取得
		int companyId = ((ComUserDto) comUser).getCompanyId();

		//メッセージIDを取得
		int message_id = Integer.parseInt(req.getParameter("messageId"));
		
		try (Connection conn = DataSourceManager.getConnection()) {

			// message情報を取得
			MessageDao messageDao = new MessageDao(conn);
			MessageDto messageDetail = messageDao.selectByMessageIdForComUser(message_id);
			// セッション設定
			req.setAttribute("messagedetail", messageDetail);
			
			//URIをリクエストスコープに保持する
			req.setAttribute("userUri", req.getRequestURI());
			
			// message詳細に遷移する
			req.getRequestDispatcher("WEB-INF/comuser-detail-message.jsp").forward(req, resp);

		} catch (SQLException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
