package jp.kelonos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.kelonos.dto.AdUserDto;
import jp.kelonos.dto.ComUserDto;
import jp.kelonos.dto.UserDto;

public class AdUserDao {
	protected Connection con;

	public AdUserDao(Connection con) {
		this.con = con;
	}

	/**
	 * 運営担当者のユーザー情報を取得する
	 * 
	 * @param id               COMUSER_ID
	 * @param COMUSER_PASSWORD パスワード
	 * @return 運営担当者のユーザー情報
	 * @throws SQLException SQL例外
	 */
	public AdUserDto findByIdAndPassword(String adUserEmail, String adUserPassword) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        ADUSER_ID");
		sb.append("       ,ADUSER_NAME");
		sb.append("       ,ADUSER_PASSWORD");
		sb.append("       ,ADUSER_AD_FLG");
		sb.append("       ,ADUSER_UPDATE_TIME");
		sb.append("       ,ADUSER_EMAIL");
		sb.append("   from ADUSER");
		sb.append("  where ADUSER_EMAIL = ?");
		sb.append("    and ADUSER_PASSWORD = ?");
		sb.append("    and ADUSER_DEL_FLG = 0");
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setString(1, adUserEmail);
			ps.setString(2, adUserPassword);

			// SQLを実行する
			ResultSet rs = ps.executeQuery();
		
			// 結果をDTOに詰める
			if (rs.next()) {
				AdUserDto adUserDto = new AdUserDto();
				adUserDto.setAdUserId(rs.getInt("ADUSER_ID"));
				adUserDto.setAdUserName(rs.getString("ADUSER_NAME"));
				adUserDto.setAdUserPassword(rs.getString("ADUSER_PASSWORD"));
				adUserDto.setAdUserAdFlg(rs.getInt("ADUSER_AD_FLG"));
				adUserDto.setAdUserUpdateTime(rs.getTimestamp("ADUSER_UPDATE_TIME"));
				adUserDto.setAdUserEmail(rs.getString("ADUSER_EMAIL"));
				return adUserDto;
			}
			// 該当するデータがない場合はnullを返却する
			return null;
		}
	}
	
	public int updatePassword(String adUserEmail, String newPassword) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update");
		sb.append("        ADUSER");
		sb.append("    set");
		sb.append("        ADUSER_PASSWORD = ? ");
		sb.append("  where ADUSER_EMAIL = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, newPassword);
			ps.setString(2, adUserEmail);

			// SQLを実行する
			return ps.executeUpdate();
		}

	}
	
	public ArrayList<AdUserDto> findAll() throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        ADUSER_ID");
		sb.append("       ,ADUSER_NAME");
		sb.append("       ,ADUSER_PASSWORD");
		sb.append("       ,ADUSER_AD_FLG");
		sb.append("       ,ADUSER_UPDATE_TIME");
		sb.append("       ,ADUSER_EMAIL");
		sb.append("   from ADUSER");
		sb.append("  where ADUSER_DEL_FLG = 0");

		ArrayList<AdUserDto> aduserList = new ArrayList<>();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする

			// SQLを実行する
			ResultSet rs = ps.executeQuery();

			// 結果をDTOに詰める
			while (rs.next()) {
				AdUserDto aduser = new AdUserDto();
				aduser.setAdUserId(rs.getInt("ADUSER_ID"));
				aduser.setAdUserName(rs.getString("ADUSER_NAME"));
				aduser.setAdUserPassword(rs.getString("ADUSER_PASSWORD"));
				aduser.setAdUserAdFlg(rs.getInt("ADUSER_AD_FLG"));
				aduser.setAdUserUpdateTime(rs.getDate("ADUSER_UPDATE_TIME"));
				aduser.setAdUserEmail(rs.getString("ADUSER_EMAIL"));
				aduserList.add(aduser);

			}
			// 該当するデータがない場合はnullを返却する
			return aduserList;
		}
	}
	
	public int insert(String adusername, int flag, String adusermail) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into ");
		sb.append("           ADUSER");
		sb.append("           (");
		sb.append("             ADUSER_NAME");
		sb.append("           ,ADUSER_AD_FLG");
		sb.append("           ,ADUSER_EMAIL");
		sb.append("           ,ADUSER_PASSWORD");
		sb.append("           )");
		sb.append("      values");
		sb.append("           (");
		sb.append("             ?");
		sb.append("            , ?");
		sb.append("            , ?");
		sb.append("            , 'CodeTrain777'");
		sb.append("            )");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, adusername);
			ps.setInt(2, flag);
			ps.setString(3, adusermail);

			// SQLを実行する
			return ps.executeUpdate();
		}
	}
	
	public AdUserDto findById(int aduserId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        ADUSER_ID");
		sb.append("       ,ADUSER_NAME");
		sb.append("       ,ADUSER_PASSWORD");
		sb.append("       ,ADUSER_AD_FLG");
		sb.append("       ,ADUSER_UPDATE_TIME");
		sb.append("       ,ADUSER_EMAIL");
		sb.append("   from ADUSER");
		sb.append("  where ADUSER_ID = ?");
		sb.append("  and ADUSER_DEL_FLG = 0");


		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, aduserId);
			// SQLを実行する
			ResultSet rs = ps.executeQuery();
		
			// 結果をDTOに詰める
			if (rs.next()) {
				AdUserDto adUserDto = new AdUserDto();
				adUserDto.setAdUserId(rs.getInt("ADUSER_ID"));
				adUserDto.setAdUserName(rs.getString("ADUSER_NAME"));
				adUserDto.setAdUserPassword(rs.getString("ADUSER_PASSWORD"));
				adUserDto.setAdUserAdFlg(rs.getInt("ADUSER_AD_FLG"));
				adUserDto.setAdUserUpdateTime(rs.getTimestamp("ADUSER_UPDATE_TIME"));
				adUserDto.setAdUserEmail(rs.getString("ADUSER_EMAIL"));
				return adUserDto;
			}
			// 該当するデータがない場合はnullを返却する
			return null;
		}
	}
	
	public int update(String adusername, int flag, String adusermail, int articleId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update ");
		sb.append("           ADUSER");
		sb.append("           set");
		sb.append("             ADUSER_NAME = ?");
		sb.append("           ,ADUSER_AD_FLG = ?");
		sb.append("           ,ADUSER_EMAIL = ?");
		sb.append("          where ADUSER_ID = ? ");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, adusername);
			ps.setInt(2, flag);
			ps.setString(3, adusermail);
			ps.setInt(4, articleId);

			// SQLを実行する
			return ps.executeUpdate();
		}
	}
	
	
	
	public int delete(int adUserId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update ADUSER");
		sb.append("    set ADUSER_DEL_FLG = 1");
		sb.append("      where ADUSER_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, adUserId);
			// SQLを実行する
			return ps.executeUpdate();
		}
	}
	
}
