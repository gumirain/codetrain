package jp.kelonos.comusercontroller;

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
import jp.kelonos.dao.UserDao;
import jp.kelonos.dto.ComUserDto;
import jp.kelonos.dto.UserDto;

@WebServlet("/delete-user")
public class DeleteUserServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("comuser-login.html");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//セッションを取得
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("comUserDto") == null) {
			// チャンネル一覧画面に遷移する
			req.getRequestDispatcher("comuser-login.html").forward(req, resp);
			return;
		}
		
		//セッションからログインユーザ情報を取得
		ComUserDto comUser = (ComUserDto)session.getAttribute("comUser");
		
		//フォームのデータを取得
		req.setCharacterEncoding("UTF-8");
		UserDto userDeleteDto = new UserDto();
		userDeleteDto.setUserId(Integer.parseInt(req.getParameter("deleteUserId")));
		
		//コネクションを取得
		try(Connection conn = DataSourceManager.getConnection()) {
			
			UserDao userDeleteDao = new UserDao(conn);
			userDeleteDao.delete(userDeleteDto.getUserId());
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		req.setAttribute("message", "ユーザを削除しました");
		//利用者一覧に遷移
		req.getRequestDispatcher("list-user").forward(req, resp);;
	}
}
