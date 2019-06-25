package jp.kelonos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.kelonos.dto.BillDto;

public class BillDao {

	protected Connection con;

	public BillDao(Connection con) {
		this.con = con;
	}

	public ArrayList<BillDto> BillOut(int companyId, int billId) throws SQLException {

		// SQL文作成
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT");
		sb.append(" 			BILL_NORMAL");
		sb.append(" 		   ,BILL_REST");
		sb.append(" 		   ,BILL_TOTAL");
		sb.append(" 		   ,BILL_DATE");
		sb.append(" 			from BILL");
		sb.append(" 			where COMPANY_ID = ?");
		sb.append(" 			and BILL_ID = ?");

		ArrayList<BillDto> billList = new ArrayList<>();
		// ステートメントオブジェクトの作成
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
			// プレースホルダに値をセット
			ps.setInt(1, companyId);
			ps.setInt(2, billId);

			// SQL文を実行
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BillDto billPastDto = new BillDto();
				billPastDto.setBillNormal(rs.getInt("BILL_NORMAL"));
				billPastDto.setBillRest(rs.getInt("BILL_REST"));
				billPastDto.setBillTotal(rs.getInt("BILL_TOTAL"));
				billPastDto.setBillDate(rs.getString("BILL_DATE"));
				billList.add(billPastDto);
			}
		}
		return billList;
	}

	public int insert(BillDto billInsertDto) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into ");
		sb.append("           BILL");
		sb.append("           (");
		sb.append("             BILL_NORMAL");
		sb.append("           ,BILL_REST");
		sb.append("           ,BILL_TOTAL");
		sb.append("           ,BILL_DATE");
		sb.append("           ,COMPANY_ID");
		sb.append("           )");
		sb.append("      values");
		sb.append("           (");
		sb.append("             ?");
		sb.append("             ,?");
		sb.append("            , ?");
		sb.append("            , ?");
		sb.append("            , ?");
		sb.append("            )");

		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダーに値をセットする
			ps.setInt(1, billInsertDto.getBillNormal());
			ps.setInt(2, billInsertDto.getBillRest());
			ps.setInt(3, billInsertDto.getBillTotal());
			ps.setString(4, billInsertDto.getBillDate());
			ps.setInt(5, billInsertDto.getCompanyId());

			// SQLを実行する
			return ps.executeUpdate();
		}
	}

	public ArrayList<BillDto> billSelectAll(int companyId) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append("   select");
		sb.append("          BILL_ID");
		sb.append("         ,BILL_NORMAL");
		sb.append("         ,BILL_REST");
		sb.append("         ,BILL_TOTAL");
		sb.append("         ,COMPANY_ID");
		sb.append("         ,BILL_DATE");
		sb.append("     	from BILL");
		sb.append("     	where COMPANY_ID = ?");
		sb.append(" order by BILL_ID desc");

		ArrayList<BillDto> billList = new ArrayList<BillDto>();
		// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {

			// プレースホルダに値をセット
			ps.setInt(1, companyId);

			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BillDto selectBill = new BillDto();
				selectBill.setBillId(rs.getInt("BILL_ID"));
				selectBill.setBillNormal(rs.getInt("BILL_NORMAL"));
				selectBill.setBillRest(rs.getInt("BILL_REST"));
				selectBill.setBillTotal(rs.getInt("BILL_TOTAL"));
				selectBill.setCompanyId(rs.getInt("COMPANY_ID"));
				selectBill.setBillDate(rs.getString("BILL_DATE"));
				billList.add(selectBill);
			}
		}
		return billList;
	}
	
	public BillDto selectByBillId(BillDto billPastDto) throws SQLException {

		// SQL文を作成する
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append(" 			BILL_ID");
		sb.append(" 			BILL_NORMAL");
		sb.append(" 		   ,BILL_REST");
		sb.append(" 		   ,BILL_TOTAL");
		sb.append(" 		   ,BILL_DATE");
		sb.append("   from BILL");
		sb.append("  where BILL_ID = ?");
		
    	// ステートメントオブジェクトを作成する
		try (PreparedStatement ps = con.prepareStatement(sb.toString())) {
        	// プレースホルダーに値をセットする
			ps.setInt(1, billPastDto.getBillId());

			// SQL文を実行する
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				billPastDto = new BillDto();
				billPastDto.setBillId(rs.getInt("BILL_ID"));
				billPastDto.setBillNormal(rs.getInt("BILL_NORMAL"));
				billPastDto.setBillRest(rs.getInt("BILL_REST"));
				billPastDto.setBillTotal(rs.getInt("BILL_TOTAL"));
				billPastDto.setBillDate(rs.getString("BILL_DATE"));
			}
		}
		return billPastDto;
	}
}
