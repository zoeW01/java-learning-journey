
# Object类（`java.lang`）

Object是所有 Java 类的根类。每个类都直接或间接继承 Object。

| 返回值                | 方法签名                            | 说明                                                                               |
| ------------------ | ------------------------------- | -------------------------------------------------------------------------------- |
| `boolean`          | `equals(Object obj)`            | 判断两个对象是否“相等”。默认实现是 `this == obj`（比较引用）。子类通常重写以比较内容。                              |
| `int`              | `hashCode()`                    | 返回对象哈希码。重写 `equals` 必须同时重写 `hashCode`，以遵守“相等对象必须有相同哈希码”的约定。                      |
| `String`           | `toString()`                    | 返回对象的字符串表示。默认返回 `包名.类名@十六进制哈希码`。通常重写以提供可读信息。                                     |
| `protected Object` | `clone()`                       | 创建并返回此对象的副本。默认实现是浅拷贝（没有实现`Cloneable` 的类会抛出 `CloneNotSupportedException`）。        |
| `void`             | `wait()`                        | 让当前线程进入等待状态，直到其他线程调用 `notify` 或 `notifyAll` 唤醒。必须在 （`synchronized`）内调用           |
| `void`             | `wait(long timeout)`            | 等待，但最多等待 `timeout` 毫秒后自动唤醒。必须在 （`synchronized`）内调用                               |
| `void`             | `wait(long timeout, int nanos)` | 等待，精度更高（毫秒+纳秒）。实际行为为 `wait(timeout + (nanos>0 ? 1 : 0))`。必须在 （`synchronized`）内调用 |
| `void`             | `notify()`                      | 唤醒在此对象监视器上等待的单个线程（随机选择）。必须在 （`synchronized`）内调用                                  |
| `void`             | `notifyAll()`                   | 唤醒在此对象监视器上等待的所有线程。必须在 （`synchronized`）内调用                                        |
| `Class<?>`         | `getClass()`                    | 返回此对象的运行时类（`Class` 对象）。                                                          |
|                    | `registerNatives()`             | 这是一个 `private static native` 方法，用于在类加载时注册本地方法。**开发者永远不需要关心或调用这个方法**，它由 JVM 内部使用。 |

`finalize()` 已废弃，对象被 GC 回收前调用，但不保证一定会执行。

# Objects类（`java.util`）


Objects是工具类，提供空值安全的静态实用方法。所有方法均为静态。

| 返回值       | 方法签名                                     | 说明                                                            |
| --------- | ---------------------------------------- | ------------------------------------------------------------- |
| `boolean` | `equals(Object a, Object b)`             | 比较两个对象，处理 null 安全（`null == null` 返回 true）。内部先比较引用再调 `equals`。 |
| `boolean` | `isNull(Object obj)`                     | 返回 `obj == null`。                                             |
| `boolean` | `nonNull(Object obj)`                    | 返回 `obj != null`。                                             |
| `String`  | `toString(Object o)`                     | 返回调用 o 的 `toString`，若 o 为 null 返回字符串 `"null"`。                |
| `String`  | `toString(Object o, String nullDefault)` | 若 o 为 null 返回 `nullDefault`，否则调用 `o.toString()`。              |


