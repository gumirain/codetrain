package jp.kelonos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.kelonos.dto.AdUserDto;
import jp.kelonos.dto.CompanyDto;
import jp.kelonos.dto.UserDto;
import jp.kelonos.dao.CompanyDao;

public class CompanyDao {

	protected Connection con;

	public CompanyDao(Connection con) {
		this.con = con;
	}
	
	public ArrayList<CompanyDto> findAll() throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append("        COMPANY_ID");
		sb.append("       ,COMPANY_NAME");
		sb.append("       ,COMPANY_DOMAIN");
		sb.append("       ,COMPANY_BILLING_ADD");
		sb.append("       ,COMPANY_UPDATE_TIME");
		sb.append("       ,COMPANY_COMUSER");
		sb.append("       ,COMPANY_EMAIL");
		sb.append("       ,COMPANY_DEL_FLG");
		sb.append("       ,COM_COMUSER_RES_POSI");
		sb.append("   from COMPANY");
		sb.append("  where COMPANY_DEL_FLG = 0");

		ArrayList<CompanyDto> companyList = new ArrayList<>();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする

			// SQLを実行する
			ResultSet rs = ps.executeQuery();

			// 結果をDTOに詰める
			while (rs.next()) {
				CompanyDto comp = new CompanyDto();
				comp.setCompanyId(rs.getInt("COMPANY_ID"));
				comp.setCompanyName(rs.getString("COMPANY_NAME"));
				comp.setCompanyDomain(rs.getString("COMPANY_DOMAIN"));
				comp.setCompanyBillingAdd(rs.getString("COMPANY_BILLING_ADD"));
				comp.setCompanyUpdateTime(rs.getTimestamp("COMPANY_UPDATE_TIME"));
				comp.setCompanyComuser(rs.getString("COMPANY_COMUSER"));
				comp.setCompanyEmail(rs.getString("COMPANY_EMAIL"));
				comp.setCompanyDelFlg(rs.getInt("COMPANY_DEL_FLG"));
				comp.setComComuserResPosi(rs.getString("COM_COMUSER_RES_POSI"));
				companyList.add(comp);

			}
			// 該当するデータがない場合はnullを返却する
			return companyList;
		}
	}
	
	public int insert(CompanyDto addCompanyDto) 
			throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into ");
		sb.append("           COMPANY");
		sb.append("           (");
		sb.append("             COMPANY_EMAIL");
		sb.append("           ,COM_COMUSER_RES_POSI");
		sb.append("           ,COMPANY_COMUSER");
		sb.append("           ,COMPANY_DOMAIN");
		sb.append("           ,COMPANY_NAME");
		sb.append("           ,COMPANY_BILLING_ADD");	
		sb.append("           )");
		sb.append("      values");
		sb.append("           (");
		sb.append("             ?");
		sb.append("            , ?");
		sb.append("            , ?");
		sb.append("            , ?");
		sb.append("            , ?");
		sb.append("            , ?");
		sb.append("            )");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, addCompanyDto.getCompanyEmail());
			ps.setString(2, addCompanyDto.getComComuserResPosi());
			ps.setString(3, addCompanyDto.getCompanyComuser());
			ps.setString(4, addCompanyDto.getCompanyDomain());
			ps.setString(5, addCompanyDto.getCompanyName());
			ps.setString(6, addCompanyDto.getCompanyBillingAdd());
			
			// SQLを実行する
			return ps.executeUpdate();
		}
	}
	
	public CompanyDto findById(int companyId) throws SQLException {

		// SQL文を作成する
				StringBuffer sb = new StringBuffer();
				sb.append(" select");
				sb.append("        COMPANY_ID");
				sb.append("       ,COMPANY_NAME");
				sb.append("       ,COMPANY_DOMAIN");
				sb.append("       ,COMPANY_BILLING_ADD");
				sb.append("       ,COMPANY_UPDATE_TIME");
				sb.append("       ,COMPANY_COMUSER");
				sb.append("       ,COMPANY_EMAIL");
				sb.append("       ,COMPANY_DEL_FLG");
				sb.append("       ,COM_COMUSER_RES_POSI");
				sb.append("   from COMPANY");
				sb.append("  where COMPANY_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, companyId);
			// SQLを実行する
			ResultSet rs = ps.executeQuery();
		
			// 結果をDTOに詰める
			if (rs.next()) {
				CompanyDto companyDto = new CompanyDto();
				companyDto.setCompanyId(rs.getInt("COMPANY_ID"));
				companyDto.setCompanyName(rs.getString("COMPANY_NAME"));
				companyDto.setCompanyDomain(rs.getString("COMPANY_DOMAIN"));
				companyDto.setCompanyBillingAdd(rs.getString("COMPANY_BILLING_ADD"));
				companyDto.setCompanyUpdateTime(rs.getTimestamp("COMPANY_UPDATE_TIME"));
				companyDto.setCompanyComuser(rs.getString("COMPANY_COMUSER"));
				companyDto.setCompanyEmail(rs.getString("COMPANY_EMAIL"));
				companyDto.setCompanyDelFlg(rs.getInt("COMPANY_DEL_FLG"));
				companyDto.setComComuserResPosi(rs.getString("COM_COMUSER_RES_POSI"));
				return companyDto;
			}
			// 該当するデータがない場合はnullを返却する
			return null;
		}
	}
	
	public int update(CompanyDto updateCompanyDto) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update ");
		sb.append("           COMPANY");
		sb.append("           set");
		sb.append("             COMPANY_EMAIL = ?");
		sb.append("           ,COM_COMUSER_RES_POSI = ?");
		sb.append("           ,COMPANY_COMUSER = ?");
		sb.append("           ,COMPANY_DOMAIN = ?");
		sb.append("           ,COMPANY_NAME = ?");
		sb.append("           ,COMPANY_BILLING_ADD = ?");
		sb.append("          where COMPANY_ID = ? ");
		
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setString(1, updateCompanyDto.getCompanyEmail());
		    ps.setString(2, updateCompanyDto.getComComuserResPosi());
			ps.setString(3, updateCompanyDto.getCompanyComuser());
			ps.setString(4, updateCompanyDto.getCompanyDomain());
			ps.setString(5, updateCompanyDto.getCompanyName());
			ps.setString(6, updateCompanyDto.getCompanyBillingAdd());
			ps.setInt(7, updateCompanyDto.getCompanyId());
			
			// SQLを実行する
			return ps.executeUpdate();
		}
    }
	
	public int delete(int companyId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" update COMPANY");
		sb.append("    set COMPANY_DEL_FLG = 1");
		sb.append("      where COMPANY_ID = ?");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダーに値をセットする
			ps.setInt(1, companyId);
			// SQLを実行する
			return ps.executeUpdate();
		}
	}
}
	
