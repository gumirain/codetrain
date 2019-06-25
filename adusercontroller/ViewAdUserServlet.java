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
import jp.kelonos.dao.AdUserDao;
import jp.kelonos.dao.MessageDao;
import jp.kelonos.dto.AdUserDto;
import jp.kelonos.dto.MessageDto;
import jp.kelonos.dto.UserDto;

@WebServlet("/view-aduser")
public class ViewAdUserServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		AdUserDto aduser = (AdUserDto) session.getAttribute("adUserDto");
		int flag = aduser.getAdUserAdFlg();

		try (Connection conn = DataSourceManager.getConnection()) {

			// message�����擾
			AdUserDao aduserDao = new AdUserDao(conn);
			List<AdUserDto> aduserList = aduserDao.findAll();

//	�Z�b�V�����ݒ�
			request.setAttribute("aduserList", aduserList);

//			管理者権限かどうかチェック
			if (flag == 1) {
				// 追加モード
				request.setAttribute("isAdd", true);
			}
			// パスワード変更画面に遷移する
			request.getRequestDispatcher("/WEB-INF/view_aduser.jsp").forward(request, response);
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
