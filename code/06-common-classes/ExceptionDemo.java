public class ExceptionDemo {
    public static void main(String[] args) {
        // 示例1：数组越界
        int[] scores = {95, 88, 76};
        try {
            System.out.println(scores[5]);   // 越界了
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("数组越界了！数组长度是 " + scores.length);
        }
        
        // 示例2：除以零
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("不能除以零！");
        }
        
        // 示例3：多种异常分别处理
        try {
            String name = null;
            System.out.println(name.length());   // 空指针
        } catch (NullPointerException e) {
            System.out.println("对象是 null，不能调用方法");
        } catch (Exception e) {
            System.out.println("其他异常");
        }
        
        // 示例4：finally —— 无论是否异常，都会执行
        try {
            System.out.println("开始执行");
            int x = 10 / 2;
            System.out.println("结果: " + x);
        } catch (Exception e) {
            System.out.println("出错");
        } finally {
            System.out.println("finally 块一定执行");
        }
        
        System.out.println("程序继续运行，没有崩溃");
    }
}
