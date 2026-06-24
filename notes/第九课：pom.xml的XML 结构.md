
Maven 的 `pom.xml` 必须使用 **XML 语法**。XML 是一种标记语言，所有配置项都必须用尖括号标签包裹，而且标签必须正确嵌套、成对出现。

在教你写出正确的 `pom.xml` 之前，你必须先理解 XML 的基本规则。如果不理解这些规则，你写出来的东西 Maven 永远报错。

---

### XML 的基本规则

1. **标签**：用 `<` 和 `>` 包围一个名字，例如 `<name>`。
2. **开标签和闭标签**：开标签 `<name>`，闭标签 `</name>`，两者之间放内容。例如 `<name>Zoe</name>`。
3. **自闭合标签**：如果没有内容，可以写成 `<tag />`。例如 `<empty />`。
4. **嵌套**：标签可以包含其他标签，必须正确嵌套。`<a><b></b></a>` 是对的，`<a><b></a></b>` 是错的。
5. **属性**：开标签里可以有 `<tag 属性="值">`，例如 `<project xmlns="...">`。
6. **声明**：XML 文件第一行通常是 `<?xml version="1.0" encoding="UTF-8"?>`。
7. **根元素**：一个合法的 XML 文件必须有且只有一个根元素，包裹所有其他元素。在 `pom.xml` 里根元素是 `<project>`。
8. **命名空间声明**：`<project>` 标签上有命名空间声明
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">
```

---

### pom.xml 的逻辑结构

你现在需要告诉 Maven 两件事：

**A. 这个项目是谁的？**  
用 `groupId`、`artifactId`、`version` 三个子标签，全部包在 `<project>` 根标签下。它们通常放在 `<modelVersion>`（固定值 `4.0.0`）之后。
	Maven 的 `pom.xml` 根元素 `<project>` 下第一项必须是 `<modelVersion>4.0.0</modelVersion>`。这个字段告诉 Maven 使用的是 pom 模型的哪个版本。4.0.0 是当前 Maven 2 和 3 唯一支持的版本号。

**B. 这个项目依赖哪些 jar 包？**  
用 `<dependencies>` 标签包裹若干个 `<dependency>` 标签。每个 `<dependency>` 里必须包含 `<groupId>`、`<artifactId>`、`<version>` 三个子标签，用来指定依赖的坐标。

**C. 用哪个 Java 版本编译？**  
用 `<properties>` 标签，里面定义 `<maven.compiler.source>` 和 `<maven.compiler.target>` 两个子标签，值都是 `17`。

---

**现在对这个 `pom.xml` 进行逐行解析，让你理解每一行的作用。**

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

**第一部分：XML 声明与命名空间**

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

XML 允许任何人自定义标签。两个人可能都定义了 `<project>` 标签，但含义完全不同。命名空间用 URI（统一资源标识符）来区分“你这个 `<project>` 是 Maven 的 `<project>`，不是别的工具的 `<project>`”。

- `xmlns="http://maven.apache.org/POM/4.0.0"`：声明默认命名空间。从这个标签开始的 `<project>` 及其所有子标签，都属于这个 Maven POM 命名空间。,同时告诉 XML 解析器"这个 `<project>` 标签及其子标签，都遵循 Maven POM 4.0.0 规范"。
- `xmlns:xsi`：引入 XML Schema Instance 命名空间，用于指定这个 XML 文件应该按哪个规范文件（XSD）来验证结构，指定验证文件的位置。
- **`xsi:schemaLocation`：** 指定"Maven POM 4.0.0 命名空间的验证文件在哪里"。值是键值对格式：`命名空间URI 验证文件URL`。Maven 在解析 `pom.xml` 时，根据这个信息找到对应的 XSD 验证文件，检查你的标签是否合法。

类比：命名空间就像给标签加了姓。`<project>` 本身是一个名字，加上 `xmlns="...maven..."` 就变成了“Maven 家的 project”。如果另一个工具也定义了 `<project>`，它的命名空间不同，就不会混淆。

---

### 第二部分：项目坐标

```xml
<modelVersion>4.0.0</modelVersion>
```
POM 模型自身的版本号。这不是项目的版本，是 POM 规范本身的版本。Maven 2 和 Maven 3 都使用 4.0.0。如果将来 Maven 发布新的大版本改变了 POM 规范，这个数字会变。

```xml
<groupId>com.zoe</groupId>
```

组织或项目的唯一标识符。通常用反向域名格式。`com.zoe` 表示“这个项目是 zoe 开发的”。将来你发布 jar 包到 Maven 中央仓库时，`groupId` 可以唯一区分你的包和别人同名的包。

```xml
<artifactId>jdbc-demo</artifactId>
```

项目名称。`groupId` + `artifactId` 的组合必须唯一。

```xml
<version>1.0-SNAPSHOT</version>
```

版本号。`SNAPSHOT` 是一个特殊后缀，表示“开发中的不稳定版本”。Maven 对 SNAPSHOT 版本的处理策略不同——每次构建都会重新去远程仓库检查是否有最新的 SNAPSHOT 版本。当你发布正式版时，去掉 SNAPSHOT，写成 `1.0`。

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

**`<dependencies>` 是什么意思？**

`<dependencies>` 是“这个项目需要的所有外部 jar 包”的容器。里面可以包含多个 `<dependency>`。

**每一个 `<dependency>` 的作用：**

你用坐标 `com.mysql:mysql-connector-j:8.0.33` 精确指定了需要 MySQL 的 JDBC 驱动。当 Maven 执行 `compile` 或 `package` 时，它会：

1. 检查本地仓库 `~/.m2/repository/com/mysql/mysql-connector-j/8.0.33/` 是否已有这个 jar 包。
2. 如果没有，从中央仓库（`https://repo1.maven.org/maven2/`）下载。
3. 如果有，直接使用本地缓存。
4. 自动把这个 jar 包加入 classpath，你不再需要手动写 `-cp`。

**传递性依赖会自动处理：**

`mysql-connector-j` 本身依赖 `protobuf-java`（Google 的一个序列化库）。Maven 读取 `mysql-connector-j` 的 pom 文件，发现它依赖 `protobuf-java`，会自动下载 `protobuf-java` 到本地仓库。你不需要知道 `protobuf-java` 的存在。你在 `pom.xml` 里只写了一个依赖，实际 classpath 里会有两个 jar 包。

---

### 第四部分：编译属性

```xml
<properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
</properties>
```

**`<properties>` 的作用：**

定义键值对，供 Maven 和插件读取。这里定义了两个键：

- `maven.compiler.source`：源代码使用的 Java 版本。设为 17 表示你的代码里可以用 Java 17 的语法（比如 `var`、`switch` 表达式等）。
- `maven.compiler.target`：编译生成的字节码版本。设为 17 表示生成的 `.class` 文件只能在 Java 17 或更高版本的 JVM 上运行。
- 这两个键不是随意命名的——这两个键是 `maven-compiler-plugin` 插件约定的配置参数名，插件读取它们来决定用哪个 Java 版本编译。

**为什么分 source 和 target？**

理论上你可以用 Java 17 语法写代码（source 设为 17），但编译成 Java 8 能运行的字节码（target 设为 8）。这叫“交叉编译”，用来兼容旧版本服务器。但对新手来说，两个都设成 17 就行。

---

