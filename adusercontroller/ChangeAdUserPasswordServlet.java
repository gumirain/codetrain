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
import jp.kelonos.dto.AdUserDto;

@WebServlet("/change-aduserpassword")
public class ChangeAdUserPasswordServlet extends HttpServlet {
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// トップページに遷移する
		response.sendRedirect("view-aduser");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oldPassword = request.getParameter("old_aduserpassword");
		String newPassword = request.getParameter("new_aduserpassword");
		String changePassword = request.getParameter("change_aduserpassword");
		
		HttpSession session = request.getSession();
		AdUserDto adUserDto = (AdUserDto) session.getAttribute("adUserDto");
		String Password = adUserDto.getAdUserPassword();
		String Email = adUserDto.getAdUserEmail();

		//入力チェック
		ArrayList<String> errorMessageList = new ArrayList<String>();
		if(oldPassword.contains(" ")
		|| oldPassword.contains("　")
		|| "".equals(oldPassword)) {
			errorMessageList.add("旧パスワードを入力してください");
			}
		if(newPassword.contains(" ")
		|| newPassword.contains("　")
		|| "".equals(newPassword)) {
		    errorMessageList.add("新規パスワードを入力してください");
			}
		if(changePassword.contains(" ")
		|| changePassword.contains("　")
		|| "".equals(changePassword)) {
		    errorMessageList.add("新規パスワードを再度正しく入力してください");
			}
		if (!(Password.equals(oldPassword)) || !(newPassword.equals(changePassword))) {
			errorMessageList.add("パスワードが正しくありません");
			}
		if (errorMessageList.size() != 0) {
			//リクエストスコープに保持する
			session.setAttribute("errorMessageList", errorMessageList);
			response.sendRedirect("new-password");
			return;
		}
		
		//コネクションを取得
		try(Connection conn = DataSourceManager.getConnection()) {
		     if (Password.equals(oldPassword) && newPassword.equals(changePassword)) {
          // 新規パスワード登録
				AdUserDao changePasswordDao = new AdUserDao(conn);
				changePasswordDao.updatePassword(Email, newPassword);
				session.setAttribute("message", "パスワードを変更しました");
				request.getRequestDispatcher("/view-aduser").forward(request, response);
		     }else {
					request.getRequestDispatcher("WEB-INF/change-aduserpassword.jsp").forward(request, response);
				}
		} catch (SQLException | NamingException e) {

			// システムエラーに遷移する
           request.getRequestDispatcher("system-error.jsp").forward(request, response);
		}
	}
}
//		try (Connection conn = DataSourceManager.getConnection()) {
//     if (Password.equals(oldPassword) && newPassword.equals(changePassword)) {
//          // 新規パスワード登録
//				AdUserDao changePasswordDao = new AdUserDao(conn);
//				changePasswordDao.updatePassword(Email, newPassword);
//				request.getRequestDispatcher("/view-aduser").forward(request, response);
//			} else {
//				String errormessage = "パスワードが正しくありません";
//				request.setAttribute("errormessage", errormessage);
//				request.getRequestDispatcher("WEB-INF/change-aduserpassword.jsp").forward(request, response);
//			}
//		} catch (SQLException | NamingException e) {
//
//               // システムエラーに遷移する
//			request.getRequestDispatcher("system-error.jsp").forward(request, response);
//		}
//	}
//}
//
