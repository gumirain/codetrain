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
import jp.kelonos.dao.MessageDao;
import jp.kelonos.dto.MessageDto;

@WebServlet("/aduser_messagedetail")
public class MessageAdUserDetailServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(true);
		int message_id = Integer.parseInt(request.getParameter("messageId"));

		try (Connection conn = DataSourceManager.getConnection()) {

			MessageDao messageDao = new MessageDao(conn);
			MessageDto messageDetail = messageDao.selectByMessageId(message_id);
			request.setAttribute("messageId", message_id);
			request.setAttribute("messagedetail", messageDetail);
			request.getRequestDispatcher("WEB-INF/aduser-detail-message.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			e.printStackTrace();
			response.sendRedirect("system-error.jsp");
		}
	}
}
