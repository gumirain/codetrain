package jp.kelonos.usercontroller;

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
import jp.kelonos.dto.UserDto;

@WebServlet("/reply-message")
public class MessageReplyServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// �g�b�v�y�[�W�ɑJ�ڂ���
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		String reprepbody = request.getParameter("rep-reply");
		int messageId = Integer.parseInt(request.getParameter("messageId"));

		// �Z�b�V�������擾����
		HttpSession session = request.getSession(true);
		UserDto user = (UserDto) session.getAttribute("user");
		int userId = user.getUserId();

		try (Connection conn = DataSourceManager.getConnection()) {

			MessageDao replyDao = new MessageDao(conn);
			replyDao.replyMessage(messageId, reprepbody);

			// message�����擾
			MessageDao messageDao = new MessageDao(conn);
			List<MessageDto> messageList = messageDao.selectByUserId(userId);

//		 	�Z�b�V�����ݒ�
			request.setAttribute("messages", messageList);

			response.sendRedirect("/CodeTrain/message-user");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("system-error.jsp");
		}
	}
}