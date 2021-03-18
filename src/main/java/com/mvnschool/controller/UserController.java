package com.mvnschool.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvnschool.repository.UserDAO;
import com.mvnschool.vo.Attend;
import com.mvnschool.vo.PageUtil;
import com.mvnschool.vo.SendEmail;
import com.mvnschool.vo.Subject;
import com.mvnschool.vo.User;

@Controller
public class UserController {
	
	@Autowired                                 
	private UserDAO userDAO; 
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String main(){
		return "/index";
	}
	
	//회원가입 창으로 이동
	@RequestMapping(value="/join.do", method = RequestMethod.GET)
	public String joinForm(){
		return "user/join"; 
	}

	//회원가입
	@RequestMapping(value="/insert.do", method = RequestMethod.POST)
	@ResponseBody 
	public int insert(@ModelAttribute User user, Model model){
		if(user.getRole() == 2) user.setRole(9);
		int result = userDAO.insert(user);
		return result; 
	}
	
	//아이디 중복체크
	@RequestMapping(value="/idcheck.do", method = RequestMethod.POST)
	@ResponseBody 
	public String idCheck(String userid, Model model){
		String result = userDAO.idCheck(userid);
		return result;
	}

	//로그인
	@RequestMapping(value="/login.do", method = RequestMethod.POST)
	@ResponseBody 
	public int login(@ModelAttribute User user, Model model, HttpServletRequest request){
		String userid = user.getUserid();
		String password = user.getPassword();
		int result = userDAO.login(userid, password);
		if(result >=1 && result <=3){
			HttpSession session = request.getSession();
			session.setAttribute("userid", userid);
			session.setAttribute("role", result);
		}
		return result;
	}

	//로그아웃
	@RequestMapping(value="/logout.do", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
	
	//로그인 후 마이페이지로 이동 (마이페이지는 학생, 교사 똑같음)
	@RequestMapping(value="/mypage.do", method = RequestMethod.GET)
	public String stu_mypage(HttpServletRequest request){
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("userid");
		User user = userDAO.mypageView(userid);
		session.setAttribute("userno", user.getUserno()); //userno도 세션이 생겼음
		request.setAttribute("user", user);
		return "user/mypage";
	}
	
	//마이페이지 정보수정
	@RequestMapping(value="/mypage_update.do", method = RequestMethod.POST)
	@ResponseBody
	public int mypage_upate(@ModelAttribute User user, HttpServletRequest request){
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("userid");
		int result = userDAO.mypageUpdate(user, userid);
		return result;
	}
	
	//비밀번호 변경으로 이동
	@RequestMapping(value="/pwd_change.do", method = RequestMethod.GET)
	public String pwd_change(){
		return "user/pwdChange";
	}
	
	//비밀번호 변경
	@RequestMapping(value="/pwd_update.do", method = RequestMethod.POST)
	@ResponseBody
	public int pwd_update(@ModelAttribute User user, HttpServletRequest request){
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("userid");
		int result = userDAO.pwdUpdate(user, userid);
		return result;
	}
	
	//관리자 로그인시 회원관리 탭으로 이동
	@RequestMapping(value="userCtrl_pre.do", method = RequestMethod.GET)
	public String userCtrlPre(){
		return "user/userCtrl_pre";
	}
	
	//회원의 목록이 담길 테이블 정보 (학생부터 나옴)
	@RequestMapping(value="/userCtrl.do", method = RequestMethod.GET)
	public String userCtrl(Model model, String pageNum, HttpServletRequest request, String setRole){
		if(pageNum == null) pageNum="1"; //사용자가 가려는 페이지
		
		if(setRole.equals("student")) setRole = "1"; //selectbox가 학생인경우 setRole=1
		if(setRole.equals("teacher")) setRole = "2"; //selectbox가 교사인경우 setRole=2
		int role = Integer.parseInt(setRole); //setRole을 정수형 숫자로 변환

		int currentPage = Integer.parseInt(pageNum); //현재페이지
		int pageSize = 5; //한 화면에 보여지는 수
		int startRow = (currentPage-1)*pageSize+1; //시작 번호
		int endRow = currentPage*pageSize; //끝 번호
		
		int count = userDAO.userCount(role); //회원의 수 (학생/교사 따로)
		
		int totPage = (count/pageSize)+(count%pageSize==0?0:1); //총 페이지수
		int pageBlock = 3; //페이지블록 [1 2 3] [4 5 6]
		int startPage = ((currentPage-1)/pageBlock)*pageBlock+1; //페이지블록에서 시작페이지 
		int endPage = startPage+pageBlock-1; //페이지블록에서 끝페이지
		if(endPage>totPage) endPage = totPage; //마지막 페이지블록 처리
		
		PageUtil pu = new PageUtil();
		pu.setCurrentPage(currentPage);
		pu.setEndPage(endPage);
		pu.setPageBlock(pageBlock);
		pu.setStartPage(startPage);
		pu.setTotPage(totPage);

		int rowNo = count-((currentPage-1)*pageSize);
		
		List<User> userlist = userDAO.userList(startRow, endRow, role);

		model.addAttribute("rowNo", rowNo);
		model.addAttribute("pu", pu);
		model.addAttribute("count", count);
		model.addAttribute("list", userlist);
		
		return "user/userCtrl";
	}
	
	//회원 상세보기 (userno로 조회)
	@RequestMapping(value="/userView.do", method = RequestMethod.GET)
	public String userView(String userno, Model model){
		User user = userDAO.userView(userno);
		model.addAttribute("user", user);
		return "user/userView";
	}
	
	//회원정보 수정하기
	@RequestMapping(value="/userUpdate.do", method = RequestMethod.POST)
	@ResponseBody
	public int userUpdate(@ModelAttribute User user){
		int result = userDAO.userUpdate(user);
		return result;
	}
	
	//교사 회원신청 리스트로 가는 페이지
	@RequestMapping(value="/tchWaitList_pre.do", method = RequestMethod.GET)
	public String tchWaitListPre(){
		return "user/tch_waitList_pre";
	}
	
	//교사 회원신청 리스트 (role이 9인 사람들)
	@RequestMapping(value="/tchWaitList.do", method = RequestMethod.GET)
	public String tchWaitListPre(Model model, String pageNum, HttpServletRequest request){
		if(pageNum == null) pageNum="1"; //사용자가 가려는 페이지
		int currentPage = Integer.parseInt(pageNum); //현재페이지
		int pageSize = 5; //한 화면에 보여지는 수
		int startRow = (currentPage-1)*pageSize+1; //시작 번호
		int endRow = currentPage*pageSize; //끝 번호
		
		int count = userDAO.waitCount(); //회원의 수 (학생/교사 따로)
		
		int totPage = (count/pageSize)+(count%pageSize==0?0:1); //총 페이지수
		int pageBlock = 3; //페이지블록 [1 2 3] [4 5 6]
		int startPage = ((currentPage-1)/pageBlock)*pageBlock+1; //페이지블록에서 시작페이지 
		int endPage = startPage+pageBlock-1; //페이지블록에서 끝페이지
		if(endPage>totPage) endPage = totPage; //마지막 페이지블록 처리
		
		PageUtil pu = new PageUtil();
		pu.setCurrentPage(currentPage);
		pu.setEndPage(endPage);
		pu.setPageBlock(pageBlock);
		pu.setStartPage(startPage);
		pu.setTotPage(totPage);

		int rowNo = count-((currentPage-1)*pageSize);
		
		List<User> waitlist = userDAO.waitList(startRow, endRow);

		model.addAttribute("rowNo", rowNo);
		model.addAttribute("pu", pu);
		model.addAttribute("count", count);
		model.addAttribute("list", waitlist);
		
		return "user/tch_waitList";
	}
	
	//교사 승인 -> dao를 통해 role이 2로 업데이트되고, 해당회원의 이메일로 메일 발송
	@RequestMapping(value="tchProve.do", method = RequestMethod.POST)
	@ResponseBody
	public int tchProve(@RequestParam(value="jStr") String jStr) throws AddressException, MessagingException{
		int result = -1;
		JSONArray jsonArr = new JSONArray(jStr);
		List<String> emailList = new ArrayList<>();
		for(int i=0; i<jsonArr.length(); i++){
			JSONObject jsonObject = jsonArr.getJSONObject(i);
			String userno = (String) jsonObject.getString("userno");
			String email = (String) jsonObject.getString("email");
			emailList.add(email);
			result = userDAO.tchProve(userno);
		}
		
		//이메일로 메일 발송
		SendEmail.sendEmail(emailList);
		
		return result;
	}
}
