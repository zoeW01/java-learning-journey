import java.util.ArrayList;
public class Review04{
	public static void main(String[] args){
		Book b1 = new Book("老人与海","海明威",30);
		Book b2 = new Book("初中物理","教研组",50);
		Book b3 = new Book("马哲","马克思",99999);
		ArrayList<Book> bookList = new ArrayList<Book>();
		bookList.add(b1);
		bookList.add(b2);
		bookList.add(b3);

		for(Book b : bookList){
			b.display();
		}
	}
}

class Book{
	private String title;
	private String author;
	private double price;

	public Book(String title,String author,double price){
		this.title = title;
		this.author = author;
		this.price = price;
	}

	public String getTitle() {return title;}
	public String getAuthor() {return author;}
	public double getPrice() {return price;}

	public void display(){
		System.out.println("书名："+title+"，作者："+author+"，价格："+price+"。");
	}
}
