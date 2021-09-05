# maven-assembly-plugin 入门指南



当你使用 Maven 对项目打包时，你需要了解以下 3 个打包 plugin，它们分别是

| plugin                | function                                       |
| --------------------- | ---------------------------------------------- |
| maven-jar-plugin      | maven 默认打包插件，用来创建 project jar       |
| maven-shade-plugin    | 用来打可执行包，executable(fat) jar            |
| maven-assembly-plugin | 支持定制化打包方式，例如 apache 项目的打包方式 |

下面我们就简单介绍一下 maven-assembly-plugin。

## 使用方法

&emsp;使用 descriptors，指定打包文件 src/assembly/src.xml，在该配置文件内指定打包操作。

```
<project>
  [...]
  <build>
    [...]
    <plugins>
      <plugin>
        <artifactId>maven-assembly-plugin</artifactId>
        <version>3.0.0</version>
        <configuration>
          <descriptors>
            <descriptor>src/assembly/src.xml</descriptor>
          </descriptors>
        </configuration>
        [...]
</project>
```

## 描述符文件元素

### id

```
<id>release</id>
```

id 标识符，添加到生成文件名称的后缀符。如果指定 id 的话，目标文件则是 ![{artifactId}-](https://math.jianshu.com/math?formula=%7BartifactId%7D-){id}.tar.gz

### formats

maven-assembly-plugin 支持的打包格式有zip、tar、tar.gz (or tgz)、tar.bz2 (or tbz2)、jar、dir、war，可以同时指定多个打包格式

```
  <formats>
    <format>tar.gz</format>
    <format>dir</format>
  </formats>
```

### dependencySets

用来定制工程依赖 jar 包的打包方式，核心元素如下表所示

| 元素              | 类型         | 作用                                 |
| ----------------- | ------------ | ------------------------------------ |
| outputDirectory   | String       | 指定包依赖目录，该目录是相对于根目录 |
| includes/include* | List<String> | 包含依赖                             |
| excludes/exclude* | List<String> | 排除依赖                             |

```
<dependencySets>
    <dependencySet>
      <outputDirectory>/lib</outputDirectory>
    </dependencySet>
  </dependencySets>
```

### fileSets

管理一组文件的存放位置，核心元素如下表所示。

| 元素              | 类型         | 作用                                                         |
| ----------------- | ------------ | ------------------------------------------------------------ |
| outputDirectory   | String       | 指定文件集合的输出目录，该目录是相对于根目录                 |
| includes/include* | List<String> | 包含文件                                                     |
| excludes/exclude* | List<String> | 排除文件                                                     |
| fileMode          | String       | 指定文件属性，使用八进制表达，分别为(User)(Group)(Other)所属属性，默认为 0644 |

```
  <fileSets>
    <fileSet>
      <includes>
        <include>bin/**</include>
      </includes>
      <fileMode>0755</fileMode>
    </fileSet>

    <fileSet>
      <includes>
        <include>/conf/**</include>
        <include>logs</include>
      </includes>
    </fileSet>

  </fileSets>
```

### files

可以指定目的文件名到指定目录，其他和 fileSets 相同，核心元素如下表所示。

| 元素            | 类型   | 作用                       |
| --------------- | ------ | -------------------------- |
| source          | String | 源文件，相对路径或绝对路径 |
| outputDirectory | String | 输出目录                   |
| destName        | String | 目标文件名                 |
| fileMode        | String | 设置文件 UNIX 属性         |

## 样例

pom.xml

```
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <descriptors>
                        <!-- 描述文件路径 -->
                        <descriptor>release.xml</descriptor>
                    </descriptors>
                    <finalName>sby</finalName>
                </configuration>
                <executions>
                    <execution>
                        <!--名字任意 -->
                        <id>make-assembly</id>
                        <!-- 绑定到package生命周期阶段上 -->
                        <phase>package</phase>
                        <goals>
                            <!-- 只运行一次 -->
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

release.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<assembly>
    <id>${project.version}</id>
    <formats>
        <format>tar.gz</format>
        <format>dir</format>
    </formats>
    <fileSets>

        <fileSet>
            <directory>${project.basedir}/bin</directory>
            <outputDirectory>bin</outputDirectory>
            <includes>
                <include>*</include>
            </includes>
            <lineEnding>unix</lineEnding>
            <fileMode>0755</fileMode>
            <directoryMode>0755</directoryMode>
        </fileSet>

        <!-- 将动态编译的配置打包进去 -->
        <fileSet>
            <directory>src/main/resources</directory>
            <outputDirectory>conf</outputDirectory>

        </fileSet>
    </fileSets>
    <dependencySets>
        <dependencySet>
            <outputDirectory>./lib</outputDirectory>
            <includes>
            	 <!-- your package -->
                <include>com.your.package*</include>
            </includes>
        </dependencySet>
        <dependencySet>
            <outputDirectory>./common_lib</outputDirectory>
            <excludes>
                 <!-- dependece lib -->
                <exclude>com.dependece.lib:*</exclude>
            </excludes>
        </dependencySet>
    </dependencySets>
</assembly>
```

最终创建生成可执行二进制工程

引用::https://www.jianshu.com/p/14bcb17b99e0