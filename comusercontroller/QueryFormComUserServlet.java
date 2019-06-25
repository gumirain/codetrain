package jp.kelonos.comusercontroller;

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
import jp.kelonos.dao.CategoryDao;
import jp.kelonos.dao.CourseDao;
import jp.kelonos.dto.CategoryDto;
import jp.kelonos.dto.ComUserDto;
import jp.kelonos.dto.CourseDto;

@WebServlet("/comuser-query-form")
public class QueryFormComUserServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// セッションを取得する
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("comUserDto") == null) {
			request.getRequestDispatcher("comuser-login.html").forward(request, response);
			return;
		}

		// セッションからログインユーザ情報を取得
		Object comUser = session.getAttribute("comUserDto");

		// カンパニーIDを取得
		int companyId = ((ComUserDto) comUser).getCompanyId();

		try (Connection conn = DataSourceManager.getConnection()) {

			// カテゴリ名とコース名を取得
			CategoryDao categoryDao = new CategoryDao(conn);
			List<CategoryDto> categoryList = categoryDao.findAll();

			CourseDao courseDao = new CourseDao(conn);
			List<CourseDto> courseList = courseDao.selectAll();

//			セッション設定
			request.setAttribute("categories", categoryList);
			request.setAttribute("courses", courseList);
			
			//URIをリクエストスコープに保持する
			request.setAttribute("userUri", request.getRequestURI());
			
			// トップページに遷移する
			request.getRequestDispatcher("WEB-INF/queryform-comuser.jsp").forward(request, response);
		} catch (SQLException | NamingException e) {

// システムエラーに遷移する
			request.getRequestDispatcher("system-error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
