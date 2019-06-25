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
import jp.kelonos.dao.BillDao;
import jp.kelonos.dto.BillDto;

@WebServlet("/bill-create")
public class BillCreateServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("aduser-login.html");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// セッションを取得する
		HttpSession session = req.getSession(true);
		if (session == null || session.getAttribute("adUserDto") == null) {
			req.getRequestDispatcher("aduser-login.html").forward(req, resp);
			return;
		}
		
		//フォームのデータを取得
		req.setCharacterEncoding("UTF-8");
		BillDto billCreate = new BillDto();
		billCreate.setCompanyId(Integer.parseInt(req.getParameter("companyId")));
		billCreate.setBillNormal(Integer.parseInt(req.getParameter("billNormal")));
		billCreate.setBillRest(Integer.parseInt(req.getParameter("billRest")));
		billCreate.setBillTotal(Integer.parseInt(req.getParameter("billTotal")));
		billCreate.setBillDate(req.getParameter("billDate"));
		
		//入力チェック
		ArrayList<String> errorMessageList = new ArrayList<String>();
			if ("".equals(billCreate.getBillNormal())) {
				errorMessageList.add("通常利用料を入力してください");
			}
			if ("".equals(billCreate.getBillRest())) {
				errorMessageList.add("休止利用料を入力してください");
			}
			if ("".equals(billCreate.getBillTotal())) {
				errorMessageList.add("合計を入力してください");
			}
			if ("".equals(billCreate.getBillDate())) {
				errorMessageList.add("日付を入力してください");
			}
			if (errorMessageList.size() != 0) {
				//利用者一覧に遷移
				req.setAttribute("errorMessageList", errorMessageList);
				req.setAttribute("billNormal", billCreate.getBillNormal());
				req.setAttribute("billRest", billCreate.getBillRest());
				req.setAttribute("billTotal", billCreate.getBillTotal());
				req.setAttribute("billDate", billCreate.getBillDate());
				req.getRequestDispatcher("bill-create").forward(req,resp);
				return;
			}
			
		try(Connection conn = DataSourceManager.getConnection()) {
			//請求明細をテーブルに追加
			BillDao billCreateDao = new BillDao(conn);
			billCreateDao.insert(billCreate);
			
		} catch (SQLException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			e.printStackTrace();
			resp.sendRedirect("system-error.jsp");
		}
		
		//担当者一覧に遷移
		resp.sendRedirect("view-aduser");
	}
}
