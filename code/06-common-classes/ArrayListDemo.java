import java.util.ArrayList;

public class ArrayListDemo {
    public static void main(String[] args) {
        // 创建 ArrayList，尖括号里指定存放的类型
        ArrayList<String> list = new ArrayList<>();
        
        // 1. 添加元素
        list.add("Zoe");
        list.add("Tom");
        list.add("Jerry");
        System.out.println("列表: " + list);
        
        // 2. 获取元素
        System.out.println("第0个: " + list.get(0));
        
        // 3. 大小
        System.out.println("大小: " + list.size());
        
        // 4. 修改元素
        list.set(1, "Tommy");
        System.out.println("修改后: " + list);
        
        // 5. 删除元素
        list.remove(0);
        System.out.println("删除后: " + list);
        
        // 6. 判断是否包含
        System.out.println("包含Jerry? " + list.contains("Jerry"));
        
        // 7. 遍历——可以用普通 for 循环
        for (int i = 0; i < list.size(); i++) {
            System.out.println("遍历: " + list.get(i));
        }
        
        // 8. 增强 for 循环（专门用来遍历集合）
        for (String name : list) {
            System.out.println("增强遍历: " + name);
        }
        
        // 9. 存储基本类型需要包装类
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(95);
        scores.add(88);
        scores.add(76);
        System.out.println("成绩列表: " + scores);
    }
}

