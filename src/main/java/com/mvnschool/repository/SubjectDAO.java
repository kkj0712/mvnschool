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

import com.mvnschool.vo.Subject;

@Repository
public class SubjectDAO {
	
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
	
	//수강편람 개수구하기
	public int subCount(){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int count = 0;
		 
		try {
			conn = getConnection();
			String sql = "select count(*) from subject";
			st = conn.createStatement();
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
	
	//수강편람 리스트보기 (left outer join을 사용하여 현재수강신청 인원(수강대기+수강승인? 어쨌든 반려된 사람은 빼야함..)도 출력하기)
	public List<Subject> subMenual(int startRow, int endRow){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List <Subject> sublist = new ArrayList<Subject>();
		
		try {
			conn = getConnection();
			
			String sql = "select * from "
						+ "(select aa.*, rownum rn from "
						+ "(select s.subno, s.subname, s.teachername, s.cnt, s.submemo, s.teacherno, count(a.subno) currentCnt "
						+ "from subject s "
						+ "left outer join attend a "
						+ "on s.subno = a.subno and a.status in(1,2) "
						+ "group by s.subno, s.subname, s.teachername, s.cnt, s.submemo, s.teacherno) aa "
						+ "where rownum<=?) where rn>=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Subject subject = new Subject();
				subject.setSubno(rs.getString("subno"));
				subject.setSubname(rs.getString("subname"));
				subject.setTeachername(rs.getString("teachername"));
				subject.setCnt(rs.getInt("cnt"));
				subject.setSubmemo(rs.getString("submemo"));
				subject.setTeacherno(rs.getString("teacherno"));
				subject.setCurrentCnt(rs.getInt("currentCnt"));
				sublist.add(subject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt, rs);
		}
		return sublist;
	}
	
	//수강편람 상세보기
	public Subject subView(String subno) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Subject subject = null;
		
		try {
			conn = getConnection();
			String sql = "select * from subject where subno='"+subno+"'";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()){
				subject = new Subject();
				subject.setSubno(rs.getString("subno"));
				subject.setSubname(rs.getString("subname"));
				subject.setTeachername(rs.getString("teachername"));
				subject.setSubmemo(rs.getString("submemo"));
				subject.setCnt(rs.getInt("cnt"));
				subject.setTeacherno(rs.getString("teacherno"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, rs);
		}
		return subject;
	}
	
	//수강편람에 insert하기
	public int insertSub(Subject subject){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int result = -1;
		try {
			conn = getConnection();
			String sql = "insert into subject (subno, subname, teachername, cnt, submemo, teacherno) "
					+ "values('SUB' || subject_seq.nextval, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subject.getSubname());
			pstmt.setString(2, subject.getTeachername());
			pstmt.setInt(3, subject.getCnt());
			pstmt.setString(4, subject.getSubmemo());
			pstmt.setString(5, subject.getTeacherno());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt);
		}
		return result;
	}

	//교사의 강의목록 가져오기 (현재 수강인원도 출력. status가 2여야함)
	public List<Subject> tchSublist(int startRow, int endRow, String teacherno){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List <Subject> tch_sublist = new ArrayList<Subject>();
		
		try {
			conn = getConnection();
			
			String sql = "select * from "
					+ "(select aa.*, rownum rn from "
					+ "(select s.subno, s.subname, s.teachername, s.submemo, s.teacherno, s.cnt, count(a.subno) currentCnt "
					+ "from subject s "
					+ "left outer join attend a "
					+ "on s.subno = a.subno and a.status = 2 "
					+ "group by s.subno, s.subname, s.teachername, s.submemo, s.teacherno, s.cnt) aa "
					+ "where teacherno='"+teacherno+"' and rownum<=?) where rn>=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Subject subject = new Subject();
				subject.setSubno(rs.getString("subno"));
				subject.setSubname(rs.getString("subname"));
				subject.setTeachername(rs.getString("teachername"));
				subject.setCnt(rs.getInt("cnt"));
				subject.setSubmemo(rs.getString("submemo"));
				subject.setTeacherno(rs.getString("teacherno"));
				subject.setCurrentCnt(rs.getInt("currentCnt"));
				tch_sublist.add(subject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt, rs);
		}
		return tch_sublist;
	}
	
	//교사의 강의 개수 가지고 오기
	public int tchSubCount(String teacherno){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int result = -1;
		
		try {
			conn = getConnection();
			String sql = "select count(*) from subject where teacherno = '"+teacherno+"'";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()){
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, rs);
		}
		return result;
	}
	
	
	//강의수정
	public int subUpdate(Subject subject){
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = getConnection();
			String sql = "update subject set subname=?, cnt=?, submemo=? where subno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subject.getSubname());
			pstmt.setInt(2, subject.getCnt());
			pstmt.setString(3, subject.getSubmemo());
			pstmt.setString(4, subject.getSubno());
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt);
		}
		return result;
	}
	
	//강의삭제
	public int subDelete(String subno){
		Connection conn = null;
		Statement st = null;
		int result = -1;
		
		try {
			conn = getConnection();
			String sql="delete from subject where subno='"+subno+"'";
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
