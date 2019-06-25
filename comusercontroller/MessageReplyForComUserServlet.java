package jp.kelonos.comusercontroller;

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
import jp.kelonos.dao.MessageDao;
import jp.kelonos.dto.ComUserDto;
import jp.kelonos.dto.MessageDto;

@WebServlet("/comuser-reply-message")
public class MessageReplyForComUserServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 利用者一覧に遷移
		resp.sendRedirect("list-user");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("UTF-8");

		String reprepbody = req.getParameter("rep-reply");
		int messageId = Integer.parseInt(req.getParameter("messageId"));
		String uri = req.getParameter("uri");

		// セッションを取得
		HttpSession session = req.getSession(true);
		if (session == null || session.getAttribute("comUserDto") == null) {
			req.getRequestDispatcher("comuser-login.html").forward(req, resp);
			return;
		}

		ComUserDto comUser = (ComUserDto) session.getAttribute("comUserDto");
		int comUserId = comUser.getComUserId();

		ArrayList<String> errorMessageList = new ArrayList<String>();
		if ("".equals(reprepbody)) {
			errorMessageList.add("返信内容を入力してください");
		}
		if (errorMessageList.size() != 0) {
			// メッセージ詳細に遷移
			req.setAttribute("errorMessageList", errorMessageList);
			req.setAttribute("messageId", reprepbody);
			req.getRequestDispatcher("comuser-detail-message").forward(req, resp);
			return;
		}

		try (Connection conn = DataSourceManager.getConnection()) {

			MessageDao replyDao = new MessageDao(conn);
			replyDao.replyMessage(messageId, reprepbody);

			MessageDao messageDao = new MessageDao(conn);
			List<MessageDto> messageList = messageDao.selectByComUserId(comUserId);

			req.setAttribute("messages", messageList);

			resp.sendRedirect("/CodeTrain/comuser-list-message");
		} catch (SQLException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		}
	}
}
