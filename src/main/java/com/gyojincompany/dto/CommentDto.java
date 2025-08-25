package com.gyojincompany.dto;

public class CommentDto {
	
	private int cnum; //댓글의 기본키(1씩 자동 증가)
	private int bnum; //원글의 기본키
	private String memberid; //댓글 쓴 아이디
	private String comment; //댓글 내용
	private String cdate; //댓글 쓴 일시
	
	public CommentDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentDto(int cnum, int bnum, String memberid, String comment, String cdate) {
		super();
		this.cnum = cnum;
		this.bnum = bnum;
		this.memberid = memberid;
		this.comment = comment;
		this.cdate = cdate;
	}
	public int getCnum() {
		return cnum;
	}
	public void setCnum(int cnum) {
		this.cnum = cnum;
	}
	public int getBnum() {
		return bnum;
	}
	public void setBnum(int bnum) {
		this.bnum = bnum;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	
	
	
	
}
