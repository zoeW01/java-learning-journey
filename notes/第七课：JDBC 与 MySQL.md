
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

