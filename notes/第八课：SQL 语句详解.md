
SQL 是一门独立的语言，专用于和数据库对话。你之前写的 `SELECT * FROM student` 就是 SQL。

---

### 一、SQL 是什么

SQL 全称 Structured Query Language，结构化查询语言。

它只做四件事：
- **增**：往表里插入数据
- **删**：从表里删除数据
- **改**：修改表里的数据
- **查**：从表里查询数据

所有 SQL 语句都是围绕这四件事展开的。

---

### 二、查询数据——SELECT

SELECT 是最常用的 SQL 语句，用来从表中取出数据。

**基本格式：**

```sql
SELECT 列名1, 列名2 FROM 表名;
```

**示例1：查所有列**

```sql
SELECT * FROM student;
```

`*` 是通配符，表示"所有列"。这句话翻译成人话：**从 student 表里，把所有行、所有列都拿给我看。**

**示例2：只查指定列**

```sql
SELECT name, score FROM student;
```

只拿 `name` 和 `score` 两列。`id` 和 `age` 不出现。

---

两条 SELECT 都正确执行了。

第一条 `SELECT * FROM student` 查了所有列。第二条 `SELECT name, age FROM student` 只查了姓名和年龄两列，`id` 和 `score` 没有出现。

你学会了 SQL 的第一条核心规则：**想要哪几列，就在 SELECT 后面写哪几列的名字，用逗号分隔。**

---

### 三、按条件筛选——WHERE

实际场景中你很少需要查全部数据。你更常问的是：“谁的成绩超过 80 分？”“Tom 的年龄是多少？”

这时候用 `WHERE`。

**格式：**

```sql
SELECT 列名 FROM 表名 WHERE 条件;
```

- **WHERE 里禁止使用聚合函数。**

**示例1：查成绩大于 80 分的学生**

```sql
SELECT name, score FROM student WHERE score > 80;
```

**示例2：查名叫 Tom 的学生**

```sql
SELECT * FROM student WHERE name = 'Tom';
```

注意：`'Tom'` 必须用单引号括起来。字符串在 SQL 里一律用单引号，数字不用。

**示例3：多个条件用 AND 连接**

```sql
SELECT * FROM student WHERE score > 80 AND age < 25;
```

两个条件都满足的行才会被选出来。

**示例4：OR 连接（满足任一即可）**

```sql
SELECT * FROM student WHERE score > 90 OR age < 23;
```

- **AND 优先级高于 OR**

---

### 四、排序——ORDER BY

查询结果默认按数据库存储顺序返回。你想让数据按成绩从高到低排、按年龄从小到大排，用 `ORDER BY`。

**格式：**

```sql
SELECT 列名 FROM 表名 ORDER BY 列名 排序方式;
```

- `ASC`：升序（从小到大），默认值
- `DESC`：降序（从大到小）

**示例1：按成绩从高到低排**

```sql
SELECT name, score FROM student ORDER BY score DESC;
```

**示例2：按年龄从小到大排**

```sql
SELECT name, age FROM student ORDER BY age ASC;
```

**示例3：多列排序——先按年龄升序，年龄相同的再按成绩降序**

```sql
SELECT * FROM student ORDER BY age ASC, score DESC;
```

---

### 五、聚合函数——统计计算

SQL 不只是查数据，还能直接做数学运算。最常用的五个聚合函数：

| 函数 | 作用 | 示例 |
|------|------|------|
| `COUNT(列名)` | 统计行数 | 一共有多少个学生 |
| `SUM(列名)` | 求和 | 所有人的成绩总和 |
| `AVG(列名)` | 求平均值 | 平均成绩是多少 |
| `MAX(列名)` | 找最大值 | 最高分是多少 |
| `MIN(列名)` | 找最小值 | 最低分是多少 |

---

**示例1：统计学生人数**

```sql
SELECT COUNT(*) FROM student;
```

`COUNT(*)` 统计表中一共有多少行。`*` 表示不限定列，只要有这一行就算一个。

---

**示例2：计算平均成绩**

```sql
SELECT AVG(score) FROM student;
```

返回 85.333...（三个人的平均成绩）。

---

**示例3：找最高分和最低分**

```sql
SELECT MAX(score), MIN(score) FROM student;
```

注意：函数之间用逗号分隔，和 `SELECT 列1, 列2` 的语法一样。

---

**示例4：配合 WHERE 使用——统计成绩大于 80 分的人数**

```sql
SELECT COUNT(*) FROM student WHERE score > 80;
```

`WHERE` 先筛选，`COUNT` 再统计筛选后的行数。

---

### 六、分组统计——GROUP BY

目前所有统计都是对整张表做的。但现实场景中，你需要按类别统计。比如：按班级统计每个班的平均分、按科目统计每科的最高分。


---

**GROUP BY 的用法：**

```sql
SELECT 分组列, 聚合函数 FROM 表名 GROUP BY 分组列;
```

**示例1：统计每个班的人数**

```sql
SELECT grade, COUNT(*) FROM student GROUP BY grade;
```

翻译成人话：按 `grade` 列分组，A 班的行归一堆，B 班的行归一堆，然后分别统计每堆有几行。

**示例2：统计每个班的平均成绩**

```sql
SELECT grade, AVG(score) FROM student GROUP BY grade;
```

---

### 七、HAVING——对分组后的结果再加条件

WHERE 是对原始行做筛选，HAVING 是对分组后的结果做筛选。
	`HAVING` 里必须用聚合函数

**示例：只显示平均成绩大于 80 分的班级**

```sql
SELECT grade, AVG(score) FROM student GROUP BY grade HAVING AVG(score) > 80;
```

翻译成人话：先按班级分组，计算每个班的平均成绩，然后只保留平均成绩大于 80 的班。A 班的 89 满足条件，B 班的 78 会被过滤掉。

**WHERE 和 HAVING 的区别：**
- WHERE：在 GROUP BY 之前执行，筛的是原始行
- HAVING：在 GROUP BY 之后执行，筛的是分组结果

---
### 八、范围判断语句——BETWEEN

`BETWEEN ... AND ...` 是 SQL 的一个范围判断语句，等价于 `>= 下界 AND <= 上界`。

**格式：**

```sql
SELECT * FROM 表名 WHERE 条件 BETWEEN 值1 AND 值2;
```

翻译成人话：条件在 值1 到 值2 之间 的。完全等价于

```sql
SELECT * FROM 表名 WHERE 条件 >= 值1 AND 条件 <= 值2;
```

---
### 九、修改数据——UPDATE

之前你在JDBC里用过UPDATE，现在拆解它的完整语法。

**格式：**

```sql
UPDATE 表名 SET 列名1 = 新值, 列名2 = 新值 WHERE 条件;
```

**关键：如果不写WHERE条件，整张表全部行都会被修改。这是非常危险的操作。**

**示例1：把Tom的成绩改为88**

```sql
UPDATE student SET score = 88 WHERE name = 'Tom';
```

**示例2：同时修改多个列**

```sql
UPDATE student SET age = 26, score = 90 WHERE name = 'Tom';
```

**示例3：配合其他条件**

```sql
UPDATE student SET score = score + 5 WHERE grade = 'A班';
```

给A班所有人成绩加5分。`score = score + 5` 意思是“在现有成绩上加5分”，不是设成5分。

---

### 十、删除数据——DELETE

**格式：**

```sql
DELETE FROM 表名 WHERE 条件;
```

**和UPDATE一样，不写WHERE会删除整张表的所有行。表结构还在，但数据全没了。这是面试常考的危险操作。**

**示例1：删除名叫Tom的学生**

```sql
DELETE FROM student WHERE name = 'Tom';
```

**示例2：删除成绩低于60分的学生**

```sql
DELETE FROM student WHERE score < 60;
```

---

### 十一、插入数据——INSERT

**格式：**

```sql
INSERT INTO 表名 (列1, 列2, ...) VALUES (值1, 值2, ...);
```

**示例1：插入一行完整数据**

```sql
INSERT INTO student (name, age, score, grade) VALUES ('Alice', 20, 88.0, 'B班');
```

`id` 不写，因为它是 AUTO_INCREMENT，数据库会自动填下一个编号。

**示例2：批量插入多行**

```sql
INSERT INTO student (name, age, score, grade) VALUES 
('Bob', 24, 76.5, 'A班'),
('Cathy', 21, 91.0, 'B班');
```

多行数据用逗号分隔，一次插入多条，效率远高于多次单条插入。

---

### 十二、子查询（嵌套查询）

子查询就是一个查询套在另一个查询里面，**内层查询的结果作为外层查询的条件**。

**执行顺序：** 先执行括号里面的内层查询，得到一个结果，然后再执行外层查询，用这个结果作为条件。

**SQL 允许你把第一个查询用括号括起来，直接放在外层 WHERE 条件里：**

```sql
SELECT name, score FROM student WHERE score > ( SELECT AVG(score) FROM student );
```

**原理：**

1. 数据库先执行括号内的 `SELECT AVG(score) FROM student`，算出一个数字。
    
2. 数据库拿这个数字替换掉括号部分，相当于变成了 `WHERE score > 那个数字`。
    
3. 再执行外层查询，返回满足条件的行。
    

**关键约束：** 子查询返回的结果必须是单行单列（一个值），不能是一个表。`AVG` 本身就返回一个单一数值，所以符合要求。


---


### 子句顺序

子句的顺序是 `SELECT → FROM → WHERE → GROUP BY → HAVING → ORDER BY`