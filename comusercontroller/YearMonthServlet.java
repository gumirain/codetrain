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

@WebServlet("/year-month")
public class YearMonthServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// コネクションを取得
		try (Connection conn = DataSourceManager.getConnection()) {
			// セッションを取得
			HttpSession session = req.getSession(false);
			if (session == null || session.getAttribute("comUserDto") == null) {
				// チャンネル一覧画面に遷移する
				req.getRequestDispatcher("comuser-login.html").forward(req, resp);
				return;
			}
			
			// セッションからログインユーザ情報を取得
			Object comUser = session.getAttribute("comUserDto");

			// カンパニーIDを取得
			// ComUserDto companyIdDto = new ComUserDto();
			int companyId = ((ComUserDto) comUser).getCompanyId();

			String strYear = req.getParameter("year");
			String strMonth = req.getParameter("month");
			int Month = Integer.parseInt(strMonth);
			int intNextMonth = Month + 1;
			String strNextMonth = Integer.toString(intNextMonth);

			UserDao userBillDao = new UserDao(conn);
			int countUserIncludeZombiForBill = userBillDao.countUserByCompanyIdIncludeZombi(strYear, strNextMonth,companyId);
			int countZombiForBill = userBillDao.countZombiUserByCompanyId(strYear, strMonth, companyId);
			int countUserForBill = countUserIncludeZombiForBill - countZombiForBill;
			int Bill = countUserForBill * 35000;

			int countRestUserIncludeZombiForBill = userBillDao.countRestUserByCompanyIdIncludeZombi(strYear,strNextMonth, companyId);
			int countRestZombiForBill = userBillDao.countZombiRestUserByCompanyId(strYear, strMonth, companyId);
			int countRestUserForBill = countRestUserIncludeZombiForBill - countRestZombiForBill;
			int RestBill = countRestUserForBill * 35000 / 2;
			
			//ユーザの数をリクエストスコープに保持する
			req.setAttribute("Bill", Bill);
			req.setAttribute("RestBill", RestBill);
			req.setAttribute("year", strYear);
			req.setAttribute("month", strMonth);
			
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
