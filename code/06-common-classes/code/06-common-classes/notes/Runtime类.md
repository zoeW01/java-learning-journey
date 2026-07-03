# Runtime 类（`java.lang`）
表示当前虚拟机的运行情况
`java.lang`下，`Runtime` 无法直接实例化（构造方法私有）。必须通过单例工厂方法获取：`Runtime runtime = Runtime.getRuntime();`

---
## 常用方法

| 返回值               | 方法签名                    | 说明                                       |
| ----------------- | ----------------------- | ---------------------------------------- |
| `static Runtime`  | `getRuntime()`          | 静态方法，获取当前应用程序的 `Runtime` 单例              |
| `void`            | `exit(int status)`      | 终止 JVM 运行（0 表示正常停止，非 0 异常停止）             |
| `long`            | `maxMemory()`           | JVM 试图使用的最大内存量（单位byte）                   |
| `long`            | `totalMemory()`         | JVM 当前已分配的内存总量（单位byte）                   |
| `long`            | `freeMemory()`          | JVM 已分配内存中的空闲量（单位byte）                   |
| `void`            | `gc()`                  | 建议 JVM 执行垃圾回收（等价于 `System.gc()`，不保证立即执行） |
| `int`             | `availableProcessors()` | 返回 JVM 可用的处理器数量（逻辑核心数）                   |
| `Process`         | `exec(String command)`  | 运行`cmd`命令                                |
| `Runtime.Version` | `version()`             | 返回当前 Java 运行时的版本对象（JDK 9+）               |





