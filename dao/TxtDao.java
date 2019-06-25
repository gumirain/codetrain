package jp.kelonos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.kelonos.dto.TxtDto;

public class TxtDao {
	protected Connection con;

	public TxtDao(Connection con) {
		this.con = con;
	}

	public int countTxtByCourseId(int courseId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("select count(TXT_ID)");
		sb.append("          from");
		sb.append("          TXT");
		sb.append("          where");
		sb.append("          COURSE_ID = ?");
		sb.append(";");

		int txtCount;
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			ps.setInt(1, courseId);
			// SQLを実行する
			ResultSet rs = ps.executeQuery();

			txtCount = rs.getInt(" count(TXT_ID)");

			return txtCount;
		}
	}
	
	public TxtDto findByArticleId(int articleId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        TXT_ID");
		sb.append("       ,TXT_BODY ");
		sb.append("       ,TXT_UPDATE_TIME ");
		sb.append("       ,COURSE_ID ");
		sb.append("       ,TXT.CATEGORY_ID ");
		sb.append("       ,CATEGORY_NAME ");
		sb.append("       ,ARTICLE_ID ");
		sb.append("       ,CATEGORY_NAME ");
		sb.append("   from TXT");
		sb.append("  inner join CATEGORY");
		sb.append("  on TXT.CATEGORY_ID");
		sb.append("      = CATEGORY.CATEGORY_ID");
		sb.append("  where ARTICLE_ID = ?");
		
		TxtDto txtDto = new TxtDto();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, articleId);

			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				txtDto = new TxtDto();
				txtDto.setTxtId(rs.getInt("TXT_ID"));
				txtDto.setTxtBody(rs.getString("TXT_BODY"));
				txtDto.setTxtUpdateTime(rs.getTimestamp("TXT_UPDATE_TIME"));
				txtDto.setCourseId(rs.getInt("COURSE_ID"));
				txtDto.setCategoryId(rs.getInt("CATEGORY_ID"));
				txtDto.setCategoryName(rs.getString("CATEGORY_NAME"));
				txtDto.setArticleId(rs.getInt("ARTICLE_ID"));
				
			}
		}
		return txtDto;
	}
	

	


	public int createTxt(int categoryId, int courseId, int articleId, String txtBody) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into TXT");
		sb.append("           (");
		sb.append("             CATEGORY_ID");
		sb.append("             ,COURSE_ID");
		sb.append("             ,ARTICLE_ID");
		sb.append("             ,TXT_BODY");
		sb.append("           )");
		sb.append("      values");
		sb.append("           (");
		sb.append("             ?");
		sb.append("             ,?");
		sb.append("             ,?");
		sb.append("             ,?");
		sb.append("           )");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setInt(1, categoryId);
			ps.setInt(2, courseId);
			ps.setInt(3, articleId);
			ps.setString(4, txtBody);
			// SQLを実行する
			return ps.executeUpdate();
		}
	}

	public TxtDto findByArticleId2(int articleId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("         CATEGORY_NAME");
		sb.append("        ,COURSE_NAME ");
		sb.append("        ,ARTICLE_TITLE");
		sb.append("        ,TXT_BODY ");
		sb.append("        ,TXT.ARTICLE_ID");
		sb.append("         from TXT inner join ");
		sb.append("        CATEGORY on ");
		sb.append("               TXT.CATEGORY_ID = CATEGORY.CATEGORY_ID");
		sb.append("      inner join COURSE on ");
		sb.append("           TXT.COURSE_ID = COURSE.COURSE_ID ");
		sb.append("   inner join ARTICLE on ");
		sb.append("     TXT.ARTICLE_ID =");
		sb.append("         ARTICLE.ARTICLE_ID ");
		sb.append("       where TXT.ARTICLE_ID = ?");

		TxtDto txtDto = new TxtDto();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, articleId);

			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				txtDto = new TxtDto();
				txtDto.setCategoryName(rs.getString("CATEGORY_NAME"));
				txtDto.setCourseName(rs.getString("COURSE_NAME"));
				txtDto.setArticleTitle(rs.getString("ARTICLE_TITLE"));
				txtDto.setTxtBody(rs.getString("TXT_BODY"));
				txtDto.setArticleId(rs.getInt("ARTICLE_ID"));
			}
		}
		return txtDto;
	}

	public ArrayList<TxtDto> findArticleTitles() throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("         TXT.ARTICLE_ID");
		sb.append("        ,ARTICLE_TITLE ");
		sb.append("        ,TXT.COURSE_ID");
		sb.append("        from ");
		sb.append("         TXT inner join ");
		sb.append("        ARTICLE on TXT.ARTICLE_ID");
		sb.append("        = ARTICLE.ARTICLE_ID ");

		TxtDto txtDto = new TxtDto();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする

			ArrayList<TxtDto> txtList = new ArrayList<TxtDto>();
			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				txtDto = new TxtDto();
				txtDto.setArticleId(rs.getInt("TXT.ARTICLE_ID"));
				txtDto.setArticleTitle(rs.getString("ARTICLE_TITLE"));
				txtDto.setCourseId(rs.getInt("TXT.COURSE_ID"));
				txtList.add(txtDto);
			}
			return txtList;
		}

	}

	public int updateTxtBody(String txt, int articleId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update");
		sb.append("        TXT");
		sb.append("    set");
		sb.append("        TXT_BODY = ? ");
		sb.append("  where ARTICLE_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, txt);
			ps.setInt(2, articleId);

			// SQLを実行する
			return ps.executeUpdate();
		}

	}

	public void deleteByArticleId(int articleId) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("delete");
		sb.append("   from TXT");
		sb.append("   where");
		sb.append("   ARTICLE_ID =");
		sb.append("   ?");

		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			ps.setInt(1, articleId);

			ps.executeUpdate();
		}
	}

	public void deleteByCourseId(int courseId) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("delete");
		sb.append("   from TXT");
		sb.append("   where");
		sb.append("   COURSE_ID =");
		sb.append("   ?");

		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			ps.setInt(1, courseId);

			ps.executeUpdate();
		}

	}

	public int countByCourseId(int courseId) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("select count(*)");
		sb.append("       from TXT");
		sb.append("        where ");
		sb.append("      COURSE_ID = ?");

		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			ps.setInt(1, courseId);

			ResultSet rs = ps.executeQuery();
			int count = 0;
			if (rs.next()) {

				count = rs.getInt("count(*)");
			}
			return count;
		}

	}

	public int countByCategoryId(int categoryId) throws SQLException{
		StringBuffer sb = new StringBuffer();

		sb.append("select count(*)");
		sb.append("       from TXT");
		sb.append("        where ");
		sb.append("      CATEGORY_ID = ?");

		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			ps.setInt(1, categoryId);

			ResultSet rs = ps.executeQuery();
			int count = 0;
			if (rs.next()) {

				count = rs.getInt("count(*)");
			}
			return count;
		}
	}

	public void deleteByCategoryId(int categoryId) throws SQLException{
		StringBuffer sb = new StringBuffer();

		sb.append("delete");
		sb.append("   from TXT");
		sb.append("   where");
		sb.append("   CATEGORY_ID =");
		sb.append("   ?");

		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			ps.setInt(1, categoryId);

			ps.executeUpdate();
		}

		
	}

	public int countByArticleId(int articleId) throws SQLException{
		StringBuffer sb = new StringBuffer();

		sb.append("select count(*)");
		sb.append("       from TXT");
		sb.append("        where ");
		sb.append("      ARTICLE_ID = ?");

		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			ps.setInt(1, articleId);

			ResultSet rs = ps.executeQuery();
			int count = 0;
			if (rs.next()) {

				count = rs.getInt("count(*)");
			}
			return count;
		}
	}
}

	

