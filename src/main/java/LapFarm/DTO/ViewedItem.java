package LapFarm.DTO;

import java.util.List;

import LapFarm.Entity.ImageEntity;

public class ViewedItem {
	private int itemId;
	private String name;
	private List<ImageEntity> image;
	private double price;
	public ViewedItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ViewedItem(int itemId, String name, List<ImageEntity> list, double price) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.image = list;
		this.price = price;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ImageEntity> getImage() {
		return image;
	}
	public void setImage(List<ImageEntity> image) {
		this.image = image;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
