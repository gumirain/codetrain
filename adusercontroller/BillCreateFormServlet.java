package jp.kelonos.adusercontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/bill-form")
public class BillCreateFormServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// セッションを取得する
		HttpSession session = req.getSession(true);
		if (session == null || session.getAttribute("adUserDto") == null) {
			req.getRequestDispatcher("aduser-login.html").forward(req, resp);
			return;
		}
		req.getRequestDispatcher("/WEB-INF/bill-create.jsp").forward(req, resp);;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
