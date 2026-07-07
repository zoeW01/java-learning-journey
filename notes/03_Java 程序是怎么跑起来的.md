
## 从零开始：Java 程序是怎么跑起来的

### 一、源代码、编译、字节码

写的 `.java` 文件叫**源代码**。它是人类能读懂的文字，但计算机读不懂。

计算机只认识二进制指令。让计算机理解源代码，需要两步：

**第一步：编译（compile）**

```bash
javac Project.java
```

`javac` 是 Java 编译器。它把 `Project.java` 这个文本文件，翻译成一个叫 `Project.class` 的文件。这个 `.class` 文件里装的是**字节码**——一种介于人类语言和机器语言之间的中间格式。它不是纯文本，但也不是机器码。你可以把它理解为一种"通用中间语言"。

**第二步：运行（run）**

```bash
java Project
```

`java` 命令启动 **JVM（Java Virtual Machine，Java 虚拟机）**。JVM 读取 `.class` 文件里的字节码，把它翻译成当前操作系统能理解的机器码，然后执行。

**为什么要有 JVM？**

你的代码写在 Windows 上能跑，放在 Linux 上也能跑，放在 Mac 上也能跑——因为 JVM 做了中间的翻译工作。每个操作系统有自己专用的 JVM，但所有 JVM 都能读懂同样的 `.class` 字节码。这就是 Java 的"一次编译，到处运行"。

- **关键规则**：运行命令不带 `.class` 后缀

---

### 二、JDK / JRE / JVM

- **JDK**（Java Development Kit）：开发工具包，包含编译器（javac）和 JRE。用于**开发**。
	- 编译器的任务是把`.java`文件变成`.class`文件（字节码）
- **JRE**（Java Runtime Environment）：运行环境，包含 JVM 和核心类库。用于**运行**。
	- JRE里没有编译器，只有运行环境。
- **JVM**（Java Virtual Machine）：Java 虚拟机，真正执行字节码（.class文件）的东西。
- **包含关系**：JDK ⊃ JRE ⊃ JVM
### 三、JAR 包是什么

你现在写的代码只有一个 `Project.java`，编译成一个 `Project.class`，很简单。

但 MySQL 的 JDBC 驱动不是这样。它由几十个甚至上百个 `.class` 文件组成，每个文件实现不同的功能。把这些文件一个一个发给别人，既麻烦又容易丢。

**JAR 包就是把一堆 `.class` 文件打成一个压缩包。** JAR 的全称是 Java ARchive，本质上就是一个 `.zip` 文件，只不过后缀名改成了 `.jar`。

当你下载了 `mysql-connector-j-8.0.33.jar`，你拿到的就是一个压缩包，里面装着 MySQL 驱动所需要的全部 `.class` 文件。

---

### 四、Classpath：JVM 去哪里找类

这是最困惑的地方。重点讲。

**场景：**

你的 `JdbcDemo.java` 里写了这行代码：

```java
Connection conn = DriverManager.getConnection(url, user, password);
```

JVM 在运行这行代码时，需要知道 `DriverManager` 这个类在哪里。它不会全磁盘搜索——那太慢了。它只在特定的目录列表里找。这个列表就叫 **classpath**，直译过来就是"类的搜索路径"，类路径。

**默认情况下，classpath 是什么？**

当你执行 `java JdbcDemo` 时，JVM 默认把**当前目录**（`.`）加入 classpath。所以它能在当前目录找到你自己写的 `JdbcDemo.class`。

但 `DriverManager` 在 `mysql-connector-j-8.0.33.jar` 里，不在当前目录。JVM 找不到它，就报错：

```
No suitable driver found for jdbc:mysql://...
```

**那怎么告诉 JVM 去 jar 包里面找？**

用 `-cp` 参数。`-cp` 是 `-classpath` 的缩写。

```bash
java -cp .:mysql-connector-j-8.0.33.jar JdbcDemo
```

这行命令的意思是：

> JVM，你的 classpath 包含两个地方：
> 1. `.`：当前目录（找我自己写的类）
> 2. `mysql-connector-j-8.0.33.jar`：这个压缩包（找 MySQL 驱动的类）
>
> 然后运行 `JdbcDemo`。

**注意分隔符：**
- Linux/Mac 上用冒号 `:` 分隔多个路径
- Windows 上用分号 `;`

我用的是 WSL（Linux），所以是 `.:mysql-connector-j-8.0.33.jar`。

---

### 五、用书架的比喻理解以上全部概念

假设你有一个书架：

- **源代码 `.java` 文件**：你手写的笔记。只有你能读懂。
- **编译 `javac`**：你找了一个翻译，把笔记翻译成一种通用速记符号（`.class` 字节码）。
- **JVM**：一个能读懂通用速记符号的阅读器。每台电脑上装的阅读器版本不一样，但都能读同样的速记符号。
- **JAR 包**：别人写好的一整套速记笔记，装订成一本册子（压缩包），方便传递和查阅。
- **Classpath**：你告诉阅读器"去哪些书架上找书"。默认只看你桌面上（当前目录）的书。如果你需要参考别人的册子（JAR 包），必须用 `-cp` 把册子也放到书架上。
- **`-cp` 参数**：就是在给书架加位置。`-cp .:某本书.jar` 意思是"书架上有桌面上的书，还有这本册子"。

---

### 六、Maven 和 Spring Boot 跟这些有什么关系——预告

你现在每一次运行 `javac` 和 `java` 都要手动加 `-cp`，还要手动下载 jar 包。项目大了以后，可能依赖几十个 jar 包，手动管理会疯掉。

**Maven** 就是一个自动管理 jar 包的工具。你告诉它"我需要 MySQL 驱动 8.0.33"，它自动下载、自动放到 classpath 里，不需要你手动 `wget` 和写 `-cp`。

**Spring Boot** 是在 Maven 的基础上更进一步。它不仅管理 jar 包，还帮你自动配置数据库连接、自动创建 Statement、自动关闭资源——你写的那些 JDBC 样板代码，Spring Boot 帮你省掉 90%。

但你必须在手写 JDBC 的过程中，理解 classpath、jar 包、编译运行的底层机制。否则你用了 Spring Boot 之后，出了问题完全不知道底层发生了什么，只能盲目搜答案。

---

