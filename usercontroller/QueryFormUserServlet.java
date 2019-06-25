package jp.kelonos.usercontroller;

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
import jp.kelonos.dao.UserDao;
import jp.kelonos.dto.CategoryDto;
import jp.kelonos.dto.CourseDto;
import jp.kelonos.dto.UserDto;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/query-form")
public class QueryFormUserServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// �Z�b�V�������擾����
		HttpSession session = request.getSession(true);

		try (Connection conn = DataSourceManager.getConnection()) {

			// �J�e�S�����ƃR�[�X�����擾
			CategoryDao categoryDao = new CategoryDao(conn);
			List<CategoryDto> categoryList = categoryDao.findAll();
			
			CourseDao courseDao = new CourseDao(conn);
			List<CourseDto> courseList = courseDao.selectAll();

//			�Z�b�V�����ݒ�
			request.setAttribute("categories", categoryList);
			request.setAttribute("courses", courseList);
			// �g�b�v�y�[�W�ɑJ�ڂ���
			request.getRequestDispatcher("WEB-INF/queryformuser.jsp").forward(request, response);
		} catch (SQLException | NamingException e) {

// �V�X�e���G���[�ɑJ�ڂ���
			request.getRequestDispatcher("system-error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
