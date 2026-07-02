# String

`String` 是你从第一课就在用的类型，但它背后有一个必须知道的事实：**String 是不可变的**——每次对字符串做操作，都是创建了一个新字符串，原字符串不变。

---

**常用方法：**

```java
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
```

## 常用方法清单

| 方法                       | 作用                 |
| ------------------------ | ------------------ |
| `length()`               | 获取长度               |
| `trim()`                 | 去掉首尾空格             |
| `toUpperCase()`          | 转大写                |
| `toLowerCase()`          | 转小写                |
| `contains(s)`            | 是否包含子串             |
| `startsWith(String s)`   | 以某串开头              |
| `endsWith(String s)`     | 以某串结尾              |
| `replace(char a,char b)` | 用b替换所有a            |
| `substring(int i)`       | 截取i到结尾             |
| `substring(int i,int j)` | 截取 [i, j-1]        |
| `split(分隔符)`             | 按分隔符切开，返回切开后的字符串数组 |
| `charAt(i)`              | 获取索引 i 的字符         |
| `toCharArray()`          | 字符串转换为字符数组         |

### equals vs ==（面试必考）

- `==` 比较的是两个引用是否指向同一个对象（比较内存地址）
- `equals` 比较的是字符串内容是否相同
- **结论：字符串比较永远用 `equals`，不要用 `==

---

# StringBuilder

StringBuilder —— 可变的字符串

因为 `String` 不可变，每次拼接都创建新对象，效率极低。`StringBuilder` 就是为了解决这个问题——它在原有字符序列上直接修改，不产生中间垃圾对象。


```java
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
```

## 常用方法

| 方法                            | 作用                          |
| ----------------------------- | --------------------------- |
| `append(s)`                   | 追加字符串                       |
| `delete(开始的下标，结束的下标)`         | 删除从`start`(包含)到`end`(不包含)   |
| `replace(开始的下标，结束的下标，要替换的内容)` | 换从`start`(包含)到`end`(不包含)的部分 |
| `insert(插入的下标，插入的内容)`         | 在索引 i处插入字符串                 |
| `reverse()`                   | 反转内容                        |
| `toString()`                  | 转回普通 String                 |
| `setLength(0)`                | 清空                          |

---
