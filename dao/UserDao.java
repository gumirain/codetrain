package jp.kelonos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.kelonos.dto.UserDto;

public class UserDao {

	protected Connection con;

	public UserDao(Connection con) {
		this.con = con;
	}

	/**
	 * ユーザ情報を取得する
	 * 
	 * @param id       ID
	 * @param password パスワード
	 * @return ユーザ情報
	 * @throws SQLException SQL例外
	 */
	public UserDto findByIdAndPassword(String id, String password) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        USER_ID");
		sb.append("       ,USER_EMAIL");
		sb.append("       ,USER_PASSWORD");
		sb.append("       ,USER_NAME");
		sb.append("       ,USER_STATUS");
		sb.append("       ,USER_DEL_FLG");

		sb.append("       ,USER_DEL_FLG_UPDATE_TIME");
		sb.append("       ,COMPANY_ID");
		sb.append("       ,USER_REASON");
		sb.append("   from USER");
		sb.append("  where USER_EMAIL = ?");
		sb.append("    and USER_PASSWORD = ?");
		sb.append("    and USER_STATUS = 0");
		sb.append("    and USER_DEL_FLG = 0");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setString(1, id);
			ps.setString(2, password);

			// SQLを実行する
			ResultSet rs = ps.executeQuery();

			// 結果をDTOに詰める
			if (rs.next()) {
				UserDto user = new UserDto();
				user.setUserId(rs.getInt("USER_ID"));
				user.setUserEmail(rs.getString("USER_EMAIL"));
				user.setUserName(rs.getString("USER_NAME"));
				user.setUserPassword(rs.getString("USER_PASSWORD"));
				user.setUserStatus(rs.getInt("USER_STATUS"));
				user.setUserDelFlg(rs.getInt("USER_DEL_FLG"));

				user.setCompanyId(rs.getInt("COMPANY_ID"));
				user.setUserReason(rs.getString("USER_REASON"));
				return user;
			}
			// 該当するデータがない場合はnullを返却する
			return null;
		}
	}

	public int updatePassword(String userEmail, String newPassword) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update");
		sb.append("        USER");
		sb.append("    set");
		sb.append("        USER_PASSWORD = ?");
		sb.append("  where USER_EMAIL= ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, newPassword);
			ps.setString(2, userEmail);

			// SQLを実行する
			return ps.executeUpdate();
		}

	}

	public ArrayList<UserDto> findAll() throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        USER_ID");
		sb.append("       ,USER_EMAIL");
		sb.append("       ,USER_PASSWORD");
		sb.append("       ,USER_NAME");
		sb.append("       ,USER_STATUS");
		sb.append("       ,USER_DEL_FLG");
		sb.append("       ,USER_START_TIME");
		sb.append("       ,USER_UPDATE_TIME");
		sb.append("       ,COMPANY_ID");
		sb.append("       ,USER_REASON");
		sb.append("   from USER");
		sb.append("  where USER_DEL_FLG = 0");

		ArrayList<UserDto> userList = new ArrayList<>();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする

			// SQLを実行する
			ResultSet rs = ps.executeQuery();

			// 結果をDTOに詰める
			while (rs.next()) {
				UserDto user = new UserDto();
				user.setUserId(rs.getInt("USER_ID"));
				user.setUserEmail(rs.getString("USER_EMAIL"));
				user.setUserName(rs.getString("USER_NAME"));
				user.setUserStatus(rs.getInt("USER_STATUS"));
				user.setUserDelFlg(rs.getInt("USER_DEL_FLG"));
				user.setUserStartTime(rs.getTimestamp("USER_START_TIME"));
				user.setUserUpdateTime(rs.getTimestamp("USER_UPDATE_TIME"));
				user.setCompanyId(rs.getInt("COMPANY_ID"));
				user.setUserReason(rs.getString("USER_REASON"));
				userList.add(user);

			}
			// 該当するデータがない場合はnullを返却する
			return userList;
		}
	}

	/**
	 * ユーザ情報を追加する
	 * 
	 * @param dto ユーザ情報
	 * @return 更新件数
	 * @throws SQLException SQL例外
	 */
	public int insert(UserDto userAddDto) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into ");
		sb.append("           USER");
		sb.append("           (");
		sb.append("             USER_NAME");
		sb.append("           ,USER_EMAIL");
		sb.append("           ,COMPANY_ID");
		sb.append("           ,USER_PASSWORD");
		sb.append("           )");
		sb.append("      values");
		sb.append("           (");
		sb.append("             ?");
		sb.append("             ,?");
		sb.append("            , ?");
		sb.append("            , 'CodeTrain777'");
		sb.append("            )");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, userAddDto.getUserName());
			ps.setString(2, userAddDto.getUserEmail());
			ps.setInt(3, userAddDto.getCompanyId());

			// SQLを実行する
			return ps.executeUpdate();
		}
	}

	/**
	 * ユーザを休止状態にする
	 * 
	 * @param UserDto ユーザ情報
	 * @return 更新件数
	 * @throws SQLException SQL例外
	 */
	public int statusOff(UserDto userDto) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update");
		sb.append("        USER");
		sb.append("    set");
		sb.append("       USER_STATUS = 1");
		sb.append("       ,USER_REASON = ?");
		sb.append("       ,USER_DEL_FLG_UPDATE_TIME = USER_DEL_FLG_UPDATE_TIME");
		sb.append("  where USER_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, userDto.getUserReason());
			ps.setInt(2, userDto.getUserId());
			// SQLを実行する
			return ps.executeUpdate();
		}
	}

	/**
	 * ユーザを復帰させる
	 * 
	 * @param UserDto ユーザ情報
	 * @return 更新件数
	 * @throws SQLException SQL例外
	 */
	public int statusOn(UserDto userDto) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update");
		sb.append("        USER");
		sb.append("    set");
		sb.append("        USER_STATUS = 0");
		sb.append("       ,USER_REASON = null");
		sb.append("       ,USER_DEL_FLG_UPDATE_TIME = USER_DEL_FLG_UPDATE_TIME");
		sb.append("    where USER_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setInt(1, userDto.getUserId());

			// SQLを実行する
			return ps.executeUpdate();
		}
	}

	/**
	 * ユーザ情報を削除する
	 * 
	 * <pre>
	 * 論理削除する
	 * </pre>
	 * 
	 * @param UserDto ユーザ情報
	 * @return 更新件数
	 * @throws SQLException SQL例外
	 */
	public int delete(int userId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update USER");
		sb.append("    set USER_DEL_FLG = 1");
		sb.append("    , USER_STATUS_UPDATE_TIME = USER_STATUS_UPDATE_TIME");
		sb.append("      where USER_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, userId);
			// SQLを実行する
			return ps.executeUpdate();
		}
	}

	/**
	 * 法人ごとの登録ユーザ数を取得する
	 * 
	 * @param id       company_id
	 * @param password パスワード
	 * @return 法人ごとの登録ユーザ数
	 * @throws SQLException SQL例外
	 */
	public int countUserByCompanyId(int companyId, java.sql.Timestamp monthStart, java.sql.Timestamp monthEnd)
			throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        COUNT(*)");
		sb.append("   from USER");
		sb.append("  where COMPANY_ID = ?");
		sb.append("    and USER_START_TIME");
		sb.append("      between ? and ?");
		sb.append("    and USER_DEL_FLG = 0");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, companyId);
			ps.setTimestamp(2, monthStart);
			ps.setTimestamp(3, monthEnd);
			// SQLを実行する
			ResultSet rs = ps.executeQuery();

			return rs.getInt("COUNT(*)");

		}

	}

	/**
	 * 特定の法人に属するユーザ情報を取得する
	 * 
	 * @param id       COMPANY_ID
	 * @param password パスワード
	 * @return ユーザ情報
	 * @throws SQLException SQL例外
	 */
	public ArrayList<UserDto> findByCompanyId(int companyId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        USER_ID");
		sb.append("       ,USER_EMAIL");
		sb.append("       ,USER_PASSWORD");
		sb.append("       ,USER_NAME");
		sb.append("       ,USER_STATUS");
		sb.append("       ,USER_DEL_FLG");
		sb.append("       ,COMPANY_ID");
		sb.append("       ,USER_REASON");
		sb.append("   from USER");
		sb.append("  where COMPANY_ID = ?");
		sb.append("    and USER_DEL_FLG = 0");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, companyId);

			// SQLを実行する
			ResultSet rs = ps.executeQuery();
			ArrayList<UserDto> userList = new ArrayList<UserDto>();
			// 結果をDTOに詰める
			while (rs.next()) {
				UserDto user = new UserDto();
				user.setUserId(rs.getInt("USER_ID"));
				user.setUserEmail(rs.getString("USER_EMAIL"));
				user.setUserName(rs.getString("USER_NAME"));
				user.setUserPassword(rs.getString("USER_PASSWORD"));
				user.setUserStatus(rs.getInt("USER_STATUS"));
				user.setUserDelFlg(rs.getInt("USER_DEL_FLG"));

				user.setCompanyId(rs.getInt("COMPANY_ID"));
				user.setUserReason(rs.getString("USER_REASON"));
				userList.add(user);
			}
			// 該当するデータがない場合はnullを返却する
			return userList;
		}
	}

	public String findUserNameByUserId(int userId) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        USER_NAME");
		sb.append("   from USER");
		sb.append("  where USER_ID = ?");
		sb.append("    and USER_DEL_FLG = 0");
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			String userName = null;
			if (rs.next()) {

				userName = rs.getString("USER_NAME");
			}

			return userName;
		}
	}

	/**
	 * 法人ごとの登録ユーザ数を取得する(新)
	 * 
	 * @param id       company_id
	 * @param password パスワード
	 * @return 法人ごとの登録ユーザ数
	 * @throws SQLException SQL例外
	 */
	public int countUserByCompanyIdIncludeZombi(String year, String nextMonth, int companyId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("         select count(*) ");
		sb.append("         from user ");
		sb.append("         where ( date_format(USER_START_TIME ,'%Y-%m') < ?");
		sb.append("         and company_id = ? ");
		sb.append("         and user_status = 0)");
		sb.append("         or  ( date_format(USER_STATUS_UPDATE_TIME ,'%Y-%m') < ?");
		sb.append("         and  date_format(USER_STATUS_UPDATE_TIME ,'%Y-%m') > ? ");
		sb.append("         and company_id = ? ");
		sb.append("         and user_status = 1)");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setString(1, year + "-0" + nextMonth);
			ps.setInt(2, companyId);
			ps.setString(3, year + "-0" + nextMonth);

			int IntNextMonth = Integer.parseInt(nextMonth);
			int IntLastMonth = IntNextMonth - 2;
			String strLastMonth = Integer.toString(IntLastMonth);

			ps.setString(4, year + "-0" + strLastMonth);
			ps.setInt(5, companyId);
			int count = 0;
			// SQLを実行する
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				count = rs.getInt("count(*)");
			}
			return count;
		}

	}

	public int countZombiUserByCompanyId(String year, String thisMonth, int companyId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("         select count(*) ");
		sb.append("         from user ");
		sb.append("         where  date_format(USER_DEL_FLG_UPDATE_TIME ,'%Y-%m')< ?");
		sb.append("         and USER_DEL_FLG = 1 ");
		sb.append("         and company_id = ? ");
		sb.append("         and user_status = 0");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setString(1, year + "-0" + thisMonth);

			ps.setInt(2, companyId);
			// SQLを実行する
			int count = 0;
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count(*)");

			}
			return count;
		}
	}

	public int countRestUserByCompanyIdIncludeZombi(String year, String nextMonth, int companyId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("         select count(*) ");
		sb.append("         from user ");
		sb.append("         where  date_format(USER_START_TIME ,'%Y-%m') < ?");
		sb.append("         and  date_format(USER_STATUS_UPDATE_TIME ,'%Y-%m') < ?");
		sb.append("         and company_id = ? ");
		sb.append("         and user_status = 1");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setString(1, year + "-0" + nextMonth);

			int IntNextMonth = Integer.parseInt(nextMonth);
			int IntThisMonth = IntNextMonth - 1;
			String strThisMonth = Integer.toString(IntThisMonth);

			ps.setString(2, year + "-0" + strThisMonth);

			ps.setInt(3, companyId);
			// SQLを実行する
			int count = 0;
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count(*)");

			}
			return count;
		}
	}

	public int countZombiRestUserByCompanyId(String year, String thisMonth, int companyId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("         select count(*) ");
		sb.append("         from user ");
		sb.append("         where  date_format(USER_DEL_FLG_UPDATE_TIME ,'%Y-%m')< ?");
		sb.append("         and USER_DEL_FLG = 1 ");
		sb.append("         and company_id = ? ");
		sb.append("         and user_status = 1");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setString(1, year + "-0" + thisMonth);

			ps.setInt(2, companyId);
			// SQLを実行する
			ResultSet rs = ps.executeQuery();
			int count = 0;
			if (rs.next()) {
				count = rs.getInt("count(*)");

			}
			return count;
		}
	}

	public int BillRecent(int companyId) throws SQLException {

		// SQL文作成
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT");
		sb.append(" 			COUNT(*)");
		sb.append(" 		    FROM USER");
		sb.append(" 		    WHERE DATE_FORMAT(USER_DEL_FLG_UPDATE_TIME, '%Y%m')");
		sb.append(" 			> DATE_FORMAT(CURDATE() - INTERVAL 1 MONTH, '%Y%m')");
		sb.append(" 			AND COMPANY_ID = ?");
		sb.append(" 			AND USER_STATUS = 0");
		
		//ステートメントオブジェクトの作成
		try(PreparedStatement ps = con.prepareStatement(sb.toString())) {
			//プレースホルダに値をセット
			ps.setInt(1, companyId);
			
			//SQL文を実行
			ResultSet rs = ps.executeQuery();
			int count = 0;
			
			if(rs.next()) {
				count =rs.getInt("COUNT(*)");
			}
			return count;
		}
	}
	
	public int RestBillRecent(int companyId) throws SQLException {

		// SQL文作成
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT");
		sb.append(" 			COUNT(*)");
		sb.append(" 		    FROM USER");
		sb.append(" 		    WHERE DATE_FORMAT(USER_DEL_FLG_UPDATE_TIME, '%Y%m')");
		sb.append(" 			> DATE_FORMAT(CURDATE() - INTERVAL 1 MONTH, '%Y%m')");
		sb.append(" 			AND COMPANY_ID = ?");
		sb.append(" 			AND USER_STATUS = 1");
		
		//ステートメントオブジェクトの作成
		try(PreparedStatement ps = con.prepareStatement(sb.toString())) {
			//プレースホルダに値をセット
			ps.setInt(1, companyId);
			
			//SQL文を実行
			ResultSet rs = ps.executeQuery();
			int count = 0;
			
			if(rs.next()) {
				count =rs.getInt("COUNT(*)");
			}
			return count;
		}
	}
}
