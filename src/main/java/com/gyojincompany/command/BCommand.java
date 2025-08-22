package com.gyojincompany.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface BCommand {
	
	public void excute(HttpServletRequest request, HttpServletResponse response);
	//추상 메서드

}
