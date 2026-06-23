public class StringDemo {
    public static void main(String[] args) {
        String str = "  Hello Java World  ";
        
        // 1. 长度
        System.out.println("长度: " + str.length());
        
        // 2. 去掉首尾空格
        System.out.println("去空格: '" + str.trim() + "'");
        
        // 3. 转大写 / 转小写
        System.out.println("大写: " + str.toUpperCase());
        System.out.println("小写: " + str.toLowerCase());
        
        // 4. 判断是否包含某字符串
        System.out.println("包含Java? " + str.contains("Java"));
        
        // 5. 判断开头/结尾
        System.out.println("以Hello开头? " + str.trim().startsWith("Hello"));
        System.out.println("以World结尾? " + str.trim().endsWith("World"));
        
        // 6. 替换
        System.out.println("替换: " + str.replace("Java", "Python"));
        
        // 7. 提取子串（从索引0开始）
        System.out.println("子串(0,5): " + str.trim().substring(0, 5));
        
        // 8. 按分隔符切分
        String csv = "apple,banana,orange";
        String[] fruits = csv.split(",");
        for (String fruit : fruits) {
            System.out.println("水果: " + fruit);
        }
        
        // 9. 字符索引
        System.out.println("第0个字符: " + str.trim().charAt(0));
        
        // 10. 字符串比较——必须用 equals，不能用 ==
        String a = "hello";
        String b = new String("hello");
        System.out.println("== 比较: " + (a == b));           // false，比的是地址
        System.out.println("equals 比较: " + a.equals(b));     // true，比的是内容
    }
}

