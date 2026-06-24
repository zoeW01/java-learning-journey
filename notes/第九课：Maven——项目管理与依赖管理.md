
### 一、问题层：没有 Maven 的时候，Java 开发有多痛苦

你已经体验过一小部分。回忆第七课 JDBC：

```bash
wget https://repo1.maven.org/.../mysql-connector-j-8.0.33.jar
javac -cp mysql-connector-j-8.0.33.jar JdbcDemo.java
java -cp .:mysql-connector-j-8.0.33.jar JdbcDemo
```

这只是一个 jar 包。一个真实的 Java Web 项目依赖几十个甚至上百个 jar 包：Spring、MyBatis、日志库、JSON 解析库、测试框架……每个 jar 包自己还可能依赖别的 jar 包。手动管理这些东西会带来三个问题：

**问题1：依赖地狱**

你用了 A.jar，A.jar 内部依赖 B.jar。你不知道 B.jar 存在，运行时直接报 `ClassNotFoundException`。你得手动找到 B.jar，下载，放到 classpath。然后 B.jar 又依赖 C.jar……这叫传递性依赖，手动管理是不可完成的。

**问题2：版本冲突**

项目里用到了两个 jar 包：X.jar 依赖 `commons-logging 1.0`，Y.jar 依赖 `commons-logging 2.0`。两个版本的 commons-logging 同时出现在 classpath 里，JVM 只加载其中一个。如果 API 不兼容，程序直接崩溃。

**问题3：构建流程不统一**

一个人用 `javac` 手动编译，另一个人用 IDE 的自动编译，第三个人写了个 shell 脚本。没有统一的构建标准。新成员加入项目，光配环境就要花两三天。

**总结：Maven 解决了三个核心问题——自动下载依赖、自动管理版本冲突、统一项目构建流程。**

---

### 二、机制层：Maven 怎么做到这些

**2.1 核心概念：坐标**

Maven 用三个坐标唯一定位一个 jar 包：

- `groupId`：组织或公司标识。比如 `com.mysql` 表示这是 MySQL 官方出的。
- `artifactId`：项目名，项目标识。比如 `mysql-connector-j` 表示这是 MySQL 的 Java 连接器。
- `version`：版本号。比如 `8.0.33`。

这三个坐标合在一起，就像 GPS 坐标一样，在全球范围内唯一确定一个 jar 包：

```
groupId: com.mysql
artifactId: mysql-connector-j
version: 8.0.33
```

你告诉 Maven 这三个坐标，Maven 就知道该下载什么、从哪里下载。

**2.2 中央仓库与本地仓库**

**中央仓库**

Maven 有一个全球公用的中央仓库（Maven Central Repository），地址是 `https://repo1.maven.org/maven2/`。几乎所有的 Java 开源 jar 包都发布到这里。

当你指定了坐标，Maven 会自动去中央仓库查找对应的 jar 包，下载到你本地的"本地仓库"里。

**本地仓库**

Maven 在你电脑上有一个本地仓库目录，默认是 `~/.m2/repository/`。下载过的 jar 包都会缓存在这里。下次再用到同一个 jar 包，直接从本地仓库拿，不用重新下载。

**工作流程：**

1. 你声明依赖 `com.mysql:mysql-connector-j:8.0.33`
    
2. Maven 先检查本地仓库 `~/.m2/repository/com/mysql/mysql-connector-j/8.0.33/` 有没有这个 jar
    
3. 有就直接用
    
4. 没有就去中央仓库下载，存到本地仓库，然后使用

**类比：**
- 中央仓库 = 图书馆
- 本地仓库 = 你的书架
- 坐标 = 书的 ISBN 号

中央仓库是一座巨大的图书馆，里面有几乎所有出版过的 Java 书籍。本地仓库是你家里的书架。你需要一本书时，先在自家书架找，找不到再去图书馆借回来放在书架上，以后随用随取。

**2.3 传递性依赖的自动管理**

这是 Maven 最核心的价值之一。你声明了一个依赖，Maven 自动处理这个依赖所依赖的所有东西。

你声明"我需要 A.jar"。Maven 去查 A.jar 的元数据（pom 文件），发现 A 依赖 B，B 依赖 C。Maven 自动把 A、B、C 全部下载到本地仓库，并放到 classpath 里。你不用知道 B 和 C 的存在。

**2.4 依赖冲突的仲裁机制**

当两个不同的依赖需要同一个第三方库的不同版本时，Maven 如何决定用哪个？

**原则一：最短路径优先**

离项目根节点更近的版本优先。如果你直接在 `pom.xml` 里声明了 `commons-logging:2.0`，而某个传递性依赖带来了 `commons-logging:1.0`，Maven 选择你直接声明的 2.0。因为你的声明在依赖树的第一层，传递性依赖在更深的层，你离根更近。

**原则二：先声明优先**

如果两个版本在同一层——比如两个直接依赖各带了一个版本的 `commons-logging`——Maven 选择在 `pom.xml` 里先出现的那个。`<dependency>` 标签的书写顺序决定了优先级。

这两个原则保证了依赖冲突的结果是可预测的，不再是玄学。

**2.5 构建生命周期**

Maven 不是把一堆命令平铺给你选。它定义了一套有顺序的阶段序列。执行后面的阶段时，前面所有的阶段会自动执行。

**三个独立的生命周期：**

- **clean 生命周期**：清理构建产物
    
- **default 生命周期**：编译、测试、打包、部署
    
- **site 生命周期**：生成项目文档
    

你目前用到的只有前两个。

**clean 生命周期只有一个阶段：**

| 阶段      | 作用              |
| ------- | --------------- |
| `clean` | 删除 `target/` 目录 |

- 删除上次编译生成的所有 `.class` 文件和 jar 包。这保证了每次构建都是从源代码重新编译，不会残留旧版本的字节码干扰结果。

**default 生命周期的重要阶段（从先到后）：**

|阶段|作用|触发前面阶段|
|---|---|---|
|`validate`|验证项目结构是否正确|否|
|`compile`|编译 `src/main/java` 下的源代码|是|
|`test`|编译并运行 `src/test/java` 下的测试代码|是|
|`package`|将编译结果打包成 JAR 或 WAR|是|
|`install`|将打包好的 JAR 安装到本地仓库|是|
|`deploy`|将最终包部署到远程仓库|是|

**关键规则：** 执行任何一个阶段，Maven 自动先执行它前面所有的阶段。

你执行 `mvn compile`，实际执行序列是：validate → compile。  
你执行 `mvn package`，实际执行序列是：validate → compile → test → package。

这就是为什么写 `mvn clean compile` 就够了——不需要写 `mvn clean validate compile`。

**阶段不干活，插件才干活。**

"阶段"只是一个时间节点。真正编译代码的是 `maven-compiler-plugin` 这个插件。Maven 的架构是：在 `compile` 这个阶段，绑定 `maven-compiler-plugin` 的 `compile` 目标。你执行 `mvn compile` 时，Maven 走到 `compile` 阶段，触发绑定在这个阶段上的插件目标，插件去调用 `javac`。

**`pom.xml` 里的 `<build>` 块就是在配置插件。**

之前添加的 `exec-maven-plugin` 并不绑定到任何生命周期阶段。它通过命令 `mvn exec:java` 直接调用。这叫"插件目标直接调用"，不走生命周期。


---

### 三、操作层：安装 Maven 并创建第一个 Maven 项目

**在 WSL 中安装 Maven**

```bash
sudo apt update
sudo apt install maven -y
```

安装完成后验证：

```bash
mvn -version
```


**第一步：创建 Maven 项目目录**

```bash
mkdir -p ~/java-projects/09-maven-jdbc && cd ~/java-projects/09-maven-jdbc
```

**第二步：Maven 项目的核心——pom.xml**

Maven 项目的所有配置都写在一个叫 `pom.xml` 的文件里。`pom` 全称 Project Object Model。这个文件告诉 Maven：这个项目叫什么、用什么 JDK 版本编译、依赖哪些 jar 包。

你需要自己写出一个 `pom.xml`，包含以下信息：

- **groupId**：`com.zoe`（你的组织名，用你自己的标识）
- **artifactId**：`jdbc-demo`（项目名）
- **version**：`1.0-SNAPSHOT`（版本号，SNAPSHOT 表示开发中版本）
- **properties**：指定 Java 编译版本为 17（`maven.compiler.source` 和 `maven.compiler.target` 都设为 17）
- **dependencies**：依赖 `mysql-connector-j`，版本 `8.0.33`

提示：`pom.xml` 是 XML 格式文件。你需要知道 XML 的基本结构——标签成对出现，有开标签 `<tag>` 和闭标签 `</tag>`。根标签是 `<project>`。

[[第九课：pom.xml的XML 结构]]

**第三步：目录结构**

Maven 项目有固定的目录结构，你必须遵守：

```
09-maven-jdbc/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── JdbcDemo.java
```

Java 源代码必须放在 `src/main/java/` 下。创建对应的目录并把你之前写的 `JdbcDemo.java` 文件放到正确位置。

**第四步：exec-maven-plugin 配置与运行**
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="JdbcDemo"
```

`mvn compile` 只编译不运行。`exec-maven-plugin` 是一个 Maven 插件，提供 `exec:java` 目标来直接运行你的 `main` 方法。它会自动把项目的所有依赖（包括传递性依赖）加入 classpath，你不需要手动写 `-cp`。


**`exec-maven-plugin`插件配置位置：** `<build>` → `<plugins>` → `<plugin>`

```xml
<build>
	<plugin>
	    <groupId>org.codehaus.mojo</groupId>
	    <artifactId>exec-maven-plugin</artifactId>
	    <version>3.0.0</version>
	    <configuration>
	        <mainClass>JdbcDemo</mainClass>
	    </configuration>
	</plugin>
</build>
```


- `<groupId>`、`<artifactId>`、`<version>`：插件的坐标。`org.codehaus.mojo` 是 Codehaus 组织下 Mojo 项目组的标识，`exec-maven-plugin` 是插件名，`3.0.0` 是版本。
- `<configuration>`：插件的配置区。每个插件有自己特定的配置项。
- `<mainClass>`：`exec-maven-plugin` 的配置项之一，指定要运行哪个类的 `main` 方法。


**运行命令：**

```bash
mvn clean compile exec:java
```

- `clean`：清理旧构建产物，删除 `target/`。
    
- `compile`：重新编译文件到 `target/classes/`。
    
- `exec:java`：调用 `exec-maven-plugin` 的 `java` 目标，将 `target/classes/` 和所有依赖 jar 包加入 classpath，执行 `JdbcDemo.main()`。不经过生命周期阶段



注意：
- Maven 的所有命令（`mvn compile`、`mvn package` 等）必须在 `pom.xml` 所在的目录下执行。
---
