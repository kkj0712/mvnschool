package com.mvnschool.vo;

public class Subject {
	private String subno;
	private String subname;
	private String teachername;
	private int cnt;
	private String submemo;
	private String teacherno; //선생고유키
	
	//join할때 사용되는 객체
	private int currentCnt;
	
	public String getSubno() {
		return subno;
	}
	public void setSubno(String subno) {
		this.subno = subno;
	}
	public String getSubname() {
		return subname;
	}
	public void setSubname(String subname) {
		this.subname = subname;
	}
	public String getTeachername() {
		return teachername;
	}
	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getSubmemo() {
		return submemo;
	}
	public void setSubmemo(String submemo) {
		this.submemo = submemo;
	}
	public int getCurrentCnt() {
		return currentCnt;
	}
	public void setCurrentCnt(int currentCnt) {
		this.currentCnt = currentCnt;
	}
	public String getTeacherno() {
		return teacherno;
	}
	public void setTeacherno(String teacherno) {
		this.teacherno = teacherno;
	}


}