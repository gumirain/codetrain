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
import jp.kelonos.dao.AdUserDao;
import jp.kelonos.dto.AdUserDto;
import jp.kelonos.dto.UserDto;

@WebServlet("/edit-aduserform")
public class EditAdUserFormServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		int aduserId = Integer.parseInt(request.getParameter("aduserId"));

		try (Connection conn = DataSourceManager.getConnection()) {

			// message�����擾
			AdUserDao aduserDao = new AdUserDao(conn);
			AdUserDto aduser = aduserDao.findById(aduserId);
			request.setAttribute("aduser", aduser);

			// パスワード変更画面に遷移する
			request.getRequestDispatcher("/WEB-INF/edit_aduser.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
