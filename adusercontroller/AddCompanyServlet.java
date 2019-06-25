package jp.kelonos.adusercontroller;

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
import jp.kelonos.dao.CompanyDao;
import jp.kelonos.dto.CompanyDto;
import jp.kelonos.dto.AdUserDto;

@WebServlet("/add-company")
public class AddCompanyServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 法人一覧画面に遷移する
		response.sendRedirect("list-company");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("adUserDto") == null) {
			req.getRequestDispatcher("aduser-login.html").forward(req, resp);
			return;
		}
		
		Object aduser = session.getAttribute("adUserDto");
		
		resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
		CompanyDto addCompanyDto = new CompanyDto();
		addCompanyDto.setCompanyEmail(req.getParameter("companyEmail"));
		addCompanyDto.setComComuserResPosi(req.getParameter("comComuserResPosi"));
		addCompanyDto.setCompanyComuser(req.getParameter("companyComuser"));
		addCompanyDto.setCompanyDomain(req.getParameter("companyDomain"));
		addCompanyDto.setCompanyName(req.getParameter("companyName"));
		addCompanyDto.setCompanyBillingAdd(req.getParameter("companyBillingAdd"));
		
		//入力チェック
		ArrayList<String> errorMessageList = new ArrayList<String>();
		if(addCompanyDto.getCompanyEmail().contains(" ")
		|| addCompanyDto.getCompanyEmail().contains("　")
		|| "".equals(addCompanyDto.getCompanyEmail())) {
			errorMessageList.add("メールアドレスを入力してください");
		}
		if (addCompanyDto.getComComuserResPosi().contains(" ") 
		 || addCompanyDto.getComComuserResPosi().contains("　")
		 || "".equals(addCompanyDto.getComComuserResPosi())) {
			errorMessageList.add("役職を入力してください");
		}
		if (addCompanyDto.getCompanyComuser().contains(" ") 
		 || addCompanyDto.getCompanyComuser().contains("　")
		 || "".equals(addCompanyDto.getCompanyComuser())) {
			errorMessageList.add("氏名を入力してください");
		}
		if (addCompanyDto.getCompanyDomain().contains(" ") 
		 || addCompanyDto.getCompanyDomain().contains("　")
		 || "".equals(addCompanyDto.getCompanyDomain())) {
			errorMessageList.add("ドメインを入力してください");
		}
		if (addCompanyDto.getCompanyName().contains(" ") 
		 || addCompanyDto.getCompanyName().contains("　")
		 || "".equals(addCompanyDto.getCompanyName())) {
			errorMessageList.add("会社名を入力してください");
		}
		if (addCompanyDto.getCompanyBillingAdd().contains(" ") 
		 || addCompanyDto.getCompanyBillingAdd().contains("　")
		 || "".equals(addCompanyDto.getCompanyBillingAdd())) {
			errorMessageList.add("請求先を入力してください");
		}
		if (addCompanyDto.getCompanyEmail().length() > 255) {
			errorMessageList.add("メールアドレスは255文字以内で入力してください");
		}
		if (addCompanyDto.getComComuserResPosi().length() > 30) {
			errorMessageList.add("役職は30文字以内で入力してください");
		}
		if (addCompanyDto.getCompanyComuser().length() > 60) {
			errorMessageList.add("氏名は30文字以内で入力してください");
		}
		if (addCompanyDto.getCompanyDomain().length() > 255) {
			errorMessageList.add("ドメインは255文字以内で入力してください");
		}
		if (addCompanyDto.getCompanyName().length() > 100) {
			errorMessageList.add("会社名は100文字以内で入力してください");
		}
		if (addCompanyDto.getCompanyBillingAdd().length() > 200) {
			errorMessageList.add("請求先は200文字以内で入力してください");
		}
		if (errorMessageList.size() != 0) {
			//法人情報をリクエストスコープに保持する
			session.setAttribute("errorMessageList", errorMessageList);
			resp.sendRedirect("add-companyform");
			return;
		}
		
		//コネクションを取得
		try(Connection conn = DataSourceManager.getConnection()) {
			//法人情報を追加
			CompanyDao companyAddDao = new CompanyDao(conn);
			companyAddDao.insert(addCompanyDto);
			
			//メッセージをリクエストスコープに保持する
			session.setAttribute("message", "法人アカウントを追加しました");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		//法人一覧に遷移
		resp.sendRedirect("list-company");
	}
}