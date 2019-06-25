package jp.kelonos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jp.kelonos.dto.CourseDto;
import jp.kelonos.dto.MessageDto;

public class CourseDao {
	protected Connection con;

	public CourseDao(Connection con) {
		this.con = con;
	}

	public ArrayList<CourseDto> selectAll() throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("   select");
		sb.append("          COURSE_ID");
		sb.append("         ,COURSE_NAME");
		sb.append("         ,COURSE_PROPERTY");
		sb.append("         ,COURSE_PREMISE");
		sb.append("         ,COURSE_GOAL");
		sb.append("         ,COURSE_TIME");
		sb.append("         ,COURSE.CATEGORY_ID");
		sb.append("         ,CATEGORY_NAME");
		sb.append("         ,COURSE_FREE_FLAG");
		sb.append("         ,COURSE_UPDATE_TIME");
		sb.append("     from COURSE");
		sb.append("     inner join CATEGORY");
		sb.append("    on COURSE.CATEGORY_ID = ");
		sb.append("    CATEGORY.CATEGORY_ID ");
		sb.append("     order by COURSE_ID");

		ArrayList<CourseDto> courseList = new ArrayList<>();
		
	
		try (Statement stmt = con.createStatement()) {
			
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				CourseDto courseDto = new CourseDto();
				courseDto.setCourseId(rs.getInt("COURSE_ID"));
				courseDto.setCourseName(rs.getString("COURSE_NAME"));
				courseDto.setCourseProperty(rs.getString("COURSE_PROPERTY"));
				courseDto.setCoursePremise(rs.getString("COURSE_PREMISE"));
				courseDto.setCourseGoal(rs.getString("COURSE_GOAL"));
				courseDto.setCourseTime(rs.getString("COURSE_TIME"));
				courseDto.setCategoryId(rs.getInt("CATEGORY_ID"));
				courseDto.setCategoryName(rs.getString("CATEGORY_NAME"));
				courseDto.setCourseFreeFlag(rs.getInt("COURSE_FREE_FLAG"));
				courseDto.setCourseUpdateTime(rs.getTimestamp("COURSE_UPDATE_TIME"));
				courseList.add(courseDto);
			}
			return courseList;
		}
	}

	public CourseDto findByCourseId(int courseId) throws SQLException {


		StringBuffer sb = new StringBuffer();
		sb.append("   select");
		sb.append("          COURSE_ID");
		sb.append("         ,COURSE_NAME");
		sb.append("         ,COURSE_PROPERTY");
		sb.append("         ,COURSE_PREMISE");
		sb.append("         ,COURSE_GOAL");
		sb.append("         ,COURSE_TIME");
		sb.append("         ,CATEGORY_ID");
		sb.append("         ,COURSE_UPDATE_TIME");
		sb.append("     from COURSE");
		sb.append("  where COURSE_ID = ?");
		
		CourseDto courseDto = new CourseDto();
	
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
		
			ps.setInt(1, courseId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				courseDto = new CourseDto();
				courseDto.setCourseId(rs.getInt("COURSE_ID"));
				courseDto.setCourseName(rs.getString("COURSE_NAME"));
				courseDto.setCourseProperty(rs.getString("COURSE_PROPERTY"));
				courseDto.setCoursePremise(rs.getString("COURSE_PREMISE"));
				courseDto.setCourseGoal(rs.getString("COURSE_GOAL"));
				courseDto.setCourseTime(rs.getString("COURSE_TIME"));
				courseDto.setCategoryId(rs.getInt("CATEGORY_ID"));
				courseDto.setCourseUpdateTime(rs.getTimestamp("COURSE_UPDATE_TIME"));
			}
		}
		return courseDto;
	}
	

	public int insert(CourseDto courseDto) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into COURSE");
		sb.append("           (");
		sb.append("             CATEGORY_ID");
		sb.append("             ,COURSE_NAME");
		sb.append("             ,COURSE_PROPERTY");
		sb.append("             ,COURSE_PREMISE");
		sb.append("             ,COURSE_GOAL");
		sb.append("             ,COURSE_TIME");
		sb.append("             ,COURSE_FREE_FLAG");
		sb.append("           )");
		sb.append("      values");
		sb.append("           (");
		sb.append("             ?");
		sb.append("             ,?");
		sb.append("             ,?");
		sb.append("             ,?");
		sb.append("             ,?");
		sb.append("             ,?");
		sb.append("             ,?");
		sb.append("           )");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setInt(1, courseDto.getCategoryId());
			ps.setString(2, courseDto.getCourseName());
			ps.setString(3, courseDto.getCourseProperty());
			ps.setString(4, courseDto.getCoursePremise());
			ps.setString(5, courseDto.getCourseGoal());
			ps.setString(6, courseDto.getCourseTime());
			ps.setInt(7, courseDto.getCourseFreeFlag());
			
			// SQLを実行する
			return ps.executeUpdate();
		}
	}

	public CourseDto findByCourseIdAndSetProgress(int courseId, int progress) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("   select");
		sb.append("          COURSE_ID");
		sb.append("         ,COURSE_NAME");
		sb.append("         ,COURSE_PROPERTY");
		sb.append("         ,COURSE_PREMISE");
		sb.append("         ,COURSE_GOAL");
		sb.append("         ,COURSE_TIME");
		sb.append("         ,COURSE.CATEGORY_ID");
		sb.append("         ,CATEGORY_NAME");
		sb.append("         ,COURSE_FREE_FLAG");
		sb.append("         ,COURSE_UPDATE_TIME");
		sb.append("     from COURSE");
		sb.append("     inner join CATEGORY");
		sb.append("    on COURSE.CATEGORY_ID = ");
		sb.append("    CATEGORY.CATEGORY_ID ");
		sb.append("     where COURSE_ID =?");

		
		


		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, courseId);
		


			CourseDto courseDto = new CourseDto();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				courseDto.setCourseId(rs.getInt("COURSE_ID"));
				courseDto.setCourseName(rs.getString("COURSE_NAME"));
				courseDto.setCourseProperty(rs.getString("COURSE_PROPERTY"));
				courseDto.setCoursePremise(rs.getString("COURSE_PREMISE"));
				courseDto.setCourseGoal(rs.getString("COURSE_GOAL"));
				courseDto.setCourseTime(rs.getString("COURSE_TIME"));
				courseDto.setCategoryId(rs.getInt("CATEGORY_ID"));
				courseDto.setCourseFreeFlag(rs.getInt("COURSE_FREE_FLAG"));
				courseDto.setCategoryName(rs.getString("CATEGORY_NAME"));
				courseDto.setCourseUpdateTime(rs.getTimestamp("COURSE_UPDATE_TIME"));
				courseDto.setProgress(progress);
			}
			return courseDto;
		}
	}
	


	public int findCountCourse() throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        count(*)");
		sb.append("   from COURSE");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// 該当件数を返却する
				return rs.getInt("count(*)");
			}
		}
		// 該当するデータがない場合は0を返却する
		return 0;
	}
	
	public int findMaxCourseId() throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        max(COURSE_ID)");
		sb.append("   from course");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする

			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// コースIDの最大値を返却する
				return rs.getInt("max(COURSE_ID)");
			}
		}
		// 該当するデータがない場合は0を返却する
		return 0;
	}
	
	public ArrayList<CourseDto> selectByCategoryId(int categoryId) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("   select");
		sb.append("          COURSE_ID");
		sb.append("         ,COURSE_NAME");
		sb.append("         ,COURSE_PROPERTY");
		sb.append("         ,COURSE_PREMISE");
		sb.append("         ,COURSE_GOAL");
		sb.append("         ,COURSE_TIME");
		sb.append("         ,COURSE.CATEGORY_ID");
		sb.append("         ,CATEGORY_NAME");
		sb.append("         ,COURSE_UPDATE_TIME");
		sb.append("     from COURSE");
		sb.append("     inner join CATEGORY");
		sb.append("    on COURSE.CATEGORY_ID = ");
		sb.append("    CATEGORY.CATEGORY_ID ");
		sb.append("     order by COURSE_UPDATE_TIME desc");
		sb.append("     where COURSE.CATEGORY_ID = ?");

		ArrayList<CourseDto> courseList = new ArrayList<>();

		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			ps.setInt(1, categoryId);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CourseDto courseDto = new CourseDto();
				courseDto.setCourseId(rs.getInt("COURSE_ID"));
				courseDto.setCourseName(rs.getString("COURSE_NAME"));
				courseDto.setCourseProperty(rs.getString("COURSE_PROPERTY"));
				courseDto.setCoursePremise(rs.getString("COURSE_PREMISE"));
				courseDto.setCourseGoal(rs.getString("COURSE_GOAL"));
				courseDto.setCourseTime(rs.getString("COURSE_TIME"));
				courseDto.setCategoryId(rs.getInt("CATEGORY_ID"));
				courseDto.setCategoryName(rs.getString("CATEGORY_NAME"));
				courseDto.setCourseUpdateTime(rs.getTimestamp("COURSE_UPDATE_TIME"));
				courseList.add(courseDto);
			}
			return courseList;
		}
	}

	public void deleteByCourseId(int courseId) throws SQLException {
		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" delete from COURSE");
		sb.append("           where ");
		sb.append("        COURSE_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setInt(1, courseId);
			// SQLを実行する
			ps.executeUpdate();
		}
	}

	public int countByCategoryId(int categoryId) throws SQLException{
		StringBuffer sb = new StringBuffer();

		sb.append("select count(*)");
		sb.append("       from COURSE");
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
		sb.append("   from COURSE");
		sb.append("   where");
		sb.append("   CATEGORY_ID =");
		sb.append("   ?");

		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			ps.setInt(1, categoryId);

			ps.executeUpdate();
		}
   }
	
}
