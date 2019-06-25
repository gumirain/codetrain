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
import jp.kelonos.dao.MessageDao;
import jp.kelonos.dto.MessageDto;
import jp.kelonos.dto.AdUserDto;

@WebServlet("/aduser-replymessage")
public class AdUserMessageReplyServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		String repBody = request.getParameter("reply");
		int messageId = Integer.parseInt(request.getParameter("messageId"));

		HttpSession session = request.getSession(true);

		try (Connection conn = DataSourceManager.getConnection()) {

			MessageDao replyDao = new MessageDao(conn);
			replyDao.replyAdUserMessage(messageId, repBody);

			MessageDao messageDao = new MessageDao(conn);
			List<MessageDto> messageList = messageDao.selectAll();

			request.setAttribute("messages", messageList);

			response.sendRedirect("/CodeTrain/message-aduser");
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			e.printStackTrace();
			response.sendRedirect("system-error.jsp");
		}
	}
}
