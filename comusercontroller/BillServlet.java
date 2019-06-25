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
import jp.kelonos.dao.BillDao;
import jp.kelonos.dao.UserDao;
import jp.kelonos.dto.BillDto;
import jp.kelonos.dto.ComUserDto;

@WebServlet("/bill-detail")
public class BillServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// コネクションを取得
		try (Connection conn = DataSourceManager.getConnection()) {
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
			
			

			//ユーザの数を取得
			UserDao userBillDao = new UserDao(conn);
//			int countUserIncludeZombiForBill = userBillDao.countUserByCompanyIdIncludeZombi("2000", "7", companyId);
//			int countZombiForBill = userBillDao.countZombiUserByCompanyId("2000", "6", companyId);
//			int countUserForBill = countUserIncludeZombiForBill - countZombiForBill;
//			int Bill = countUserForBill * 35000;
			
			int countUser = userBillDao.BillRecent(companyId);
			int normalBill = countUser * 35000;
			
//			int countRestUserIncludeZombiForBill = userBillDao.countRestUserByCompanyIdIncludeZombi("2000", "7", companyId);
//			int countRestZombiForBill = userBillDao.countZombiRestUserByCompanyId("2000", "6", companyId);
//			int countRestUserForBill = countRestUserIncludeZombiForBill -  countRestZombiForBill;
//			int RestBill = countRestUserForBill *35000 / 2;
			
			int countRestUser = userBillDao.RestBillRecent(companyId);
			int restBill = countRestUser * 17500;
			
			//ユーザの数をリクエストスコープに保持する
			req.setAttribute("normalBill", normalBill);
			req.setAttribute("restBill", restBill);
			
			//履歴を出す
			BillDao billPast = new BillDao(conn);
			ArrayList<BillDto> billList = billPast.billSelectAll(companyId);
			
			//履歴をリクエストスコープに保持
			req.setAttribute("billList", billList);
			
			//URIをリクエストスコープに保持する
			req.setAttribute("userUri", req.getRequestURI());
			
			//請求明細画面に遷移
			req.getRequestDispatcher("/WEB-INF/bill-detail.jsp").forward(req, resp);
			
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
