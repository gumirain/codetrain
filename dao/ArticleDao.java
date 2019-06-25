package jp.kelonos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.kelonos.dto.ArticleDto;

public class ArticleDao {
	protected Connection con;

	public ArticleDao(Connection con) {
		this.con = con;
	}
	
	public ArrayList<ArticleDto> findAll() throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        ARTICLE_ID");
		sb.append("       ,CATEGORY_ID");
		sb.append("       ,COURSE_ID");
		sb.append("       ,ARTICLE_TITLE");
		sb.append("   from ARTICLE");
		ArrayList<ArticleDto> articleList = new ArrayList<>();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする

			// SQLを実行する
			ResultSet rs = ps.executeQuery();

			// 結果をDTOに詰める
			while (rs.next()) {
				ArticleDto articleDto = new ArticleDto();
				articleDto.setArticleId(rs.getInt("ARTICLE_ID"));
				articleDto.setCategoryId(rs.getInt("CATEGORY_ID"));
				articleDto.setCourseId(rs.getInt("COURSE_ID"));
				articleDto.setArticleTitle(rs.getString("ARTICLE_TITLE"));
				articleList.add(articleDto);

			}
			// 該当するデータがない場合はnullを返却する
			return articleList;
		}
	}

	public ArrayList<ArticleDto> findByCategoryIdCourseId(int courseId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("         ARTICLE_ID ");
		sb.append("       ,ARTICLE_TITLE");
		sb.append("       ,COURSE_FREE_FLAG");
		sb.append("   from ARTICLE");
		sb.append("       INNER JOIN COURSE ON");
		sb.append("       ARTICLE.COURSE_ID = COURSE.COURSE_ID");
		sb.append("    where COURSE.COURSE_ID = ?");
		sb.append("    order by ARTICLE_CREATE_TIME");
		

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, courseId);
			// SQLを実行する
			ResultSet rs = ps.executeQuery();
			ArrayList<ArticleDto> articleList = new ArrayList<ArticleDto>();
			// 結果をDTOに詰める
			while (rs.next()) {
				ArticleDto articleDto = new ArticleDto();
				articleDto.setArticleId(rs.getInt("ARTICLE_ID"));
				articleDto.setArticleTitle(rs.getString("ARTICLE_TITLE"));
				articleDto.setCourseFreeFlag(rs.getInt("COURSE_FREE_FLAG"));
				articleList.add(articleDto);
			}
			// 該当するデータがない場合はnullを返却する
			return articleList;
		}
	}
	
	public String findArticleTitleByarticleId(int articleId) throws SQLException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select ARTICLE_TITLE");
		sb.append("          from");
		sb.append("          ARTICLE");
		sb.append("          where");
		sb.append("          ARTICLE_ID = ?");
		sb.append(";");
		
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			String articleTitle = null;
			ps.setInt(1, articleId);
	
			ResultSet rs = ps.executeQuery();
          if(rs.next()) {
        	  articleTitle = rs.getString("ARTICLE_TITLE");
          }
			return articleTitle;
		}
	}
	
	public int countArticleByCourseId(int courseId) throws SQLException {

		// SQL�����쐬����
		StringBuffer sb = new StringBuffer();
		sb.append("select count(ARTICLE_ID)");
		sb.append("          from");
		sb.append("          ARTICLE");
		sb.append("          where");
		sb.append("          COURSE_ID = ?");
		sb.append(";");

		
		// �X�e�[�g�����g�I�u�W�F�N�g���쐬����
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			int txtCount = 0;
			ps.setInt(1, courseId);
			// SQL�����s����
			ResultSet rs = ps.executeQuery();
          if(rs.next()) {
			txtCount = rs.getInt("count(ARTICLE_ID)");
          }
			return txtCount;
		}
	}
	
	public int update(int categoryId, int courseId, String articleTitle, int articleId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update");
		sb.append("       ARTICLE");
		sb.append("    set");
		sb.append("        category_id = ?");
		sb.append("         ,course_id = ?");
		sb.append("   ,article_title = ?");
		sb.append("    where article_id = ?");
	

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setInt(1, categoryId);
			ps.setInt(2, courseId);
			ps.setString(2, articleTitle);
			ps.setInt(2, articleId);

			// SQLを実行する
			return ps.executeUpdate();
		}

	}
	
	public ArrayList<ArticleDto> findlExeNull() throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("          ARTICLE_TITLE");
		sb.append("   from ARTICLE");
		sb.append("    LEFT OUTER JOIN");
		sb.append("    EXE ON ARTICLE.ARTICLE_ID  ");
		sb.append("    = EXE.ARTICLE_ID");
		sb.append("    WHERE EXE_ID IS NULL");
		
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			// SQLを実行する
			ResultSet rs = ps.executeQuery();
			ArrayList<ArticleDto> articleList = new ArrayList<ArticleDto>();
			// 結果をDTOに詰める
			while (rs.next()) {
				ArticleDto articleDto = new ArticleDto();
		
				articleDto.setArticleTitle(rs.getString("ARTICLE_TITLE"));

				articleList.add(articleDto);
			}
			// 該当するデータがない場合はnullを返却する
			return null;
		}
}
	
	public int countByCourseId(int courseId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("         count(*) ");
		sb.append("   from ARTICLE");
		sb.append("   where COURSE_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, courseId);

			// SQLを実行する
			ResultSet rs = ps.executeQuery();
			int count = 0;

			if (rs.next()) {
				count = rs.getInt("count(*)");
			}

			return count;

		}
	}

	public void deleteByCourseId(int courseId) throws SQLException {
		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" delete");
		sb.append("   from ARTICLE");
		sb.append("   where COURSE_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, courseId);

			// SQLを実行する
			ps.executeUpdate();
		}

	}

	public int countByCategoryId(int categoryId) throws SQLException{
		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("         count(*) ");
		sb.append("   from ARTICLE");
		sb.append("   where CATEGORY_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, categoryId);

			// SQLを実行する
			ResultSet rs = ps.executeQuery();
			int count = 0;

			if (rs.next()) {
				count = rs.getInt("count(*)");
			}

			return count;
		}
	}

	public void deleteByCategoryId(int categoryId) throws SQLException{
		// SQL文を作成する
				StringBuffer sb = new StringBuffer();
				sb.append(" delete");
				sb.append("   from ARTICLE");
				sb.append("   where CATEGORY_ID = ?");

				// ステートメントオブジェクトを作成する
				try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
					// プレースホルダーに値をセットする
					ps.setInt(1, categoryId);

					// SQLを実行する
					ps.executeUpdate();
				}
		
	}
	public void deleteByArticleId(int articleId) throws SQLException{
		// SQL文を作成する
				StringBuffer sb = new StringBuffer();
				sb.append(" delete");
				sb.append("   from ARTICLE");
				sb.append("   where ARTICLE_ID = ?");

				// ステートメントオブジェクトを作成する
				try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
					// プレースホルダーに値をセットする
					ps.setInt(1, articleId);

					// SQLを実行する
					ps.executeUpdate();
				}
		
	}

	public int insert(int categoryId, int courseId, String articleTitle) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into");
		sb.append("       ARTICLE");
		sb.append("    (");
		sb.append("        category_id");
		sb.append("         ,course_id");
		sb.append("   ,article_title");
		sb.append("   )");
		sb.append("   values");
		sb.append("   (");
		sb.append("   ?");
		sb.append("   ,?");
		sb.append("   ,?");
		sb.append("   )");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setInt(1, categoryId);
			ps.setInt(2, courseId);
			ps.setString(3, articleTitle);

			// SQLを実行する
			return ps.executeUpdate();
		}

	}
	
	public int selectMaxArticleId() throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("         MAX(ARTICLE_ID) ");
		sb.append("   from ARTICLE");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする

			// SQLを実行する
			ResultSet rs = ps.executeQuery();
			int articleId = 0;

			if (rs.next()) {
				articleId = rs.getInt("MAX(ARTICLE_ID)");
			}

			return articleId;
		}
	}
	
}

