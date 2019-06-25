package jp.kelonos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.kelonos.dto.MessageDto;

public class MessageDao {

	/** コネクション */
	protected Connection conn;

	/**
	 * コンストラクタ コネクションをフィールドに設定する
	 * 
	 * @param conn コネクション
	 * @return
	 */
	public MessageDao(Connection conn) {
		this.conn = conn;
	}

	public int sendMessage(int categoryId, int courseId, String title, String body, int userId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into MESSAGE");
		sb.append("           (");
		sb.append("             CATEGORY_ID");
		sb.append("             ,COURSE_ID");
		sb.append("             ,MESSAGE_TITLE");
		sb.append("            ,MESSAGE_BODY");
		sb.append("            ,USER_ID");
		sb.append("           )");
		sb.append("      values");
		sb.append("           (");
		sb.append("             ?");
		sb.append("             ,?");
		sb.append("             ,?");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("           )");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setInt(1, categoryId);
			ps.setInt(2, courseId);
			ps.setString(3, title);
			ps.setString(4, body);
			ps.setInt(5, userId);

			// SQLを実行する
			return ps.executeUpdate();
		}
	}

	public ArrayList<MessageDto> selectByUserId(int userId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        MESSAGE_TITLE");
		sb.append("       ,MESSAGE_BODY ");
		sb.append("       ,REP_BODY ");
		sb.append("       ,REP_REP_BODY ");
		sb.append("       ,CATEGORY_ID ");
		sb.append("      ,COURSE_ID ");
		sb.append("      ,ADUSER_ID ");
		sb.append("      ,MESSAGE_ID ");
		sb.append("      ,MESSAGE_START_TIME ");
		sb.append("   from MESSAGE");
		sb.append("  where USER_ID = ?");

		MessageDto messageDto = new MessageDto();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, userId);
			ArrayList<MessageDto> messageList = new ArrayList<>();
			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				messageDto = new MessageDto();
				messageDto.setMessageTitle(rs.getString("MESSAGE_TITLE"));
				messageDto.setMessageBody(rs.getString("MESSAGE_BODY"));
				messageDto.setRepBody(rs.getString("REP_BODY"));
				messageDto.setRepRepBody(rs.getString("REP_REP_BODY"));
				messageDto.setCategoryId(rs.getInt("CATEGORY_ID"));
				messageDto.setAdUserId(rs.getInt("ADUSER_ID"));
				messageDto.setMessageId(rs.getInt("MESSAGE_ID"));
				messageDto.setMessageStartTime(rs.getTimestamp("MESSAGE_START_TIME"));
				messageList.add(messageDto);
			}
			return messageList;
		}
	}

	public MessageDto selectByMessageId(int messageId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        MESSAGE_ID");
		sb.append("        ,MESSAGE_TITLE");
		sb.append("       ,MESSAGE_BODY");
		sb.append("       ,REP_BODY");
		sb.append("       ,REP_REP_BODY");
		sb.append("       ,CATEGORY_ID");
		sb.append("      ,COURSE_ID");
		sb.append("      ,USER_ID");
		sb.append("      ,ADUSER_ID");
		sb.append("      ,MESSAGE_START_TIME");
		sb.append("   from MESSAGE");
		sb.append("  where MESSAGE_ID = ?");

		MessageDto messageDto = new MessageDto();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, messageId);

			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				messageDto = new MessageDto();
				messageDto.setMessageId(rs.getInt("MESSAGE_ID"));
				messageDto.setMessageTitle(rs.getString("MESSAGE_TITLE"));
				messageDto.setMessageBody(rs.getString("MESSAGE_BODY"));
				messageDto.setRepBody(rs.getString("REP_BODY"));
				messageDto.setRepRepBody(rs.getString("REP_REP_BODY"));
				messageDto.setCategoryId(rs.getInt("CATEGORY_ID"));
				messageDto.setCourseId(rs.getInt("COURSE_ID"));
				messageDto.setUserId(rs.getInt("USER_ID"));
				messageDto.setAdUserId(rs.getInt("ADUSER_ID"));
				messageDto.setMessageStartTime(rs.getTimestamp("MESSAGE_START_TIME"));

			}
		}
		return messageDto;
	}

	public int replyMessage(int messageId, String repRepBody) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update");
		sb.append("        MESSAGE");
		sb.append("    set");
		sb.append("        REP_REP_Body = ? ");
		sb.append("  where MESSAGE_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, repRepBody);
			ps.setInt(2, messageId);

			// SQLを実行する
			return ps.executeUpdate();
		}

	}

	public int replyAdUserMessage(int messageId, String repBody) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update");
		sb.append("        MESSAGE");
		sb.append("    set");
		sb.append("        REP_BODY = ? ");
		sb.append("  where MESSAGE_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, repBody);
			ps.setInt(2, messageId);

			// SQLを実行する
			return ps.executeUpdate();
		}
	}	
	
	public ArrayList<MessageDto> selectAll() throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        MESSAGE_TITLE");
		sb.append("       ,MESSAGE_ID");
		sb.append("       ,MESSAGE_BODY ");
		sb.append("       ,REP_BODY ");
		sb.append("       ,REP_REP_BODY ");
		sb.append("       ,CATEGORY_ID ");
		sb.append("      ,COURSE_ID ");
		sb.append("      ,ADUSER_ID ");
		sb.append("      ,MESSAGE_START_TIME ");
		sb.append("   from MESSAGE");
		sb.append(" order by MESSAGE_START_TIME DESC");

		MessageDto messageDto = new MessageDto();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする

			ArrayList<MessageDto> messageList = new ArrayList<>();
			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				messageDto = new MessageDto();
				messageDto.setMessageTitle(rs.getString("MESSAGE_TITLE"));
				messageDto.setMessageId(rs.getInt("MESSAGE_ID"));
				messageDto.setMessageBody(rs.getString("MESSAGE_BODY"));
				messageDto.setRepBody(rs.getString("REP_BODY"));
				messageDto.setRepRepBody(rs.getString("REP_REP_BODY"));
				messageDto.setCategoryId(rs.getInt("CATEGORY_ID"));
				messageDto.setCourseId(rs.getInt("COURSE_ID"));
				messageDto.setAdUserId(rs.getInt("ADUSER_ID"));
				messageDto.setMessageStartTime(rs.getTimestamp("MESSAGE_START_TIME"));

				messageList.add(messageDto);
			}
			return messageList;
		}
	}

	public ArrayList<MessageDto> selectByComUserId(int comUserId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        MESSAGE_TITLE");
		sb.append("       ,MESSAGE_BODY ");
		sb.append("       ,REP_BODY ");
		sb.append("       ,REP_REP_BODY ");
		sb.append("       ,CATEGORY_ID ");
		sb.append("       ,COMUSER_ID ");
		sb.append("      ,COURSE_ID ");
		sb.append("      ,ADUSER_ID ");
		sb.append("      ,MESSAGE_ID ");
		sb.append("      ,MESSAGE_START_TIME ");
		sb.append("   from MESSAGE");
		sb.append("  where COMUSER_ID = ?");
		sb.append(" 	order by MESSAGE_START_TIME desc");

		MessageDto messageDto = new MessageDto();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, comUserId);
			ArrayList<MessageDto> messageList = new ArrayList<>();
			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				messageDto = new MessageDto();
				messageDto.setMessageTitle(rs.getString("MESSAGE_TITLE"));
				messageDto.setMessageBody(rs.getString("MESSAGE_BODY"));
				messageDto.setRepBody(rs.getString("REP_BODY"));
				messageDto.setRepRepBody(rs.getString("REP_REP_BODY"));
				messageDto.setCategoryId(rs.getInt("CATEGORY_ID"));
				messageDto.setAdUserId(rs.getInt("ADUSER_ID"));
				messageDto.setComUserId(rs.getInt("COMUSER_ID"));
				messageDto.setMessageId(rs.getInt("MESSAGE_ID"));
				messageDto.setMessageStartTime(rs.getTimestamp("MESSAGE_START_TIME"));
				messageList.add(messageDto);
			}
			return messageList;
		}
	}
	
	public int comUserSendMessage(int categoryId, int courseId, String title, String body, int comUserId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into MESSAGE");
		sb.append("           (");
		sb.append("             CATEGORY_ID");
		sb.append("             ,COURSE_ID");
		sb.append("             ,MESSAGE_TITLE");
		sb.append("            ,MESSAGE_BODY");
		sb.append("            ,COMUSER_ID");
		sb.append("           )");
		sb.append("      values");
		sb.append("           (");
		sb.append("             ?");
		sb.append("             ,?");
		sb.append("             ,?");
		sb.append("            ,?");
		sb.append("            ,?");
		sb.append("           )");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setInt(1, categoryId);
			ps.setInt(2, courseId);
			ps.setString(3, title);
			ps.setString(4, body);
			ps.setInt(5, comUserId);

			// SQLを実行する
			return ps.executeUpdate();
		}
	}
	
	public MessageDto selectByMessageIdForComUser(int messageId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        MESSAGE_ID");
		sb.append("        ,MESSAGE_TITLE");
		sb.append("       ,MESSAGE_BODY");
		sb.append("       ,REP_BODY");
		sb.append("       ,REP_REP_BODY");
		sb.append("       ,CATEGORY_ID");
		sb.append("      ,COURSE_ID");
		sb.append("      ,COMUSER_ID");
		sb.append("      ,ADUSER_ID");
		sb.append("      ,MESSAGE_START_TIME");
		sb.append("   from MESSAGE");
		sb.append("  where MESSAGE_ID = ?");

		MessageDto messageDto = new MessageDto();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, messageId);

			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				messageDto = new MessageDto();
				messageDto.setMessageId(rs.getInt("MESSAGE_ID"));
				messageDto.setMessageTitle(rs.getString("MESSAGE_TITLE"));
				messageDto.setMessageBody(rs.getString("MESSAGE_BODY"));
				messageDto.setRepBody(rs.getString("REP_BODY"));
				messageDto.setRepRepBody(rs.getString("REP_REP_BODY"));
				messageDto.setCategoryId(rs.getInt("CATEGORY_ID"));
				messageDto.setCourseId(rs.getInt("COURSE_ID"));
				messageDto.setComUserId(rs.getInt("COMUSER_ID"));
				messageDto.setAdUserId(rs.getInt("ADUSER_ID"));
				messageDto.setMessageStartTime(rs.getTimestamp("MESSAGE_START_TIME"));

			}
		}
		return messageDto;
	}
}
