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
import jp.kelonos.dao.AdUserDao;
import jp.kelonos.dao.CompanyDao;

@WebServlet("/new-password")
public class NewPasswordAduserServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		HttpSession session = request.getSession(true);
		request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
		request.setAttribute("message", session.getAttribute("message"));
		session.removeAttribute("errorMessageList");
		session.removeAttribute("message");
		
    try (Connection conn = DataSourceManager.getConnection()) {
			
			AdUserDao adUserDao = new AdUserDao(conn);
			// パスワード変更画面に遷移する
			request.getRequestDispatcher("WEB-INF/change-aduserpassword.jsp").forward(request, response);
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
//		// パスワード変更画面に遷移する
//		request.getRequestDispatcher("WEB-INF/change-aduserpassword.jsp").forward(request, response);
//	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

