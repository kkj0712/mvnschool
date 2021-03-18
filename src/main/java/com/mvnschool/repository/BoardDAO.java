package com.mvnschool.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mvnschool.vo.Board;
import com.mvnschool.vo.User;

@Repository
public class BoardDAO {
	
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
	
	//게시판 업로드
	public int boardInsert(Board board){
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = getConnection();
			String sql = "insert into board "
					+ "(bnum, userno, name, title, content, hit, regdate, replycnt, fileupload) "
					+ "values('BOA' || board_seq.nextval, ?, ?, ?, ?, 0, sysdate, 0, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getUserno());
			pstmt.setString(2, board.getName());
			pstmt.setString(3, board.getTitle());
			pstmt.setString(4, board.getContent());
			pstmt.setString(5, board.getFileupload());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt);
		}
		return result;
	}
	
	//게시글 개수
	public int getCount(String field, String word){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "";
		try {
			conn = getConnection();
			st = conn.createStatement();
			if(word.equals("")) {
				sql = "select count(*) from board";
			}else{
				sql = "select count(*) from board where "+field+" like '%"+word+"%'";
			}
			rs = st.executeQuery(sql);
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, rs);
		}
		return count;
	}
	
	//게시판 목록 (검색아닐때)
	public List<Board> boardList(int startRow, int endRow){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> boardlist = new ArrayList<>();
		
		try {
			conn = getConnection();
			
			String sql = "select * from "
					+ " (select aa.*, rownum rn from "
					+ " (select * from board "
					+ " order by regdate desc) aa "
					+ " where rownum<=?) where rn>=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Board board = new Board();
				board.setUserno(rs.getString("userno"));
				board.setName(rs.getString("name"));
				board.setBnum(rs.getString("bnum"));
				board.setContent(rs.getString("content"));
				board.setFileupload(rs.getString("fileupload"));
				board.setHit(rs.getInt("hit"));
				board.setRegdate(rs.getString("regdate"));
				board.setReplycnt(rs.getInt("replycnt"));
				board.setTitle(rs.getString("title"));
				boardlist.add(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt, rs);
		}
		return boardlist;
	}
	
	//게시판 목록 (검색)
	public List<Board> boardList(int startRow, int endRow, String field, String word){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> boardlist = new ArrayList<>();
		
		try {
			conn = getConnection();
			
			String sql = "select * from "
					+ " (select aa.*, rownum rn from "
					+ " (select * from board where "+field+" like '%"+word+"%' "
					+ " order by regdate desc) aa "
					+ " where rownum<=?) where rn>=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Board board = new Board();
				board.setUserno(rs.getString("userno"));
				board.setName(rs.getString("name"));
				board.setBnum(rs.getString("bnum"));
				board.setContent(rs.getString("content"));
				board.setFileupload(rs.getString("fileupload"));
				board.setHit(rs.getInt("hit"));
				board.setRegdate(rs.getString("regdate"));
				board.setReplycnt(rs.getInt("replycnt"));
				board.setTitle(rs.getString("title"));
				boardlist.add(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt, rs);
		}
		return boardlist;
	}
	
	//게시글 보기
	public Board boardView(String bnum){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Board board = new Board();
		
		try {
			conn = getConnection();
			String sql = "select * from board where bnum='"+bnum+"'";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()){
				board.setBnum(rs.getString("bnum"));
				board.setContent(rs.getString("content"));
				board.setFileupload(rs.getString("fileupload"));
				board.setHit(rs.getInt("hit"));
				board.setName(rs.getString("name"));
				board.setRegdate(rs.getString("regdate"));
				board.setReplycnt(rs.getInt("replycnt"));
				board.setTitle(rs.getString("title"));
				board.setUserno(rs.getString("userno"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, rs);
		}
		return board;
	}
	
	//조회수 업데이트
	public void updateHit(String bnum){
		Connection conn = null;
		Statement st = null;
		
		try {
			conn = getConnection();
			String sql = "update board set hit=hit+1 where bnum='"+bnum+"'";
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, null);
		}
	}
	
	//게시글 수정에서 파일만 삭제할 경우
	public int fileDelUpdate(String fileName){
		Connection conn = null;
		Statement st = null;
		int result = 0;
		
		try {
			conn = getConnection();
			String sql = "update board set fileupload = NULL where fileupload='"+fileName+"'";
			st = conn.createStatement();
			result = st.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, null);
		}
		return result;
	}
	
	//게시글 수정하기
	public int boardUpdatePro(Board board){
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = getConnection();
			String sql = "update board set title=?, content=?, fileupload=? where bnum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getFileupload());
			pstmt.setString(4, board.getBnum());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt);
		}
		return result;
	}
	
	//게시글 삭제하기
	public int boardDelete(String bnum){
		Connection conn = null;
		Statement st = null;
		int result = 0;
		try {
			conn = getConnection();
			String sql = "delete from board where bnum='"+bnum+"'";
			st = conn.createStatement();
			result = st.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, null);
		}
		return result;
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