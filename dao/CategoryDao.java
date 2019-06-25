package jp.kelonos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.kelonos.dto.CategoryDto;

public class CategoryDao {

	protected Connection con;

	public CategoryDao(Connection con) {
		this.con = con;
	}

	public ArrayList<CategoryDto> findAll() throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        CATEGORY_ID");
		sb.append("       ,CATEGORY_NAME");
		sb.append("       ,CATEGORY_UPDATE_TIME");
		sb.append("   from CATEGORY");
		ArrayList<CategoryDto> categoryList = new ArrayList<>();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする

			// SQLを実行する
			ResultSet rs = ps.executeQuery();

			// 結果をDTOに詰める
			while (rs.next()) {
				CategoryDto categoryDto = new CategoryDto();
				categoryDto.setCategoryId(rs.getInt("CATEGORY_ID"));
				categoryDto.setCategoryName(rs.getString("CATEGORY_NAME"));
				categoryDto.setCategoryUpdateTime(rs.getTimestamp("CATEGORY_UPDATE_TIME"));
				categoryList.add(categoryDto);

			}
			// 該当するデータがない場合はnullを返却する
			return categoryList;
		}
	}
	public int createCategory(String categoryName) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into CATEGORY");
		sb.append("           (");
		sb.append("             CATEGORY_NAME");
		sb.append("           )");
		sb.append("      values");
		sb.append("           (");
		sb.append("             ?");
		sb.append("           )");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, categoryName);
			// SQLを実行する
			return ps.executeUpdate();
		}
	}
	

	public void deleteByCategoryId(int categoryId) throws SQLException {
		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" delete from CATEGORY");
		sb.append("           where ");
		sb.append("        CATEGORY_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setInt(1, categoryId);
			// SQLを実行する
			ps.executeUpdate();
		}
	}
}