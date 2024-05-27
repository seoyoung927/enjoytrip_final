package com.ssafy.enjoytrip.product.model.dto;


public class ProductDto {
	private int pid;
	private String ownerId;
	private String title;
	private String intro;
	private int price;
	private int people;
	private int imageId;
	private String imagePath;
	
	
	public ProductDto() {
		
	}
	
	
	public ProductDto(int pid, String ownerId, String title, String intro, int price, int people, int imageId,
			String imagePath) {
		super();
		this.pid = pid;
		this.ownerId = ownerId;
		this.title = title;
		this.intro = intro;
		this.price = price;
		this.people = people;
		this.imageId = imageId;
		this.imagePath = imagePath;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPeople() {
		return people;
	}
	public void setPeople(int people) {
		this.people = people;
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
	
	
	
	
}
