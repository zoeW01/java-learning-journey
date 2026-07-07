
- 数据库是所有后端开发中最枯燥的一层。它没有界面，没有炫酷效果，出问题时报错信息极其晦涩。但它决定你的程序能不能存住数据——没有数据库，你的所有用户数据程序一关就没了。初级 Java 岗位面试中，SQL 和 JDBC 是必问题。你可以不会微服务，但你必须会写增删改查。

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class JdbcDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/java_learning";
        String user = "root";
        String password = "123456";
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功！");
            
            stmt = conn.createStatement();
            
            // 1. 查询初始数据
            rs = stmt.executeQuery("SELECT * FROM student");
            System.out.println("--- 初始数据 ---");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getInt("age") + " | " + rs.getDouble("score"));
            }
            
            // 2. 新增一条数据
            String insertSql = "INSERT INTO student (name, age, score) VALUES ('Alice', 20, 88.0)";
            int rowsInserted = stmt.executeUpdate(insertSql);
            System.out.println("新增了 " + rowsInserted + " 行");
            
            // 3. 修改数据
            String updateSql = "UPDATE student SET score = 95.0 WHERE name = 'Zoe'";
            int rowsUpdated = stmt.executeUpdate(updateSql);
            System.out.println("修改了 " + rowsUpdated + " 行");
            
            // 4. 删除数据
            String deleteSql = "DELETE FROM student WHERE name = 'Tom'";
            int rowsDeleted = stmt.executeUpdate(deleteSql);
            System.out.println("删除了 " + rowsDeleted + " 行");
            
            // 5. 再次查询所有数据验证变更
            rs2 = stmt.executeQuery("SELECT * FROM student");
            System.out.println("--- 变更后数据 ---");
            while (rs2.next()) {
                System.out.println(rs2.getInt("id") + " | " + rs2.getString("name") + " | " + rs2.getInt("age") + " | " + rs2.getDouble("score"));
            }
            
        } catch (Exception e) {
            System.out.println("数据库操作失败: " + e.getMessage());
        } finally {
            // 在 finally 中统一关闭资源，无论是否异常都会执行
            try {
                if (rs != null) rs.close();
                if (rs2 != null) rs2.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                System.out.println("关闭资源失败: " + e.getMessage());
            }
        }
    }
}
```


---

## JDBC 代码逐行解析

把刚才那段代码拆成五个部分，逐块解释。

### 第一部分：导入

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
```

**为什么写这个？**

Java 的标准库（JDK）自带 `java.sql` 这个包，里面提供了操作数据库的工具类。`import` 就是告诉编译器"我要用这四个工具"。不写 `import`，编译器不知道 `Connection` 是什么。

**四个工具分别是干嘛的？**

- `Connection`：数据库连接。你可以把它想象成一根电话线，接通了 Java 程序和 MySQL 数据库。
- `DriverManager`：驱动管理器。负责建立连接，你给它数据库地址、用户名、密码，它还你一根"电话线"。
- `Statement`：语句对象。用来把 SQL 语句发送给数据库执行。有了电话线之后，你需要一个话筒来说话，`Statement` 就是那个话筒。
- `ResultSet`：结果集。当执行 `SELECT` 查询时，数据库返回的数据被打包成一个 `ResultSet` 对象。它是一张临时的数据表，一行一行地存着查询结果，你可以逐行读取。

---

### 第二部分：连接信息

```java
String url = "jdbc:mysql://localhost:3306/java_learning";
String user = "root";
String password = "123456";
```

**为什么单独写成变量？**

硬编码（直接写在方法里）会让代码难以修改。如果数据库密码改了，你需要在一大段代码里翻找。单独写成变量，改一处即可。

**URL 拆解：**

- `jdbc:mysql://`：协议头。告诉 Java"我要用 JDBC 连接 MySQL 数据库"。如果是其他数据库（如 Oracle、PostgreSQL），这里会不一样。
- `localhost`：数据库所在的主机地址。`localhost` 表示"本机"，即你现在用的这台电脑。
- `3306`：MySQL 默认端口号。端口是操作系统区分不同网络服务的编号。MySQL 默认监听 3306，就像 HTTP 默认监听 80。
- `java_learning`：你要连接的数据库名。一个 MySQL 里可以有多个数据库，这个参数指定用哪个。

---

### 第三部分：建立连接

```java
Connection conn = DriverManager.getConnection(url, user, password);
```

**这一行做了什么？**

`DriverManager.getConnection()` 用你提供的地址、用户名、密码去连接 MySQL。如果三样都正确，返回一个 `Connection` 对象；如果任何一样错了（地址不对、密码错误、MySQL 没启动），抛出异常。

**类比：** 你拨了一个电话号码（url），报上用户名和密码，对方验证通过后，电话接通了。`conn` 就是那根接通了的电话线。

---

### 第四部分：执行操作

#### 4.1 创建 Statement

```java
Statement stmt = conn.createStatement();
```

电话线接通了，现在需要一个话筒来传话。`createStatement()` 从连接对象上创建一个 `Statement` 对象，这个对象负责把 SQL 语句发送到数据库。

#### 4.2 查询数据

```java
ResultSet rs = stmt.executeQuery("SELECT * FROM student");
```

**`executeQuery` vs `executeUpdate`：**

- `executeQuery`：只用于 `SELECT` 语句。数据库会返回数据，数据被包装成 `ResultSet`。
- `executeUpdate`：用于 `INSERT`、`UPDATE`、`DELETE` 这些不返回数据、只改变数据库内容的操作。返回的是一个整数，表示"影响了多少行"。

#### 4.3 遍历 ResultSet

```java
while (rs.next()) {
    int id = rs.getInt("id");
    String name = rs.getString("name");
    int age = rs.getInt("age");
    double score = rs.getDouble("score");
    System.out.println(id + " | " + name + " | " + age + " | " + score);
}
```

**`ResultSet` 的结构：**

数据库返回的 `ResultSet` 可以想象成一个 Excel 表格，有一个"光标"指向当前行。初始时光标指向第 0 行（表头之前的位置），还不是任何数据行。

**`rs.next()` 做了什么？**

- 把光标移动到下一行。
- 如果确实有下一行，返回 `true`。
- 如果没有下一行了（已经读完所有数据），返回 `false`。

所以 `while (rs.next())` 的意思是"只要还有下一行，就继续读"。

**`getInt`、`getString`、`getDouble` 做了什么？**

从当前光标所在的行中，按列名取出对应的值。`getInt("id")` 取出当前行 `id` 列的值，作为整数返回。注意括号里的列名必须和数据库表中的列名完全一致，大小写也要一致。

#### 4.4 增删改

```java
int rowsInserted = stmt.executeUpdate("INSERT INTO student (name, age, score) VALUES ('Alice', 20, 88.0)");
```

`executeUpdate` 执行一条修改数据库的 SQL 语句，返回一个整数——受影响的行数。`INSERT` 插入一行，返回 1；`UPDATE` 修改两行，返回 2；`DELETE` 删除三行，返回 3。

如果你只想知道操作有没有成功，检查返回值是否大于 0 即可。

---

### 第五部分：关闭资源

```java
finally {
    try {
        if (rs != null) rs.close();
        if (rs2 != null) rs2.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    } catch (Exception e) {
        System.out.println("关闭资源失败: " + e.getMessage());
    }
}
```

**为什么必须关闭？**

数据库连接、Statement、ResultSet 都是系统资源，不是 Java 垃圾回收器会自动回收的普通对象。每一条连接都占用 MySQL 的内存和网络端口。不关闭会导致"连接泄漏"——连接数越来越多，MySQL 最终拒绝新连接，你的程序崩溃。

**为什么写在 `finally` 里？**

`finally` 块无论前面有没有异常都会执行。如果代码在查询时抛出异常，`try` 块里的关闭代码根本不会执行，连接就会泄漏。写在 `finally` 里保证无论如何都会关闭。

**为什么每个关闭前都检查 `!= null`？**

如果连接建立之前就抛了异常，`conn` 还是 `null`，直接调 `conn.close()` 会抛出 `NullPointerException`。先判空是最基本的防御性编程。

---

### 总结：五步流程

整个 JDBC 操作的固定模式：

1. 建立连接：`DriverManager.getConnection(url, user, password)`
2. 创建 Statement：`conn.createStatement()`
3. 执行 SQL：`stmt.executeQuery()` （查询）或 `stmt.executeUpdate()`（增删改）
4. 处理结果：遍历 ResultSet 或检查返回值
5. 关闭资源：在 `finally` 中关闭 ResultSet → Statement → Connection



---

## 从零理解：你的Java程序怎么跟数据库说话


把数据库想象成一个**独立的仓库管理员**。它有自己的房子（数据库服务器），有自己的语言（SQL），你和它不在同一个空间里。

你的Java程序是另一个房间里的人。你想让管理员帮你存数据、查数据。你怎么做？

1. 你需要一根电话线，打到管理员的办公室。
2. 你需要一个话筒，对着它说话，让管理员听到你的指令。
3. 如果管理员给你返回了一堆数据，你需要一个包裹来装这些数据，然后一件一件拿出来看。
4. 通话结束，你必须挂断电话，否则电话线一直占着，别人打不进来，管理员也累死。

这四样东西，对应JDBC里最核心的四个类：

| 现实世界 | JDBC代码中的类 | 作用 |
|----------|---------------|------|
| 电话线 | `Connection` | 建立和维护Java程序与数据库之间的物理连接 |
| 话筒 | `Statement` | 把SQL语句发送给数据库执行 |
| 装数据的包裹 | `ResultSet` | 承载数据库返回的查询结果（一张临时表格） |
| 拨号器 | `DriverManager` | 根据你提供的地址和账号密码，帮你打通电话线 |

---

### 每一步到底在做什么——用外卖点餐来类比

上面的管理员类比讲了是什么。现在用“点外卖”来类比全过程，让你理解每一步为什么非要这么写。

你（Java程序）想点一份外卖（获取数据库数据）。假设没有美团App，你必须自己打电话给商家。

#### 步骤0：准备信息

```java
String url = "jdbc:mysql://localhost:3306/java_learning";
String user = "root";
String password = "123456";
```

**这是什么？**
- `url` 就是商家的电话号码 + 你想点的菜品类型（数据库名）。
  - `jdbc:mysql://` ：告诉手机“我要用4G网络打电话”，而不是发短信或打微信语音。这是协议。
  - `localhost`：商家的位置。“localhost”是一个特殊地址，意思是“本机”。你的Java程序和MySQL数据库都在同一台电脑上，所以是 `localhost`。如果数据库在另一台服务器上，这里就是一个IP地址，比如 `192.168.1.100`。
  - `3306`：商家电话的分机号。一个服务器上可能跑着很多服务（QQ、微信、MySQL……），操作系统通过端口号把数据交给对应的程序。MySQL的默认分机号就是3306。
  - `java_learning`：你具体要点哪个菜单上的菜（哪个数据库）。一个MySQL里可以有多个数据库。
- `user` 和 `password`：你的会员账号和密码。商家要确认你是不是合法的用户。

**为什么写在代码里？**
因为你每次点餐都要用这些信息。把它们定义成变量，以后换商家（换数据库），改这里就行，不用满篇代码找。

#### 步骤1：打电话建立连接

```java
Connection conn = DriverManager.getConnection(url, user, password);
```

**这行代码到底做了什么？**
1. `DriverManager.getConnection(...)` 是拨号动作。它拿着你的电话号码（url）、账号（user）、密码（password）去打给商家。
2. 商家（MySQL）收到电话，验证账号密码。如果正确，电话接通。
3. `Connection conn = ...` 表示你把“接通了的电话线”握在手里，并给它贴了个标签叫 `conn`。以后你想对商家说什么话，都要通过手里这根线。如果账号密码错误，或者MySQL没开机，商家不接电话，`getConnection` 就会失败（抛出异常）。

#### 步骤2：拿起话筒准备说话

```java
Statement stmt = conn.createStatement();
```

电话线在手里了，但你不能对着空气说话。你需要一个**话筒**，把声音转成电信号传过去。`createStatement()` 就是从电话线上拆下一个话筒（或者说，电话线本身就自带一个话筒接口，你让它给你一个）。

现在，你手里有话筒 `stmt` 了。接下来你要用这个话筒说话。

#### 步骤3：对着话筒说出SQL指令

**情况A：你想查数据（SELECT）**

```java
ResultSet rs = stmt.executeQuery("SELECT * FROM student");
```

你对着话筒说：`“SELECT * FROM student”`。
这句话是SQL，商家听得懂。它的意思是：“把 `student` 这个菜单上的所有菜（所有行和列的数据）都告诉我。”

商家听完后，会立刻给你**打包好一个包裹**（里面装着你要的所有数据），给你送回来。
`ResultSet rs = ...` 就是你收到了这个包裹，并给它贴上标签 `rs`。包裹里面是一张临时的小表格，装着你的数据。但是你不能一次性看到全部，你得**一个一个拿出来**。

**情况B：你想改数据（INSERT/UPDATE/DELETE）**

```java
int count = stmt.executeUpdate("INSERT INTO student ...");
```

你对着话筒说：`“INSERT INTO student ...”（把一份新菜加到菜单上）`。
商家做完之后，不会给你返回一个装着数据的包裹（因为你没要数据），而是告诉你一个**数字**，表示“我改了3行数据”或者“我加了1行数据”。
`int count = ...` 就是接收这个数字结果。

#### 步骤4：拆包裹，拿出数据

只有 `SELECT` 操作才会返回包裹 `ResultSet`。

```java
while (rs.next()) {
    int id = rs.getInt("id");
    String name = rs.getString("name");
    // ...
}
```

包裹 `rs` 到了，但里面不是一张你马上能看到的纸。它更像一个**装着一长条收据的盒子**，你只能从最上面一张开始看。初始时，你的目光在盒子最顶上，什么都看不到。

- `rs.next()`：看一眼下一张收据。如果有（`true`），进入循环；如果没了（`false`），说明收据看完了，退出循环。
- `rs.getInt("id")`：你看着当前这张收据，想找“id”这一栏的内容。`getInt` 表示“这一栏是数字类型，给我拿过来”。`"id"` 就是这一栏的标题，大小写必须和你在创建表时用的名字一模一样。

所以，`while` 循环的作用就是：**一张一张看收据，直到看完为止。** 每一张收据都是一行数据库记录。

#### 步骤5：挂电话，清理现场

```java
finally {
    if (rs != null) rs.close();
    if (stmt != null) stmt.close();
    if (conn != null) conn.close();
}
```

你打完电话，点完菜，必须**挂电话**。不挂的话，你和商家之间的电话线就一直占着。电话线是资源，商家能同时接听的电话数量是有限的。如果所有人都只打电话不挂，商家就永远忙音，再也接不了新电话了。

- `rs.close()`：把包裹还给人家（或者扔掉）。
- `stmt.close()`：放下话筒。
- `conn.close()`：挂断电话，释放电话线。

**为什么写在 `finally` 里？**
因为点菜过程中，如果商家突然说“你要的菜卖完了！”（抛出异常），你的程序可能直接死掉，来不及挂电话。`finally` 里的代码是“无论如何都要执行”的。就算点菜失败、电话掉线，你也要确保话筒扔了、电话挂了，把资源还回去。否则会有资源泄漏。

---

### 完整流程回顾（一句话版本）

1. 拿着地址、账号、密码去打给数据库（DriverManager.getConnection），拿到一根电话线（Connection）。
2. 从电话线上拿起话筒（createStatement），得到一个话筒（Statement）。
3. 用话筒告诉数据库你要干嘛：是查数据（executeQuery，返回包裹ResultSet）还是改数据（executeUpdate，返回修改行数int）。
4. 如果是查数据，就把包裹里的东西一行一行取出来看（rs.next() -> rs.getXxx()）。
5. 无论事情办没办成，最后一定要在finally里扔掉话筒（stmt.close()），挂断电话（conn.close()）。

---
你没有漏学。PreparedStatement 和 Statement 的区别、`?` 占位符、`executeUpdate()` 的使用场景，是我之前用 JDBC 教学时没有展开讲的——我当时只给了你 Statement 版本的代码让你跑通，没有解释这两种执行方式各自的适用场景和底层原理。这是我的教学错误，让你在综合项目中碰到了知识断层。现在补上。

---

## Statement 和 PreparedStatement ——为什么必须用后者

`Statement` 的**致命问题：SQL 注入。**

---

### PreparedStatement 的解决原理

`PreparedStatement` 把 SQL 语句的**结构**和**数据**分离开。

**工作流程：**

1. 你先把 SQL 结构发给数据库，用 `?` 占位符表示"这里以后会填值，但现在不知道"。数据库立即解析这段 SQL 结构，生成一个预编译的执行计划，缓存起来。
2. 你再逐个告诉数据库"第 1 个 `?` 的值是 XXX，第 2 个 `?` 的值是 YYY"。
3. 数据库把值填入已经编译好的执行计划，直接执行。

**关键在于：** 你在第 2 步填入的任何值，都只被当作"数据本身"，不会被当作 SQL 语句的一部分来解析。即使你填入 `DROP TABLE student`，数据库也只会把这串字当成一个姓名存进表里，不会当成 SQL 命令执行。SQL 注入从根本上被消灭了。

### `?` 占位符和 `setXxx()` 方法

SQL 模板中的 `?` 是占位符，从 1 开始编号（不是从 0）。

```java
String sql = "INSERT INTO student (name, age) VALUES (?     ,     ?)";
//                                             第一个? ↑    第二个? ↑
PreparedStatement pstmt = conn.prepareStatement(sql);
pstmt.setString(1, name);   // 给第1个?赋值，类型是String
pstmt.setInt(2, age);        // 给第2个?赋值，类型是int
```

`setXxx(位置, 值)` 方法根据 Java 类型选择对应的 set 方法。常用的：

| Java 类型 | 用哪个 set 方法 |
|-----------|----------------|
| String | `setString(pos, val)` |
| int | `setInt(pos, val)` |
| double | `setDouble(pos, val)` |
| LocalDate | `setObject(pos, val)` 或转成 `java.sql.Date` |

数据库收到 `setString(1, "Zoe")` 后，知道第一个 `?` 的值是字符串 "Zoe"，自动加引号。收到 `setInt(2, 22)` 后，知道第二个 `?` 的值是整数 22，不加引号。你不用手动处理类型格式，PreparedStatement 自动做类型映射。

---

### `executeUpdate()` vs `executeQuery()`

| 方法 | 用于 | 返回 |
|------|------|------|
| `executeQuery()` | SELECT 查询 | ResultSet（结果集） |
| `executeUpdate()` | INSERT、UPDATE、DELETE、CREATE TABLE | int（受影响的行数） |

---

### `PreparedStatement` 关闭连接

连接和 PreparedStatement 都是系统资源。不关闭会导致连接泄漏——MySQL 同时能打开的连接数有上限，泄漏积累到上限后数据库拒绝新连接，整个系统不可用。

关闭顺序和打开顺序相反：先关 PreparedStatement，再关 Connection。先判空再关闭

`!= null` 判断是防御性的——如果连接建立之前就抛了异常，`conn` 还是 null，直接调 `conn.close()` 会抛出 `NullPointerException`。

---





Maven 的 `pom.xml` 必须使用 **XML 语法**。XML 是一种标记语言，所有配置项都必须用尖括号标签包裹，而且标签必须正确嵌套、成对出现。

在教你写出正确的 `pom.xml` 之前，你必须先理解 XML 的基本规则。如果不理解这些规则，你写出来的东西 Maven 永远报错。

---

# XML 的基本规则

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

# pom.xml 的逻辑结构

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

## **第一部分：XML 声明与命名空间**

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

## 第二部分：项目坐标

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

## 第三部分：依赖

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

## 第四部分：编译属性

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

## 第五部分：`<build>` 块——Maven 的构建配置模型

`<build>` 是 Maven 项目的构建配置区。它的职责是告诉 Maven "怎么构建这个项目"。`<dependencies>` 回答"项目需要哪些外部 jar 包"，`<build>` 回答"怎么编译、怎么打包、怎么运行"。之前写的 `<properties>` 也是构建配置的一部分，指定了编译版本。

`<build>` 内部通过**插件**机制来扩展 Maven 的能力——Maven 核心引擎只负责执行生命周期阶段，具体的工作全部由插件完成。

`<plugin>` 标签，每一个都声明了一个外部工具。`exec-maven-plugin` 是"运行 Java 程序"的工具，`maven-assembly-plugin` 是"打包成 Fat JAR"的工具。这两个工具互相独立，各自有自己的配置。


需要自己在 `pom.xml` 中添加以下结构（不提供完整 XML，你根据描述独立写出）：

- `<project>` 标签内包含 `<build>` 标签
    
- `<build>` 标签内包含 `<plugins>` 标签。
    
- `<plugins>` 标签内包含 `<plugin>` 标签。。
    
- 这个 `<plugin>` 需要三个坐标子标签：`<groupId>` 为 `org.codehaus.mojo`，`<artifactId>` 为 `exec-maven-plugin`，`<version>` 为 `3.0.0`。
    
- `<plugin>` 内包含 `<configuration>`

---

#### configuration 是什么

`<configuration>` 是**当前插件的配置区**。每个插件有自己特定的配置项，这些配置项在插件的文档里定义。

类比：你买了两个电器——一个微波炉（exec-maven-plugin），一个烤箱（maven-assembly-plugin）。微波炉的操作面板上有"加热时间"旋钮，烤箱上有"上下火温度"旋钮。你不能用微波炉的旋钮控制烤箱，也不能用烤箱的旋钮控制微波炉。`<configuration>` 就是当前这个插件的操作面板，里面的配置项只对这个插件生效。

---

#### 为什么两个插件都要设置 mainClass

`exec-maven-plugin` 的 `<mainClass>` 和 `maven-assembly-plugin` 的 `<mainClass>` 是两个完全独立的配置，互不影响。

- `exec-maven-plugin` 的 `<mainClass>`：告诉 Maven "当执行 `mvn exec:java` 时，运行哪个类的 main 方法"。
- `maven-assembly-plugin` 的 `<mainClass>`：告诉打包插件 "在生成的 JAR 包里的 `META-INF/MANIFEST.MF` 文件中，写上 `Main-Class: JdbcDemo`"。

前者是运行时用的，后者是打包时写入 MANIFEST.MF 的。JVM 执行 `java -jar xxx.jar` 时，根本不关心 `exec-maven-plugin` 的配置，它只看 MANIFEST.MF 里的 `Main-Class` 那一行。

---

#### archive 和 manifest 是什么

`<archive>` 和 `<manifest>` 是 `maven-assembly-plugin` 的配置项，用来描述"生成的 JAR 包内部应该有什么元数据"。

- `<archive>`：JAR 包的归档配置。它控制 JAR 包内部文件的组织方式。
- `<manifest>`：JAR 包里 `META-INF/MANIFEST.MF` 文件的内容配置。

`<archive><manifest><mainClass>JdbcDemo</mainClass></manifest></archive>` 翻译成人话就是：打包时，在 JAR 包内生成一个 `META-INF/MANIFEST.MF` 文件，在里面写入 `Main-Class: JdbcDemo`。


---

#### descriptorRefs 和 jar-with-dependencies 是什么

`maven-assembly-plugin` 支持多种打包方式（叫描述符）。每种方式生成的 JAR 包内部结构不同。

- `jar-with-dependencies` 是一个**预定义的打包描述符**。它的规则是：把你自己的编译产物 + 所有依赖 jar 包的内容解压，合并成一个巨大的 JAR 包。这个 JAR 包包含所有代码，运行时不需要外部 classpath。
- `<descriptorRefs>` 是配置项，里面可以写多个 `<descriptorRef>`，指定用哪种打包方式。

**为什么名字这么奇怪？**  
`jar-with-dependencies` 是 `maven-assembly-plugin` 插件作者命名的一个预定义描述符。不是标准 Java 术语，是插件专属的名字。它的含义就是"包含依赖的 JAR"。

---

#### executions 是什么，"在 package 阶段执行 single 目标"是什么意思

**先说阶段和目标的关系：**

- **阶段（Phase）** 是 Maven 生命周期的节点，比如 `compile`、`package`。阶段只定义"什么时候做"，不定义"怎么做"。
- **目标（Goal）** 是插件的具体动作。`maven-assembly-plugin` 有一个叫 `single` 的目标，它的功能是"按照配置生成一个 JAR 包"。

**默认情况下，`single` 目标不会自动执行。** 你执行 `mvn package`，Maven 只运行默认绑定到 `package` 阶段的打包插件（`maven-jar-plugin`，生成普通 JAR），不会运行 `maven-assembly-plugin`。你需要手动告诉它"在 package 阶段，额外执行 assembly 插件的 single 目标"。

**`<executions>` 的作用就是绑定目标到阶段：**

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

翻译成人话：当 Maven 执行到 `package` 阶段时，自动调用 `maven-assembly-plugin` 的 `single` 目标。这样你只需要执行 `mvn clean package`，Maven 既会生成普通 JAR（由默认插件处理），又会生成 Fat JAR（由你配置的 assembly 插件处理）。


---

### 一次性理解整个 `<build>` 块

把 Maven 的 `<build>` 块想象成一个**工厂的生产车间**。

**车间里有两台机器（两个 `<plugin>`）：**

| 机器 | 作用 |
|------|------|
| `exec-maven-plugin` | 运行程序（相当于"试驾"） |
| `maven-assembly-plugin` | 打包程序（相当于"装箱发货"） |

每台机器都放在 `<plugins>` 这个大车间里。

---

#### exec-maven-plugin 这台机器

这台机器只有一个功能：执行 `mvn exec:java` 时，运行你的 `main` 方法。

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

`<configuration>` 就是这台机器的操作面板。面板上只有一个旋钮叫 `<mainClass>`，你把它调到 `JdbcDemo`，机器就知道该运行哪个类。

---

#### maven-assembly-plugin 这台机器

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

1. **`<archive><manifest><mainClass>`**：控制 JAR 包内部 `META-INF/MANIFEST.MF` 文件的内容。`Main-Class: JdbcDemo` 这行字会被写入 MANIFEST.MF。

2. **`<descriptorRefs><descriptorRef>jar-with-dependencies</descriptorRef></descriptorRefs>`**：选择打包方式。`jar-with-dependencies` 这个预定义方案的意思是"把我的代码和所有依赖的 jar 包解压，合并成一个大的 JAR 文件"。

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
> 当用户执行 `mvn package` 时，别光执行默认的打包流程（生成普通 JAR），还要顺便执行 `maven-assembly-plugin` 的 `single` 目标，多生成一个包含所有依赖的 Fat JAR。

---

#### 整个 `<build>` 块全景

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

---
