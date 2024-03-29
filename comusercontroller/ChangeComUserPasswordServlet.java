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
import jp.kelonos.dao.AdUserDao;
import jp.kelonos.dao.ComUserDao;
import jp.kelonos.dto.AdUserDto;
import jp.kelonos.dto.ComUserDto;

@WebServlet("/change-comuser-password")
public class ChangeComUserPasswordServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// トップページに遷移する
		response.sendRedirect("list-user");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oldPassword = request.getParameter("old_comuserpassword");
		String newPassword = request.getParameter("new_comuserpassword");
		String changePassword = request.getParameter("change_comuserpassword");

		HttpSession session = request.getSession();
		ComUserDto comUserDto = (ComUserDto) session.getAttribute("adUserDto");
		String Password = comUserDto.getComUserPassword();
		String Email = comUserDto.getComUserEmail();
		
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
						ComUserDao changePasswordDao = new ComUserDao(conn);
						changePasswordDao.updateComPassword(Email, newPassword);
						session.setAttribute("message", "パスワードを変更しました");
						request.getRequestDispatcher("/list-user").forward(request, response);
				     }else {
							request.getRequestDispatcher("WEB-INF/change-comuserpassword.jsp").forward(request, response);
					}
				} catch (SQLException | NamingException e) {

					// システムエラーに遷移する
		           request.getRequestDispatcher("system-error.jsp").forward(request, response);
				}
			}
		}
//		HttpSession session = request.getSession();
//		if (session == null || session.getAttribute("comUserDto") == null) {
//			// チャンネル一覧画面に遷移する
//			request.getRequestDispatcher("comuser-login.html").forward(request, response);
//			return;
//		}
//
//		ComUserDto comUserDto = (ComUserDto) session.getAttribute("comUserDto");
//		String Password = comUserDto.getComUserPassword();
//		String Email = comUserDto.getComUserEmail();
//
//		ArrayList<String> errorMessageList = new ArrayList<String>();
//		if ("".equals(oldPassword)) {
//			errorMessageList.add("旧パスワードを入力してください");
//		}
//		if ("".equals(newPassword)) {
//			errorMessageList.add("新しいパスワードを入力してください");
//		}
//		if ("".equals(changePassword)) {
//			errorMessageList.add("新しいパスワードを再度正しく入力してください");
//		}
//		if (errorMessageList.size() != 0) {
//			// 利用者一覧に遷移
//			request.setAttribute("errorMessageList", errorMessageList);
//			request.setAttribute("old_comuserpassword", oldPassword);
//			request.setAttribute("new_comuserpassword", newPassword);
//			request.setAttribute("change_comuserpassword", changePassword);
//			request.getRequestDispatcher("change-comuser-passwordform").forward(request, response);
//			return;
//		}
//
//		try (Connection conn = DataSourceManager.getConnection()) {
//			if (Password.equals(oldPassword) && newPassword.equals(changePassword)) {
//				// 新規パスワード登録
//				ComUserDao changePasswordDao = new ComUserDao(conn);
//				changePasswordDao.updateComPassword(Email, newPassword);
//				request.getRequestDispatcher("list-user").forward(request, response);
//			} else {
//				String errormessage = "パスワードが正しくありません";
//				request.setAttribute("errormessage", errormessage);
//				request.getRequestDispatcher("/WEB-INF/change-comuserpassword.jsp").forward(request, response);
//			}
//		} catch (SQLException | NamingException e) {
//
//			// システムエラーに遷移する
//			request.getRequestDispatcher("system-error.jsp").forward(request, response);
//		}
//	}
//}
