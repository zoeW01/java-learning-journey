public class TestStudent {
    public static void main(String[] args) {
        Student s1 = new Student("Zoe", 22, 92.5);
        Student s2 = new Student("Tom", 25, 78.0);
        
        s1.introduce();    // 继承自 Person 的方法
        s2.introduce();
        
        s1.showScore();    // Student 自己的方法
        s2.showScore();
        
        System.out.println("学生总数: " + Student.getCount());
    }
}
// 第四课核心代码
