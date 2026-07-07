# Math（`java.lang` ）

Math 类在 `java.lang` 包，不需要 import。
所有方法都是静态的，类用 `final` 修饰，构造器私有。

## 常用方法

|               方法签名               |                                                说明                                                 |
| :------------------------------: | :-----------------------------------------------------------------------------------------------: |
|         `int abs(int a)`         |                                  返回绝对值（重载支持 `long/float/double`）                                  |
|      `int absExact(int a)`       | 返回绝对值，如果 a 是 `Integer.MIN_VALUE` 则抛出 `ArithmeticException`（因为 `Integer.MIN_VALUE` 的绝对值无法用 int 表示） |
|     `int max(int a, int b)`      |                                  返回较大值（重载支持 `long/float/double`）                                  |
|     `int min(int a, int b)`      |                                 返回较小值（重载支持 ``long/float/double``）                                 |
|      `long round(double a)`      |                                        四舍五入，返回最接近的 `long`                                         |
|       `int round(float a)`       |                                         四舍五入，返回最接近的 `int`                                         |
|     `double ceil(double a)`      |                                        向上取整，返回 ≥ `a` 的最小整数                                        |
|     `double floor(double a)`     |                                        向下取整，返回 ≤ `a` 的最大整数                                        |
|     `double rint(double a)`      |                                         返回最接近的整数，若在中间则取偶数                                         |
| `double pow(double a, double b)` |                                          返回 `a` 的 `b` 次幂                                          |
|     `double  sqrt(double a)`     |                                            返回 `a` 的平方根                                            |
|     `double cbrt(double a)`      |                                            返回 `a` 的立方根                                            |
|        `double random()`         |                                   返回 `[0.0, 1.0)` 的伪随机 `double`                                   |
|                常量                |                                                 值                                                 |
|         `double Math.PI`         |                                   圆周率 `π`（`3.141592653589793`）                                    |
|         `double Math.E`          |                                   自然常数 `e`（`2.718281828459045`）                                   |
