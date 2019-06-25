package jp.kelonos.usercontroller;

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

@WebServlet("/user-detail-message")
public class UserDetailMessageServlet extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		UTF-8設定
		response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
//        セッション情報取得
        HttpSession session = request.getSession(true);
        int message_id = Integer.parseInt(request.getParameter("messageId"));
        
        try (Connection conn = DataSourceManager.getConnection()) {
        	
        	// message情報を取得
        				MessageDao messageDao = new MessageDao(conn);
        				MessageDto messageDetail =  messageDao.selectByMessageId(message_id);
//        				セッション設定
        				request.setAttribute("messagedetail", messageDetail);
        				//message詳細に遷移する
        				request.getRequestDispatcher("WEB-INF/user-detail-message.jsp").forward(request, response);
        				
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	response.sendRedirect("system-error.jsp");
} catch (NamingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	response.sendRedirect("system-error.jsp");
}
}}
