// 继承 Animal，同时实现 Flyable 接口
public class FlyingDog extends Animal implements Flyable {
    public FlyingDog(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println(getName() + "说：汪汪");
    }
    
    @Override
    public void fly() {
        System.out.println(getName() + "飞起来了！");
    }
}
