package com.mvnschool.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvnschool.repository.BoardDAO;
import com.mvnschool.repository.ReplyDAO;
import com.mvnschool.repository.UserDAO;
import com.mvnschool.vo.Reply;
import com.mvnschool.vo.User;

@Controller
public class ReplyController {

	@Autowired                                 
	private ReplyDAO replyDAO;
	
	@Autowired
	private BoardDAO boardDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	//댓글 입력
	@RequestMapping(value="/replyInsert.do", method = RequestMethod.POST)
	@ResponseBody
	public void replyInsert(@ModelAttribute Reply reply, HttpServletResponse response) throws IOException{
		String userno = reply.getUserno();
		User user = userDAO.userView(userno);
		String name = user.getName();
		reply.setName(name);
		
		replyDAO.insert(reply);
		String bnum = reply.getBnum();
		response.sendRedirect("replyList.do?bnum="+bnum);
	}
	
	//댓글 목록
	@RequestMapping(value="/replyList.do", method = RequestMethod.GET)
	@ResponseBody
	public String replyList(String bnum) throws UnsupportedEncodingException{
		List<Reply> replyList = new ArrayList<Reply>(); 
		replyList = replyDAO.replyList(bnum);
		JSONObject mainObj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		for(Reply reply : replyList){
			JSONObject obj = new JSONObject();
			obj.put("bnum", reply.getBnum());
			obj.put("rnum", reply.getRnum());
			obj.put("msg", reply.getMsg());
			obj.put("userno", reply.getUserno());
			obj.put("name", reply.getName());
			obj.put("regdate", reply.getRegdate());
			jarr.put(obj);
		}
		mainObj.put("jarr", jarr);
		return mainObj.toString();
	}
	
	//댓글 삭제
	@RequestMapping(value="/replyDelete.do", method = RequestMethod.POST)
	@ResponseBody
	public void replyDelete(@ModelAttribute Reply reply, HttpServletResponse response) throws IOException{
		replyDAO.replyDelete(reply.getRnum());
		String bnum = reply.getBnum();
		response.sendRedirect("replyList.do?bnum="+bnum);
	}

}