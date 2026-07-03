public class Teacher extends Person {
    private String subject;
    
    public Teacher(String name, int age, String subject) {
        super(name, age);
        this.subject = subject;
    }
    
    // 重写父类的 introduce 方法
    @Override
    public void introduce() {
        System.out.println("我是老师" + getName() + "，教" + subject);
    }
    
    public String getSubject() {
        return subject;
    }
}
