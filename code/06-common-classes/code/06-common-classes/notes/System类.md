# System 类（`java.lang`）

 包下，所有方法均为静态方法，通过 `System.xxx()` 调用。注意：该类无法实例化（构造方法私有）。

## 常用方法

| 返回值      | 方法签名                                                                      | 说明                                                            |
| -------- | ------------------------------------------------------------------------- | ------------------------------------------------------------- |
| `void`   | `exit(int status)`                                                        | 终止 JVM 运行（0 表示正常停止，非 0 异常停止）                                  |
| `void`   | `gc()`                                                                    | 建议 JVM 执行垃圾回收（不保证立即执行）                                        |
| `long`   | `currentTimeMillis()`                                                     | 返回当前时间与 1970-01-01 UTC 的毫秒差（返回值除一千为秒，再除六十为分钟，再除六十为小时，再除二十四为天） |
| `void`   | `arraycopy(Object src, int srcPos, Object dest, int destPos, int length)` | 复制数组（比循环快，浅拷贝）                                                |
| `String` | `getProperty(String key)`                                                 | 获取系统属性（如 `"java.version"`, `"user.dir"`, `"file.separator"`）  |
| `String` | `getenv(String name)`                                                     | 获取环境变量（如 `"PATH"`, `"JAVA_HOME"`）                             |
**常用属性速查** (通过 `System.getProperty` 获取)：

- `java.version` — Java 运行时版本
    
- `java.home` — Java 安装目录
    
- `os.name` — 操作系统名称
    
- `os.arch` — 操作系统架构
    
- `user.name` — 当前用户名
    
- `user.home` — 用户主目录
    
- `user.dir` — 当前工作目录
    
- `file.separator` — 文件分隔符（`/` 或 `\`）
    
- `path.separator` — 路径分隔符（`:` 或 `;`）
    
- `line.separator` — 行分隔符（`\n` 或 `\r\n`）**
## 常量

| 常量           | 类型            | 含义                                |
| ------------ | ------------- | --------------------------------- |
| `System.out` | `PrintStream` | 标准输出流（通常连到控制台）                    |
| `System.err` | `PrintStream` | 标准错误输出流（也连到控制台，但不同流，输出可能不同颜色）     |
| `System.in`  | `InputStream` | 标准输入流（控制台键盘输入，通常包装成 `Scanner` 使用） |

常量的常用方法（全是 PrintStream 的实例方法）

| 返回值           | 方法签名                                    | 说明                   |
| ------------- | --------------------------------------- | -------------------- |
| `void`        | `print(任意基本类型 / 对象)`                    | 打印内容，不换行             |
| `void`        | `println(任意基本类型 / 对象)`                  | 打印内容并换行              |
| `void`        | `println()`                             | 只输出一个换行              |
| `PrintStream` | `printf(String format, Object... args)` | 格式化输出（同 C 语言 printf） |
| `PrintStream` | `format(String format, Object... args)` | 与 `printf` 完全一样      |