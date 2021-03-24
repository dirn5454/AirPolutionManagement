package 고객팀프;


import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQL {
	Connection conn;
	String url;
	PreparedStatement pstmt;

	public Connection makeConnection() {
		url = "jdbc:mysql://localhost:3306?charcterEncoding=+UTF-8&serverTimezone=UTC";
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("데이터베이스 연결중...");
			con = DriverManager.getConnection(url, "root", "1234");
			System.out.println("데이터베이스 연결 성공");
			conn=con;
			String sql;
			Statement stmt = null;
			stmt = conn.createStatement();
			
			sql = "drop DATABASE DB10";
			stmt.executeUpdate(sql);
			
			
		} catch (ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		}
		return con;
	}
	
/*	public void writeCSV() throws SQLException {
		
		Statement stmt = conn.createStatement();
		String sql = "select * from polu into outfile 'c:/Users/Admin/Desktop/test.csv' fields terminated by ',' lines terminated by '\r\n' ";
		stmt.executeUpdate(sql);
	
		
		return;
	}*/
	public void writeCSV() throws Exception{
		StringBuffer tmp = new StringBuffer();
		ArrayList<String> list = new ArrayList<>();
		BufferedWriter fw = null;
	    //파일 풀경로
	    fw = new BufferedWriter(new FileWriter("c:/Users/Admin/Desktop/TC01-01.csv", false));
	    
		String sql="select * from polu";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery(sql);
		while(rs.next()	) {
			String date = rs.getString(1);
			String place = rs.getString(2);
			String n2o = rs.getString(3);
			String o3 = rs.getString(4);
			String co2 = rs.getString(5);
			String so2= rs.getString(6);
			String dust = rs.getString(7);
			String udust = rs.getString(8);
			String data = date +", "+ place +", "+ n2o +", "+ o3 +", "+ co2 +", "+ so2 +", "+ dust +", "+ udust;
			list.add(data);
		}
	    //List에서 데이터 추출
	    for(String dom : list) {
	       //버퍼 데이터 기록
	       fw.write(dom);
	       //다음 행으로 이동
	       //fw.newLine();
	    }
	    //스트림 플러시
	    fw.flush();
	    fw.close();
		
	    return;
	}


	public void Insertdata(Connection conn, String day, String local, String N2O, String O3, String CO2,
			String SO2, String DUST, String UDUST) throws SQLException {
		this.conn = conn;
		String sql = "insert into polu(date, place, n2o, o3, co2, so2, dust, udust) values(?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, day);
			pstmt.setString(2, local);
			pstmt.setString(3, N2O);
			pstmt.setString(4, O3);
			pstmt.setString(5, CO2);
			pstmt.setString(6, SO2);
			pstmt.setString(7, DUST);
			pstmt.setString(8, UDUST);

			int r = pstmt.executeUpdate();
			System.out.println("변경된 row : " + r);

		} catch (SQLException e) {
			System.out.println("[SQL Error : " + e.getMessage() + "]");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void Updatedata(Connection conn,  String day, String local, String N2O, String O3, String CO2,
			String SO2, String DUST, String UDUST) throws SQLException {
		this.conn = conn;
		String sql = "update polu set n2o = ?, o3 = ?, co2 = ?, so2 = ?, dust = ?, udust = ? where date = ? and place = ?";
		pstmt = conn.prepareStatement(sql);

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(7, day);
			pstmt.setString(8, local);
			pstmt.setString(1, N2O);
			pstmt.setString(2, O3);
			pstmt.setString(3, CO2);
			pstmt.setString(4, SO2);
			pstmt.setString(5, DUST);
			pstmt.setString(6, UDUST);

			int r = pstmt.executeUpdate();
			System.out.println("변경된 row : " + r);

		} catch (SQLException e) {
			System.out.println("[SQL Error : " + e.getMessage() + "]");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void Deletedata(Connection conn, String day, String local) throws SQLException {
		this.conn = conn;
		String sql = "delete from polu where date = ? and place = ?";
		pstmt = conn.prepareStatement(sql);

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, day);
			pstmt.setString(2, local);

			int r = pstmt.executeUpdate();
			System.out.println("변경된 row : " + r);

		} catch (SQLException e) {
			System.out.println("[SQL Error : " + e.getMessage() + "]");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Model monthDataShow(Connection conn, String day, String local) throws SQLException {

		this.conn = conn;
		String sql = "select round(avg(n2o), 3) as avg_n2o, round(avg(o3), 3) as avg_o3, round(avg(co2), 3) as avg_co2, round(avg(so2), 3) as avg_so2, round(avg(dust), 0) as avg_dust, round(avg(udust), 0) as avg_udust from polu where substring(date, 1, 6) = ? and place = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, day);
		pstmt.setString(2, local);
		ResultSet rs = pstmt.executeQuery();

		Model a = new Model();
		double dust = 0, udust = 0;
		double n2o = 0, o3 = 0, co2 = 0, so2 = 0;
		int date=0;

		while (rs.next()) {
			
			/*
			if (rs.getString("n2o").trim() != null) {
				n2o = Double.parseDouble(rs.getString("n2o").trim());
			}else {
				n2o =0;
			}
			
			if (rs.getString("o3").trim() != null) {
				o3 = Double.parseDouble(rs.getString("o3").trim());
			}else {
				o3=0;
			}
			
			if (rs.getString("co2").trim() != null) {
				co2 = Double.parseDouble(rs.getString("co2").trim());
			}else {
				co2=0;
			}
			
			if (rs.getString("so2").trim() != null) {
				so2 = Double.parseDouble(rs.getString("so2").trim());
			}else {
				so2=0;
			}
			
			if (rs.getString("dust").trim() != null) {
				dust = Integer.parseInt(rs.getString("dust").trim());
			}else {
				dust=0;
			}
			
			if (rs.getString("udust").trim() != null) {
				udust = Integer.parseInt(rs.getString("udust").trim());
			}else {
				udust=0;
			}*/
			date = Integer.parseInt(day);
			n2o = Double.parseDouble(rs.getString(1));
			o3 = Double.parseDouble(rs.getString(2));
			co2 = Double.parseDouble(rs.getString(3));
			so2 = Double.parseDouble(rs.getString(4));
			dust = Double.parseDouble(rs.getString(5));
			udust = Double.parseDouble(rs.getString(6));

			int Idust = (int)dust;
			int Iudust = (int)udust;
			
			a.set_date(date);
			a.set_place(local);
			a.set_n2o(n2o);
			a.set_o3(o3);
			a.set_co2(co2);
			a.set_so2(so2);
			a.set_dust(Idust);
			a.set_udust(Iudust);
		}

		return a;

	}

	public Model currentDataShow(Connection conn, String day, String local) throws SQLException {

		this.conn = conn;

		String sql = "SELECT * FROM polu where date = ? and place = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, day);
		pstmt.setString(2, local);
		ResultSet rs = pstmt.executeQuery();

		Model a = new Model();
		int date, dust, udust;
		double n2o, o3, co2, so2;

		String place;
		while (rs.next()) {
			
			date = 0;
			dust = 0;
			udust = 0;
			n2o = 0;
			o3 = 0;
			co2 = 0;
			so2 = 0;
			place = "";
			
/*			n2o = Double.parseDouble(rs.getString(3));
			o3 = Double.parseDouble(rs.getString(4));
			co2 = Double.parseDouble(rs.getString(5));
			so2 = Double.parseDouble(rs.getString(6));
			dust = Integer.parseInt(rs.getString(7));
			udust = Integer.parseInt(rs.getString(8));
			*/
			
			
			if (rs.getString("n2o").trim() != null) {
				n2o = Double.parseDouble(rs.getString("n2o").trim());
			}
			if (rs.getString("o3").trim() != null) {
				o3 = Double.parseDouble(rs.getString("o3").trim());
			}
			if (rs.getString("co2").trim() != null) {
				co2 = Double.parseDouble(rs.getString("co2").trim());
			}
			if (rs.getString("so2").trim() != null) {
				so2 = Double.parseDouble(rs.getString("so2").trim());
			}
			if (rs.getString("dust").trim() != null) {
				dust = Integer.parseInt(rs.getString("dust").trim());
			}
			if (rs.getString("udust").trim() != null) {
				udust = Integer.parseInt(rs.getString("udust").trim());
			}

			a.set_date(date);
			a.set_place(local);
			a.set_n2o(n2o);
			a.set_o3(o3);
			a.set_co2(co2);
			a.set_so2(so2);
			a.set_dust(dust);
			a.set_udust(udust);
		}
		return a;
	}
}