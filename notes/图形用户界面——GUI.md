GUI 就是图形用户界面，你平时用的所有窗口、按钮、输入框都属于 GUI。

核心思路：**先搭框架（窗口），再往里面放东西（组件），再决定东西怎么摆（布局），最后让这些东西能响应你的操作（事件处理）**

完整步骤：
1. 创建`JFrame`对象，也就是顶级容器，或者创建一个类，继承`JFrame`，然后创建该类的对象，

| 方法                            | 作用             | 说明                               |
| ----------------------------- | -------------- | -------------------------------- |
| `setSize(width,higth)`        | 宽高             |                                  |
| `setTitle(string)`            | 标题             |                                  |
| `setAlwaysOnTop(boolean)`     | 页面顶层顶          |                                  |
| `setLocationRelativeTo(null)` | 页面居中           |                                  |
| `setDefaultCloseOperation()`  | 关闭模式           | `JFrame.DO_NOTHING_ON_CLOSE = 0` |
|                               |                | `JFrame.HIDE_ON_CLOSE = 1`默认     |
|                               |                | `JFrame.DISPOSE_ON_CLOSE = 2`    |
|                               |                | `JFrame.EXIT_ON_CLOSE = 3`       |
| `setJMenuBar(JMenuBar)`       | 添加`JMenuBar`对象 |                                  |
| `setLayout(null)`             | 设置布局           | 布局默认为居中，null取消布局                 |
| `getContentPane()`            | 获取隐藏容器，窗体的对象   | 隐藏容器在创建`JFrame`时就已经创建。           |
| `getContentPane().add()`      | 添加组件           |                                  |
| `setVisible(booleam)`         | 显示             | 最后设置                             |
2. 创建`JMenuBar`对象，也就是整个菜单栏，然后创建`JMenu`对象，也就是每一个菜单，然后创建`JMenuItem`对象，也就是每个菜单条目对象。
3. 把`JMenuItem`放到`JMenu`里面，把`JMenu`放到`JMenuBar`里面，最后再把`JMenuBar`添加到整个`JFrame`界面中

| 方法                      | 作用  | 说明                                               |
| ----------------------- | --- | ------------------------------------------------ |
| `new JMenuBar()`        |     |                                                  |
| `new JMenu(String)`     |     |                                                  |
| `new JMenuItem(String)` |     |                                                  |
| `add()`                 | 添加  | 可以把`JMenuItem`放到`JMenu`中，也可以把`JMenu`放到`JMenuBar` |
4. 创建`JLabel`对象，也就是用来管理图片和文字的容器对象，然后创建`ImageIcon`对象，也就是图片对象。然后把`ImageIcon`对象添加到`JLabel`中，让`JLabel`对象管理图片，最后再把`JLabel`对象添加到整个`ContentPane`隐藏容器中，这个隐藏容器就在`JFrame`上

| 方法                           | 作用            | 说明                   |
| ---------------------------- | ------------- | -------------------- |
| `new ImageIcon(String)`      | 创建图片对象        | 参数是图片在电脑中的绝对路径       |
| `new JLabel(组件)`             | 创建`JLabl`对象   | 参数是需要添加到`JLable`中的组件 |
| `setBounds(x,y,width,heigh)` | 设置组件的xy坐标以及宽高 | 以框架的左上角为原点，容器的左上角为坐标 |
5. 创建一个`JButton`对象，也就是按钮对象，然后给按钮对象添加事件监听。`ActionListener`是一个接口，我们需要`ActionListenner`接口的实现类，实现类中重写`actionPerformed()`方法，这个方法就是事件触发之后要执行的代码，然后把实现类对象添加到`JButton`对象上。最后再把`JButton`对象添加到隐藏容器`ContentPane`上。

| 方法                                  | 作用      | 说明                                                                               |
| ----------------------------------- | ------- | -------------------------------------------------------------------------------- |
| `new JButton(Stirng)`               | 创建按钮对象  | 参数是按钮上显示的字                                                                       |
| `setBounds(x,y,width,height)`       |         |                                                                                  |
| `addActionListener(ActionListener)` | 给组件添加事件 | 动作监听：鼠标左键点击和键盘空格                                                                 |
|                                     |         | 三种实现方法：①实现类②匿名内部类③用本界面实现`ActionListenner`接口，然后在`addActionListener()`中传递`this`为参数 |
| `e.getSource()`                     | 获取事件源   | 在事件方法中调用                                                                         |


---
## 一、AWT 与 Swing 

|     | **AWT**（Abstract Window Toolkit）         | Swing                                     |
| --- | ---------------------------------------- | ----------------------------------------- |
| 包   | `java.awt`                               | `javax.swing`                             |
| 实现  | 重量级组件。ava最早的GUI库。直接调用操作系统本地组件，外观和所在平台一致。 | AWT基础上构建，轻量级组件。纯 Java 实现，不依赖本地组件，跨平台外观一致。 |
| 速度  | 较快（调用本地方法）                               | 较慢（完全用 Java 绘制），但现代硬件下差异可忽略               |
| 移植性 | 弱。“一次编写，处处不同”，不同平台有细微行为差异                | 强。在任何平台外观和行为一致                            |
| 组件名 | Button、TextField 等                       | **J**Button、**J**TextField 等（前缀 “J”）      |
| 依赖  | 核心，JDK 1.0 起                             | 建立在 AWT 基础之上，复用了 AWT 的事件模型、布局管理器、图形环境等    |

---
## 二、组件体系结构（继承关系）

```text
java.lang.Object
│
└── java.awt.Component (抽象，所有 GUI 组件的根基类)
    │
    ├── java.awt.Container (可容纳其他组件的容器)
    │   │
    │   ├── java.awt.Window (顶层窗口，无边框无标题栏)
    │   │   ├── java.awt.Frame
    │   │   │   └── javax.swing.JFrame ★ 标准窗口
    │   │   └── java.awt.Dialog
    │   │       └── javax.swing.JDialog ★ 对话框
    │   │
    │   ├── java.awt.Panel
    │   │   └── java.applet.Applet
    │   │       └── javax.swing.JApplet (已过时)
    │   │
    │   └── javax.swing.JComponent ★ (除顶层容器外所有 Swing 组件的父类)
    │       ├── JPanel ★ 面板
    │       ├── JLabel ★ 标签
    │       ├── AbstractButton (抽象)
    │       │   ├── JButton ★ 按钮
    │       │   ├── JToggleButton
    │       │   │   ├── JCheckBox ★ 复选框
    │       │   │   └── JRadioButton ★ 单选按钮
    │       │   └── JMenuItem (菜单项)
    │       │       ├── JMenu ★ 菜单
    │       │       ├── JCheckBoxMenuItem
    │       │       └── JRadioButtonMenuItem
    │       ├── JTextComponent (抽象)
    │       │   ├── JTextField ★ 单行文本框
    │       │   ├── JTextArea ★ 多行文本域
    │       │   ├── JPasswordField ★ 密码框
    │       │   └── JEditorPane
    │       ├── JComboBox ★ 下拉列表框
    │       ├── JList ★ 选择列表
    │       ├── JScrollPane ★ 带滚动条的容器
    │       ├── JTable
    │       ├── JTree
    │       ├── JSlider
    │       ├── JProgressBar
    │       ├── JToolTip
    │       └── JMenuBar ★ 菜单栏（注：非 Container 子类，不能放其它组件）
    │
    ├── java.awt.Button
    ├── java.awt.Label
    ├── java.awt.TextComponent
    │   ├── java.awt.TextField
    │   └── java.awt.TextArea
    ├── java.awt.List
    ├── java.awt.Choice
    └── java.awt.Canvas
```

**记忆口诀**：
- `JFrame` 的爸爸是 `Frame`，爷爷是 `Window`。
- `JButton`、`JLabel`、`JPanel` 的爸爸都是 `JComponent`。
- `JComponent` 的爸爸是 `Container`，爷爷是 `Component`。

---
## 三、GUI基础体系

### 顶级容器（不能放入其他容器内）

- `JFrame`：框架窗口，最常用的顶级窗口，带边框、标题栏、最大/最小/关闭按钮的标准窗口。
- `JDialog`：对话框窗口，通常用于提示或输入，可以设置为模态（阻塞父窗口）。
- `JWindow` / `JApplet`（已淘汰，考试很少考）

### 中间容器

不能独立显示，必须放在顶级容器里

| 容器              | 作用              |
| -------------- | --------------- |
| `JPane          | 最常用的面板，用于分组存放组件 |
| `JScrollPa  `   | 提供滚动条的面板        |
| `JSplitP   `    | 可拖动分割线的双面板      |
| `JTabbed  ne`   | 选项卡面板         `JLayeredPane` dPane` | 分层面板            |

### 常用组件

窗口是个空壳子，你要往里面放按钮、标签、输入框这些东西。这些就叫**组件**。

|         组件         |                                   主要构造                                    |                                                        常用方法                                                         | 说明                                                            |
| :----------------: | :-----------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------: | ------------------------------------------------------------- |
|    **JButton**     |          `JButton()` `JButton(String text)` `JButton(Icon icon)`          |                      `setText(String)` `getText()` `setEnabled(boolean)` `addActionListener()`                      | 最常用的点击按钮。通常通过 `addActionListener` 绑定事件。                       |
|   **JCheckBox**    |    `JCheckBox(String text)` `JCheckBox(String text, boolean selected)`    |                              `isSelected()` `setSelected(boolean)` `addItemListener()`                              | 复选框，可独立选中/取消。用 `isSelected()` 判断状态。                           |
|  **JRadioButton**  | `JRadioButton(String text)` `JRadioButton(String text, boolean selected)` |                             `isSelected()` `setSelected(boolean)` `addActionListener()`                             | 单选按钮，**必须放入 `ButtonGroup`** 才能实现互斥。`ButtonGroup` 不是组件，只是逻辑分组。 |
|     **JLabel**     |                 `JLabel(String text)` `JLabel(Icon icon)`                 |                                    `setText(String)` `getText()` `setIcon(Icon)`                                    | 显示不可编辑的文本或图片。                                                 |
|   **JTextField**   |            `JTextField(int columns)` `JTextField(String text)`            |                     `getText()` `setText(String)` `setEditable(boolean)` `addActionListener()`                      | 单行文本输入框。按回车触发 `ActionEvent`。                                  |
| **JPasswordField** |                       `JPasswordField(int columns)`                       |                                   `getPassword()` 返回 `char[]` `getText()` **已过时**                                   | 密码输入框，内容被遮蔽。**务必使用 `getPassword()`** 获取内容以保证安全。               |
|   **JTextArea**    |                      `JTextArea(int rows, int cols)`                      |             `setText(String)` `append(String)` `getText()` `setLineWrap(true)` `setWrapStyleWord(true)`             | 多行文本域。通常放入 `JScrollPane` 以支持滚动。                               |
| **JComboBox\<E\>** |                   `JComboBox(E[] items)` `JComboBox()`                    |                   `getSelectedItem()` `setSelectedItem(Object)` `addItem(E)` `addItemListener()`                    | 下拉列表框。`getSelectedItem()` 返回当前选中项（需强转）。                       |
|   **JList\<E\>**   |                            `JList(E[] items)`                             | `getSelectedValue()` `setSelectionMode(int)` (如 `ListSelectionModel.SINGLE_SELECTION`) `addListSelectionListener()` | 多行选择列表。通常放入 `JScrollPane`。                                    |

菜单系统组件

| 组件                       | 主要构造                                                         | 常用方法                                                     | 说明                                     |
| ------------------------ | ------------------------------------------------------------ | -------------------------------------------------------- | -------------------------------------- |
| **JMenuBar**             | `JMenuBar()`                                                 | `add(JMenu)`                                             | 菜单栏，通过 `frame.setJMenuBar(bar)` 挂载到窗口。 |
| **JMenu**                | `JMenu(String text)`                                         | `add(JMenuItem)` `addSeparator()`                        | 菜单（如“文件”）。                             |
| **JMenuItem**            | `JMenuItem(String text)` `JMenuItem(String text, Icon icon)` | `addActionListener()`                                    | 菜单项，可响应点击。                             |
| **JCheckBoxMenuItem**    | 同上                                                           | `isSelected()` `setSelected(boolean)`                    | 带勾选的菜单项。                               |
| **JRadioButtonMenuItem** | 同上                                                           | 同上，需加入 `ButtonGroup`                                     | 互斥单选菜单项。                               |
| **JPopupMenu**           | `JPopupMenu()`                                               | `add(JMenuItem)` `show(Component invoker, int x, int y)` | 右键弹出菜单，通常在鼠标事件中调用 `show()`。            |

**必须记住的包含关系**：  
`JFrame`（窗口）→ `JPanel`（面板，设置布局）→ 具体组件（按钮、文本框等）

---
## 四、布局管理器

布局管理器负责容器内组件的排列。Swing默认 `BorderLayout`（除 `JPanel` 默认为 `FlowLayout`）。

| 布局              | 特点                                                        | 默认构造                              |                                             | 默认使用容器      |
| --------------- | --------------------------------------------------------- | --------------------------------- | ------------------------------------------- | ----------- |
| `FlowLayout`    | 从左到右、从上到下流式排列，保持组件原始大小                                    | `new FlowLayout()`                | 默认居中，可指定对齐方式：`FlowLayout.LEFT、CENTER、RIGHT` | `JPanel` 默认 |
| `BorderLayout`  | 分为五个区域：`NORTH`、`SOUTH`、`EAST`、`WEST`、`CENTER`，默认放`CENTER` | `new BorderLayout()`              | 不指定区域时默认 CENTER，后加的覆盖前面的                    | `JFrame` 默认 |
| `GridLayout`    | 网格布局，将容器等分为 N 行 × M 列的网格，每个格子大小相同。                        | `new GridLayout(行, 列)`            | 所有格子等大，组件填满格子                               | 无           |
| `BoxLayout`     | 沿 X 轴或 Y 轴排列，组件保持首选大小，可加伸缩空间                              | `BoxLayout(容器, BoxLayout.Y_AXIS)` |                                             | 无           |
| `CardLayout`    | 卡片式，每次只显示一张                                               | `new CardLayout()`                |                                             | 无           |
| `GridBagLayout` | 最强大，使用 `GridBagConstraints` 精确控制每个单元格的行、列、跨行、跨列、填充方式      | `new GridBagLayout()`             |                                             | 无           |

---
## 五、事件处理模型

前面的都是静态界面，点了按钮什么反应都没有。要让它动起来，需要**事件监听器**。

### 委托模型（考试必考名词）

**事件处理三要素**：
- **事件源**：产生事件的组件（如按钮被点击）
- **事件对象**：包含事件信息（如点击的坐标）
- **事件监听器**：接收事件并处理的接口

Swing 事件处理基于**委托模型（处理流程）**：
1. 定义监听器类（实现对应接口）
2. 创建监听器对象
3. 将监听器注册到事件源（`事件源.addXxxListener(监听器)`）
4. 事件发生时，JVM自动调用监听器的方法
### 常见事件和监听器接口

| 事件源            | 产生的低级事件           | 监听器接口                 | 需要重写的方法                                                                                                                   |
| -------------- | ----------------- | --------------------- | ------------------------------------------------------------------------------------------------------------------------- |
| `JButton`      | `ActionEvent`     | `ActionListener`      | `actionPerformed(ActionEvent e)`                                                                                          |
| `JTextField`   | `ActionEvent`（回车） | `ActionListener`      | `actionPerformed(ActionEvent e)`                                                                                          |
| `JCheckBox`    | `ItemEvent`       | `ItemListener`        | `itemStateChanged(ItemEvent e)`                                                                                           |
| `JRadioButton` | `ActionEvent`     | `ActionListener`      | `actionPerformed(ActionEvent e)`                                                                                          |
| `JComboBox`    | `ItemEvent`       | `ItemListener`        | `itemStateChanged(ItemEvent e)`                                                                                           |
| 窗口（`JFrame`）   | `WindowEvent`     | `WindowListener`      | 7 个方法：`windowOpened, windowClosing, windowClosed, windowIconified, windowDeiconified, windowActivated, windowDeactivated` |
| 鼠标             | `MouseEvent`      | `MouseListener`       | `mouseClicked, mousePressed, mouseReleased, mouseEntered, mouseExited`                                                    |
| 鼠标移动/拖拽        | `MouseEvent`      | `MouseMotionListener` | `mouseMoved, mouseDragged`                                                                                                |
| 键盘             | `KeyEvent`        | `KeyListener`         | `keyTyped, keyPressed, keyReleased`                                                                                       |
| 焦点             | `FocusEvent`      | `FocusListener`       | `focusGained, focusLost`                                                                                                  |

**适配器（Adapter）类**：
- 很多监听器有多个方法，但只需实现其中一个。适配器提供空实现，只需重写需要的方法。
- 如 `WindowAdapter` 实现了 `WindowListener` 的七个空方法，使用时只需重写 `windowClosing()` 等。

### 三种写法（考试会要求你区别）

**① 外部类**
```java
class MyActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        System.out.println("点击");
    }
}
button.addActionListener(new MyActionListener());
```

**② 匿名内部类（最常用）**
```java
button.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        System.out.println("匿名类点击");
    }
});
```

**③ Lambda 表达式（JDK 8+，简洁）**
```java
button.addActionListener(e -> System.out.println("Lambda 点击"));
```
前提是接口只有一个抽象方法（函数式接口），`ActionListener` 满足此条件。

###  事件对象常用方法
- `e.getSource()`：返回事件源对象。
- `ActionEvent`：`e.getActionCommand()` 返回按钮标签。
- `KeyEvent`：`e.getKeyChar()`、`e.getKeyCode()`（与 `KeyEvent.VK_A` 等比较）。
- `MouseEvent`：`e.getX()`、`e.getY()`、`e.getClickCount()`。


---

## 六、图形绘制

在 `JPanel` 或 `JComponent` 中重写 `paintComponent(Graphics g)` 方法。



**核心方法**（`Graphics` 对象）：

| 方法                         | 作用    |
| -------------------------- | ----- |
| `drawLine(x1, y1, x2, y2)` | 画直线   |
| `drawRect(x, y, w, h)`     | 画矩形边框 |
| `fillRect(x, y, w, h)`     | 画填充矩形 |
| `drawOval(x, y, w, h)`     | 画椭圆边框 |
| `fillOval(x, y, w, h)`     | 画填充椭圆 |
| `drawString(str, x, y)`    | 绘制文字  |
| `setColor(Color c)`        | 设置颜色  |
| `setFont(Font f)`          | 设置字体  |
**要点**：
- `Graphics` 是抽象类，实际传入的是 `Graphics2D` 对象（可强制转换后获得更多功能）。
- 不要自己调用 `paintComponent`，由系统调用（通过 `repaint()` 触发）。
- 坐标原点在左上角，x 向右，y 向下。

---

## 九、JOptionPane 标准对话框

JOptionPane 属于 `javax.swing` 包，提供一组**静态方法**来弹出标准对话框。考试只考四种方法的名称和参数，不考复杂用法。

四种标准对话框均为静态方法：

```java
// 消息对话框
JOptionPane.showMessageDialog(parent, "消息内容", "标题", JOptionPane.INFORMATION_MESSAGE);

// 确认对话框，返回 int（YES_OPTION / NO_OPTION / CANCEL_OPTION）
int choice = JOptionPane.showConfirmDialog(parent, "确定删除？", "确认", JOptionPane.YES_NO_OPTION);

// 输入对话框，返回用户输入的字符串（取消返回 null）
String name = JOptionPane.showInputDialog(parent, "请输入姓名：", "输入", JOptionPane.QUESTION_MESSAGE);

// 选项对话框
Object[] options = {"A", "B", "C"};
int sel = JOptionPane.showOptionDialog(parent, "选一个", "选择", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
```

**参数** `parent` 若为 `null`，对话框在屏幕中央显示。消息类型常量：`ERROR_MESSAGE`、`INFORMATION_MESSAGE`、`WARNING_MESSAGE`、`QUESTION_MESSAGE`、`PLAIN_MESSAGE`。

---


##### 四种标准对话框

| 方法                    | 对话框类型 | 作用                              |
| --------------------- | ----- | ------------------------------- |
| `showInputDialog()`   | 输入对话框 | 弹出一个带输入框的对话框，用户输入后返回字符串         |
| `showMessageDialog()` | 消息对话框 | 弹出一个提示信息，只有"确定"按钮               |
| `showConfirmDialog()` | 确认对话框 | 弹出 Yes/No/Cancel 等按钮，返回整数表示用户选择 |
| `showOptionDialog()`  | 选项对话框 | 最灵活，可自定义按钮文本                    |

| `showMessageDialog()`             | 图标类型常量  |
| --------------------------------- | ------- |
| `JOptionPane.ERROR_MESSAGE`       | 错误图标    |
| `JOptionPane.WARNING_MESSAGE`     | 警告图标    |
| `JOptionPane.INFORMATION_MESSAGE` | 信息图标    |
| `JOptionPane.QUESTION_MESSAGE`    | 问号图标    |
|                                   |         |
| `showConfirmDialog()`             | **返回值** |
| `JOptionPane.YES_OPTION`          | 点了"是"   |
| `JOptionPane.NO_OPTION`           | 点了"否"   |
| `JOptionPane.CANCEL_OPTION`       | 点了"取消"  |
| `JOptionPane.CLOSED_OPTION`       | 直接关闭对话框 |

---


