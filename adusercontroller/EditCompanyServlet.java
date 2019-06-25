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
import jp.kelonos.dao.AdUserDao;
import jp.kelonos.dao.CompanyDao;
import jp.kelonos.dto.CompanyDto;

@WebServlet("/edit-company")
public class EditCompanyServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect("/list-company");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("adUserDto") == null) {
			// チャンネル一覧画面に遷移する
			req.getRequestDispatcher("aduser-login.html").forward(req, resp);
			return;
		}

		Object aduser = session.getAttribute("adUserDto");

		resp.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		CompanyDto updateCompanyDto = new CompanyDto();
		updateCompanyDto.setCompanyEmail(req.getParameter("companyEmail"));
		updateCompanyDto.setComComuserResPosi(req.getParameter("comComuserResPosi"));
		updateCompanyDto.setCompanyComuser(req.getParameter("companyComuser"));
		updateCompanyDto.setCompanyDomain(req.getParameter("companyDomain"));
		updateCompanyDto.setCompanyName(req.getParameter("companyName"));
		updateCompanyDto.setCompanyBillingAdd(req.getParameter("companyBillingAdd"));
		updateCompanyDto.setCompanyId(Integer.parseInt(req.getParameter("companyId")));

		// 入力チェック
		ArrayList<String> errorMessageList = new ArrayList<String>();
		if (updateCompanyDto.getCompanyEmail().contains(" ") 
		 || updateCompanyDto.getCompanyEmail().contains("　")
		 || "".equals(updateCompanyDto.getCompanyEmail())) {
			errorMessageList.add("メールアドレスを入力してください");
		}
		if (updateCompanyDto.getComComuserResPosi().contains(" ") 
		 || updateCompanyDto.getComComuserResPosi().contains("　")
		 || "".equals(updateCompanyDto.getComComuserResPosi())) {
			errorMessageList.add("役職を入力してください");
		}
		if (updateCompanyDto.getCompanyComuser().contains(" ") 
		 || updateCompanyDto.getCompanyComuser().contains("　")
		 || "".equals(updateCompanyDto.getCompanyComuser())) {
			errorMessageList.add("氏名を入力してください");
		}
		if (updateCompanyDto.getCompanyDomain().contains(" ") 
		 || updateCompanyDto.getCompanyDomain().contains("　")
		 || "".equals(updateCompanyDto.getCompanyDomain())) {
			errorMessageList.add("ドメインを入力してください");
		}
		if (updateCompanyDto.getCompanyName().contains(" ") 
		 || updateCompanyDto.getCompanyName().contains("　")
		 || "".equals(updateCompanyDto.getCompanyName())) {
			errorMessageList.add("会社名を入力してください");
		}
		if (updateCompanyDto.getCompanyBillingAdd().contains(" ") 
		 || updateCompanyDto.getCompanyBillingAdd().contains("　")
		 || "".equals(updateCompanyDto.getCompanyBillingAdd())) {
			errorMessageList.add("請求先を入力してください");
		}
		if (updateCompanyDto.getCompanyEmail().length() > 255) {
			errorMessageList.add("メールアドレスは255文字以内で入力してください");
		}
		if (updateCompanyDto.getComComuserResPosi().length() > 30) {
			errorMessageList.add("役職は30文字以内で入力してください");
		}
		if (updateCompanyDto.getCompanyComuser().length() > 60) {
			errorMessageList.add("氏名は30文字以内で入力してください");
		}
		if (updateCompanyDto.getCompanyDomain().length() > 255) {
			errorMessageList.add("ドメインは255文字以内で入力してください");
		}
		if (updateCompanyDto.getCompanyName().length() > 100) {
			errorMessageList.add("会社名は100文字以内で入力してください");
		}
		if (updateCompanyDto.getCompanyBillingAdd().length() > 200) {
			errorMessageList.add("請求先は200文字以内で入力してください");
		}
		if (errorMessageList.size() != 0) {
			// 法人情報をリクエストスコープに保持する
			session.setAttribute("errorMessageList", errorMessageList);
			int companyId = Integer.parseInt(req.getParameter("companyId"));
			resp.sendRedirect("edit-companyform?companyId="+companyId);
			return;
		}

		// コネクションを取得
		try (Connection conn = DataSourceManager.getConnection()) {
			// 法人情報を追加
			CompanyDao companyEditDao = new CompanyDao(conn);
			companyEditDao.update(updateCompanyDto);

			// メッセージをリクエストスコープに保持する
			session.setAttribute("message", "法人アカウント情報を編集しました");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}

		// 法人一覧に遷移
		resp.sendRedirect("list-company");
	}
}