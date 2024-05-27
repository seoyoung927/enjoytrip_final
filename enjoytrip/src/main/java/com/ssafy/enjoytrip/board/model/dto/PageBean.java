package com.ssafy.enjoytrip.board.model.dto;

import java.io.Serializable;

public class PageBean implements Serializable{
	/**검색 단어*/
	private String word;
	/**현재 페이지 번호*/
	private int pageNo;
	/**한 페이지에 보여주 content 개수*/
	private int interval = 10;
	private int total;
	/**페이지 시작 번호*/
	private int start=0;
	public PageBean() {	}
	public PageBean(String word, int pageNo) {
		setWord(word);
		setPageNo(pageNo);
	}
	public PageBean(String word, String pageNo) {
		setWord(word);
		setPageNo(pageNo);
	}
	private void setPageNo(String pageNo) {
		try {
			this.pageNo = Integer.parseInt(pageNo);
		} catch (Exception e) {
			this.pageNo = 1;
		}
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getInterval() {
		return interval;
	}
	
	public int getTotal() {
		total = total%interval==0? total/interval : total/interval+1; 
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public int getStart() {
		if(pageNo>1) {
			return start = (pageNo-1)*interval;
		}else {
			return 0;
		}
	}
	public void setStart(int start) {
		this.start = start;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageBean [key=").append(", word=").append(word).append(", pagelink=")
				.append(", pageNo=").append(pageNo).append(", interval=").append(interval)
				.append(", start=").append(start).append("]");
		return builder.toString();
	}
}
