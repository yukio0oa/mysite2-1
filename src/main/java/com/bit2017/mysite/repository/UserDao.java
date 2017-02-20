package com.bit2017.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.bit2017.mysite.vo.UserVo;

@Repository
public class UserDao {
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		//1. JDBC Driver Loading ( JDBC Class Loading )
		try {
			Class.forName( "oracle.jdbc.driver.OracleDriver" );
		} catch (ClassNotFoundException e) {
			System.out.println( "Driver 로딩 실패 :" + e );
		}
		
		//2. Connection 얻어오기( Connect to DB )
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		conn = DriverManager.getConnection(url, "webdb", "webdb");
		
		return conn;
	}
	
	public UserVo get( Long userNo ) {
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select no, name, email, gender from users where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong( 1, userNo );
			
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				Long no = rs.getLong( 1 );
				String name = rs.getString( 2 );
				String email = rs.getString( 3 );
				String gender = rs.getString( 4 );
;				
				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setGender(gender);
			}
			
		} catch (SQLException e) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println( "error:" + e );
			}  
		}
		
		return userVo;		
	}
	
	public UserVo get( String email, String password ) {
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select no, name from users where email=? and password=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString( 1, email );
			pstmt.setString( 2, password );
			
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				Long no = rs.getLong( 1 );
				String name = rs.getString( 2 );
				
				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
			}
			
		} catch (SQLException e) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println( "error:" + e );
			}  
		}
		
		return userVo;
	}
	
	public boolean insert( UserVo userVo ) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "insert into users values ( seq_users.nextval, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString( 1, userVo.getName() );
			pstmt.setString( 2, userVo.getEmail() );
			pstmt.setString( 3, userVo.getPassword() );
			pstmt.setString( 4, userVo.getGender() );
			
			int count = pstmt.executeUpdate();
			
			result = count == 1; 
		} catch( SQLException e ) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch( SQLException e ) {
				System.out.println( "error:" + e );
			}
		}
		
		return result;
	}
	
	public boolean update( UserVo userVo ) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = null;
			if( "".equals( userVo.getPassword() ) ) {
				sql = "update users set name=?, gender=? where no = ?";
			} else {
				sql = "update users set name=?, password=?, gender=? where no = ?";
			}
			pstmt = conn.prepareStatement(sql);
			
			if( "".equals( userVo.getPassword() ) ) {
				pstmt.setString( 1, userVo.getName() );
				pstmt.setString( 2, userVo.getGender() );
				pstmt.setLong( 3, userVo.getNo() );
			} else {
				pstmt.setString( 1, userVo.getName() );
				pstmt.setString( 2, userVo.getPassword() );
				pstmt.setString( 3, userVo.getGender() );
				pstmt.setLong( 4, userVo.getNo() );
			}
			
			int count = pstmt.executeUpdate();
			
			result = count == 1; 
		} catch( SQLException e ) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch( SQLException e ) {
				System.out.println( "error:" + e );
			}
		}
		
		return result;
	}	
}
