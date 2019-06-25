package jp.kelonos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.kelonos.dto.ComUserDto;


public class ComUserDao {
	protected Connection con;

	public ComUserDao(Connection con) {
		this.con = con;
	}

	/**
	 * 法人管理者ユーザ情報を取得する
	 * 
	 * @param id       COMUSER_ID
	 * @param COMUSER_PASSWORD パスワード
	 * @return 法人管理者ユーザ情報
	 * @throws SQLException SQL例外
	 */
	public ComUserDto findByIdAndPassword(String comUserEmail, String comUserPassword) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        COMUSER_ID");
		sb.append("       ,COMUSER_NAME");
		sb.append("       ,COMUSER_EMAIL");
		sb.append("       ,COMUSER_PASSWORD");
		sb.append("       ,COMUSER_RES_POSITION");
		sb.append("       ,COMUSER_UPDATE_TIME");
		sb.append("       ,COMPANY_ID");
		sb.append("   from COMUSER");
		sb.append("  where COMUSER_EMAIL = ?");
		sb.append("    and COMUSER_PASSWORD = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setString(1, comUserEmail);
			ps.setString(2, comUserPassword);

			// SQLを実行する
			ResultSet rs = ps.executeQuery();
            ComUserDto comUserDto = new ComUserDto();
			// 結果をDTOに詰める
			if (rs.next()) {
				
				comUserDto.setComUserId(rs.getInt("COMUSER_ID"));
				comUserDto.setComUserName(rs.getString("COMUSER_NAME"));
				comUserDto.setComUserEmail(rs.getString("COMUSER_EMAIL"));
				comUserDto.setComUserPassword(rs.getString("COMUSER_PASSWORD"));
				comUserDto.setComUserResPosition(rs.getString("COMUSER_RES_POSITION"));
				comUserDto.setComUserUpdateTime(rs.getTimestamp("COMUSER_UPDATE_TIME"));
				comUserDto.setCompanyId(rs.getInt("COMPANY_ID"));
				return comUserDto;
			}
			// 該当するデータがない場合はnullを返却する
			return null;
		}
	}

	
	
	public ArrayList<ComUserDto> findAll() throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        COMUSER_ID");
		sb.append("       ,COMUSER_NAME");
		sb.append("       ,COMUSER_EMAIL");
		sb.append("       ,COMUSER_PASSWORD");
		sb.append("       ,COMUSER_RES_POSITION");
		sb.append("       ,COMUSER_UPDATE_TIME");
		sb.append("       ,COMPANY_ID");
		sb.append("   from COMUSER");
	

	
		ArrayList<ComUserDto> comUserList = new ArrayList<>();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			
			// SQLを実行する
			ResultSet rs = ps.executeQuery();

			// 結果をDTOに詰める
			while (rs.next()) {
				ComUserDto comUserDto = new ComUserDto();
				comUserDto.setComUserId(rs.getInt("COMUSER_ID"));
				comUserDto.setComUserName(rs.getString("COMUSER_NAME"));
				comUserDto.setComUserEmail(rs.getString("COMUSER_EMAIL"));
				comUserDto.setComUserPassword(rs.getString("COMUSER_PASSWORD"));
				comUserDto.setComUserResPosition(rs.getString("COMUSER_RES_POSITION"));
				comUserDto.setComUserUpdateTime(rs.getTimestamp("COMUSER_UPDATE_TIME"));
				comUserDto.setComUserId(rs.getInt("COMPANY_ID"));
				comUserList.add(comUserDto);
			
			}
			
			return comUserList;
		}
		
	}
	
	public int updateComPassword(String comUserEmail, String newPassword) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update");
		sb.append("        COMUSER");
		sb.append("    set");
		sb.append("        COMUSER_PASSWORD = ? ");
		sb.append("  where COMUSER_EMAIL = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, newPassword);
			ps.setString(2, comUserEmail);

			// SQLを実行する
			return ps.executeUpdate();
		}

	}
	
	public int update(String comUserId, String newPassword) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update");
		sb.append("        COMUSER");
		sb.append("    set");
	
	
		sb.append("        COMUSER_PASSWORD = ? ");
		sb.append("        COMUSER_ID = ? ");
	

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, newPassword);
			ps.setString(2, comUserId);

			// SQLを実行する
			return ps.executeUpdate();
		}

	}
	
	/**
	 * 法人管理者情報を削除する
	 * 
	 * <pre>
	 * 論理削除する
	 * </pre>
	 * 
	 * @param comUserId ユーザ情報
	 * @return 更新件数
	 * @throws SQLException SQL例外
	 */
	public int delete(int comUserId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update COMUSER");
		sb.append("    set COMUSER_DEL_FLG = 1");
		sb.append("      where COMUSER_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, comUserId);
			// SQLを実行する
			return ps.executeUpdate();
		}
	}
}
