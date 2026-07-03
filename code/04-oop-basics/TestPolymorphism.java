public class TestPolymorphism {
    public static void main(String[] args) {
        // 父类引用指向子类对象
        Person p1 = new Student("Zoe", 22, 92.5);
        Person p2 = new Teacher("李老师", 35, "Java编程");
        
        // 调用 introduce()，实际运行的是子类重写后的版本
        p1.introduce();    // 预期输出：我叫Zoe，今年22岁
        p2.introduce();    // 预期输出：我是老师李老师，教Java编程
        
        // 验证类型
        System.out.println("p1是Person类型吗？" + (p1 instanceof Person));   // true
        System.out.println("p1是Student类型吗？" + (p1 instanceof Student)); // true
        
        // 多态的限制：父类引用只能调用父类里定义的方法
        // p1.showScore();  // 这行会编译报错！因为 Person 类没有 showScore 方法
        
        // 如果确实需要调用子类特有方法，需要强制转换
        if (p1 instanceof Student) {
            Student s = (Student) p1;
            s.showScore();    // 这样才能调用 Student 专属方法
        }
    }
}
