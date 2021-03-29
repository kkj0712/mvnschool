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
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mvnschool.vo.Attend;

@Repository
public class AttendDAO {

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
	
	//수강신청 테이블에 그 과목을 이미 신청했는지 확인
	public List<Attend> mySublist(String userno){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;		
		List <Attend> mysublist = new ArrayList<Attend>();
		
		try {
			conn = getConnection();
			String sql = "select subno from attend where stuno='"+userno+"'";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){ 
				Attend attend = new Attend();
				attend.setSubno(rs.getString("subno"));
				mysublist.add(attend);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, rs);
		}
		return mysublist;
	}
	
	
	//수강편람에서 수강신청 버튼을 눌렀을 경우 =>수강신청 테이블에 insert시키기
	public int insert(Attend attend){
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = -1;
		
		try {
			conn = getConnection();
			String sql="insert into attend(attno, stuno, regdate, subname, teachername, status, subno, teacherno) "
					+ "values('ATT' || attend_seq.nextval, ?, sysdate, ?, ?, (select com_num from com_code where com_name='wait' and com_id='status'), ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, attend.getStuno());
			pstmt.setString(2, attend.getSubname());
			pstmt.setString(3, attend.getTeachername());
			pstmt.setString(4, attend.getSubno());
			pstmt.setString(5, attend.getTeacherno());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt);
		}
		return result;
		
	}
	
	//내가 수강신청한 과목 갯수 가지고 오기
	public int myAttendCount(String userno){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int result = -1;
		
		try {
			conn = getConnection();
			String sql = "select count(*) from attend where stuno = '"+userno+"'";
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
	
	//수강신청한 목록 리스트보기 (현재 그 과목의 수강신청 인원도 출력해야함)
	public List<Object> myAttendPre(int startRow, int endRow, String userno){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Object> list = new ArrayList<>();
		
		try {
			conn = getConnection();
			String sql = "select * from "
						+ "(select aa.*, rownum rn from "
						+ "(select a.attno, a.subno, a.subname, a.teachername, s.cnt, s.submemo, count(a.subno) currentCnt, a.status, a.regdate "
						+ "from attend a "
						+ "join subject s "
						+ "on s.subno = a.subno "
						+ "where stuno = '"+userno+"' "
						+ "group by a.attno, a.subno, a.subname, a.teachername, s.cnt, s.submemo, a.status, a.regdate "
						+ "order by a.regdate desc) aa "
						+ "where rownum<=?) where rn>=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Map<String, String> hm = new HashMap<>();
				hm.put("attno", rs.getString("attno"));
				hm.put("status", Integer.toString(rs.getInt("status")));
				hm.put("subno", rs.getString("subno"));
				hm.put("subname", rs.getString("subname"));
				hm.put("teachername", rs.getString("teachername"));
				hm.put("cnt", Integer.toString(rs.getInt("cnt")));
				hm.put("currentCnt", rs.getString("currentCnt"));
				hm.put("regdate", rs.getString("regdate"));
				list.add(hm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt, rs);
		}
		return list;
	}
	
	//수강 신청취소
	public int delete(String attno){
		Connection conn = null;
		Statement st = null;
		int result = -1;
		
		try {
			conn = getConnection();
			st = conn.createStatement();
			String sql = "delete from attend where attno='"+attno+"'";
			result = st.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, null);
		}
		return result;
	}
	
	
	//승인된 수강 목록 리스트보기 (현재 그 과목의 수강신청 인원도 출력해야함)
	public List<Object> prvAttend(int startRow, int endRow, String userno){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Object> list = new ArrayList<>();
		
		try {
			conn = getConnection();
			String sql = "select * from "
						+ "(select aa.*, rownum rn from "
						+ "(select a.subno, a.subname, a.teachername, s.cnt, s.submemo, count(a.subno) currentCnt "
						+ "from attend a "
						+ "join subject s "
						+ "on s.subno = a.subno "
						+ "where stuno = '"+userno+"' "
						+ "and status = 2 "
						+ "group by a.subno, a.subname, a.teachername, s.cnt, s.submemo) aa "
						+ "where rownum<=?) where rn>=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Map<String, String> hm = new HashMap<>();
				hm.put("subno", rs.getString("subno"));
				hm.put("subname", rs.getString("subname"));
				hm.put("teachername", rs.getString("teachername"));
				hm.put("cnt", Integer.toString(rs.getInt("cnt")));
				hm.put("currentCnt", rs.getString("currentCnt"));
				list.add(hm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt, rs);
		}
		return list;
	}
	
	//승인된 수강목록 갯수 가지고 오기
	public int myPrvAttendCount(String userno){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int result = -1;
		
		try {
			conn = getConnection();
			String sql = "select count(*) from attend where stuno = '"+userno+"' and status = 2";
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
	
	//교사의 강의를 수강신청한 학생들의 수
	public int attendCtrlCount(String userno){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int result = -1;
		
		try {
			conn = getConnection();
			String sql = "select count(*) from attend where teacherno = '"+userno+"' and status=1";
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
	
	//수강신청 관리 리스트보기 (status가 1인 학생들의 이름도 출력)
	public List<Object> attendCtrl(int startRow, int endRow, String userno){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Object> list = new ArrayList<>();
		
		try {
			conn = getConnection();
			String sql = "select * from "
						+ "(select aa.*, rownum rn from "
						+ "(select a.subno, a.subname, u.userno, u.name, a.regdate "
						+ "from attend a "
						+ "join user_haksa u "
						+ "on a.stuno = u.userno "
						+ "where teacherno = '"+userno+"'"
						+ "and status = 1) aa "
						+ "where rownum<=?) where rn>=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Map<String, String> hm = new HashMap<>();
				hm.put("subno", rs.getString("subno"));
				hm.put("subname", rs.getString("subname"));
				hm.put("stuno", rs.getString("userno")); //userno가 stuno가 됨
				hm.put("name", rs.getString("name"));
				hm.put("regdate", rs.getString("regdate"));
				list.add(hm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt, rs);
		}
		return list;
	}
	
	//수강신청 승인하기
	public int attProve(String stuno, String subno){
		Connection conn = null;
		Statement st = null;
		int result = -1;
		try {
			conn = getConnection();
			String sql = "update attend set status=2 where stuno='"+stuno+"' and subno='"+subno+"'";
			st = conn.createStatement();
			result = st.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, null);
		}
		return result;
	}
	
	//수강신청 반려하기
	public int attReject(String stuno, String subno){
		Connection conn = null;
		Statement st = null;
		int result = -1;
		try {
			conn = getConnection();
			String sql = "update attend set status=3 where stuno='"+stuno+"' and subno='"+subno+"'";
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
