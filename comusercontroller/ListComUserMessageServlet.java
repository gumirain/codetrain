package jp.kelonos.comusercontroller;

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
import jp.kelonos.dto.ComUserDto;
import jp.kelonos.dto.MessageDto;

@WebServlet("/comuser-list-message")
public class ListComUserMessageServlet extends HttpServlet{

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		// セッションを取得する
		HttpSession session = request.getSession(true);
		if (session == null || session.getAttribute("comUserDto") == null) {
			request.getRequestDispatcher("comuser-login.html").forward(request, response);
			return;
		}
		
		ComUserDto comUser = (ComUserDto) session.getAttribute("comUserDto");
		int comUserId = comUser.getComUserId();

		try (Connection conn = DataSourceManager.getConnection()) {

			// message情報を取得
			MessageDao comUserMessageDao = new MessageDao(conn);
			List<MessageDto> comUserMessageList = comUserMessageDao.selectByComUserId(comUserId);

			// セッション設定
			request.setAttribute("messages", comUserMessageList);

			// メッセージ一覧画面に遷移する
			request.getRequestDispatcher("WEB-INF/comuser-list-message.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			e.printStackTrace();
			response.sendRedirect("system-error.jsp");
		}
	}
}
