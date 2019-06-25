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
import jp.kelonos.dao.CompanyDao;
import jp.kelonos.dto.CompanyDto;

@WebServlet("/list-company")
public class ListCompanyServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		HttpSession session = request.getSession(true);
		CompanyDto company = (CompanyDto) session.getAttribute("companyDto");
		
		try (Connection conn = DataSourceManager.getConnection()) {
			
			//法人一覧情報を取得
			CompanyDao companyDao = new CompanyDao(conn);
			List<CompanyDto> companyList = companyDao.findAll();
			
			//法人一覧情報をリクエストスコープに保持
			request.setAttribute("companyList", companyList);
			
			//法人一覧画面に遷移
			request.getRequestDispatcher("WEB-INF/list-company.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			//response.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			e.printStackTrace();
			//response.sendRedirect("system-error.jsp");
		}
	}
			
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}	
}
