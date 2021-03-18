package com.mvnschool.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mvnschool.vo.Reply;

@Repository
public class ReplyDAO {

	//디비셋팅
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "1234");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB연결 실패");
		}
		return conn;
	}
	
	//댓글 입력
	public void insert(Reply reply){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "insert into reply (rnum, userno, name, msg, regdate, bnum) "
					+ "values('REP' || reply_seq.nextval, ?, ?, ?, sysdate, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reply.getUserno());
			pstmt.setString(2, reply.getName());
			pstmt.setString(3, reply.getMsg());
			pstmt.setString(4, reply.getBnum());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt);
		}
	}
	
	//댓글 목록
	public List<Reply> replyList(String bnum){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List<Reply> replyList = new ArrayList<Reply>();
		
		try {
			conn = getConnection();
			String sql = "select * from reply where bnum='"+bnum+"' order by regdate desc";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Reply reply = new Reply();
				reply.setBnum(rs.getString("bnum"));
				reply.setMsg(rs.getString("msg"));
				reply.setName(rs.getString("name"));
				reply.setRegdate(rs.getString("regdate"));
				reply.setRnum(rs.getString("rnum"));
				reply.setUserno(rs.getString("userno"));
				replyList.add(reply);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, rs);
		}
		return replyList;
	}
	
	//댓글 삭제
	public void replyDelete(String rnum){
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConnection();
			String sql = "delete from reply where rnum='"+rnum+"'";
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, null);
		}
	}
	
	//closeConnection
	private void closeConnection(Connection conn, PreparedStatement pstmt) {
		try {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	private void closeConnection(Connection conn, Statement st, ResultSet rs) {
		try {
			if(st != null) st.close();
			if(conn != null) conn.close();
			if(rs != null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

}
