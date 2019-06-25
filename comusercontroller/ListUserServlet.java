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
import jp.kelonos.dao.UserDao;
import jp.kelonos.dto.ComUserDto;
import jp.kelonos.dto.UserDto;

@WebServlet("/list-user")
public class ListUserServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//コネクションを取得
		try(Connection conn = DataSourceManager.getConnection()){
			//セッションを取得
			HttpSession session = req.getSession(false);
			if (session == null || session.getAttribute("comUserDto") == null) {
				req.getRequestDispatcher("comuser-login.html").forward(req, resp);
				return;
			}
			
			
			//セッションからログインユーザ情報を取得
			Object comUser = session.getAttribute("comUserDto");
			
			//カンパニーIDを取得
			//ComUserDto companyIdDto = new ComUserDto();
			int companyId = ((ComUserDto) comUser).getCompanyId();
			
			//利用者一覧を取得
			UserDao userListDao = new UserDao(conn);
			ArrayList<UserDto> userList = userListDao.findByCompanyId(companyId);
			
			//利用者一覧データをリクエストスコープに保持する
			req.setAttribute("userList", userList);
			
			//URIをリクエストスコープに保持する
			req.setAttribute("userUri", req.getRequestURI());
			
			//利用者一覧画面に遷移
			req.getRequestDispatcher("/WEB-INF/list-user.jsp").forward(req, resp);
			
		} catch (SQLException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}