[[第九课：pom.xml的XML 结构]]
### 一、问题层：为什么需要打包

现在运行 `JdbcDemo` 的方式是：

```bash
mvn clean compile exec:java
```

这个命令在工作，但它有致命缺陷：只能在自己的开发电脑上、在有 Maven 和 JDK 的环境下运行。如果想把程序发给别人、部署到服务器、或者让没有装 Maven 的人运行，这种方式完全不可行。

因此需要一个方案：把编译好的代码和所有依赖打包成一个独立的文件，这个文件可以在任何装了 JRE 的机器上直接运行。这个独立文件就是 JAR 包。

### 二、机制层：JAR 包是什么

JAR 的全称是 Java ARchive。它的本质是一个 ZIP 压缩文件，只是后缀名从 `.zip` 改成了 `.jar`。JAR 包内部有一个固定的目录结构和一个特殊的元数据文件。

**标准 JAR 包内部结构：**

```
jdbc-demo.jar
├── META-INF/
│   └── MANIFEST.MF          ← 元数据文件
├── JdbcDemo.class           ← 你编译的类
├── com/                     ← 第三方库的类（依赖）
│   └── mysql/...
└── com/
    └── google/
        └── protobuf/...
```

**两种 JAR 包：**

| 类型 | 内容 | 能否直接运行 |
|------|------|-------------|
| 普通 JAR | 只有你自己的 `.class` 文件 | 否。运行时必须手动用 `-cp` 指定依赖 jar 包的位置 |
| 可执行 JAR（Fat JAR / Uber JAR） | 包含你自己的代码 + 所有依赖的 jar 包 | 是。`java -jar xxx.jar` 直接运行 |

Maven 默认的 `package` 阶段生成的是普通 JAR，只包含你自己的代码。要生成可执行 JAR，需要额外配置插件。

### 三、MANIFEST.MF 的作用

`META-INF/MANIFEST.MF` 是 JAR 包的配置文件。对于可执行 JAR，它必须包含一项关键信息：

```
Main-Class: JdbcDemo
```

这一行告诉 JVM：当你执行 `java -jar xxx.jar` 时，从这个类的 `main` 方法开始启动程序。

	`java -jar xxx.jar`是 Java 运行 JAR 包的标准命令。

如果没有这一行，JVM 不知道该从哪个类开始执行，直接报错。

**MANIFEST.MF 的其他常见条目：**

| 条目 | 作用 |
|------|------|
| `Main-Class` | 指定主类，使 JAR 可执行 |
| `Class-Path` | 指定依赖 jar 包的路径（传统方式，非 Fat JAR） |
| `Manifest-Version` | Manifest 规范版本，固定为 `1.0` |

### 四、操作层：生成可执行 JAR 包

需要修改 `pom.xml`，添加 `maven-assembly-plugin` 插件。这个插件的作用是把写的代码和所有依赖合并成一个 Fat JAR。

在 `pom.xml` 的 `<plugins>` 内，`exec-maven-plugin` 之后，添加第二个 `<plugin>`。你需要写入以下坐标和配置：

- `groupId`：`org.apache.maven.plugins`
- `artifactId`：`maven-assembly-plugin`
- `version`：`3.6.0`
- `<configuration>` 里需要指定：
  - `<archive>` → `<manifest>` → `<mainClass>` 设为 `JdbcDemo`
  - `<descriptorRefs>` → `<descriptorRef>` 设为 `jar-with-dependencies`
- `<executions>` 里需要指定在 `package` 阶段执行 `single` 目标

**运行打包命令：**

```bash
cd ~/java-projects/09-maven-jdbc
mvn clean package
```

成功后，`target/` 目录下会生成两个 JAR 文件：
- `jdbc-demo-1.0-SNAPSHOT.jar`：普通 JAR（只有你的代码）
- `jdbc-demo-1.0-SNAPSHOT-jar-with-dependencies.jar`：Fat JAR（包含所有依赖）

**运行可执行 JAR：**

```bash
java -jar target/jdbc-demo-1.0-SNAPSHOT-jar-with-dependencies.jar
```



`java -jar xxx.jar` 是 Java 运行 JAR 包的标准命令。拆开解释：

---

### 1. `java` 是什么

`java` 命令启动 JVM（Java 虚拟机）。JVM 的职责是加载 `.class` 字节码文件，把它们翻译成机器码，然后执行。

你从第一课到现在运行程序，一直在用 `java` 命令。比如：

```bash
java HelloWorld
```

这行命令的意思是：启动 JVM，在当前目录找到 `HelloWorld.class`，执行它的 `main` 方法。

---

### 2. `-jar` 参数的作用

`-jar` 是一个**参数开关**，告诉 JVM 改变行为模式。

**没有 `-jar` 时**，JVM 认为你给的是一个**类名**。它会去 classpath 里找 `类名.class` 文件。

**有 `-jar` 时**，JVM 认为你给的是一个**JAR 包文件名**。它会做三件事：
1. 打开这个 `.jar` 文件（本质是一个 ZIP 压缩包）
2. 读取里面的 `META-INF/MANIFEST.MF` 文件
3. 找到 `Main-Class` 这一行指定的类，执行那个类的 `main` 方法

---

### 3. 完整命令拆解

```bash
java -jar target/jdbc-demo-1.0-SNAPSHOT-jar-with-dependencies.jar
```

- `java`：启动 JVM
- `-jar`：告诉 JVM“接下来要运行的是一个 JAR 包，不是类名”
- `target/...jar`：JAR 包的路径和文件名

JVM 打开这个 JAR 包，找到 `META-INF/MANIFEST.MF`，读到：

```
Main-Class: JdbcDemo
```

然后 JVM 执行 `JdbcDemo.main()`。程序启动。

---

### 4. 和之前运行方式的对比

| 方式 | 命令 | 需要什么 |
|------|------|----------|
| 直接运行类 | `java JdbcDemo` | 当前目录有 `JdbcDemo.class`，且 classpath 里有所有依赖的 jar 包 |
| Maven 运行 | `mvn exec:java` | 当前目录有 `pom.xml`，且安装了 Maven |
| 运行 JAR | `java -jar xxx.jar` | 只要求电脑装了 JRE，JAR 包里已包含所有代码和依赖 |

**运行 JAR 是最终交付方式。** 你把 JAR 包发给别人，对方只需要装 Java，不需要装 Maven，不需要知道依赖是什么。这是你后面找工作时，项目成果的标准交付形式。

---

### 5. 类比

- `java HelloWorld` = 你告诉厨师“做一道菜单上的宫保鸡丁”。厨师（JVM）在菜谱（classpath）里找到宫保鸡丁的做法，开始做。
- `java -jar 年夜饭礼盒.jar` = 你给厨师一个礼盒，礼盒里装好了所有食材和一张做法说明（MANIFEST.MF）。厨师打开礼盒，按说明做菜。不需要菜单，不需要额外买食材。

---

现在你理解了 `java -jar` 的含义。继续第十课的任务：在 `pom.xml` 中添加 `maven-assembly-plugin` 配置，然后执行 `mvn clean package` 打包，最后用 `java -jar` 运行生成的 Fat JAR。把运行结果贴给我。