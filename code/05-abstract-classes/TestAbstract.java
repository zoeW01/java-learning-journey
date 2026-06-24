public class TestAbstract {
    public static void main(String[] args) {
        Animal dog = new Dog("旺财");
        Animal cat = new Cat("咪咪");
        
        dog.makeSound();
        cat.makeSound();
    }
}
