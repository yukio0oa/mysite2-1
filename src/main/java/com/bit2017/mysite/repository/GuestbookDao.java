package com.bit2017.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bit2017.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	
	public boolean insert( GuestbookVo guestbookVo ) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. JDBC Driver Loading ( JDBC Class Loading )
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. Get Connection( Connect to DB )
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		
			// 3. Prepare SQL
			String sql = "insert into guestbook values( seq_guestbook.nextval, ?, ?, ?, sysdate )";
			pstmt = conn.prepareStatement( sql );
			
			// 4. binding
			pstmt.setString( 1, guestbookVo.getName() );
			pstmt.setString( 2, guestbookVo.getPassword() );
			pstmt.setString( 3, guestbookVo.getContent() );
			
			// 5. execute SQL
			int count = pstmt.executeUpdate();
			result = count == 1;
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 3. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		
		return result;
	}
	
	public boolean delete( GuestbookVo guestbookVo ) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. JDBC Driver Loading ( JDBC Class Loading )
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. Get Connection( Connect to DB )
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		
			// 3. Prepare SQL
			String sql = "delete from guestbook where no = ? and password = ?";
			pstmt = conn.prepareStatement( sql );
			
			// 4. binding
			pstmt.setLong( 1, guestbookVo.getNo() );
			pstmt.setString( 2, guestbookVo.getPassword() );
			
			// 5. execute SQL
			int count = pstmt.executeUpdate();
			result = count == 1;
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 3. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		
		return result;
	}

	public List<GuestbookVo> getList() {
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// 1. JDBC Driver Loading ( JDBC Class Loading )
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. Connection 얻어오기( Connect to DB )
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

			// 3. SQL문 실행
			String sql = "select no, name, content, to_char(reg_date, 'yyyy-mm-dd') from guestbook order by reg_date desc";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			// 4. 결과 처리
			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String content = rs.getString(3);
				String regDate = rs.getString(4);

				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setContent(content);
				vo.setRegDate(regDate);

				list.add(vo);
			}

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 3. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

		return list;
	}
}
