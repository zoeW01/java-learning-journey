public class TestInterface {
    public static void main(String[] args) {
        FlyingDog superDog = new FlyingDog("超人狗");
        
        superDog.makeSound();
        superDog.fly();
        
        // 接口也可以作为引用类型
        Flyable flyer = new FlyingDog("小飞狗");
        flyer.fly();      // 可以调用 Flyable 定义的方法
        // flyer.makeSound();  // 编译错误！Flyable 接口没有 makeSound 方法
    }
}
