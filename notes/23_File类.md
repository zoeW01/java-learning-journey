
# **File类：**

1. **File类概述：**在 Java 的 IO 体系中，File类扮演着特殊的角色，它是文件和目录路径名的抽象表示 。File类主要用于对文件和目录的属性进行管理，以及执行一些基本的操作，但它并不涉及文件内容的读写操作。简单来说，File类就像是文件和目录的 “信息管理者”，通过它可以获取文件或目录的各种属性信息，对文件和目录进行一些常规的操作，但如果要读取或写入文件的具体内容，就需要输入输出流了。
    
2. **File类的构造函数：**没有无参构造，因为是个类所有需要new对象。
    
    1. **File(String pathname)**：通过将给定的路径名字符串转换为抽象路径名来创建新的File实例。
        
    2. **File(String parent, String child)**：从父路径名字符串和子路径名字符串创建新的File实例。
        
    3. **File(File parent, String child)**：从父抽象路径名和子路径名字符串创建新的File实例。
        
    4. File file1 = new File("test.txt");                // 相对路径  
        File file2 = new File("/user/docs", "readme.md"); // 父路径+子路径  
        File file3 = new File(file2, "version.txt");      // 父File对象+子路径
        
    5. 绝对路径：全部路径，从根目录开始
        
        相对路径：使用`./`表示当前目录下，通常`./`也可以省略，不写默认从当前目录开始
        
    6. 可以使用“/”和“\”作为分隔符分割路径，因为单“\”表示转义所有使用时应该使用双斜杠“`\\`”
        
    7. 创建一个文件对象和创建一个文件在 Java 中是两个不同的概念。前者是在虚拟机中创建一个文件对象，但却没有将它真正地创建到 OS 的文件系统，随着虚拟机的关闭，这个创建的对象也就消失了。而创建一个文件才是在系统中真正地创建文件。一个 File 对象表示的文件并不是真正的文件，只是一个代理而已，通过这个代理可以操作文件。
        
3. **File类的常用方法：**
    
    1. **文件和目录的判断方法：**
        
        1. **isFile()**：用于判断File对象表示的是否为文件。返回true表示是文件，false表示不是文件，可能是目录或者不存在。
            
        2. **isDirectory()**：判断File对象表示的是否为目录。返回true表示是目录，false表示不是目录，可能是文件或者不存在。
            
        3. **exists()**：判断File对象所表示的文件或目录是否实际存在。返回true表示存在，false表示不存在。
            
        4. **canRead()**：判断File对象所表示的文件或目录是否可读。返回true表示可读，false表示不可读。
            
        5. **canWrite()**：判断File对象所表示的文件或目录是否可写。返回true表示可写，false表示不可写。
            
        6. **isHidden()**：判断File对象所表示的文件或目录是否隐藏。返回true表示隐藏，false表示未隐藏。
            
        7. **isAbsolute()**：判断File对象所表示的文件或目录是否为绝对路径。
            
    2. **文件和目录的获取方法：**
        
        1. **getPath()**：将File对象转换为路径名字符串，返回的是创建File对象时使用的路径。
            
        2. **getAbsolutePath()**：返回File对象的绝对路径名字符串，是从盘符开始的完整路径。
            
        3. **getName()**：返回File对象表示的文件或目录的名称，不包含路径。
            
        4. **list()**：返回一个String数组，表示File对象所代表目录中的所有子文件和子目录的名称。
            
        5. **listFiles()**：返回一个File数组，表示File对象所代表目录中的所有子文件和子目录的File对象（如目录不可读或路径不存在可能返回null）。
            
    3. **文件和目录的创建与删除方法：**
        
        1. **createNewFile()**：当且仅当具有该名称的文件尚不存在时，创建一个新的空文件。创建文件时，文件所在目录必须存在，否则创建时会抛出异常。如果创建成功返回true，否则返回false。
            
        2. **mkdir()**：创建由此File对象表示的目录。如果目录创建成功返回true，否则返回false，该方法只能创建单级目录。
            
        3. **mkdirs()**：创建由此File对象表示的目录，包括任何必需但不存在的父目录。如果目录创建成功返回true，否则返回false，可以创建多级目录。
            
        4. **delete()**：删除由此File对象表示的文件或目录。如果删除成功返回true，否则返回false。注意，删除目录时，该目录必须为空才能删除（无法删除多级目录）。
            
    4. **length() ：**返回long类型的字节长度
        
    5. **其他方法：**
        
    
    6. |方法|描述|
        |---|---|
        |public String getParent()|获取文件父路径字符串。|
        |public boolean isAbsolute()|测试路径名是否为绝对路径。|
        |||
        |public String toString()|返回文件的路径名字符串。|
        |public String getCanonicalPath() throws IOException|返回规范的路径名字符串。|
        |public File getCanonicalFile() throws IOException|返回文件（含相对路径名）规范形式。|
        |public File getAbsoluteFile()|返回此抽象路径名的绝对形式。|
        |public boolean renameTo(File dest)|重命名由此抽象路径名表示的文件。|
        |public static File createTempFile(String prefix, String suffix, File directory) throws IOException|在指定的目录中创建一个新的空文件，使用给定的前缀和后缀字符串生成其名称。|
        |public static File createTempFile(String prefix, String suffix) throws IOException|在默认临时文件目录中创建一个空文件，使用给定的前缀和后缀生成其名称。|
        |||
        |public long lastModified()|返回指定的文件最后被修改的时间。|
        |public void deleteOnExit()|当虚拟机执行结束时，请求删除指定的文件或目录。|
        |目录操作||
        |public String[] list()|返回指定目录下的文件（存入数组）。|
        |public String[] list(FilenameFilter filter)|返回指定目录下满足指定文件过滤器的文件。|
        |public File[] listFiles(FilenameFilter filter)|返回指定目录下满足指定文件过滤器的文件。|
        |public File[] listFiles(FileFilter filter)|返回指定目录下满足指定文件过滤器的文件（返回路径名应满足文件过滤器）。|
        |public static File[] listRoots()|列出可用文件系统的根目录结构。|
        |文件属性设置||
        |public boolean setLastModified(long time)|设置指定文件或目录的最后修改时间，操作成功返回 true，否则返回 false。|
        |public boolean setReadOnly()|标记指定的文件或目录为只读属性，操作成功返回 true，否则返回 false。|
        |其他||
        |public URL toURL() throws MalformedURLException|把相对路径名存入 URL 文件|
        |public int compareTo(Object o)|比较两个抽象的路径名字|
        |public boolean equals(Object obj)|测试此抽象路径名与给定对象的相等性；|
        |public int hashCode()|返回文件名的哈希值。|