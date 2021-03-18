package com.mvnschool.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvnschool.repository.AttendDAO;
import com.mvnschool.repository.SubjectDAO;
import com.mvnschool.repository.UserDAO;
import com.mvnschool.vo.Attend;
import com.mvnschool.vo.PageUtil;
import com.mvnschool.vo.Subject;
import com.mvnschool.vo.User;

@Controller
public class SubjectController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private SubjectDAO subjectDAO;
	
	@Autowired
	private AttendDAO attendDAO;
	
	//수강편람 pre페이지로 보내기
	@RequestMapping(value="/submenual_pre.do", method = RequestMethod.GET)
	public String subMenual_Pre(){
		return "subject/submenual_pre";
	}
	
	//수강편람 테이블 정보가 담기는 경로
	@RequestMapping(value="/submenual.do", method = RequestMethod.GET)
	public String subMenual(Model model, String pageNum, HttpServletRequest request){
		if(pageNum == null) pageNum="1"; //사용자가 가려는 페이지
		int currentPage = Integer.parseInt(pageNum); //현재페이지
		int pageSize = 5; //한 화면에 보여지는 수
		int startRow = (currentPage-1)*pageSize+1; //시작 번호
		int endRow = currentPage*pageSize; //끝 번호
		int count = subjectDAO.subCount(); //과목 개수
		
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
		
		List<Subject> sublist = subjectDAO.subMenual(startRow, endRow);

		model.addAttribute("rowNo", rowNo);
		model.addAttribute("pu", pu);
		model.addAttribute("count", count);
		model.addAttribute("sublist", sublist);
		
		//현재로그인된 userno값 가져오기 => modelAttribute로 넘겨서 jsp에서 el태그 ${}로 받기
		HttpSession session = request.getSession();
		String userno = (String) session.getAttribute("userno");
		model.addAttribute("userno", userno);
		
		//내가 신청한 과목인지 판별하기 위해 이중for문으로 돌리려고
		List<Attend> mysublist = attendDAO.mySublist(userno);
		model.addAttribute("mysublist", mysublist);
		
		return "subject/submenual";
	}
	
	//수강편람 상세보기
	//subno로 select해야하는데..subno를 어떻게 들고오지? "window.open('subview.do?subno=${sublist.subno}.. "로 들고온다. 
	@RequestMapping(value="/subview.do", method = RequestMethod.GET)
	public String subView(HttpServletRequest request){
		String subno = request.getParameter("subno");
		Subject subject = subjectDAO.subView(subno);
		request.setAttribute("subject", subject);
		return "subject/subview";
	}
	
	//강의입력 페이지로 이동 (교사의  userno와 name을 들고간다)
	@RequestMapping(value="/subInsert.do", method = RequestMethod.GET)
	public String subInsert(HttpServletRequest request){
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		User user = userDAO.mypageView(userid);
		
		String name = user.getName();
		String userno = user.getUserno();
		request.setAttribute("name", name);
		request.setAttribute("userno", userno);
		
		return "subject/subInsert";
	}
	
	//실제 강의입력 진행
	@RequestMapping(value="/subInsertPro.do", method = RequestMethod.POST)
	@ResponseBody
	public int subInsertPro(@ModelAttribute Subject subject){
		int result = subjectDAO.insertSub(subject);
		return result;
	}
	
	//해당 교사의 강의목록 이동
	@RequestMapping(value="/tch_subList_pre.do", method = RequestMethod.GET)
	public String subListPre(){
		return "subject/tch_subList_pre";
	}
	
	//실제 교사의 강의목록이 담길 페이지
	@RequestMapping(value="/tch_subList.do", method = RequestMethod.GET)
	public String subList(Model model, String pageNum, HttpServletRequest request){
		
		//현재로그인된 교사의 teacherno(userno)값 가져오기
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		User user = userDAO.mypageView(userid);
		String teacherno = user.getUserno();
		
		if(pageNum == null) pageNum="1"; //사용자가 가려는 페이지
		int currentPage = Integer.parseInt(pageNum); //현재페이지
		int pageSize = 5; //한 화면에 보여지는 수
		int startRow = (currentPage-1)*pageSize+1; //시작 번호
		int endRow = currentPage*pageSize; //끝 번호
		
		int count = subjectDAO.tchSubCount(teacherno); //교사의 강의 개수
		
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
		
		List<Subject> tch_sublist = subjectDAO.tchSublist(startRow, endRow, teacherno);

		model.addAttribute("rowNo", rowNo);
		model.addAttribute("pu", pu);
		model.addAttribute("count", count);
		model.addAttribute("tch_sublist", tch_sublist);
		
		return "subject/tch_subList";
	}
	
	//교사의 강의목록에서 상세페이지로 이동 (팝업창 아님)
	@RequestMapping(value="/tch_subview.do", method = RequestMethod.GET)
	public String tchSubView(HttpServletRequest request){
		String subno = request.getParameter("subno");
		Subject subject = subjectDAO.subView(subno);
		request.setAttribute("subject", subject);
		return "subject/tch_subview";
	}
	
	//강의 수정하기 폼으로 이동
	@RequestMapping(value="/updateForm.do", method = RequestMethod.GET)
	public String updateForm(HttpServletRequest request){
		String subno = request.getParameter("subno");
		Subject subject = subjectDAO.subView(subno);
		request.setAttribute("subject", subject);
		return "subject/tch_subupdate";
	}
	
	//강의 실제 수정하기 이벤트
	@RequestMapping(value="/updatePro.do", method = RequestMethod.POST)
	@ResponseBody
	public int updatePro(@ModelAttribute Subject subject){
		int result = subjectDAO.subUpdate(subject);
		return result; 
	}
	
	//강의 삭제하기
	@RequestMapping(value="/subDelete.do", method = RequestMethod.POST)
	@ResponseBody
	public int subDelete(String subno){
		int result = subjectDAO.subDelete(subno);
		return result;
	}
	
	//수강생목록 확인
	@RequestMapping(value="/tch_chkStu_pre.do", method = RequestMethod.GET)
	public String checkStudentPre(HttpServletRequest request, Model model){
		String subno = request.getParameter("subno");
		model.addAttribute("subno", subno); //subno를 보내기
		return "subject/tch_chkStudent_pre";
	}
	
	//수강생목록이 담길 페이지
	@RequestMapping(value="/tch_chkStu.do", method = RequestMethod.GET)
	public String checkStudent(Model model, String pageNum, HttpServletRequest request, String subno){
		if(pageNum == null) pageNum="1"; //사용자가 가려는 페이지
		int currentPage = Integer.parseInt(pageNum); //현재페이지
		int pageSize = 5; //한 화면에 보여지는 수
		int startRow = (currentPage-1)*pageSize+1; //시작 번호
		int endRow = currentPage*pageSize; //끝 번호
		
		int count = userDAO.chkStuCount(subno); //그 강의를 듣는 학생의 수
		
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
		
		List<User> stuList = userDAO.chkStu(startRow, endRow, subno); 

		Subject subject = subjectDAO.subView(subno);
		String subname = subject.getSubname();
		
		model.addAttribute("rowNo", rowNo);
		model.addAttribute("pu", pu);
		model.addAttribute("count", count);
		model.addAttribute("list", stuList);
		model.addAttribute("subname", subname);
		
		return "subject/tch_chkStudent";
	}
	
}
