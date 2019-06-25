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

@WebServlet("/edit-companyform")
public class EditCompanyFormServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//セッションの情報をリクエストに保存しなおしている
		HttpSession session = request.getSession(true);
		request.setAttribute("errorMessageList", session.getAttribute("errorMessageList"));
		request.setAttribute("message", session.getAttribute("message"));
		session.removeAttribute("errorMessageList");
		session.removeAttribute("message");
		
		int companyId = Integer.parseInt(request.getParameter("companyId"));
		
		try (Connection conn = DataSourceManager.getConnection()) {
			
			CompanyDao companyDao = new CompanyDao(conn);
			CompanyDto company = companyDao.findById(companyId);
			request.setAttribute("company", company);	
			
			request.getRequestDispatcher("WEB-INF/edit-company.jsp").forward(request, response);
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
