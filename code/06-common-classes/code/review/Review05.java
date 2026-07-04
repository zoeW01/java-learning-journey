public class Review05{
	public static void main(String[] args){
		Shape circle = new Circle(3.2);
		Shape rectangle = new Rectangle(2.7,8.2);

		System.out.println(circle.area());
		System.out.println(rectangle.area());
	}
}

abstract class Shape{
	public abstract double area();
}

class Circle extends Shape{
	private double radius;

	public Circle(double radius){
		this.radius = radius;
	}

	public double area(){
		return radius * radius * 3.14;
	}

	public double getRadius(){
		return radius;
	}
}
class Rectangle extends Shape{
	private double width;
	private double height;

	public Rectangle(double width,double height){
		this.width = width;
		this.height = height;
	}

	public double area(){
		return width * height;
	}

	public double getWidth(){
		return width;
	}

	public double getHeight(){
		return height;
	}
}


