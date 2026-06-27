# 一、SQL概述

## 1、什么是SQL
SQL 是一门独立的语言，专用于和数据库对话。SQL 全称 Structured Query Language，结构化查询语言。

它只做四件事：
- **增**：往表里插入数据
- **删**：从表里删除数据
- **改**：修改表里的数据
- **查**：从表里查询数据

所有 SQL 语句都是围绕这四件事展开的。

## 2、SQL的语法要求

- SQL 语句可以单行或多行书写，以分号结尾；
- 可以用空格和缩进来来增强语句的可读性；
- 关键字不区别大小写，建议使用大写；
## 3、SQL分类

- **DDL**（Data Definition Language）：数据定义语言，用来定义数据库对象：库、表、列等；
    
- **DML**（Data Manipulation Language）：数据操作语言，用来定义数据库记录（数据）；
    
- **DCL**（Data Control Language）：数据控制语言，用来定义访问权限和安全级别；
    
- **DQL**（Data Query Language）：数据查询语言，用来查询记录（数据）
    
- **TCL**（Transaction Control Language）：事务控制语言。`COMMIT`、`ROLLBACK`

## 4、SQL数据类型

- **整型**：
	- `TINYINT`（1字节）
	- `SMALLINT`（2字节）
	- `INT`（4字节）
	- `BIGINT`（8字节）。
	
- **浮点型**：
	- `FLOAT`（4字节）
	- `DOUBLE`（8字节）
		- 它们存的是近似值，会有精度丢失
	- `DECIMAL(m,n)`，它用字符串存储，精确保留小数。m表示总位数，n表示小数点后几位
		
- **字符串**：
	- `CHAR(n)` 定长，存不够 n 个字符就补空格，适合固定长度数据
	- `VARCHAR(n)` 变长，只存实际占用的字符加一个长度前缀，节省空间，但更新可能导致行溢出。
	- `text`
	
- **日期时间**：
	- `DATE` 存年月日，格式为：yyyy-MM-dd；
	- `TIME` 存时分秒，格式为：hh:mm:ss
	- `DATETIME` 存日期时间
		- 内部用整数存储，可比较大小。
	- `TIMESTAMP` 时间戳
## 5、约束：数据库的“宪法”
### 5.1 PRIMARY KEY——主键约束
```sql
列名 类型 PRIMARY KEY;
```
### 5.2 NOT NULL——非空约束

### 5.3 UNIQUE——唯一约束
```sql
列名 数据类型 UNIQUE;
```
### 5.4 DEFAULT——默认值约束
```sql
列名 数据类型 DEFAULT 默认值;
```
### 5.5 AUTO_INCREMENT——自增
```sql
列名 数据类型 PRIMARY KEY AUTO_INCREMENT;
```
### 5.6 FOREIGN KEY——外键约束
```sql
FOREIGN KEY (列名1) REFERENCES 表名(列名2) ON DELETE (选项) ON UPDATE (选项);
```
#### 外键约束的连带规则——ON DELETE 和 ON UPDATE
 四种策略
##### ① CASCADE（级联）
- **DELETE 行为**：删除父行时，子表中所有关联行**自动删除**。
- **UPDATE 行为**：修改父表主键时，子表外键值**自动跟随更新**。
##### ② RESTRICT（限制，默认行为）
- **如果不写 `ON DELETE` 和 `ON UPDATE`，默认就是 RESTRICT。**
- **DELETE 行为**：如果子表中有关联行，**禁止删除**父行，直接报错。
- **UPDATE 行为**：如果子表中有关联行，**禁止修改**父表主键。
##### ③ SET NULL（置空）
- **DELETE 行为**：删除父行时，子表中外键列**设为 NULL**。
- **UPDATE 行为**：修改父表主键时，子表中外键列**设为 NULL**
##### ④ NO ACTION
- 和 `RESTRICT` 几乎一样——不允许产生孤儿数据。区别在于**检查时机**。
	- `RESTRICT`：在语句执行**之前**检查，发现有关联行立即拒绝。
    
	- `NO ACTION`：在语句执行**之后**、事务提交**之前**检查。如果事务内后续操作补充了关联数据，可以继续。
### 5.7 CHECK———检查约束
```sql
列名 数据类型 CHECK (布尔表达式);
```
- 根据布尔表达式来验证列的值是否满足特定条件。
# 二、DDL——数据定义语言

DDL 不操作数据本身，而是操作存放数据的**容器结构**。

**基本操作**：
	查看所有数据库：

```sql
SHOW DATABASES;
```

	切换数据库
```sql
USE 库名;
```

## 1、操作数据库

### 1.1 创建数据库
```sql
CREATE DATABASE [IF NOT EXISTS] 库名;
```

- 例如：`CREATE DATABASE mydb1`，创建一个名为 mydb1 的数据库。如果这个数据已经存在，那么会报错。
- 例如 `CREATE DATABASE IF NOT EXISTS mydb1`，在名为 mydb1 的数据库不存在时创建该库，这样可以避免报错。

### 1.2 删除数据库
```sql
DROP DATABASE [IF EXISTS] 库名;
```

### 1.3 修改数据库编码
```sql
ALTER DATABASE 库名 CHARACTER SET utf8;
```

注意，在 MySQL 中所有的 UTF-8 编码都不能使用中间的`-`，即 UTF-8 要书写为 UTF8。

## 2、操作表
### 2.1 创建表
```sql
CREATE TABLE 表名(
	列名 数据类型 [约束], 
	列名 数据类型 [约束],
	...... 
);
```
### 2.2 查看表
```sql
DESC 表名;
```
### 2.3 删除表
```sql
DROP TABLE 表名;
```
### 2.4 修改表
 - 增删改查

---

#### 添加列
```sql
ALTER TABLE 表名 ADD 列名 数据类型 [约束];
```
- 不写 `()`，直接跟列定义。
- 可以一次添加多列：`ALTER TABLE stu ADD (列1 类型, 列2 类型);`

#### 修改列的数据类型
```sql
ALTER TABLE 表名 MODIFY 列名 新数据类型;
```
- 只能修改类型（如 `CHAR(2)`），不能同时重命名列。

#### 修改列名（同时可改类型）
```sql
ALTER TABLE 表名 CHANGE 旧列名 新列名 新数据类型;
```
- `CHANGE` 必须同时指定新旧列名和数据类型。如果不想改类型，也要照写原类型。

#### 删除列
```sql
ALTER TABLE 表名 DROP COLUMN 列名;
```
- `COLUMN` 关键字可选（`DROP 列名` 也可，但推荐保留 `COLUMN` 以清晰）。

#### 重命名表
```sql
ALTER TABLE 旧表名 RENAME TO 新表名;
```
- 也可以写成 `RENAME TABLE 旧表名 TO 新表名;`

---

**关键规则**：
- `ADD` / `MODIFY` / `CHANGE` / `DROP` / `RENAME TO` 都是 `ALTER TABLE` 的子句。
- 可以一条语句执行多个操作，用逗号分隔：
  ```sql
  ALTER TABLE stu
    ADD COLUMN classname VARCHAR(100),
    MODIFY gender CHAR(2),
    DROP COLUMN phone;
  ```
- 修改列定义时，必须重新声明该列所需的约束（如 `NOT NULL`），否则可能丢失原有约束。

# 三、DML——数据操作语言

## 1、INSERT：插入数据
### 基本语法
```sql
INSERT INTO 表名 (列1, 列2, 列3, ...) VALUES (值1, 值2, 值3, ...);
```
- 列名列表和值列表必须一一对应：个数、顺序、数据类型都要匹配。
- 可以为指定列插入，其他列取默认值（DEFAULT 指定的值，或 NULL）。
### 全列插入
```sql
INSERT INTO 表名 VALUES (值1, 值2, 值3, ...);
```
- 如果按表定义的顺序给所有列都赋值（包括自增主键），可以省掉列名
###  批量插入
```sql
INSERT INTO 表名 (列1, 列2, 列3, ...) VALUES 
(值1, 值2, 值3, ...),
(值1, 值2, 值3, ...),
(值1, 值2, 值3, ...);
```
## 2、UPDATE：修改数据
### 基本语法
```sql
UPDATE 表名 SET 列1 = 新值1, 列2 = 新值2, ... [WHERE 条件];
```
- `SET` 子句：指定要修改的列和对应的新值。
- `WHERE` 子句：指定哪些行需要被修改。
## 3、DELETE：删除数据
### 基本语法
```sql
DELETE FROM 表名 [WHERE 条件];
```
- **必须带 `WHERE`**，否则删除全表所有行（表结构保留，数据全空）。
### `TRUNCATE`删除整张表
 ```sql
TRUNCATE TABLE student;
 ```
# 四、DCL——数据控制语言
## 权限体系

|层级|作用范围|示例|
|---|---|---|
|全局权限|整个 MySQL 服务器|`CREATE USER`、`SHUTDOWN`|
|数据库权限|某个数据库|对 `java_learning` 的所有操作|
|表权限|某个数据库的某张表|只能操作 `student` 表|
|列权限|某张表的某几列|只能查看 `name` 和 `phone`，不能看其他列|
|存储过程权限|某个存储过程或函数|`EXECUTE`|
## 1、CREATE USER：创建用户
```sql
CREATE USER '用户名'@主机名 IDENTIFIED BY '密码';
```
## 2、GRANT：授予权限
```sql
GRANT 权限列表 ON 数据库.表名 TO '用户名'@主机名 [IDENTIFIED BY '密码'] [WITH GRANT OPTION];
```
- `权限列表`：用逗号分隔多个权限，或用 `ALL PRIVILEGES` 表示所有权限
- `ON 数据库.表名`：指定权限作用于哪个数据库和表。`*.*` 表示所有数据库的所有表。`*`表示所有
-  `TO '用户名'@'主机名'`：指定用户和允许从哪台机器连接。`'localhost'` 表示只能本机连接。`'%'` 表示任意主机。
- `IDENTIFIED BY '密码'`：如果用户不存在则创建，同时设置密码。
- `WITH GRANT OPTION`：允许该用户把自己拥有的权限转授给别人。
## 3、REVOKE：撤销权限
```sql
REVOKE 权限列表 ON 数据库.表名 FROM '用户名'@主机名;
```
## 4、SHOW：查看权限
```sql
SHOW GRANTS FOR '用户名'@主机名;
```
- `SHOW GRANTS;` 查看所有权限
## 5、DROP USER：删除用户
```sql
DROP USER '用户名'@主机名;
```
- 同时撤销所有权限并删除用户记录
## 6、ALTER USER：修改密码
```sql
ALTER USER '用户名'@主机名' IDENTIFIED BY '新密码';
```

# 五、DQL——数据查询语言
## 1、SELECT的完整语法结构
```sql
SELECT [DISTINCT] 列名1, 列名2, 聚合函数(列名3)
FROM 表名
[JOIN 其他表 ON 连接条件]
[WHERE 行过滤条件]
[GROUP BY 分组列]
[HAVING 分组后过滤条件]
[ORDER BY 排序列 [ASC|DESC]]
[LIMIT 偏移量, 行数];
```
**关键规则：子句的书写顺序是固定的，不能调换。** 你不能把 `WHERE` 写在 `GROUP BY` 后面，也不能把 `ORDER BY` 写在 `WHERE` 前面。
## 2、逻辑执行顺序
据库实际执行这些子句的顺序，和书写的顺序**完全不同**。

**书写顺序**：

```text
SELECT → FROM → JOIN → WHERE → GROUP BY → HAVING → ORDER BY → LIMIT
```

**逻辑执行顺序**：

```text
1. FROM        → 确定数据来源表，以及 JOIN 产生的虚拟表
2. WHERE       → 对每一行进行条件过滤
3. GROUP BY    → 将过滤后的行分组
4. HAVING      → 对分组后的结果进行过滤
5. SELECT      → 选择要输出的列，计算聚合函数和表达式
6. ORDER BY    → 对输出结果排序
7. LIMIT       → 截取指定行数
```

**为什么理解这个顺序至关重要？**

- `WHERE` 在 `GROUP BY` 之前执行，所以 **`WHERE` 子句中不能使用聚合函数**
    
- `HAVING` 在 `GROUP BY` 之后执行，所以 `HAVING` 中可以使用聚合函数。
    
- `SELECT` 中定义的别名在 `WHERE` 中不能用（因为 `SELECT` 执行在 `WHERE` 之后），但可以在 `ORDER BY` 和 `LIMIT` 中使用（因为它们执行在 `SELECT` 之后）。
## 3、基础查询

**基本格式：**
```sql
SELECT 列名1, 列名2 FROM 表名;
```

**查所有列**
```sql
SELECT * FROM student;
```
`*` 是通配符，表示所有

### 3.1 AS：别名
```sql
SELECT 列名1 AS 别名1, 列名2 AS 别名2 FROM 表名;
```
- `AS` 可以省略，但不推荐
## 4、DISTINCT：去重
```sql
SELECT DISTINCT 列名 FROM 表名;
```
## 5、JOIN：多表连接查询
### 5.1 `JOIN` 的完整语法结构
```sql
SELECT 列列表
FROM 表1 别名1
[连接类型] JOIN 表2 别名2 ON 连接条件
[[连接类型] JOIN 表3 别名3 ON 连接条件]
[WHERE 过滤条件]
[GROUP BY 分组列]
[HAVING 分组过滤]
[ORDER BY 排序列]
[LIMIT 行数];
```
- **连接类型**：`INNER`、`LEFT`、`RIGHT`、`CROSS`。省略时默认为 `INNER`。
- **ON 子句**：指定两张表的连接条件。通常是外键等于主键。
- **多表 JOIN**：可以串联多个 JOIN，每一张新表和前面生成的虚拟表做连接。
### 5.2 INNER JOIN（内连接）
```sql
SELECT 列名1 别名1,列名2 别名2 ··· FROM 表名1 别名 INNER JOIN 表名2 别名 ON 连接条件;
```
### 5.3 LEFT JOIN （左外连接）
```sql
SELECT 列名1 别名,列名2 别名 ··· FROM 表名1 别名 LEFT JOIN 表名2 别名 ON 连接条件;
```
### 5.4 RIGHT JOIN（右外连接）
```sql
SELECT 列名1 别名,列名2 别名 FROM 表名1 别名 RIGHT JOIN 表名2 别名 ON 连接条件;
```
### 5.5 CROSS JOIN（交叉连接）
```sql
SELECT 列名1 别名,列名2 别名 FROM 表名1 别名 CROSS JOIN 表名2 别名;
```
- 返回两张表的**笛卡尔积**
## 6、WHERE：条件过滤
```sql
SELECT 列名 FROM 表名 WHERE 条件;
```
比较运算：`=、!=、<>、<、<=、>、>=`
逻辑运算：`AND、OR、NOT`
	**优先级**：`NOT` > `AND` > `OR`。不确定时用括号
`BETWEEN A AND B`：A和B之间，包括A，B
`IN (值列表)` / `NOT IN (值列表)`：等价于多个 OR 条件。
模糊匹配：`LIKE`
	`%` 表示任意 0 个或多个字符
	 `_` 表示任意单个字符
`IS NULL`  / `IS NOT NULL`
存在性检查：`EXISTS`
## 7、GROUP BY：分组
```sql
SELECT 列名 FROM 表名 GROUP BY 列名;
```
## 8、HAVING：分组后过滤（分组后加条件）
WHERE 是对原始行做筛选，HAVING 是对分组后的结果做筛选。
```sql
SELECT 列名 FROM 表名 GROUP BY 列名 HAVING 条件（聚合函数）;
```
`HAVING` 里必须用聚合函数
## 9、聚合函数
| 函数          | 作用             |
| ----------- | -------------- |
| `COUNT(列名)` | 统计行数           |
| `COUNT(*)`  | 统计所有行数（包括NULL） |
| `SUM(列名)`   | 求和             |
| `AVG(列名)`   | 求平均值           |
| `MAX(列名)`   | 找最大值           |
| `MIN(列名)`   | 找最小值           |
## 10、ORDER BY：排序
```sql
SELECT 列名 FROM 表名 ORDER BY 列名 排序规则;
```
- `ASC`：升序（从小到大），默认
- `DESC`：降序（从大到小）
- 多列排序——先按第一列排，第一列相同的再按第二列排
## 11、LIMIT：分页
```sql
SELECT 列名 FROM 表名 ORDER BY 列名 LIMIT 行数;（取前几行）
SELECT 列名 FROM 表名 ORDER BY 列名 LIMIT 行数1，行数2;（跳过行数1行，取行数2行）
SELECT 列名 FROM 表名 ORDER BY 列名 LIMIT 行数1 OFFSET 行数2;（同上，跳过行数2，取行数1行）
```
## 12、UNION：联合查询
```sql
SELECT 列名 FROM 表名 WHERE 条件
UNION
SELECT 列名 FROM 表名 WHERE 条件;
```
- 合并两个查询的结果集，默认去重。
- `UNION ALL` 保留重复行，性能更好。
- 两个查询的列数和类型必须一致。
## 13、子查询（嵌套查询）
子查询就是一个查询套在另一个查询里面，**内层查询的结果作为外层查询的条件**。
# 六、TCL——事务控制语言
TCL 不操作表结构（DDL），不操作数据（DML），不控制权限（DCL）。TCL 只做一件事：**控制事务的边界和状态**。它的核心语句只有三个：`COMMIT`、`ROLLBACK`、`SAVEPOINT`。
## 1. 什么是事务？

事务是数据库操作的最小逻辑单元，包含一组 SQL 语句。这些语句要么全部执行成功并永久保存（COMMIT），要么全部撤销到事务开始前的状态（ROLLBACK）。

## 2. ACID 特性
事务必须满足四个特性。
### 2.1 原子性（Atomicity）

**事务中的所有操作是一个不可分割的整体。要么全做，要么全不做。**
### 2.2 一致性（Consistency）

**事务执行前后，数据库必须从一个一致状态转换到另一个一致状态。所有约束（主键、外键、NOT NULL、CHECK）都不能被破坏。**
### 2.3 隔离性（Isolation）

**多个事务并发执行时，彼此互不干扰。每个事务都感觉自己是数据库中唯一运行的事务。**

如果没有隔离性：

- **脏读**：事务 A 修改了一行但还没提交，事务 B 读到了这个未提交的修改。如果事务 A 后来回滚了，事务 B 读到的数据就是“脏数据”。
- **不可重复读**：事务 A 在同一个事务内两次读取同一行，中间事务 B 修改了这一行并提交。事务 A 两次读到的结果不同。
- **幻读**：事务 A 在同一个事务内两次执行相同条件的查询，中间事务 B 插入了一些新行。事务 A 第二次查到了上次不存在的行。
### 2.4 持久性（Durability）
**事务一旦提交，它的修改就是永久的。即使数据库随后崩溃，重启后数据也不会丢失。**

---
## 3. 事务的隔离级别

SQL 标准定义了四个隔离级别，从低到高：

| 隔离级别 | 脏读 | 不可重复读 | 幻读 | 并发性能 |
|----------|------|-----------|------|----------|
| `READ UNCOMMITTED` | ✗ | ✗ | ✗ | 最高 |
| `READ COMMITTED` | ✓ | ✗ | ✗ | 高 |
| `REPEATABLE READ` | ✓ | ✓ | ✗（InnoDB 中大部分消除） | 中 |
| `SERIALIZABLE` | ✓ | ✓ | ✓ | 最低 |

- ✗ 表示可能发生该问题
- ✓ 表示已解决该问题

### 3.1 READ UNCOMMITTED（读未提交）

最低级别。事务可以读到其他事务未提交的修改。基本不使用，因为脏读会导致严重的数据逻辑错误。

### 3.2 READ COMMITTED（读已提交）

只能读到其他事务已提交的修改。解决了脏读，但不能解决不可重复读——同一事务内两次查询可能得到不同结果。这是 Oracle 和 PostgreSQL 的默认级别。

### 3.3 REPEATABLE READ（可重复读）

同一事务内的查询结果保持一致——即使其他事务修改了数据并提交，本事务读到的还是自己开始时的快照。解决了脏读和不可重复读，但在某些场景下可能出现幻读。**这是 默认隔离级别。**。

### 3.4 SERIALIZABLE（串行化）

最高级别。事务串行执行，完全消除了并发问题，但并发性能极差。相当于把所有事务排成一队，一个一个执行。

### 3.5 设置隔离级别

```sql
-- 设置当前会话的隔离级别（只影响当前连接）
SET SESSION TRANSACTION ISOLATION LEVEL 级别;

-- 设置全局默认隔离级别（影响之后所有新连接，不影响已有连接）
SET GLOBAL TRANSACTION ISOLATION LEVEL 级别;
```

查看当前隔离级别：

```sql
-- 查看当前会话的隔离级别
SELECT @@transaction_isolation;

-- 查看全局默认隔离级别
SELECT @@global.transaction_isolation;
```

### 3.6 设置事务访问模式
```sql
SET TRANSACTION READ ONLY;
SET TRANSACTION READ WRITE;
```

---

## 4. TCL 核心语句
### 4.1 START（开启事务）
```sql
--MySQL 中有三种等价的开启事务方式--
START TRANSACTION;--标准写法--
BEGIN;
BEGIN WORK;

--START TRANSACTION 可以带选项--
START TRANSACTION READ ONLY;
START TRANSACTION READ WRITE;
START TRANSACTION WITH CONSISTENT SNAPSHOT;
```

| 选项                         | 作用                                                                            |
| -------------------------- | ----------------------------------------------------------------------------- |
| `READ ONLY`                | 声明该事务只读，数据库可优化性能（禁止写操作）                                                       |
| `READ WRITE`               | 声明该事务会读写（默认）                                                                  |
| `WITH CONSISTENT SNAPSHOT` | 启动事务时立即创建一个一致性快照，等同于先执行 `START TRANSACTION` 再执行 `SELECT`，用于保证整个事务读到的数据都是同一时刻的 |
### 4.2 COMMIT（提交事务）

```sql
COMMIT;
COMMIT WORK;
COMMIT AND CHAIN;
COMMIT RELEASE;
```

|语法|作用|
|---|---|
|`COMMIT`|提交当前事务，修改永久保存。标准写法|
|`COMMIT WORK`|等价于 `COMMIT`，是 SQL 标准语法|
|`COMMIT AND CHAIN`|提交当前事务，并**自动开启一个新事务**，新事务沿用旧事务的隔离级别|
|`COMMIT RELEASE`|提交当前事务，并**断开客户端连接**（极少使用）|

**隐式提交**：以下语句会隐式提交当前事务（不需要显式写 COMMIT）：

- DDL 语句（`CREATE`、`ALTER`、`DROP`、`TRUNCATE`）
- `BEGIN`（启动新事务时自动提交上一个事务）
- 正常断开数据库连接

**MySQL 默认行为**：默认情况下，每一条单独的 SQL 语句都是一个自动提交的事务。执行完一条 `INSERT`，MySQL 自动执行 `COMMIT`。你可以关闭自动提交：

```sql
SET autocommit = 0;
```

之后所有操作都在一个事务中，直到你手动 `COMMIT` 或 `ROLLBACK`。

### 4.3 ROLLBACK（回滚事务）

```sql
ROLLBACK;
ROLLBACK WORK;
ROLLBACK AND CHAIN;
ROLLBACK RELEASE;
```

|语法|作用|
|---|---|
|`ROLLBACK`|回滚整个事务，撤销所有未提交修改|
|`ROLLBACK WORK`|等价于 `ROLLBACK`，SQL 标准语法|
|`ROLLBACK AND CHAIN`|回滚当前事务，并**自动开启一个新事务**|
|`ROLLBACK RELEASE`|回滚当前事务，并**断开客户端连接**|

**底层原理**：数据库读取事务的 Undo Log，逆向执行所有修改。如果你把 name 从 'Tom' 改成 'Jerry'，Undo Log 里记录了旧值 'Tom'，回滚时用旧值覆盖新值。

### 4.3 SAVEPOINT（保存点）

```sql
SAVEPOINT 保存点名;-- 设置保存点
ROLLBACK TO 保存点名;-- 回滚到保存点
ROLLBACK TO SAVEPOINT 保存点名;-- 回滚到保存点
RELEASE SAVEPOINT 保存点名;-- 释放保存点
```

---



