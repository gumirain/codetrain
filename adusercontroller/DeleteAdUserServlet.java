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

@WebServlet("/delete-aduser")
public class DeleteAdUserServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// �g�b�v�y�[�W�ɑJ�ڂ���
		response.sendRedirect("/view-aduser");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		AdUserDto aduser = (AdUserDto) session.getAttribute("adUserDto");
		int id = aduser.getAdUserId();

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		
		int aduserId = Integer.parseInt(request.getParameter("aduserId"));
	
		try (Connection conn = DataSourceManager.getConnection()) {

			AdUserDao aduserDao = new AdUserDao(conn);
			if(id != aduserId) {
			aduserDao.delete(aduserId);}
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
