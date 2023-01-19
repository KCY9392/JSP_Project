package com.kh.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.vo.Member;

public class MemberDao {
	
	private Properties prop = new Properties();
	
	public MemberDao() {
		
		String fileName = MemberDao.class.getResource("/sql/member/member-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Member loginMember(String userId, String userPwd, Connection conn) {
		// select문 => ResultSet객체 => Member객체
		Member m = null;
		
		PreparedStatement psmt = null;
		
		ResultSet rset = null;
		
		String sql = prop.getProperty("loginMember");
		
		
		try {
			psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, userId);
			psmt.setString(2, userPwd);
			
			rset = psmt.executeQuery(); //실행하고 결과얻기
			
			//값이 무조건 하나일 수 밖에 없으니까 while문이 아닌 if문 활용
			if(rset.next()) {
				m = new Member(
						   rset.getInt("USER_NO"),
						   rset.getString("USER_ID"),
						   rset.getString("USER_PWD"),
						   rset.getString("USER_NAME"),
						   rset.getString("PHONE"),
						   rset.getString("EMAIL"),
						   rset.getString("ADDRESS"),
						   rset.getString("INTEREST"),
						   rset.getDate("ENROLL_DATE"),
						   rset.getDate("MODIFY_DATE"),
						   rset.getString("STATUS"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(psmt);
		}
		
		return m;
	}
	
	
	public int insertMember(Member m , Connection conn) {
	    
	    // insert문 처리된 행의 개수를 반환하여 result에 저장시키기!
	    int result = 0;
	    
	    PreparedStatement psmt = null;
	    
	    String sql = prop.getProperty("insertMember"); // 미완성된 sql
	    
	    try {
            psmt = conn.prepareStatement(sql);
            
            /*
             *  INSERT INTO MEMBER
             *  VALUES (.NEXTVAL , ? , ? , ? , ? , ? , ? , ? , SYSDATE , NULL , 'Y'); 
             */
            
            //미완성된 sql 완성시키기
            psmt.setString(1, m.getUserId());
            psmt.setString(2, m.getUserPwd());
            psmt.setString(3, m.getUserName());
            psmt.setString(4, m.getPhone());
            psmt.setString(5, m.getEmail());
            psmt.setString(6, m.getAddress());
            psmt.setString(7, m.getInterest());
            
            result = psmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(psmt);
        }
	    
	    return result;
	}
	
	public int updateMember(Member m, Connection conn) {
	    
	    int result = 0;
	    
	    PreparedStatement psmt = null;
	    
	    String sql = prop.getProperty("updateMember");
	    
	    try {
            /*
             * UPDATE MEMBER SET
             * USER_NAME = ?,
             * PHONE = ?,
             * EMAIL = ?,
             * ADDRESS = ?,
             * INTEREST = ?
             * WHERE USER_ID = ?
             */
	        
	        psmt = conn.prepareStatement(sql);
            
            psmt.setString(1, m.getUserName());
            psmt.setString(2, m.getPhone());
            psmt.setString(3, m.getEmail());
            psmt.setString(4, m.getAddress());
            psmt.setString(5, m.getInterest());
            psmt.setString(6, m.getUserId());
            
            result = psmt.executeUpdate();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(psmt);
        }
	    
	    return result;
	}
	
	
	public Member selectMember(String userId, Connection conn) {
	    
	    Member m = null;
	    
	    PreparedStatement psmt = null;
	    ResultSet rset = null;
	    
	    String sql = prop.getProperty("selectMember");
	    
	    try {
	        /*
	         * SELECT * FROM MEMBER
	         * WHERE USER_ID = ?
	         */
	        
            psmt = conn.prepareStatement(sql);
            
            psmt.setString(1, userId);
            
            rset = psmt.executeQuery();
            
          //값이 무조건 하나일 수 밖에 없으니까 while문이 아닌 if문 활용
            if(rset.next()) {
                m = new Member(
                           rset.getInt("USER_NO"),
                           rset.getString("USER_ID"),
                           rset.getString("USER_PWD"),
                           rset.getString("USER_NAME"),
                           rset.getString("PHONE"),
                           rset.getString("EMAIL"),
                           rset.getString("ADDRESS"),
                           rset.getString("INTEREST"),
                           rset.getDate("ENROLL_DATE"),
                           rset.getDate("MODIFY_DATE"),
                           rset.getString("STATUS"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(psmt);
        }
	    
	    return m;
	}
	
	
	
	public int updatePwdMember(String userId, String userPwd, String updatePwd, Connection conn) {
	    
	    int result = 0;
	    
	    PreparedStatement psmt = null;
	   
	    String sql = prop.getProperty("updatePwdMember");
	    
	    
	    try {
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, updatePwd);
            psmt.setString(2, userId);
            psmt.setString(3, userPwd);
            
            result = psmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(psmt);
        }
	    
	    return result;
	}
	
	
	
	public int deleteMember(String userId, String userPwd, Connection conn) {
	    
	    int result = 0;
        
        PreparedStatement psmt = null;
       
        String sql = prop.getProperty("deleteMember");
        
        
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, userId);
            psmt.setString(2, userPwd);
            
            result = psmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(psmt);
        }
        
        return result;
	      
	}
	
	
	public int idCheck(String checkId,Connection conn) {
	    
	    //select -> ResultSet으로 담기 ( 숫자 하나 )
	    
	    int count = 0;
	    
	    PreparedStatement psmt = null;
	    
	    ResultSet rset = null;
	    
	    String sql = prop.getProperty("idCheck");
	    
	    try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setString(1, checkId);
            
            rset = psmt.executeQuery();
            
            if(rset.next()) {
                count = rset.getInt(1); // 첫번째 칼럼 의 값을 count에 넣기
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(psmt);
        }
	    return count;
	}
	
	
}
