package jp.kelonos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.kelonos.dto.AdUserDto;
import jp.kelonos.dto.MeigenDto;

public class MeigenDao {
	protected Connection con;

	public MeigenDao(Connection con) {
		this.con = con;
	}
	
	public ArrayList<MeigenDto> selectAll() throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        MEIGENID");
		sb.append("       ,BODY");
		sb.append("       ,AUTHOR");
		sb.append("   from MEIGEN");
		
		ArrayList<MeigenDto> meigenList = new ArrayList<>();

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// SQLを実行する
			ResultSet rs = ps.executeQuery();
		
			// 結果をDTOに詰める
			while (rs.next()) {
				MeigenDto meigenDto = new MeigenDto();
				meigenDto.setMeigenId(rs.getInt("MEIGENID"));
				meigenDto.setBody(rs.getString("BODY"));
				meigenDto.setAuthor(rs.getString("AUTHOR"));
			    meigenList.add(meigenDto);
			}
			// 該当するデータがない場合はnullを返却する
			return meigenList;
		}
	}
	
	public int countmeigen() throws SQLException {

		// SQL�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�成�ｿｽ�ｿｽ�ｿｽ�ｿｽ
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        count( distinct MEIGENID)");
		sb.append("   from MEIGEN");

		// �ｿｽX�ｿｽe�ｿｽ[�ｿｽg�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽg�ｿｽI�ｿｽu�ｿｽW�ｿｽF�ｿｽN�ｿｽg�ｿｽ�ｿｽ�ｿｽ�成�ｿｽ�ｿｽ�ｿｽ�ｿｽ
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// SQL�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽs�ｿｽ�ｿｽ�ｿｽ�ｿｽ
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// �ｿｽY�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾔ却�ｿｽ�ｿｽ�ｿｽ�ｿｽ
				return rs.getInt(1);
			}
		}
		// �ｿｽY�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽf�ｿｽ[�ｿｽ^�ｿｽ�ｿｽ�ｿｽﾈゑｿｽ�ｿｽ鼾��ｿｽ�ｿｽ0�ｿｽ�ｿｽﾔ却�ｿｽ�ｿｽ�ｿｽ�ｿｽ
		return 0;
	}
	
	
}
