package client;

public class FoodDTO {
	private String name;
	private int price;
	private int stock;
	private int total;
	private String today;
	
	public FoodDTO() {
		super();
	}
	
public FoodDTO(String name, int price, int stock) {
		super();
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.total = total;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
}