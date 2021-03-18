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

import com.mvnschool.vo.User;

@Repository
public class UserDAO {

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
	
	//회원가입
	public int insert(User user){
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "INSERT INTO user_haksa "
					+ " (userno, name, userid, password, address, phone, email, role, regdate) "
					+ " VALUES ('USER' || user_haksa_seq.nextval,?,?,?,?,?,?,?,sysdate)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getUserid());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getAddress());
			pstmt.setInt(5, user.getPhone());
			pstmt.setString(6, user.getEmail());
			pstmt.setInt(7, user.getRole());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt);
		}
		return result;
	}
	
	//아이디 중복체크
	public String idCheck(String userid){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String result = "yes"; //가입가능
		
		try {
			conn = getConnection();
			String sql = "select password from user_haksa where userid='"+userid+"'";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) result = "no";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, rs);
		}
		return result;
	}

	//로그인
	public int login(String userid, String password){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int result = -1; //회원아님
		
		try {
			conn = getConnection();
			String sql = "select password, role from user_haksa where userid='"+userid+"'";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()){ //id 맞음
				if(rs.getString("password").equals(password)){ //비번 일치
					result = rs.getInt("role"); //로그인 성공. 권한 가져오기
				}else{ //비번 오류
					result = 4;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, rs);
		}
		return result;
	}
	
	//마이페이지 보기
	public User mypageView(String userid){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		User user = null;
		
		try {
			conn = getConnection();
			String sql = "select * from user_haksa where userid='"+userid+"'";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()){
				user = new User();
				user.setUserno(rs.getString("userno"));
				user.setUserid(rs.getString("userid"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setAddress(rs.getString("address"));
				user.setPhone(rs.getInt("phone"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getInt("role"));
				user.setRegdate(rs.getString("regdate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, rs);
		}
		return user;
	}
	
	//마이페이지 정보수정
	public int mypageUpdate(User user, String userid){
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = getConnection();
			String sql = "update user_haksa set email=?, address=?, phone=? where userid='"+userid+"'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getAddress());
			pstmt.setInt(3, user.getPhone());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt);
		}
		return result;
	}

	//마이페이지 비밀번호 변경
	public int pwdUpdate(User user, String userid){
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = getConnection();
			String sql = "update user_haksa set password=? where userid='"+userid+"'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt);
		}
		return result;
	}
	
	//수강생 목록의 개수
	public int chkStuCount(String subno){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = getConnection();
			String sql = "select count(*)"
					+ "from user_haksa where userno "
					+ "in (select stuno from attend where subno = '"+subno+"' and status = 2)";
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
	
	//수강생 목록
	public List<User> chkStu(int startRow, int endRow, String subno){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> stuList = new ArrayList<>();
		
		try {
			conn = getConnection();
			
			String sql = "select * from "
					+ "(select u.name, u.userid, u.address, u.phone, u.email, rownum rn "
					+ "from user_haksa u "
					+ "where userno in "
					+ " (select stuno from attend where subno = '"+subno+"' and status=2) and rownum <= ?) where rn >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();
			while(rs.next()){
				User user = new User();
				user.setName(rs.getString("name"));
				user.setUserid(rs.getString("userid"));
				user.setAddress(rs.getString("address"));
				user.setPhone(rs.getInt("phone"));
				user.setEmail(rs.getString("email"));
				stuList.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt, rs);
		}
		return stuList;
	}
	
	//회원의 수
	public int userCount(int role){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = getConnection();
			String sql = "select count(*) from user_haksa where role = "+role;
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
	
	//회원 목록 (학생)
	public List<User> userList(int startRow, int endRow, int role){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> userlist = new ArrayList<>();
		
		try {
			conn = getConnection();
			
			String sql = "select * from "
					+ " (select aa.*, rownum rn from "
					+ " (select * from user_haksa where role = "+ role
					+ " order by regdate desc) aa "
					+ " where rownum<=?) where rn>=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();
			while(rs.next()){
				User user = new User();
				user.setUserno(rs.getString("userno"));
				user.setName(rs.getString("name"));
				user.setUserid(rs.getString("userid"));
				user.setAddress(rs.getString("address"));
				user.setPhone(rs.getInt("phone"));
				user.setEmail(rs.getString("email"));
				userlist.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt, rs);
		}
		return userlist;
	}
	
	//회원 상세보기
	public User userView(String userno){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		User user = new User();
		
		try {
			conn = getConnection();
			String sql = "select name, address, phone, email, userno from user_haksa where userno='"+userno+"'";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()){
				user.setUserno(rs.getString("userno"));
				user.setName(rs.getString("name"));
				user.setAddress(rs.getString("address"));
				user.setPhone(rs.getInt("phone"));
				user.setEmail(rs.getString("email"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, st, rs);
		}
		return user; 
	}
	
	//회원정보 수정하기
	public int userUpdate(User user){
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = getConnection();
			String sql = "update user_haksa set address=?, phone=?, email=? where userno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getAddress());
			pstmt.setInt(2, user.getPhone());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getUserno());
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt);
		}
		return result;
	}
	
	//교사 회원신청 기다리는 사람의 수
	public int waitCount(){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = getConnection();
			String sql = "select count(*) from user_haksa where role = 9";
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
	
	//교사 회원신청 기다리는 사람 리스트
	public List<User> waitList(int startRow, int endRow){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> userlist = new ArrayList<>();
		
		try {
			conn = getConnection();
			
			String sql = "select * from "
					+ " (select aa.*, rownum rn from "
					+ " (select * from user_haksa where role = 9"
					+ " order by regdate desc) aa "
					+ " where rownum<=?) where rn>=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();
			while(rs.next()){
				User user = new User();
				user.setUserno(rs.getString("userno"));
				user.setName(rs.getString("name"));
				user.setUserid(rs.getString("userid"));
				user.setAddress(rs.getString("address"));
				user.setPhone(rs.getInt("phone"));
				user.setEmail(rs.getString("email"));
				userlist.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, pstmt, rs);
		}
		return userlist;
	}
	
	//교사 승인
	public int tchProve(String userno){
		Connection conn = null;
		Statement st = null;
		int result = 0; 
		try {
			conn = getConnection();
			String sql = "update user_haksa set role =2 where userno='"+userno+"'";
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