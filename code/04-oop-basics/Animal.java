// abstract class 表示这个类不能被直接 new
public abstract class Animal {
    private String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    // 抽象方法——没有方法体，分号直接结束
    // 子类必须重写这个方法，否则子类也必须是抽象类
    public abstract void makeSound();
}
