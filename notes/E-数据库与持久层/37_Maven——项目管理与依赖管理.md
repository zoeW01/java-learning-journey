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

[[38_Maven 的 package 阶段与 jar 包机制]]

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

# pom.xml的XML 结构

Maven 的 `pom.xml` 必须使用 **XML 语法**。XML 是一种标记语言，所有配置项都必须用尖括号标签包裹，而且标签必须正确嵌套、成对出现。

在教你写出正确的 `pom.xml` 之前，你必须先理解 XML 的基本规则。如果不理解这些规则，你写出来的东西 Maven 永远报错。

---

## XML 的基本规则


1. **标签**：用 `<` 和 `>` 包围一个名字，例如 `<name>`。
2. **开标签和闭标签**：开标签 `<name>`，闭标签 `</name>`，两者之间放内容。例如 `<name>Zoe</name>`。
3. **自闭合标签**：如果没有内容，可以写成 `<tag />`。例如 `<empty />`。
4. **嵌套**：标签可以包含其他标签，必须正确嵌套。`<a><b></b></a>` 是对的，`<a><b></a></b>` 是错的。
5. **属性**：开标签里可以有 `<tag 属性="值">`，例如 `<project xmlns="...">`。
6. **声明**：XML 文件第一行通常是 `<?xml version="1.0" encoding="UTF-8"?>`。
7. **根元素**：一个合法的 XML 文件必须有且只有一个根元素，包裹所有其他元素。在 `pom.xml` 里根元素是 `<project>`。
8. **命名空间声明**：`<project>` 标签上有命名空间声明

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">
```

---

## pom.xml 的逻辑结构


你现在需要告诉 Maven 两件事：

**A. 这个项目是谁的？**  
用 `groupId`、`artifactId`、`version` 三个子标签，全部包在 `<project>` 根标签下。它们通常放在 `<modelVersion>`（固定值 `4.0.0`）之后。 Maven 的 `pom.xml` 根元素 `<project>` 下第一项必须是 `<modelVersion>4.0.0</modelVersion>`。这个字段告诉 Maven 使用的是 pom 模型的哪个版本。4.0.0 是当前 Maven 2 和 3 唯一支持的版本号。

**B. 这个项目依赖哪些 jar 包？**  
用 `<dependencies>` 标签包裹若干个 `<dependency>` 标签。每个 `<dependency>` 里必须包含 `<groupId>`、`<artifactId>`、`<version>` 三个子标签，用来指定依赖的坐标。

**C. 用哪个 Java 版本编译？**  
用 `<properties>` 标签，里面定义 `<maven.compiler.source>` 和 `<maven.compiler.target>` 两个子标签，值都是 `17`。

---

**现在对这个 `pom.xml` 进行逐行解析，让你理解每一行的作用。**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.zoe</groupId>
    <artifactId>jdbc-demo</artifactId>
    <version>1.0-SNAPSHOT</version>
    
    <dependencies>
	    <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.0.33</version>
        </dependency>
    </dependencies>
    
    <properties>
	    <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>
</project>
```

### **第一部分：XML 声明与命名空间**



```xml
<?xml version="1.0" encoding="UTF-8"?>
```

告诉解析器“这是一个 XML 1.0 文件，使用 UTF-8 编码”。如果不写，XML 解析器也能猜出来，但写上是最佳实践。

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">
```

这三行是 Maven 的命名空间声明。

**为什么需要命名空间？**

XML 允许任何人自定义标签。两个人可能都定义了 `<project>` 标签，但含义完全不同。命名空间用 URI（统一资源标识符）来区分“你这个 `<project>` 是 Maven 的 `<project>`，不是别的工具的 `<project>`”。

- `xmlns="http://maven.apache.org/POM/4.0.0"`：声明默认命名空间。从这个标签开始的 `<project>` 及其所有子标签，都属于这个 Maven POM 命名空间。,同时告诉 XML 解析器"这个 `<project>` 标签及其子标签，都遵循 Maven POM 4.0.0 规范"。
- `xmlns:xsi`：引入 XML Schema Instance 命名空间，用于指定这个 XML 文件应该按哪个规范文件（XSD）来验证结构，指定验证文件的位置。
- **`xsi:schemaLocation`：** 指定"Maven POM 4.0.0 命名空间的验证文件在哪里"。值是键值对格式：`命名空间URI 验证文件URL`。Maven 在解析 `pom.xml` 时，根据这个信息找到对应的 XSD 验证文件，检查你的标签是否合法。

类比：命名空间就像给标签加了姓。`<project>` 本身是一个名字，加上 `xmlns="...maven..."` 就变成了“Maven 家的 project”。如果另一个工具也定义了 `<project>`，它的命名空间不同，就不会混淆。

---

### 第二部分：项目坐标



```xml
<modelVersion>4.0.0</modelVersion>
```

POM 模型自身的版本号。这不是项目的版本，是 POM 规范本身的版本。Maven 2 和 Maven 3 都使用 4.0.0。如果将来 Maven 发布新的大版本改变了 POM 规范，这个数字会变。

```xml
<groupId>com.zoe</groupId>
```

组织或项目的唯一标识符。通常用反向域名格式。`com.zoe` 表示“这个项目是 zoe 开发的”。将来你发布 jar 包到 Maven 中央仓库时，`groupId` 可以唯一区分你的包和别人同名的包。

```xml
<artifactId>jdbc-demo</artifactId>
```

项目名称。`groupId` + `artifactId` 的组合必须唯一。

```xml
<version>1.0-SNAPSHOT</version>
```

版本号。`SNAPSHOT` 是一个特殊后缀，表示“开发中的不稳定版本”。Maven 对 SNAPSHOT 版本的处理策略不同——每次构建都会重新去远程仓库检查是否有最新的 SNAPSHOT 版本。当你发布正式版时，去掉 SNAPSHOT，写成 `1.0`。

---

### 第三部分：依赖



```xml
<dependencies>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>
```

**`<dependencies>` 是什么意思？**

`<dependencies>` 是“这个项目需要的所有外部 jar 包”的容器。里面可以包含多个 `<dependency>`。

**每一个 `<dependency>` 的作用：**

你用坐标 `com.mysql:mysql-connector-j:8.0.33` 精确指定了需要 MySQL 的 JDBC 驱动。当 Maven 执行 `compile` 或 `package` 时，它会：

1. 检查本地仓库 `~/.m2/repository/com/mysql/mysql-connector-j/8.0.33/` 是否已有这个 jar 包。
2. 如果没有，从中央仓库（`https://repo1.maven.org/maven2/`）下载。
3. 如果有，直接使用本地缓存。
4. 自动把这个 jar 包加入 classpath，你不再需要手动写 `-cp`。

**传递性依赖会自动处理：**

`mysql-connector-j` 本身依赖 `protobuf-java`（Google 的一个序列化库）。Maven 读取 `mysql-connector-j` 的 pom 文件，发现它依赖 `protobuf-java`，会自动下载 `protobuf-java` 到本地仓库。你不需要知道 `protobuf-java` 的存在。你在 `pom.xml` 里只写了一个依赖，实际 classpath 里会有两个 jar 包。

---

### 第四部分：编译属性



```xml
<properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
</properties>
```

**`<properties>` 的作用：**

定义键值对，供 Maven 和插件读取。这里定义了两个键：

- `maven.compiler.source`：源代码使用的 Java 版本。设为 17 表示你的代码里可以用 Java 17 的语法（比如 `var`、`switch` 表达式等）。
- `maven.compiler.target`：编译生成的字节码版本。设为 17 表示生成的 `.class` 文件只能在 Java 17 或更高版本的 JVM 上运行。
- 这两个键不是随意命名的——这两个键是 `maven-compiler-plugin` 插件约定的配置参数名，插件读取它们来决定用哪个 Java 版本编译。

**为什么分 source 和 target？**

理论上你可以用 Java 17 语法写代码（source 设为 17），但编译成 Java 8 能运行的字节码（target 设为 8）。这叫“交叉编译”，用来兼容旧版本服务器。但对新手来说，两个都设成 17 就行。

---

### 第五部分：`<build>` 块——Maven 的构建配置模型



`<build>` 是 Maven 项目的构建配置区。它的职责是告诉 Maven "怎么构建这个项目"。`<dependencies>` 回答"项目需要哪些外部 jar 包"，`<build>` 回答"怎么编译、怎么打包、怎么运行"。之前写的 `<properties>` 也是构建配置的一部分，指定了编译版本。

`<build>` 内部通过**插件**机制来扩展 Maven 的能力——Maven 核心引擎只负责执行生命周期阶段，具体的工作全部由插件完成。

`<plugin>` 标签，每一个都声明了一个外部工具。`exec-maven-plugin` 是"运行 Java 程序"的工具，`maven-assembly-plugin` 是"打包成 Fat JAR"的工具。这两个工具互相独立，各自有自己的配置。

需要自己在 `pom.xml` 中添加以下结构（不提供完整 XML，你根据描述独立写出）：

- `<project>` 标签内包含 `<build>` 标签
    
- `<build>` 标签内包含 `<plugins>` 标签。
    
- `<plugins>` 标签内包含 `<plugin>` 标签。。
    
- 这个 `<plugin>` 需要三个坐标子标签：`<groupId>` 为 `org.codehaus.mojo`，`<artifactId>` 为 `exec-maven-plugin`，`<version>` 为 `3.0.0`。
    
- `<plugin>` 内包含 `<configuration>`
    

---

##### configuration 是什么



`<configuration>` 是**当前插件的配置区**。每个插件有自己特定的配置项，这些配置项在插件的文档里定义。

类比：你买了两个电器——一个微波炉（exec-maven-plugin），一个烤箱（maven-assembly-plugin）。微波炉的操作面板上有"加热时间"旋钮，烤箱上有"上下火温度"旋钮。你不能用微波炉的旋钮控制烤箱，也不能用烤箱的旋钮控制微波炉。`<configuration>` 就是当前这个插件的操作面板，里面的配置项只对这个插件生效。

---

##### 为什么两个插件都要设置 mainClass


`exec-maven-plugin` 的 `<mainClass>` 和 `maven-assembly-plugin` 的 `<mainClass>` 是两个完全独立的配置，互不影响。

- `exec-maven-plugin` 的 `<mainClass>`：告诉 Maven "当执行 `mvn exec:java` 时，运行哪个类的 main 方法"。
- `maven-assembly-plugin` 的 `<mainClass>`：告诉打包插件 "在生成的 JAR 包里的 `META-INF/MANIFEST.MF` 文件中，写上 `Main-Class: JdbcDemo`"。

前者是运行时用的，后者是打包时写入 MANIFEST.MF 的。JVM 执行 `java -jar xxx.jar` 时，根本不关心 `exec-maven-plugin` 的配置，它只看 MANIFEST.MF 里的 `Main-Class` 那一行。

---

##### archive 和 manifest 是什么


`<archive>` 和 `<manifest>` 是 `maven-assembly-plugin` 的配置项，用来描述"生成的 JAR 包内部应该有什么元数据"。

- `<archive>`：JAR 包的归档配置。它控制 JAR 包内部文件的组织方式。
- `<manifest>`：JAR 包里 `META-INF/MANIFEST.MF` 文件的内容配置。

`<archive><manifest><mainClass>JdbcDemo</mainClass></manifest></archive>` 翻译成人话就是：打包时，在 JAR 包内生成一个 `META-INF/MANIFEST.MF` 文件，在里面写入 `Main-Class: JdbcDemo`。

---

##### descriptorRefs 和 jar-with-dependencies 是什么



`maven-assembly-plugin` 支持多种打包方式（叫描述符）。每种方式生成的 JAR 包内部结构不同。

- `jar-with-dependencies` 是一个**预定义的打包描述符**。它的规则是：把你自己的编译产物 + 所有依赖 jar 包的内容解压，合并成一个巨大的 JAR 包。这个 JAR 包包含所有代码，运行时不需要外部 classpath。
- `<descriptorRefs>` 是配置项，里面可以写多个 `<descriptorRef>`，指定用哪种打包方式。

**为什么名字这么奇怪？**  
`jar-with-dependencies` 是 `maven-assembly-plugin` 插件作者命名的一个预定义描述符。不是标准 Java 术语，是插件专属的名字。它的含义就是"包含依赖的 JAR"。

---

##### executions 是什么，"在 package 阶段执行 single 目标"是什么意思



**先说阶段和目标的关系：**

- **阶段（Phase）** 是 Maven 生命周期的节点，比如 `compile`、`package`。阶段只定义"什么时候做"，不定义"怎么做"。
- **目标（Goal）** 是插件的具体动作。`maven-assembly-plugin` 有一个叫 `single` 的目标，它的功能是"按照配置生成一个 JAR 包"。

**默认情况下，`single` 目标不会自动执行。** 你执行 `mvn package`，Maven 只运行默认绑定到 `package` 阶段的打包插件（`maven-jar-plugin`，生成普通 JAR），不会运行 `maven-assembly-plugin`。你需要手动告诉它"在 package 阶段，额外执行 assembly 插件的 single 目标"。

**`<executions>` 的作用就是绑定目标到阶段：**

```xml
<executions>
    <execution>
        <phase>package</phase>
        <goals>
            <goal>single</goal>
        </goals>
    </execution>
</executions>
```

翻译成人话：当 Maven 执行到 `package` 阶段时，自动调用 `maven-assembly-plugin` 的 `single` 目标。这样你只需要执行 `mvn clean package`，Maven 既会生成普通 JAR（由默认插件处理），又会生成 Fat JAR（由你配置的 assembly 插件处理）。

---

#### 一次性理解整个 `<build>` 块



把 Maven 的 `<build>` 块想象成一个**工厂的生产车间**。

**车间里有两台机器（两个 `<plugin>`）：**

|机器|作用|
|---|---|
|`exec-maven-plugin`|运行程序（相当于"试驾"）|
|`maven-assembly-plugin`|打包程序（相当于"装箱发货"）|

每台机器都放在 `<plugins>` 这个大车间里。

---

##### exec-maven-plugin 这台机器



这台机器只有一个功能：执行 `mvn exec:java` 时，运行你的 `main` 方法。

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>       <!-- 机器品牌 -->
    <artifactId>exec-maven-plugin</artifactId>   <!-- 机器型号 -->
    <version>3.0.0</version>                     <!-- 机器版本 -->
    <configuration>                               <!-- 操作面板 -->
        <mainClass>JdbcDemo</mainClass>           <!-- 面板上唯一的按钮：运行哪个类 -->
    </configuration>
</plugin>
```

`<configuration>` 就是这台机器的操作面板。面板上只有一个旋钮叫 `<mainClass>`，你把它调到 `JdbcDemo`，机器就知道该运行哪个类。

---

##### maven-assembly-plugin 这台机器



这台机器复杂一些。它的功能是：把代码和依赖打包成一个 JAR 包。

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>  <!-- 品牌 -->
    <artifactId>maven-assembly-plugin</artifactId> <!-- 型号 -->
    <version>3.6.0</version>                      <!-- 版本 -->
    
    <configuration>                                <!-- 操作面板 -->
        <archive>                                  <!-- JAR包内部文件组织方式 -->
            <manifest>                             <!-- MANIFEST.MF 的内容 -->
                <mainClass>JdbcDemo</mainClass>    <!-- 写入 Main-Class: JdbcDemo -->
            </manifest>
        </archive>
        <descriptorRefs>                           <!-- 打包方式选择 -->
            <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
    </configuration>
    
    <executions>                                   <!-- 自动触发设置 -->
        <execution>
            <phase>package</phase>                 <!-- 在 package 阶段自动执行 -->
            <goals>
                <goal>single</goal>                <!-- 执行 single 目标 -->
            </goals>
        </execution>
    </executions>
</plugin>
```

**操作面板（`<configuration>`）上有两个设置项：**

1. **`<archive><manifest><mainClass>`**：控制 JAR 包内部 `META-INF/MANIFEST.MF` 文件的内容。`Main-Class: JdbcDemo` 这行字会被写入 MANIFEST.MF。
    
2. **`<descriptorRefs><descriptorRef>jar-with-dependencies</descriptorRef></descriptorRefs>`**：选择打包方式。`jar-with-dependencies` 这个预定义方案的意思是"把我的代码和所有依赖的 jar 包解压，合并成一个大的 JAR 文件"。
    

**自动触发（`<executions>`）：**

这台机器默认不会自己启动。你需要告诉它"什么时候自动干活"。

```xml
<executions>
    <execution>
        <phase>package</phase>    ← 当 Maven 执行到 package 阶段时，自动触发
        <goals>
            <goal>single</goal>   ← 触发后执行 single 这个动作（生成 Fat JAR）
        </goals>
    </execution>
</executions>
```

**翻译成人话：**

> 当用户执行 `mvn package` 时，别光执行默认的打包流程（生成普通 JAR），还要顺便执行 `maven-assembly-plugin` 的 `single` 目标，多生成一个包含所有依赖的 Fat JAR。

---

##### 整个 `<build>` 块全景

```
<build>                       ← 工厂车间
    <plugins>                 ← 机器清单
        <plugin>              ← 第一台机器：试驾
            ...
        </plugin>
        <plugin>              ← 第二台机器：装箱
            ...
        </plugin>
    </plugins>
</build>
```