package jp.kelonos.usercontroller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.kelonos.DataSourceManager;
import jp.kelonos.dto.UserDto;
import jp.kelonos.dao.CategoryDao;
import jp.kelonos.dao.CourseDao;
import jp.kelonos.dao.MessageDao;
import jp.kelonos.dto.CategoryDto;
import jp.kelonos.dto.CourseDto;
import jp.kelonos.dto.MessageDto;

@WebServlet("/user-list-message")
public class ListUserMessageServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		// �Z�b�V�������擾����
		HttpSession session = request.getSession(true);
		UserDto user = (UserDto) session.getAttribute("user");
		int userId = user.getUserId();

		try (Connection conn = DataSourceManager.getConnection()) {

			// message�����擾
			MessageDao messageDao = new MessageDao(conn);
			List<MessageDto> messageList = messageDao.selectByUserId(userId);
			
			CourseDao coursedao = new CourseDao(conn);
			ArrayList<CourseDto> courseList = coursedao.selectAll();
			request.setAttribute("courseList", courseList);
			
			CategoryDao categorydao = new CategoryDao(conn);
			ArrayList<CategoryDto> categoryList = categorydao.findAll();
			request.setAttribute("categoryList", categoryList);

//	�Z�b�V�����ݒ�
			request.setAttribute("messages", messageList);

			// ���b�Z�[�W�ꗗ��ʂɑJ�ڂ���
						request.getRequestDispatcher("WEB-INF/user-list-message.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("system-error.jsp");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("system-error.jsp");
		}
	}
}
