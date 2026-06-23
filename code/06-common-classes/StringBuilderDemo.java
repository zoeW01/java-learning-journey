public class StringBuilderDemo {
    public static void main(String[] args) {
        // String 拼接（低效）—— 每次循环创建新 String
        long start = System.currentTimeMillis();
        String s = "";
        for (int i = 0; i < 100000; i++) {
            s += "a";
        }
        long end = System.currentTimeMillis();
        System.out.println("String 拼接耗时: " + (end - start) + "ms");
        
        // StringBuilder 拼接（高效）—— 始终操作同一个对象
        start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            sb.append("a");
        }
        end = System.currentTimeMillis();
        System.out.println("StringBuilder 耗时: " + (end - start) + "ms");
        
        // 常用方法
        sb.setLength(0);          // 清空
        sb.append("Hello");
        sb.append(" ");
        sb.append("World");
        System.out.println("结果: " + sb.toString());
        
        sb.insert(5, " Java");    // 在索引5处插入
        System.out.println("插入: " + sb);
        
        sb.reverse();             // 反转
        System.out.println("反转: " + sb);
    }
}
