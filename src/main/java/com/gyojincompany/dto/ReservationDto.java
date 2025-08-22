package com.gyojincompany.dto;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class ReservationDto {
	
	private int rnum; //예약번호(기본키)->AI 자동증가
	private String memberid; //예약한 사람의 아이디(로그인 되어 있는 아이디)->회원 정보 테이블과 연동할 외래키
	private String rname;
	private String rphone;
	private Date rdate; //예약날짜
	private Time rtime; //예약시간
	private Timestamp creatdate; //예약을 등록한 날짜시간
	

}
