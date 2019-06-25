package jp.kelonos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.kelonos.dto.ProgressDto;

public class ProgressDao {
	protected Connection con;

	public ProgressDao(Connection con) {
		this.con = con;
	}

	/**
	 * �ｿｽ�ｿｽ�ｿｽ[�ｿｽU�ｿｽﾌ終�ｿｽ�ｿｽ�ｿｽe�ｿｽL�ｿｽX�ｿｽg�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ謫ｾ�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 * 
	 * @param userId �ｿｽ�ｿｽ�ｿｽ[�ｿｽUID�ｿｽ@
	 * @return �ｿｽR�ｿｽ[�ｿｽX�ｿｽ�ｿｽ�ｿｽﾆの�ｿｽ�ｿｽ[�ｿｽU�ｿｽﾌ終�ｿｽ�ｿｽ�ｿｽe�ｿｽL�ｿｽX�ｿｽg�ｿｽ�ｿｽ�ｿｽ�ｿｽ
	 * @throws SQLException SQL�ｿｽ�ｿｽO
	 */
	public int countTxtByUserIdAndCourseId(int userId, int courseId) throws SQLException {

		// SQL�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�成�ｿｽ�ｿｽ�ｿｽ�ｿｽ
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        count( distinct TXT_ID)");
		sb.append("   from PROGRESS");
		sb.append("  where USER_ID = ?");
		sb.append("  and COURSE_ID = ?");

		// �ｿｽX�ｿｽe�ｿｽ[�ｿｽg�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽg�ｿｽI�ｿｽu�ｿｽW�ｿｽF�ｿｽN�ｿｽg�ｿｽ�ｿｽ�ｿｽ�成�ｿｽ�ｿｽ�ｿｽ�ｿｽ
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// �ｿｽv�ｿｽ�ｿｽ�ｿｽ[�ｿｽX�ｿｽz�ｿｽ�ｿｽ�ｿｽ_�ｿｽ[�ｿｽﾉ値�ｿｽ�ｿｽ�ｿｽZ�ｿｽb�ｿｽg�ｿｽ�ｿｽ�ｿｽ�ｿｽ
			ps.setInt(1, userId);
			ps.setInt(2, courseId);
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

	public void completeTxt(int userId, int txtId, int articleId, int courseId) throws SQLException {

		// SQL�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�成�ｿｽ�ｿｽ�ｿｽ�ｿｽ
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into PROGRESS");
		sb.append("       (");
		sb.append("       USER_ID");
		sb.append("       ,TXT_ID");
		sb.append("       ,ARTICLE_ID");
		sb.append("       ,COURSE_ID");
		sb.append("       )");
		sb.append("       values");
		sb.append("       (");
		sb.append("       ?");
		sb.append("       ,?");
		sb.append("       ,?");
		sb.append("       ,?");
		sb.append("       )");

		// �ｿｽX�ｿｽe�ｿｽ[�ｿｽg�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽg�ｿｽI�ｿｽu�ｿｽW�ｿｽF�ｿｽN�ｿｽg�ｿｽ�ｿｽ�ｿｽ�成�ｿｽ�ｿｽ�ｿｽ�ｿｽ
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// �ｿｽv�ｿｽ�ｿｽ�ｿｽ[�ｿｽX�ｿｽz�ｿｽ�ｿｽ�ｿｽ_�ｿｽ[�ｿｽﾉ値�ｿｽ�ｿｽ�ｿｽZ�ｿｽb�ｿｽg�ｿｽ�ｿｽ�ｿｽ�ｿｽ
			ps.setInt(1, userId);
			ps.setInt(2, txtId);
			ps.setInt(3, articleId);
			ps.setInt(4, courseId);
			// SQL�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽs�ｿｽ�ｿｽ�ｿｽ�ｿｽ
			ps.executeUpdate();
		}
		// �ｿｽY�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽf�ｿｽ[�ｿｽ^�ｿｽ�ｿｽ�ｿｽﾈゑｿｽ�ｿｽ鼾��ｿｽ�ｿｽ0�ｿｽ�ｿｽﾔ却�ｿｽ�ｿｽ�ｿｽ�ｿｽ

	}
	
	public ArrayList<ProgressDto> detailByUserId(int userId) throws SQLException {

		// SQL譁�繧剃ｽ懈�舌☆繧�
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("      COURSE_NAME ");
		sb.append("       ,ARTICLE_TITLE");
		sb.append("       ,PROGRESS_UPDATE_TIME");
		sb.append("   from PROGRESS");
		sb.append(" inner join COURSE on ");
		sb.append("   PROGRESS.COURSE_ID ");
		sb.append("    = COURSE.COURSE_ID");
		sb.append("   inner join ARTICLE on ");
		sb.append("     PROGRESS.ARTICLE_ID");
		sb.append("    = ARTICLE.ARTICLE_ID");
		sb.append("    where USER_ID = ?");

		// 繧ｹ繝�繝ｼ繝医Γ繝ｳ繝医が繝悶ず繧ｧ繧ｯ繝医ｒ菴懈�舌☆繧�
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// 繝励Ξ繝ｼ繧ｹ繝帙Ν繝�繝ｼ縺ｫ蛟､繧偵そ繝�繝医☆繧�
			ps.setInt(1, userId);

			// SQL繧貞ｮ溯｡後☆繧�
			ResultSet rs = ps.executeQuery();
			ArrayList<ProgressDto> progressList = new ArrayList<ProgressDto>();
			// 邨先棡繧奪TO縺ｫ隧ｰ繧√ｋ
			while (rs.next()) {
				ProgressDto progressDto = new ProgressDto();
				progressDto.setCourseName(rs.getString("COURSE_NAME"));
				progressDto.setArticleTitle(rs.getString("ARTICLE_TITLE"));
				progressDto.setProgressUpdateTime(rs.getTimestamp("PROGRESS_UPDATE_TIME"));
				progressList.add(progressDto);
			}
			// 隧ｲ蠖薙☆繧九ョ繝ｼ繧ｿ縺後↑縺�蝣ｴ蜷医�ｯnull繧定ｿ泌唆縺吶ｋ
			return progressList;
		}
	}
	
	public ArrayList<Integer> findArticleIdFromProgress(int courseId, int userId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("         ARTICLE_ID ");
		sb.append("   from PROGRESS");
		sb.append("    where COURSE_ID = ?");
		sb.append("    and USER_ID = ?");
		sb.append("    order by PROGRESS_UPDATE_TIME");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, courseId);
			ps.setInt(2, userId);
			// SQLを実行する
			ResultSet rs = ps.executeQuery();
			ArrayList<Integer> ProgressArticleList = new ArrayList<Integer>();
			// 結果をDTOに詰める
			while (rs.next()) {
				ProgressArticleList.add((rs.getInt("ARTICLE_ID")));
			
			// 該当するデータがない場合はnullを返却する
			
		}
			return ProgressArticleList;
		}
	}
	
	public int countArticleByUserIdAndCourseId(int userId, int courseId) throws SQLException {

		// SQL�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�成�ｿｽ�ｿｽ�ｿｽ�ｿｽ
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        count(ARTICLE_ID)");
		sb.append("   from PROGRESS");
		sb.append("  where USER_ID = ?");
		sb.append("  and COURSE_ID = ?");

		// �ｿｽX�ｿｽe�ｿｽ[�ｿｽg�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽg�ｿｽI�ｿｽu�ｿｽW�ｿｽF�ｿｽN�ｿｽg�ｿｽ�ｿｽ�ｿｽ�成�ｿｽ�ｿｽ�ｿｽ�ｿｽ
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// �ｿｽv�ｿｽ�ｿｽ�ｿｽ[�ｿｽX�ｿｽz�ｿｽ�ｿｽ�ｿｽ_�ｿｽ[�ｿｽﾉ値�ｿｽ�ｿｽ�ｿｽZ�ｿｽb�ｿｽg�ｿｽ�ｿｽ�ｿｽ�ｿｽ
			ps.setInt(1, userId);
			ps.setInt(2, courseId);
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

