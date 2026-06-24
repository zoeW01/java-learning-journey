
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
- `artifactId`：项目名。比如 `mysql-connector-j` 表示这是 MySQL 的 Java 连接器。
- `version`：版本号。比如 `8.0.33`。

这三个坐标合在一起，就像 GPS 坐标一样，在全球范围内唯一确定一个 jar 包：

```
groupId: com.mysql
artifactId: mysql-connector-j
version: 8.0.33
```

你告诉 Maven 这三个坐标，Maven 就知道该下载什么、从哪里下载。

**2.2 中央仓库**

Maven 有一个全球公用的中央仓库（Maven Central Repository），地址是 `https://repo1.maven.org/maven2/`。几乎所有的 Java 开源 jar 包都发布到这里。

当你指定了坐标，Maven 会自动去中央仓库查找对应的 jar 包，下载到你本地的"本地仓库"里。

**2.3 本地仓库**

Maven 在你电脑上有一个本地仓库目录，默认是 `~/.m2/repository/`。下载过的 jar 包都会缓存在这里。下次再用到同一个 jar 包，直接从本地仓库拿，不用重新下载。

**类比：**
- 中央仓库 = 图书馆
- 本地仓库 = 你的书架
- 坐标 = 书的 ISBN 号

你告诉 Maven 一个 ISBN 号，它先去你书架上找。有就直接用，没有就去图书馆借一本回来放书架上。

**2.4 传递性依赖的自动管理**

你声明"我需要 A.jar"。Maven 去查 A.jar 的元数据（pom 文件），发现 A 依赖 B，B 依赖 C。Maven 自动把 A、B、C 全部下载到本地仓库，并放到 classpath 里。你不用知道 B 和 C 的存在。

**2.5 依赖冲突的仲裁机制**

当 X 要 `commons-logging 1.0`、Y 要 `commons-logging 2.0` 时，Maven 遵循两个原则：

1. **就近优先**：离项目越近的版本越优先。直接声明的版本 > 间接传递来的版本。
2. **先声明优先**：如果两个版本都在同一层，谁在配置文件中先出现就用谁。

**2.6 统一构建生命周期**

Maven 定义了标准的构建生命周期，分三个阶段：

- **clean**：清理之前的编译结果（删除 `target/` 目录）
- **compile**：编译源代码（`javac`）
- **package**：编译 + 打包成 JAR 或 WAR

不管你在 Windows、Mac 还是 Linux 上，`mvn clean compile` 做的事情完全一样。这解决了"构建流程不统一"的问题。

2.7

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

[[第九课：XML 规则]]

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

**第四步：编译运行**

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="JdbcDemo"
```

注意：
- Maven 的所有命令（`mvn compile`、`mvn package` 等）必须在 `pom.xml` 所在的目录下执行。
- `exec:java` 需要 `exec-maven-plugin`，这个暂时先不引入。你可以先只执行 `mvn clean compile` 验证编译通过，然后用传统的 `java -cp` 指定 classpath 来运行——但这次 classpath 里包含的是 Maven 下载到本地仓库的 jar 包，而不是你手动下载的那个。

---

现在，独立写出 `pom.xml` 文件，创建正确的目录结构，把 JDBC 代码放进去。用 `mvn clean compile` 验证编译通过。把 `pom.xml` 的内容和编译输出贴给我。