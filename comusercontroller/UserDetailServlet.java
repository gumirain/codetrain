package jp.kelonos.comusercontroller;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.kelonos.DataSourceManager;
import jp.kelonos.dao.UserDao;
import jp.kelonos.dao.ProgressDao;
import jp.kelonos.dto.UserDto;
import jp.kelonos.dto.ProgressDto;


/**
 * Servlet implementation class ViewKnowledge
 */
@WebServlet("/user-detail")
public class UserDetailServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   //  パラメータからユーザーIDを取得する
		String userIdStr = request.getParameter("userId");
		int userId = Integer.parseInt(userIdStr);
		
		try (Connection con = DataSourceManager.getConnection()) {

			// ユーザーIDを使って、前の画面で選択したユーザーの学習履歴を取得するメソッドを呼び出す=>変数に入れる
			ProgressDao progressDao = new ProgressDao(con);
			ArrayList<ProgressDto> userProgressList = progressDao.detailByUserId(userId);
			
			// ユーザーIDを使って、前の画面で選択したユーザー情報を取得するメソッドを呼び出す=>変数に入れる
		   UserDao userDao = new UserDao(con);
		   String userName = userDao.findUserNameByUserId(userId);
			
			// リクエストアトリビュートに取得したユーザー学習履歴を格納する
			request.setAttribute("userProgressList", userProgressList);
			
			// リクエストアトリビュートに取得したユーザー情報を格納する
			request.setAttribute("userName", userName);
		   
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		// JSPに遷移（フォワード）する
		request.getRequestDispatcher("/WEB-INF/comuser-detail.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
