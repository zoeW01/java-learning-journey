`Arrays` 类常用方法

必须导入 `import java.util.Arrays;`

| 方法                     | 作用                     | 返回值                         |
| ---------------------- | ---------------------- | --------------------------- |
| `sort(数组)`             | 原地升序排序                 | `void`                      |
| `binarySearch(数组, 键值)` | 二分查找，**前提数组已排序**       | `int`（找到则返回下标，未找到返回负数插入点-1） |
| `fill(数组, 值)`          | 用指定值填满数组               | `void`                      |
| `equals(数组1, 数组2)`     | 比较元素内容是否相同             | `boolean`                   |
| `toString(数组)`         | 返回格式如 `[1, 2, 3]` 的字符串 | `String`                    |

**核心易错**：
- `binarySearch` 如果数组未排序，结果是**不确定的**，可能返回负数，不是正确下标。选择题会给你未排序数组让你判断binarySearch的输出，答案是"不确定"或直接给一个负数。
- `sort` 是 `void`，不能写成 `arr = Arrays.sort(arr);`。
- `equals` 比较的是**元素内容**，`arr1.equals(arr2)` 比较的是引用地址（两个数组对象不同返回false）。选择题经常让你区分 `Arrays.equals(arr1, arr2)` 和 `arr1.equals(arr2)` 的结果。
