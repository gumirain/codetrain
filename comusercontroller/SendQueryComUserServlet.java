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

@WebServlet("/send-query-comuser")
public class SendQueryComUserServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 利用者一覧に遷移
		resp.sendRedirect("list-user");
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("UTF-8");

		int categoryId = Integer.parseInt(req.getParameter("categoryId"));
		int courseId = Integer.parseInt(req.getParameter("courseId"));
		String title = req.getParameter("title");
		String body = req.getParameter("body");

		HttpSession session = req.getSession(true);
		if (session == null || session.getAttribute("comUserDto") == null) {
			req.getRequestDispatcher("comuser-login.html").forward(req, resp);
			return;
		}
		
		ComUserDto user = (ComUserDto) session.getAttribute("comUserDto");
		int comUserId = user.getComUserId();
		
		ArrayList<String> errorMessageList = new ArrayList<String>();
		if ("カテゴリを選択してください".equals(categoryId)) {
			errorMessageList.add("カテゴリを選択してください");
		}
		if ("コースを選択してください".equals(courseId)) {
			errorMessageList.add("コースを選択してください");
		}
		if ("".equals(title)) {
			errorMessageList.add("件名を入力してください");
		}
		if ("".equals(body)) {
			errorMessageList.add("本文を入力してください");
		}
		if (errorMessageList.size() != 0) {
			// 利用者一覧に遷移
			req.setAttribute("errorMessageList", errorMessageList);
			req.setAttribute("categoryId", categoryId);
			req.setAttribute("courseId", courseId);
			req.setAttribute("title", title);
			req.setAttribute("body", body);
			req.getRequestDispatcher("comuser-query-form").forward(req, resp);
			return;
		}

		try (Connection conn = DataSourceManager.getConnection()) {

			MessageDao sendDao = new MessageDao(conn);
			sendDao.comUserSendMessage(categoryId, courseId, title, body, comUserId);
			req.getRequestDispatcher("list-user").forward(req, resp);

		} catch (SQLException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		}
	}
}
