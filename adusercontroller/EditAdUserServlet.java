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
import jp.kelonos.dto.AdUserDto;
import jp.kelonos.dto.UserDto;

@WebServlet("/edit-aduser")
public class EditAdUserServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// �g�b�v�y�[�W�ɑJ�ڂ���
		response.sendRedirect("/view-aduser");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		
		int aduserId = Integer.parseInt(request.getParameter("aduserId"));
		String adusername = request.getParameter("aduserName");
		String adusermail = request.getParameter("aduserMail");
		String authority = request.getParameter("authority");
		int flag;
		if (authority == null) {
			flag = 0;
		} else {
			flag = Integer.parseInt(request.getParameter("authority"));
		}

		try (Connection conn = DataSourceManager.getConnection()) {

			AdUserDao aduserDao = new AdUserDao(conn);
			aduserDao.update(adusername, flag, adusermail, aduserId);
			request.getRequestDispatcher("/view-aduser").forward(request, response);

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
