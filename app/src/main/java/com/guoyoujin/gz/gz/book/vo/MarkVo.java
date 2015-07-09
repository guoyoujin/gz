package com.guoyoujin.gz.gz.book.vo;

/**
 * ��¼��ǩ�ĸ������
 * 
 * @author
 * 
 */
public class MarkVo {
	private String text;
	private int page;
	private int begin; // ��ǩ��¼ҳ��Ľ����λ��
	private int count;
	private String time;
	private String bookPath;

	public MarkVo(String text, int page, int count, int begin, String time,
			String bookPath) {
		super();
		this.text = text;
		this.page = page;
		this.count = count;
		this.time = time;
		this.bookPath = bookPath;
		this.begin = begin;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getBookPath() {
		return bookPath;
	}

	public void setBookPath(String bookPath) {
		this.bookPath = bookPath;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

}
