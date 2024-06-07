package guestbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import guestbook.vo.GuestbookVo;

public class GuestbookDao {
	
	private Connection getConnection() throws SQLException {

	    Connection conn = null;

        try {
    	    //1. JDBC Driver 로딩 
			Class.forName("org.mariadb.jdbc.Driver");

	        //2. 연결하기 
			String url = "jdbc:mariadb://192.168.64.7:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} 
        
	    return conn;
	}
	
//	public int insert(GuestbookVo vo) {
//		
//		int result = 0;
//		
//		try (
//			Connection conn = getConnection();
//			PreparedStatement pstmt1 = conn.prepareStatement("insert into guestbook(name, password, contents, reg_date) values(?, ?, ?, now())");
//			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
//		) {
//			
//			pstmt1.setString(1, vo.getName());
//			pstmt1.setString(2, vo.getPassword());
//			pstmt1.setString(3, vo.getContents());
//			result = pstmt1.executeUpdate();
//			
//			ResultSet rs = pstmt2.executeQuery();
//			
//			vo.setNo(rs.next() ? rs.getLong(1) : null);
//			
//			rs.close();
//		     
//		} catch (SQLException e) {
//			System.out.println("error: " + e);
//		}
//	    
//		return result;
//		
//	}
	
	
	public int insert(GuestbookVo vo) {
		
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
				
		try {
			conn = getConnection();

			pstmt1 = conn.prepareStatement("update guestbook_log set count = count + 1 where date = current_date()");
			pstmt2 = conn.prepareStatement("insert into guestbook_log values(current_date(), 1)");
			pstmt3 = conn.prepareStatement("insert into guestbook(name, password, contents, reg_date) values(?, ?, ?, now())");
			
			pstmt3.setString(1, vo.getName());
			pstmt3.setString(2, vo.getPassword());
			pstmt3.setString(3, vo.getContents());
			
			// TX:BEGIN ////////////
			conn.setAutoCommit(false);
			
			// DML1
			int rowCount = pstmt1.executeUpdate();
			
			// DML2
			if(rowCount == 0) {
				pstmt2.executeUpdate();
			}
			
			// DML3
			result = pstmt3.executeUpdate();
			
			// TX:END (SUCCESS) ////////////
			conn.commit();
		     
		} catch (SQLException e) {
			System.out.println("error: " + e);
			
			// TX:END (FAIL) ////////////
			try {
				if(conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignored) {
			}
			
		} finally {
			try {
				if(pstmt3 != null) {
					pstmt3.close();
				}
				if(pstmt2 != null) {
					pstmt2.close();
				}
				if(pstmt1 != null) {
					pstmt1.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(SQLException ignored) {
			}
		}
	    
		return result;
		
	}
	
	
	public List<GuestbookVo> findAll() {
		
		List<GuestbookVo> result = new ArrayList<>();
		
		try (
	    	Connection conn = getConnection();
    		PreparedStatement pstmt = conn.prepareStatement("select no, name, contents, date_format(reg_date, '%Y/%m/%d %h:%i:%s') from guestbook order by reg_date desc");
    		ResultSet rs = pstmt.executeQuery();
	    ) {

	    	while(rs.next()) {
	    		Long no = rs.getLong(1);
	    		String name = rs.getString(2);
	    		String contents = rs.getString(3);
	    		String regDate = rs.getString(4);
	    		
	    		GuestbookVo vo = new GuestbookVo();
	    		vo.setNo(no);
	    		vo.setName(name);
	    		vo.setContents(contents);
	    		vo.setRegDate(regDate);
	    		
	    		result.add(vo);
	    	}
		     
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		return result;
		
	}
	
	public int deleteByNoAndPassword(String strNo, String password) {

		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		try {
			
			conn = getConnection();
			
//			String date = "select date_format('%Y-%n-%d', reg_date) from guestbook where no = ?";
			
			Long no = Long.parseLong(strNo);
			
			pstmt1 = conn.prepareStatement("update guestbook_log set count = count - 1 where date = (select date(reg_date) from guestbook where no = ?)");
			pstmt1.setLong(1, no);
			
			pstmt2 = conn.prepareStatement("delete from guestbook where no = ? and password = ?");
			pstmt2.setLong(1, no);
			pstmt2.setString(2, password);
			
			// TX:BEGIN ////////////
			conn.setAutoCommit(false);
			
			// DML1
			pstmt1.executeUpdate();
			
			// DML2
			result = pstmt2.executeUpdate();
			
			// TX:END (SUCCESS) ////////////
			conn.commit();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);

			// TX:END (FAIL) ////////////
			try {
				if(conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignored) {
			}
			
		} finally {
			try {
				if(pstmt2 != null) {
					pstmt2.close();
				}
				if(pstmt1 != null) {
					pstmt1.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(SQLException ignored) {
			}
		}
		
		return result;
		
	}

}
