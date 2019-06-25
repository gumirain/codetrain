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

		// �g�b�v�y�[�W�ɑJ�ڂ���
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
		
		//���̓`�F�b�N
				ArrayList<String> errorMessageList = new ArrayList<String>();
				if(oldPassword.contains(" ")
				|| oldPassword.contains("�@")
				|| "".equals(oldPassword)) {
					errorMessageList.add("���p�X���[�h����͂��Ă�������");
					}
				if(newPassword.contains(" ")
				|| newPassword.contains("�@")
				|| "".equals(newPassword)) {
				    errorMessageList.add("�V�K�p�X���[�h����͂��Ă�������");
					}
				if(changePassword.contains(" ")
				|| changePassword.contains("�@")
				|| "".equals(changePassword)) {
				    errorMessageList.add("�V�K�p�X���[�h���ēx���������͂��Ă�������");
					}
				if (!(Password.equals(oldPassword)) || !(newPassword.equals(changePassword))) {
					errorMessageList.add("�p�X���[�h������������܂���");
					}
				if (errorMessageList.size() != 0) {
					//���N�G�X�g�X�R�[�v�ɕێ�����
					session.setAttribute("errorMessageList", errorMessageList);
					response.sendRedirect("new-password");
					return;
				}
				
				//�R�l�N�V�������擾
				try(Connection conn = DataSourceManager.getConnection()) {
				  if (Password.equals(oldPassword) && newPassword.equals(changePassword)) {
		          // �V�K�p�X���[�h�o�^
						ComUserDao changePasswordDao = new ComUserDao(conn);
						changePasswordDao.updateComPassword(Email, newPassword);
						session.setAttribute("message", "�p�X���[�h��ύX���܂���");
						request.getRequestDispatcher("/list-user").forward(request, response);
				     }else {
							request.getRequestDispatcher("WEB-INF/change-comuserpassword.jsp").forward(request, response);
					}
				} catch (SQLException | NamingException e) {

					// �V�X�e���G���[�ɑJ�ڂ���
		           request.getRequestDispatcher("system-error.jsp").forward(request, response);
				}
			}
		}
//		HttpSession session = request.getSession();
//		if (session == null || session.getAttribute("comUserDto") == null) {
//			// �`�����l���ꗗ��ʂɑJ�ڂ���
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
//			errorMessageList.add("���p�X���[�h����͂��Ă�������");
//		}
//		if ("".equals(newPassword)) {
//			errorMessageList.add("�V�����p�X���[�h����͂��Ă�������");
//		}
//		if ("".equals(changePassword)) {
//			errorMessageList.add("�V�����p�X���[�h���ēx���������͂��Ă�������");
//		}
//		if (errorMessageList.size() != 0) {
//			// ���p�҈ꗗ�ɑJ��
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
//				// �V�K�p�X���[�h�o�^
//				ComUserDao changePasswordDao = new ComUserDao(conn);
//				changePasswordDao.updateComPassword(Email, newPassword);
//				request.getRequestDispatcher("list-user").forward(request, response);
//			} else {
//				String errormessage = "�p�X���[�h������������܂���";
//				request.setAttribute("errormessage", errormessage);
//				request.getRequestDispatcher("/WEB-INF/change-comuserpassword.jsp").forward(request, response);
//			}
//		} catch (SQLException | NamingException e) {
//
//			// �V�X�e���G���[�ɑJ�ڂ���
//			request.getRequestDispatcher("system-error.jsp").forward(request, response);
//		}
//	}
//}
