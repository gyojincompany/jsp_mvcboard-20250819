package com.gyojincompany.dto;

public class BoardDto {
	
	private int bno; //새로운 글 게시판 번호
	private int bnum; //게시판 글 번호
	private String btitle;
	private String bcontent;
	private String memberid; //글쓴이 아이디
	private int bhit; //조회수
	private String bdate; //글 등록일
	
	private MemberDto memberDto; //회원정보 클래스로 선언한 객체를 멤버 변수 영입
	
	public BoardDto() {
		super();
		// TODO Auto-generated constructor stub
	}	

	public BoardDto(int bno, int bnum, String btitle, String bcontent, String memberid, int bhit, String bdate,
			MemberDto memberDto) {
		super();
		this.bno = bno;
		this.bnum = bnum;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.memberid = memberid;
		this.bhit = bhit;
		this.bdate = bdate;
		this.memberDto = memberDto;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public int getBnum() {
		return bnum;
	}

	public void setBnum(int bnum) {
		this.bnum = bnum;
	}

	public String getBtitle() {
		return btitle;
	}

	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}

	public String getBcontent() {
		return bcontent;
	}

	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public int getBhit() {
		return bhit;
	}

	public void setBhit(int bhit) {
		this.bhit = bhit;
	}

	public String getBdate() {
		return bdate;
	}

	public void setBdate(String bdate) {
		this.bdate = bdate;
	}

	public MemberDto getMemberDto() {
		return memberDto;
	}

	public void setMemberDto(MemberDto memberDto) {
		this.memberDto = memberDto;
	}

	

}
