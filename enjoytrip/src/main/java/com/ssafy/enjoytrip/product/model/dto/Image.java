package com.ssafy.enjoytrip.product.model.dto;

public class Image {
	private int imageId;
	private String imagePath;
	private int pid;
	public Image() {
		
	}
	public Image(int imageId, String imagePath, int pid) {
		super();
		this.imageId = imageId;
		this.imagePath = imagePath;
		this.pid = pid;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public int getpid() {
		return pid;
	}
	public void setpid(int pid) {
		this.pid = pid;
	}
	
	
}
