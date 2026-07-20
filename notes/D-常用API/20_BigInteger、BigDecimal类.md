# BigInteger（`java.math` 包）

**用途**：处理任意精度的整数（BigInteger）和任意精度的浮点数（BigDecimal），解决基本类型溢出和精度丢失问题。两者均为不可变对象。

**注意**：BigInteger 是不可变对象，所有运算返回新实例。

|         返回值         |                   方法                    |            说明             |
| :-----------------: | :-------------------------------------: | :-----------------------: |
|    `BigInteger`     |      `new BigInteger(String val)`       |      用十进制字符串构造（最常用）       |
|    `BigInteger`     | `new BigInteger(String val, int radix)` |        指定进制（2~36）         |
| `static BigInteger` |     `BigInteger.valueOf(long val)`      | 用 long 值构造（有缓存，范围 -16~16） |
|    `BigInteger`     |          `add(BigInteger val)`          |            加法             |
|    `BigInteger`     |       `subtract(BigInteger val)`        |            减法             |
|    `BigInteger`     |       `multiply(BigInteger val)`        |            乘法             |
|    `BigInteger`     |        `divide(BigInteger val)`         |       除法（整数除法，截断小数）       |
|   `BigInteger[]`    |  `divideAndRemainder(BigInteger val)`   | 除法，返回商和余数（`[0]`商，`[1]`余数） |
|    `BigInteger`     |           `mod(BigInteger m)`           |         取模（结果非负）          |
|    `BigInteger`     |       `remainder(BigInteger val)`       |       取余（符号与被除数相同）        |
|    `BigInteger`     |           `pow(int exponent)`           |            幂运算            |
|    `BigInteger`     |                 `abs()`                 |            绝对值            |
|    `BigInteger`     |               `negate()`                |            取反             |
|      `boolean`      |           `equals(Object x)`            |      比较值是否相等（不是同一对象）      |
|        `int`        |       `compareTo(BigInteger val)`       |   相等返回 0，小于返回负数，大于返回正数    |
|    `BigInteger`     |          `max(BigInteger val)`          |           返回较大值           |
|    `BigInteger`     |          `min(BigInteger val)`          |           返回较小值           |
|        `int`        |               `signum()`                |     返回 -1（负数）、0、1（正数）     |
|        `int`        |              `intValue()`               |      转为 int（可能丢失数据）       |
|       `long`        |              `longValue()`              |          转为 long          |
|       `float`       |             `floatValue()`              |     转为 float（可能丢失精度）      |
|      `double`       |             `doubleValue()`             |         转为 double         |
|      `String`       |              `toString()`               |          十进制字符串           |
|      `String`       |          `toString(int radix)`          |          指定进制字符串          |
|    `BigInteger`     |          `and(BigInteger val)`          |            按位与            |
|    `BigInteger`     |          `or(BigInteger val)`           |            按位或            |
|    `BigInteger`     |          `xor(BigInteger val)`          |            异或             |
|    `BigInteger`     |                 `not()`                 |            按位非            |
|    `BigInteger`     |           `shiftLeft(int n)`            |            左移             |
|    `BigInteger`     |           `shiftRight(int n)`           |        算术右移（符号扩展）         |
|    `BigInteger`     |          `gcd(BigInteger val)`          |           最大公约数           |

---

# BigDecimal（`java.math` 包）

**用途**：处理任意精度的整数（BigInteger）和任意精度的浮点数（BigDecimal），解决基本类型溢出和精度丢失问题。两者均为不可变对象。

**注意**：BigDecimal 是不可变对象，所有运算返回新实例。

|         返回值         |                                 方法                                 |                              说明                              |
| :-----------------: | :----------------------------------------------------------------: | :----------------------------------------------------------: |
|    `BigDecimal`     |                    `new BigDecimal(String val)`                    |                    **最推荐**：精确表示字符串所描述的数值                     |
|    `BigDecimal`     |                    `new BigDecimal(double val)`                    |        **不推荐**：将 double 的二进制近似值转为 BigDecimal，可能得到奇怪结果        |
| `static BigDecimal` |                  `BigDecimal.valueOf(double val)`                  |              **推荐**：内部将 double 转为字符串再构造，避免了精度问题              |
|    `BigDecimal`     |                      `add(BigDecimal augend)`                      |                              加法                              |
|    `BigDecimal`     |                 `subtract(BigDecimal subtrahend)`                  |                              减法                              |
|    `BigDecimal`     |                `multiply(BigDecimal multiplicand)`                 |                              乘法                              |
|    `BigDecimal`     |                    `divide(BigDecimal divisor)`                    |                         除法（若除不尽则抛异常）                         |
|    `BigDecimal`     | `divide(BigDecimal divisor, int scale, RoundingMode roundingMode)` |                         除法，指定小数位数和舍入                         |
|    `BigDecimal`     |      `divide(BigDecimal divisor, RoundingMode roundingMode)`       |                    除法（不指定 scale，使用被除数的精度）                    |
|    `BigDecimal`     |                            `pow(int n)`                            |                             幂运算                              |
|    `BigDecimal`     |                              `abs()`                               |                             绝对值                              |
|    `BigDecimal`     |                             `negate()`                             |                              取反                              |
|      `boolean`      |                         `equals(Object x)`                         |      同时比较值和标度（scale），`2.0` 和 `2.00` 返回 **false**，因为精度不同      |
|        `int`        |                    `compareTo(BigDecimal val)`                     | **最推荐**。比较值，忽略小数位数.相等返回 0，小于返回负数，大于返回正数（`2.0` 和 `2.00` 返回 0） |
|    `BigDecimal`     |                       `max(BigDecimal val)`                        |                             最大值                              |
|    `BigDecimal`     |                       `min(BigDecimal val)`                        |                             最小值                              |
|        `int`        |                             `signum()`                             |                             符号函数                             |
|        `int`        |                  `intValue()` / `intValueExact()`                  |                       后者在溢出或小数部分非零时抛异常                       |
|       `long`        |                 `longValue()` / `longValueExact()`                 |                              同上                              |
|       `float`       |                           `floatValue()`                           |                            可能损失精度                            |
|      `double`       |                          `doubleValue()`                           |                            可能损失精度                            |
|      `String`       |                            `toString()`                            |                       科学计数法形式（可能包含指数）                        |
|      `String`       |                         `toPlainString()`                          |                     普通字符串，不包含指数（推荐用于显示）                      |

### 舍入模式（）

需指定小数位数和舍入模式。JDK 提供 8 种模式：
`RoundingMode` 在 `java.math` 包下

| 模式（enum）                   | 行为                              |
| -------------------------- | ------------------------------- |
| `RoundingMode.UP`          | 远离零方向舍入（正数加 1）                  |
| `RoundingMode.DOWN`        | 向零方向舍入（截断）                      |
| `RoundingMode.CEILING`     | 正数向正无穷，负数向零                     |
| `RoundingMode.FLOOR`       | 正数向零，负数向负无穷                     |
| `RoundingMode.HALF_UP`     | 四舍五入（>=5 进位，最常用）                |
| `RoundingMode.HALF_DOWN`   | 五舍六入（>5 进位）                     |
| `RoundingMode.HALF_EVEN`   | 银行家舍入：四舍六入五留双（5 的前一位是偶数则舍，奇数则入） |
| `RoundingMode.UNNECESSARY` | 不需要舍入，若结果不精确则抛异常                |


