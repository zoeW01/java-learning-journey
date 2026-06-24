public class Student extends Person {
    private double score;
    private static int count = 0;
    
    public Student(String name, int age, double score) {
        super(name, age);      // 调用父类的构造方法
        this.score = score;
        count++;
    }
    
    public double getScore() {
        return score;
    }
    
    public void setScore(double score) {
        if (score >= 0 && score <= 100) {
            this.score = score;
        } else {
            System.out.println("成绩必须在0-100之间");
        }
    }
    
    public void showScore() {
        System.out.println(getName() + "的成绩是" + score + "分");
    }
    
    public static int getCount() {
        return count;
    }
}
// 第四课核心代码
