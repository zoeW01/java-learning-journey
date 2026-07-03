import java.util.Objects;
import java.util.HashSet;

public class ObjectDemo{
	public static void main(String[] args){
		Book b1 = new Book("123","zhangsandeshu",123);
		Book b2 = new Book("123","lisideshu",159);
		HashSet<Book> hs = new HashSet<>();
		hs.add(b1);
		hs.add(b2);
		System.out.println(hs.size());
		System.out.println(b1.equals(b2));
	}
}

class Book{
	private String isbn;
	private String title;
	private int pages;

	public Book(String isbn,String title,int pages){
		this.isbn = isbn;
		this.title = title;
		this.pages = pages;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null || obj.getClass() != this.getClass()) return false;
		return Objects.equals(((Book)obj).isbn,isbn);
	}

	public String getISBN(){
		return isbn;
	}

	@Override
	public int hashCode(){
		return Objects.hash(isbn);
	}

	@Override
	public String toString(){
		return "Book{isbn='"+isbn+"',title='"+title+"',pages="+pages+"}";
	}
}
