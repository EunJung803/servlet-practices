package emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emaillist.vo.EmaillistVo;

public class EmaillistDao {

	public boolean insert(EmaillistVo vo) {
		
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {

			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			// 2. 연결
			String url = "jdbc:mariadb://192.168.64.7:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			// 3. Statement 준비
			String sql = "insert into emaillist values(null, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			// 4. binding
			pstmt.setString(1, vo.getFirstName());
			pstmt.setString(2, vo.getLastName());
			pstmt.setString(3, vo.getEmail());
			
			// 5. SQL 실행
			int count = pstmt.executeUpdate();
			
			// 5. 결과 처리
			result = count == 1;
			
		} catch (ClassNotFoundException e) {
			
			System.out.println("드라이버 로딩 실패:" + e);
			
		} catch (SQLException e) {
			
			System.out.println("error:" + e);
			
		} finally {
			
			try {
				if(pstmt != null) {		// 열땐 Connection 먼저 열었으니 닫을땐 Statement 먼저
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
		}
		
		return result;
	}

	public boolean deleteByEmail(String email) {

		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {

			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			// 2. 연결
			String url = "jdbc:mariadb://192.168.64.7:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			// 3. Statement prepare
			String sql = "delete from emaillist where email = ?";
			pstmt = conn.prepareStatement(sql);
			
			// 4. binding
			pstmt.setString(1, email);
			
			// 4. SQL 실행
			int count = pstmt.executeUpdate();
			
			// 5. 결과 처리
			result = count == 1;
			
		} catch (ClassNotFoundException e) {
			
			System.out.println("드라이버 로딩 실패:" + e);
			
		} catch (SQLException e) {
			
			System.out.println("error:" + e);
			
		} finally {
			
			try {
				if(pstmt != null) {		// 열땐 Connection 먼저 열었으니 닫을땐 Statement 먼저
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
		}
		
		return result;
		
	}

	public List<EmaillistVo> findAll() {
		List<EmaillistVo> result = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		
		  try {
		     //1. JDBC Driver 로딩 
		     Class.forName("org.mariadb.jdbc.Driver");
		
		     //2. 연결하기 
		     String url = "jdbc:mariadb://192.168.64.7:3306/webdb?charset=utf8";
		     conn = DriverManager.getConnection(url, "webdb", "webdb");
		
		     // 3. Statement 준비
		     String sql = "select no, first_name, last_name, email" +
		    		 		" from emaillist" +
		    		 		" order by no desc";
		     pstmt = conn.prepareStatement(sql);
		
		
		     // 4. binding (? == parameter, 파라미터 바인딩 과정)
		     // 조건이 없어서 바인딩 안함
		
		     // 5. SQL 실행 
		     rs = pstmt.executeQuery();
		
		     // 5. 결과 처리 
		     while(rs.next()) {
		        Long no =    rs.getLong(1);
		        String firstName =   rs.getString(2);
		        String lastName = rs.getString(3);
		        String email = rs.getString(4);
		        
		        EmaillistVo vo = new EmaillistVo();
		        vo.setNo(no);
		        vo.setFirstName(firstName);
		        vo.setLastName(lastName);
		        vo.setEmail(email);
		        
		        result.add(vo);
		        
		     }
		     
		  } catch(ClassNotFoundException e) {
		     System.out.println("드라이버 로딩 실패: " + e);
		  } catch (SQLException e) {
		     System.out.println("error: " + e);
		  } finally {
		     try {
		        if (rs != null) {
		           rs.close();
		        }
		        if(pstmt != null) {
		           pstmt.close();
		        }
		        if(conn != null) {
		           conn.close();
		        }
		     } catch(SQLException e) {
		        System.out.println("error: " + e);
		     }
		  }
		  
		return result;
	}

}
