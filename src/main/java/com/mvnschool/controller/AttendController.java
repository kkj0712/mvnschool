package com.mvnschool.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvnschool.repository.AttendDAO;
import com.mvnschool.vo.Attend;
import com.mvnschool.vo.PageUtil;
import com.mvnschool.vo.Subject;

@Controller
public class AttendController {

	@Autowired
	private AttendDAO attendDAO;
	
	//수강편람에서 수강신청 버튼을 눌렀을 경우
	@RequestMapping(value="/attend.do", method = RequestMethod.POST)
	@ResponseBody
	public int selectSubList(@RequestParam(value="jStr") String jStr,
			@RequestParam(value="userno") String userno){
		
		int result = -1;
		JSONArray jsonArr = new JSONArray(jStr);
		for(int i=0; i<jsonArr.length(); i++){
			JSONObject jsonObject = jsonArr.getJSONObject(i);
			Attend attend = new Attend();
			attend.setStuno(userno);
			attend.setSubno((String) jsonObject.get("subno"));
			attend.setSubname((String) jsonObject.get("subname"));
			attend.setTeachername((String) jsonObject.get("teachername"));
			attend.setTeacherno((String) jsonObject.getString("teacherno"));
			result=attendDAO.insert(attend);
		}
		return result;
	}
	
	//수강신청목록 보기 => 얘도 페이징 처리를 해야하므로 다른 페이지로 보내기
	@RequestMapping(value="/myattend_pre.do", method = RequestMethod.GET)
	public String myAttendPre(){
		return "attend/myattend_pre";
	}
	
	//수강신청목록 테이블 정보가 담기는 경로
	@RequestMapping(value="/myattend.do", method = RequestMethod.GET)
	public String myAttend(Model model, String pageNum, HttpServletRequest request, HttpServletResponse response){
		//현재로그인된 userno값 가져오기 => modelAttribute로 넘겨서 jsp에서 el태그 ${}로 받기
		HttpSession session = request.getSession();
		String userno = (String) session.getAttribute("userno");
		model.addAttribute("userno", userno);
		
		if(pageNum == null) pageNum="1"; //사용자가 가려는 페이지
		int currentPage = Integer.parseInt(pageNum); //현재페이지
		int pageSize = 5; //한 화면에 보여지는 수
		int startRow = (currentPage-1)*pageSize+1; //시작 번호
		int endRow = currentPage*pageSize; //끝 번호
		
		int count = attendDAO.myAttendCount(userno); //내가 수강신청한 강의 개수
		
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
		
		model.addAttribute("rowNo", rowNo);
		model.addAttribute("pu", pu);
		model.addAttribute("count", count);
		
		List<Object> list = attendDAO.myAttendPre(startRow, endRow, userno);
		model.addAttribute("list", list);
		return "attend/myattend";
	}
	
	//수강신청목록에서 신청취소 버튼을 눌렀을 경우
	@RequestMapping(value="/attCancel.do", method = RequestMethod.POST)
	@ResponseBody
	public int attCancel(@RequestParam(value="jStr") String jStr){
		int result = -1;
		
		JSONArray jsonArr = new JSONArray(jStr);
		
		for(int i=0; i<jsonArr.length(); i++){
			JSONObject jsonObject = jsonArr.getJSONObject(i);
			String attno = jsonObject.getString("attno").toString();
			result=attendDAO.delete(attno);
		}
		return result;
	}
	
	//승인된 수강목록 보기 => 얘도 페이징 처리를 해야하므로 다른 페이지로 보내기
	@RequestMapping(value="/prvAttend_pre.do", method = RequestMethod.GET)
	public String prvAttend(){
		return "attend/prvAttend_pre";
	}
	
	//승인된 수강목록 테이블 정보가 담기는 경로
	@RequestMapping(value="/prvAttend.do", method = RequestMethod.GET)
	public String prvAttend(Model model, String pageNum, HttpServletRequest request, HttpServletResponse response){
		//현재로그인된 userno값 가져오기 => modelAttribute로 넘겨서 jsp에서 el태그 ${}로 받기
		HttpSession session = request.getSession();
		String userno = (String) session.getAttribute("userno");
		model.addAttribute("userno", userno);
		
		if(pageNum == null) pageNum="1"; //사용자가 가려는 페이지
		int currentPage = Integer.parseInt(pageNum); //현재페이지
		int pageSize = 5; //한 화면에 보여지는 수
		int startRow = (currentPage-1)*pageSize+1; //시작 번호
		int endRow = currentPage*pageSize; //끝 번호
		
		int count = attendDAO.myPrvAttendCount(userno); //승인된 수강목록 테이블의 row개수만 따로 가져와야함
		
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
		
		model.addAttribute("rowNo", rowNo);
		model.addAttribute("pu", pu);
		model.addAttribute("count", count);
		
		List<Object> list = attendDAO.prvAttend(startRow, endRow, userno);
		model.addAttribute("list", list);
		return "attend/prvAttend";
	}
	
	//교사의 수강신청 관리로 이동하는 페이지
	@RequestMapping(value="/attendCtrl_pre.do", method = RequestMethod.GET)
	public String attendCtrlPre(){
		return "attend/attendctrl_pre";
	}
	
	//교사의 수강신청 관리 테이블 정보가 담기는 경로
	@RequestMapping(value="/attendCtrl.do", method = RequestMethod.GET)
	public String tchAttendCtrl(Model model, String pageNum, HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		String userno = (String) session.getAttribute("userno");
		model.addAttribute("teacherno", userno); //현재 로그인한 교사의 userno(=teacherno) 가져오기
		
		if(pageNum == null) pageNum="1"; //사용자가 가려는 페이지
		int currentPage = Integer.parseInt(pageNum); //현재페이지
		int pageSize = 5; //한 화면에 보여지는 수
		int startRow = (currentPage-1)*pageSize+1; //시작 번호
		int endRow = currentPage*pageSize; //끝 번호
		
		int count = attendDAO.attendCtrlCount(userno); //내 강의를 수강신청한 학생들의 수
		
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
		
		model.addAttribute("rowNo", rowNo);
		model.addAttribute("pu", pu);
		model.addAttribute("count", count);
		
		List<Object> list = attendDAO.attendCtrl(startRow, endRow, userno);
		model.addAttribute("list", list);
		return "attend/attendctrl";
	}
	
	//수강신청 승인
	@RequestMapping(value="/attProve.do", method = RequestMethod.POST)
	@ResponseBody
	public int attProve(@RequestParam(value="jStr") String jStr){
		int result = -1;
		
		JSONArray jsonArr = new JSONArray(jStr);
		
		for(int i=0; i<jsonArr.length(); i++){
			JSONObject jsonObject = jsonArr.getJSONObject(i);
			String stuno = (String) jsonObject.getString("stuno");
			String subno = (String) jsonObject.getString("subno");
			result=attendDAO.attProve(stuno, subno);
		}
		return result;
	}
	
	//수강신청 반려
	@RequestMapping(value="/attReject.do", method = RequestMethod.POST)
	@ResponseBody
	public int attReject(@RequestParam(value="jStr") String jStr){
		int result = -1;
		
		JSONArray jsonArr = new JSONArray(jStr);
		
		for(int i=0; i<jsonArr.length(); i++){
			JSONObject jsonObject = jsonArr.getJSONObject(i);
			String stuno = (String) jsonObject.getString("stuno");
			String subno = (String) jsonObject.getString("subno");
			result=attendDAO.attReject(stuno, subno);
		}
		return result;
	}
}
