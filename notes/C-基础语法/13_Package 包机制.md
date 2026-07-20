### 包的概念与作用

**包的本质**：  
- 包是类的**命名空间**，用于组织和管理类。  
- 物理上对应文件系统中的**目录结构**。  
- 避免类名冲突，提供访问保护。

**标准简答**：  
① 避免同名类冲突；  
② 实现访问控制（同一个包内可以访问默认权限成员）； 
③ 便于类的组织和管理。

### `package` 语句

**规则**：
1. 必须是源文件中**第一条非注释语句**。
2. 只能有一个 `package` 声明。
3. 命名惯例：公司域名倒写，小写，点号分隔。如 `package com.example.demo;`

**易错判断题**：
- "一个源文件中可以有多个 package 语句" —— **错**，最多一个。  
- "package 语句可以写在 import 语句之后" —— **错**，必须写在最前面。  

**经典错误示例**：
```java
import java.util.*;       // 错，import 不能在前
package mypackage;
```
编译不通过，必须 `package` 在第一行。

### `import` 语句


**两种导入方式**：
```java
import java.util.Date;    // 单类型导入：只导入 Date 类
import java.util.*;       // 按需导入：导入 java.util 包中所有类
```

**注意**：
- `import` 只导入类，不能导入包中的子包。`import java.*;` 会试图导入 `java` 包中的所有类，但 `java.util` 是子包，不会自动导入其下的类。
- 按需导入 `*` 不影响效率，编译器只引入实际使用的类。

**常见坑**：
```java
import java.util.*;
import java.sql.*;
// 两个包中都有 Date 类，以下代码报错
Date d = new Date();  // 编译错误：Date 引用不明确
```
必须明确写全限定名：`java.util.Date d = new java.util.Date();`

**`java.lang` 包是自动导入的**，不需要显式 `import`。这是高频选择题：问 "以下哪行 `import` 不是必需的？" 答案若涉及 `java.lang`，选不需要。

---

### 同一包与不同包的访问权限

| 修饰符            | 同一类 | 同一包 | 子类  | 任何地方 |
| -------------- | --- | --- | --- | ---- |
| `private`      | ✓   |     |     |      |
| `default`（无修饰） | ✓   | ✓   |     |      |
| `protected`    | ✓   | ✓   | ✓   |      |
| `public`       | ✓   | ✓   | ✓   | ✓    |

**考题陷阱**：  
父类和子类在**不同包**时，子类可以访问父类的 `protected` 成员，但只能通过继承方式访问（在自己的方法内直接使用），**不能通过父类对象访问**。这一点教材可能没有强调，但自考可能涉及。

```java
// 父类在包 a 中
package a;
public class Father {
    protected int x;
}

// 子类在包 b 中
package b;
import a.Father;
public class Son extends Father {
    void method() {
        System.out.println(x);       // 可以
    }
    void test(Father f) {
        // f.x = 5;   // 编译错误，不是通过继承访问
    }
}
```

---

### 静态导入（`import static`）
`import` 语句


**两种导入方式**：
```java
import java.util.Date;    // 单类型导入：只导入 Date 类
import java.util.*;       // 按需导入：导入 java.util 包中所有类
```

**注意**：
- `import` 只导入类，不能导入包中的子包。`import java.*;` 会试图导入 `java` 包中的所有类，但 `java.util` 是子包，不会自动导入其下的类。
- 按需导入 `*` 不影响效率，编译器只引入实际使用的类。

**常见坑**：
```java
import java.util.*;
import java.sql.*;
// 两个包中都有 Date 类，以下代码报错
Date d = new Date();  // 编译错误：Date 引用不明确
```
必须明确写全限定名：`java.util.Date d = new java.util.Date();`

**`java.lang` 包是自动导入的**，不需要显式 `import`。这是高频选择题：问 "以下哪行 `import` 不是必需的？" 答案若涉及 `java.lang`，选不需要。

---

### 考点4：同一包与不同包的访问权限

**考试定位**：选择题给代码判断是否可见。

在第五章访问控制部分已有框架，此处强调与包的结合：

- `public` —— 任何包可访问。  
- `protected` —— 同包 + 不同包的子类。  
- `default`（无修饰）—— **只能同一包**。  
- `private` —— 仅同一类。

**考题陷阱**：  
父类和子类在**不同包**时，子类可以访问父类的 `protected` 成员，但只能通过继承方式访问（在自己的方法内直接使用），**不能通过父类对象访问**。这一点教材可能没有强调，但自考可能涉及。

```java
// 父类在包 a 中
package a;
public class Father {
    protected int x;
}

// 子类在包 b 中
package b;
import a.Father;
public class Son extends Father {
    void method() {
        System.out.println(x);       // 可以
    }
    void test(Father f) {
        // f.x = 5;   // 编译错误，不是通过继承访问
    }
}
```

---

### 考点5：静态导入（`import static`）

**考试定位**：选择题识别写法，是否涉及取决于教材版本。

**格式**：
```java
import static java.lang.Math.PI;   // 导入静态常量
import static java.lang.Math.*;    // 导入 Math 类的所有静态成员
```

**作用**：直接使用静态成员名，而不必写类名。
```java
System.out.println(PI);  // 不必写 Math.PI
```

**易混**：  
- `import java.util.*;` 导入**类**。  
- `import static java.util.Collections.*;` 导入**静态方法/常量**。  

选择题经常把一个类的静态方法写成只有 `import` 没有 `static` 的情况，然后问能否编译成功。

---

### 考点6：类路径（Classpath）


**classpath 的作用**：  
告诉 JVM 和编译器在**哪些目录或 jar 文件**中查找类。  

**默认情况**：  
如果没有设置 classpath，JDK 只搜索当前目录。  

**设置方式**：
- 系统环境变量 `CLASSPATH`  
- 编译或运行时用 `-cp` 或 `-classpath` 参数

```bash
java -cp .;lib/myclasses.jar MyProgram
```

**常考结论**：
- classpath 可以包含多个路径，用 `;`（Windows）或 `:`（Unix）分隔。  
- 如果在 `-cp` 中显式设置了类路径，会覆盖系统 `CLASSPATH` 环境变量。  
- 当前目录 `.` 如果没有列入 classpath，当前目录下的类也找不到。

---

### 考点7：常用系统包（背记）

**考试定位**：选择题给类名问属于哪个包。

| 包 | 内容 |
|----|------|
| `java.lang` | 核心类：String, Math, System, Thread, Object |
| `java.util` | 工具类：Date, Calendar, ArrayList, HashMap, Collections |
| `java.io` | 输入输出：InputStream, OutputStream, Reader, Writer |
| `java.net` | 网络：Socket, ServerSocket, URL |
| `java.awt` | 抽象窗口工具包：Frame, Button, Label |
| `javax.swing` | Swing 图形界面：JFrame, JButton, JLabel |

**必记**：  
- `String` 在 `java.lang`，不在 `java.util`。  
- `ArrayList` 在 `java.util`，不在 `java.lang`。  
- `JFrame` 在 `javax.swing`，不是 `java.awt`。