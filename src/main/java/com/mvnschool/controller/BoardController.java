package com.mvnschool.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mvnschool.repository.BoardDAO;
import com.mvnschool.repository.UserDAO;
import com.mvnschool.vo.Board;
import com.mvnschool.vo.PageUtil;
import com.mvnschool.vo.User;

@Controller
public class BoardController {
	
	@Autowired                                 
	private BoardDAO boardDAO;
	
	@Autowired                                 
	private UserDAO userDAO;
	
	//게시판으로 이동
	@RequestMapping(value="/board_pre.do", method = RequestMethod.GET)
	public String boardPre(){
		return "board/boardList_pre";
	}
	
	//게시판 보기
	@RequestMapping(value="/boardList.do", method = RequestMethod.GET)
	public String board(Model model, String pageNum, String field, String word){
		//페이징
		if(pageNum == null) pageNum="1";
		int currentPage = Integer.parseInt(pageNum);//현재페이지
		int pageSize = 5;//한 화면에 보여지는 수
		int startRow = (currentPage-1)*pageSize+1;
		int endRow = currentPage*pageSize; //끝 번호

		//게시글 개수
		if(field == null) field = "";
		if(word == null) word = "";
		int count = boardDAO.getCount(field, word);
		
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
		pu.setField(field);
		pu.setWord(word);
		
		List<Board> boardlist = null;
		//검색아닐때
		if(word.equals("")){
			boardlist = boardDAO.boardList(startRow, endRow);
		}else{
			boardlist = boardDAO.boardList(startRow, endRow, field, word);
		}
		
		int rowNo = count-((currentPage-1)*pageSize);
		
		model.addAttribute("rowNo", rowNo);
		model.addAttribute("pu", pu);
		model.addAttribute("list", boardlist);
		return "board/boardList";
	}
	
	//게시글 폼으로 이동
	@RequestMapping(value="/boardForm.do", method = RequestMethod.GET)
	public String boardForm(){
		return "board/boardInsert";
	}

	//게시글 업로드
	@RequestMapping(value="/boardInsert.do", method = RequestMethod.POST)
	@ResponseBody
	public int boardInsert(HttpServletRequest request, MultipartHttpServletRequest multiRequest,
			@ModelAttribute Board board) throws IllegalStateException, IOException{
		//글쓴이 정보 생성
		HttpSession session = request.getSession();
		String userno = (String)session.getAttribute("userno");
		User user = userDAO.userView(userno);
		String name = user.getName();

		//board에 글쓴이 정보를 세팅
		board.setUserno(userno);
		board.setName(name);
		
		MultipartFile mf = null;
		//파일을 업로드 하지 않는다면 fileupload 컬럼에는 그냥 null이 입력됨
		if(multiRequest.getFile("file").getSize() != 0){
			mf = multiRequest.getFile("file"); //input file로 넘어온 값
			
			//경로 지정
			String path = "D:\\kj\\mvnschool\\webapp\\WEB-INF\\upload";
			File fileDir = new File(path);
			if(!fileDir.exists()){
				fileDir.mkdirs();
			}

			//중복방지를 위해 time 추가
			long time = System.currentTimeMillis();
			String originFileName = mf.getOriginalFilename(); //원본 파일명
			String saveFileName = String.format("%d_%s", time, originFileName); //저장 파일명
			
			//파일 생성
			mf.transferTo(new File(path, saveFileName)); 

			board.setFileupload(saveFileName);
		}
		
		int result = boardDAO.boardInsert(board);
		
		return result;
	}
	
	//게시글 보기
	@RequestMapping(value="/boardView.do", method = RequestMethod.GET)
	public String boardView(String bnum, Model model){
		Board board = boardDAO.boardView(bnum);
		//조회수 업데이트
		boardDAO.updateHit(bnum);
		model.addAttribute("board", board);
		return "board/boardView";
	}
	
	//파일 다운로드
	@RequestMapping(value="/fileDownload.do", method = RequestMethod.GET)
	@ResponseBody
	public void fileDownload(String fileName, HttpServletRequest request, HttpServletResponse response){
		String realFileName = "D:\\kj\\mvnschool\\webapp\\WEB-INF\\upload\\" + fileName;

		//파일명 지정
		response.setContentType("application/octer-stream");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
		
		try {
			OutputStream os = response.getOutputStream();
			FileInputStream fis = new FileInputStream(realFileName);
			
			int ncount = 0;
			byte [] bytes = new byte[512];
			
			while((ncount = fis.read(bytes)) != -1){
				os.write(bytes, 0, ncount);
			}
			fis.close();
			os.close();
		} catch (Exception e) {
			System.out.println("FileNotFoundException: "+e);
		}
	}
	
	//글수정 폼으로 이동
	@RequestMapping(value="/boardUpdateForm.do", method=RequestMethod.GET)
	public String boardUpdateForm(String bnum, Model model){
		Board board = boardDAO.boardView(bnum);
		model.addAttribute("board", board);
		return "board/boardUpdateForm";
	}
	
	//글 수정하기 (파일을 삭제안했으면 그대로 저장되어야함!!)
	@RequestMapping(value="/boardUpdatePro.do", method = RequestMethod.POST)
	@ResponseBody
	public int boardUpdatePro(HttpServletRequest request, MultipartHttpServletRequest multiRequest,
			@ModelAttribute Board board) throws IllegalStateException, IOException{
		MultipartFile mf = null;

		//원래 파일이 없었을 경우 새로 파일을 추가해도 mf값이 null이 나온다.
		if(multiRequest.getFile("file").getSize() != 0){
			mf = multiRequest.getFile("file"); //input file로 넘어온 값
			
			//경로 지정
			String path = "D:\\kj\\mvnschool\\webapp\\WEB-INF\\upload";
			File fileDir = new File(path);
			if(!fileDir.exists()){
				fileDir.mkdirs();
			}

			//중복방지를 위해 time 추가
			long time = System.currentTimeMillis();
			String originFileName = mf.getOriginalFilename(); //원본 파일명
			String saveFileName = String.format("%d_%s", time, originFileName); //저장 파일명
			
			//파일 생성
			mf.transferTo(new File(path, saveFileName)); 
			
			//새로 정한 파일로 덮어쓰기가 됨
			board.setFileupload(saveFileName);
		}else{
			
		}
		System.out.println("mf: "+mf);
		System.out.println("fileupload: "+board.getFileupload());
		int result = boardDAO.boardUpdatePro(board);
		return result;
	}

	//파일 삭제하기 (글 수정시 파일만 삭제할 수도 있음. db에서 fileupload의 내용도 null로 업뎃해야함)
	@RequestMapping(value="/fileDelete.do", method = RequestMethod.POST)
	@ResponseBody
	public int fileDelete(@ModelAttribute Board board){
		String fileName = board.getFileupload();
		String realFileName = "D:\\kj\\mvnschool\\webapp\\WEB-INF\\upload\\" + fileName;
		File file = new File (realFileName);
		file.delete();
		int result = boardDAO.fileDelUpdate(fileName);
		return result;
	}
	
	//글 삭제하기 (글 삭제시 파일도 같이 삭제됨)
	@RequestMapping(value="/boardDelete.do", method = RequestMethod.POST)
	@ResponseBody
	public int boardDelete(String bnum){
		Board board = boardDAO.boardView(bnum);
		String fileName = board.getFileupload();
		int result = 0;
		if(fileName != ""){
			String realFileName = "D:\\kj\\mvnschool\\webapp\\WEB-INF\\upload\\" + fileName;
			File file = new File (realFileName);
			file.delete();
			result = boardDAO.fileDelUpdate(fileName);
		}
		result = boardDAO.boardDelete(bnum);
		return result;
	}
}
