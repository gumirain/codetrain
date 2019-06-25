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

@WebServlet("/add-user")
public class AddUserServlet extends HttpServlet{
	
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
		Object comUser = session.getAttribute("comUserDto");
		
		//カンパニーIDを取得
		//ComUserDto companyIdDto = new ComUserDto();
		int companyId = ((ComUserDto) comUser).getCompanyId();
		
		//フォームのデータを取得
		req.setCharacterEncoding("UTF-8");
		UserDto userAddDto = new UserDto();
		userAddDto.setUserName(req.getParameter("userName"));
		userAddDto.setUserEmail(req.getParameter("userEmail"));
		userAddDto.setCompanyId(companyId);
		//入力チェック
		ArrayList<String> errorMessageList = new ArrayList<String>();
		if ("".equals(userAddDto.getUserName())) {
			errorMessageList.add("氏名を入力してください");
		}
		if ("".equals(userAddDto.getUserEmail())) {
			errorMessageList.add("メールアドレスを入力してください");
		}
		if (userAddDto.getUserName().length() > 60) {
			errorMessageList.add("氏名は６0文字以内で入力してください");
		}
		if (userAddDto.getUserEmail().length() > 255) {
			errorMessageList.add("メールアドレスは２５５文字以内で入力してください");
		}
		if (errorMessageList.size() != 0) {
			//利用者一覧に遷移
			req.setAttribute("errorMessageList", errorMessageList);
			req.setAttribute("userName", userAddDto.getUserName());
			req.setAttribute("userEmail", userAddDto.getUserEmail());
			req.getRequestDispatcher("list-user").forward(req,resp);
			return;
		}
		
		//コネクションを取得
		try(Connection conn = DataSourceManager.getConnection()) {
			//利用者情報を追加
			UserDao userAddDao = new UserDao(conn);
			userAddDao.insert(userAddDto);
			
			//メッセージをリクエストスコープに保持する
			req.setAttribute("message", "ユーザを追加しました");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		}
		
		//利用者一覧に遷移
		resp.sendRedirect("list-user");
	}
}
