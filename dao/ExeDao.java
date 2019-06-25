package jp.kelonos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.kelonos.dto.ExeDto;

public class ExeDao {
	protected Connection con;

	public ExeDao(Connection con) {
		this.con = con;
	}
	
	public int creatExe(int categoryId, int courseId, int articleId, String exeBody) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into EXE");
		sb.append("           (");
		sb.append("             CATEGORY_ID");
		sb.append("             ,COURSE_ID");
		sb.append("             ,ARTICLE_ID");
		sb.append("             ,EXE_BODY");
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
			ps.setString(4, exeBody);
			// SQLを実行する
			return ps.executeUpdate();
		}
	}
	public ExeDto findByArticleId(int articleId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        EXE_ID");
		sb.append("       ,EXE_BODY ");
		sb.append("       ,EXE_UPDATE_TIME");
		sb.append("       ,COURSE_ID ");
		sb.append("       ,CATEGORY.CATEGORY_ID ");
		sb.append("       ,CATEGORY_NAME ");
		sb.append("       ,ARTICLE_ID ");
		sb.append("   from EXE");
		sb.append("   inner join CATEGORY");
		sb.append("   on EXE.CATEGORY_ID =");
		sb.append("   CATEGORY.CATEGORY_ID");
		sb.append("  where ARTICLE_ID = ?");
		
		ExeDto exeDto = new ExeDto();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, articleId);

			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				exeDto = new ExeDto();
				exeDto.setExeId(rs.getInt("EXE_ID"));
				exeDto.setExeBody(rs.getString("EXE_BODY"));
				exeDto.setExeUpdateTime(rs.getTimestamp("EXE_UPDATE_TIME"));
				exeDto.setCourseId(rs.getInt("COURSE_ID"));
				exeDto.setCategoryId(rs.getInt("CATEGORY_ID"));
				exeDto.setCategoryName(rs.getString("CATEGORY_NAME"));
				exeDto.setArticleId(rs.getInt("ARTICLE_ID"));
				
			}
		}
		return exeDto;
	}
	


	public void createExe(int categoryId, int courseId, int articleId, String exeBody) throws SQLException {
		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into EXE");
		sb.append("           (");
		sb.append("             CATEGORY_ID");
		sb.append("             ,COURSE_ID");
		sb.append("             ,ARTICLE_ID");
		sb.append("             ,EXE_BODY");
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
			ps.setString(4, exeBody);
			// SQLを実行する
			ps.executeUpdate();
		}

	}

	public ArrayList<ExeDto> findArticleTitles() throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("         EXE.ARTICLE_ID");
		sb.append("        ,ARTICLE_TITLE ");
		sb.append("        ,EXE.COURSE_ID");
		sb.append("        from ");
		sb.append("         EXE inner join ");
		sb.append("        ARTICLE on EXE.ARTICLE_ID");
		sb.append("        = ARTICLE.ARTICLE_ID ");

		ExeDto exeDto = new ExeDto();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする

			ArrayList<ExeDto> exeList = new ArrayList<ExeDto>();
			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				exeDto = new ExeDto();
				exeDto.setArticleId(rs.getInt("EXE.ARTICLE_ID"));
				exeDto.setArticleTitle(rs.getString("ARTICLE_TITLE"));
				exeDto.setCourseId(rs.getInt("EXE.COURSE_ID"));
				exeList.add(exeDto);
			}
			return exeList;
		}

	}

	public ExeDto findByArticleId2(int articleId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("         CATEGORY_NAME");
		sb.append("        ,COURSE_NAME ");
		sb.append("        ,ARTICLE_TITLE");
		sb.append("        ,EXE_BODY ");
		sb.append("        ,EXE.ARTICLE_ID");
		sb.append("         from EXE inner join ");
		sb.append("        CATEGORY on ");
		sb.append("               EXE.CATEGORY_ID = CATEGORY.CATEGORY_ID");
		sb.append("      inner join COURSE on ");
		sb.append("           EXE.COURSE_ID = COURSE.COURSE_ID ");
		sb.append("   inner join ARTICLE on ");
		sb.append("     EXE.ARTICLE_ID =");
		sb.append("         ARTICLE.ARTICLE_ID ");
		sb.append("       where EXE.ARTICLE_ID = ?");

		ExeDto exeDto = new ExeDto();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, articleId);

			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				exeDto = new ExeDto();
				exeDto.setCategoryName(rs.getString("CATEGORY_NAME"));
				exeDto.setCourseName(rs.getString("COURSE_NAME"));
				exeDto.setArticleTitle(rs.getString("ARTICLE_TITLE"));
				exeDto.setExeBody(rs.getString("EXE_BODY"));
				exeDto.setArticleId(rs.getInt("ARTICLE_ID"));
			}
		}
		return exeDto;
	}

	public int updateExeBody(String exe, int articleId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update");
		sb.append("        EXE");
		sb.append("    set");
		sb.append("        EXE_BODY = ? ");
		sb.append("  where ARTICLE_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, exe);
			ps.setInt(2, articleId);

			// SQLを実行する
			return ps.executeUpdate();
		}

	}

	public void deleteByArticleId(int articleId) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("delete");
		sb.append("   from EXE");
		sb.append("   where");
		sb.append("   ARTICLE_ID =");
		sb.append("   ?");

		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			ps.setInt(1, articleId);

			ps.executeUpdate();
		}
	}

	public int countByCourseId(int courseId) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("select count(*)");
		sb.append("       from EXE");
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

	public void deleteByCourseId(int courseId) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("delete");
		sb.append("   from EXE");
		sb.append("   where");
		sb.append("   COURSE_ID =");
		sb.append("   ?");

		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			ps.setInt(1, courseId);

			ps.executeUpdate();
		}

	}

	public int countByCategoryId(int categoryId) throws SQLException {

		StringBuffer sb = new StringBuffer();

		sb.append("select count(*)");
		sb.append("       from EXE");
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

	public void deleteByCategoryId(int categoryId) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("delete");
		sb.append("   from EXE");
		sb.append("   where");
		sb.append("   CATEGORY_ID =");
		sb.append("   ?");

		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			ps.setInt(1, categoryId);

			ps.executeUpdate();
		}

	}

	public int countByArticleId(int articleId) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("select count(*)");
		sb.append("       from EXE");
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


