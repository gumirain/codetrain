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
import jp.kelonos.dao.CompanyDao;
import jp.kelonos.dto.CompanyDto;

@WebServlet("/delete-company")
public class DeleteCompanyServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("list-company");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//セッションを取得
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("adUserDto") == null) {
			// チャンネル一覧画面に遷移する
			req.getRequestDispatcher("aduser-login.html").forward(req, resp);
			return;
		}
		
		//セッションからログインユーザ情報を取得
		Object aduser = session.getAttribute("adUserDto");
		
		//フォームのデータを取得
		req.setCharacterEncoding("UTF-8");
		CompanyDto companyDeleteDto = new CompanyDto();
		companyDeleteDto.setCompanyId(Integer.parseInt(req.getParameter("deleteCompanyId")));
		
		//コネクションを取得
		try(Connection conn = DataSourceManager.getConnection()) {
			
			CompanyDao companyDeleteDao = new CompanyDao(conn);
			companyDeleteDao.delete(companyDeleteDto.getCompanyId());
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		req.setAttribute("message", "法人アカウントを削除しました");
		//利用者一覧に遷移
		req.getRequestDispatcher("list-company").forward(req, resp);;
	}
}
