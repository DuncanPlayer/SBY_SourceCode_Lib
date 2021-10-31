## 1. Maven 构建生命周期

Maven 构建生命周期就是 Maven 将一个整体任务划分为一个个的阶段，类似于流程图，按顺序依次执行。也可以指定该任务执行到中间的某个阶段结束。
 Maven 的内部有三个构建生命周期，分别是 clean, default, site。其中 default 生命周期的核心阶段如下所示：

![img](https://upload-images.jianshu.io/upload_images/94765-809bca97c9fcb03a.png?imageMogr2/auto-orient/strip|imageView2/2/w/115/format/webp)

## 2. 如何使用构建生命周期来完成构建工作

- 可以指定某个生命周期的阶段

执行 mvn install 命令，将完成 validate, compile, test, package, verify, install 阶段，并将 package 生成的包发布到本地仓库中。其中某些带有连字符的阶段不能通过 shell 命令单独指定。例如：(pre-*, post-*, or process-*)



```undefined
mvn install
```

- 可以指定多个不同构建生命周期的阶段

执行 mvn clean deploy 命令，首先完成的 clean lifecycle，将以前构建的文件清理，然后再执行 default lifecycle 的 validate, compile, test, package, verify, insstall, deploy 阶段，将 package 阶段创建的包发布到远程仓库中。

```undefined
mvn clean deploy
```

## 3. 阶段与插件的关系

如上所述，Maven 将构建过程定义为 default lifecycle，并将 default lifecycle 划分为一个个的阶段 phase，这一系列 phase 仅仅是规定执行顺序，至于每个阶段做什么工作？由谁来做？答案就在 插件（plugins） 中。
 Maven 对工程的所有操作实实在在的都是由 插件 来完成的。一个插件可以支持多种功能，称之为目标（goal），例如：compiler 插件有两个目标：compile 和 testCompile，分别实现编译源代码 和 编译测试代码。
 如何将插件与 Maven 的构建生命周期绑定在一起呢？通过将插件的目标（goal）与 build lifecycle 中 phase 绑定到一起，这样，当要执行某个 phase 时，就调用插件来完成绑定的目标。
 如下图所示：从图中可以看出，每一个阶段可以绑定0 个 或 多个目标，每个插件可以提供 1 个或多个目标。

![img](https://upload-images.jianshu.io/upload_images/94765-1fd6ddbcb906ab66.png?imageMogr2/auto-orient/strip|imageView2/2/w/441/format/webp)

## 4. 如何为自己的工程创建构建生命周期

- 设置不同的 packaging 类型

在 pom.xml 文件中，packaging 类型支持 jar, war, ear, pom 等多种类型，不同的 packaging 类型会使得不同的 phase 绑定不同的 plugin goal。下面是 packaging 类型为 jar 时，phase 与 plugin goal 的映射关系。

| 阶段                   | 目标                    |
| ---------------------- | ----------------------- |
| process-resources      | resources:resources     |
| compile                | compiler:compile        |
| process-test-resources | resources:testResources |
| test-compile           | compiler:testCompile    |
| test                   | surefire:test           |
| package                | jar:jar                 |
| install                | install:install         |
| deploy                 | deploy:deploy           |

- 配置 plugin

在 pom.xml 文件中， <build> <plugins> 元素下可以添加 <plugin>，通过指定 goal 和 phase 来进行绑定。
 例如：将插件 modello-maven-plugin 的 java 目标绑定到 generate-sources 阶段

```xml
<plugin>
  <groupId>org.codehaus.modello</groupId>
  <artifactId>modello-maven-plugin</artifactId>
  <version>1.8.1</version>
  <executions>
    <execution>
      <configuration>
        <models>
          <model>src/main/mdo/maven.mdo</model>
        </models>
        <version>4.0.0</version>
      </configuration>
      <phase>generate-sources</phase>
      <goals>
        <goal>java</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

## 5. 我没有在 pom.xml 指定任何 plugin，但是也能正常构建工程

你可以能会疑问，默认的 pom.xml 文件并没有配置各种 plugin，但是也能正常构建工程？答案是 Maven 自己默认指定了 plugin。
 下面是一个没有配置任何 plugin 的 pom.xml，执行 mvn install 的输出日志，从日志中可以看到 一系列的 插件(plugin):版本号:目标(phase)，例如 maven-resources-plugin:2.6:resources (default-resources)，maven-compiler-plugin:3.1:compile (default-compile) ，maven-resources-plugin:2.6:testResources (default-testResources)，maven-compiler-plugin:3.1:testCompile (default-testCompile)，maven-surefire-plugin:2.12.4:test (default-test)，maven-jar-plugin:2.4:jar (default-jar) ，maven-install-plugin:2.4:install (default-install)，

```csharp
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building my-app 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ my-app ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO]
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ my-app ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /Users/zhangguanghui/git/my-app/target/classes
[INFO]
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ my-app ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /Users/zhangguanghui/git/my-app/src/test/resources
[INFO]
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ my-app ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /Users/zhangguanghui/git/my-app/target/test-classes
[INFO]
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ my-app ---
[INFO] Surefire report directory: /Users/zhangguanghui/git/my-app/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.mycompany.app.AppTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.004 sec

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

[INFO]
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ my-app ---
[INFO] Building jar: /Users/zhangguanghui/git/my-app/target/my-app-1.0-SNAPSHOT.jar
[INFO]
[INFO] --- maven-install-plugin:2.4:install (default-install) @ my-app ---
[INFO] Installing /Users/zhangguanghui/git/my-app/target/my-app-1.0-SNAPSHOT.jar to /Users/zhangguanghui/.m2/repository/com/mycompany/app/my-app/1.0-SNAPSHOT/my-app-1.0-SNAPSHOT.jar
[INFO] Installing /Users/zhangguanghui/git/my-app/pom.xml to /Users/zhangguanghui/.m2/repository/com/mycompany/app/my-app/1.0-SNAPSHOT/my-app-1.0-SNAPSHOT.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.726 s
[INFO] Finished at: 2016-11-20T00:41:11+08:00
[INFO] Final Memory: 15M/310M
[INFO] ------------------------------------------------------------------------
```

## 5. 完整的 clean, default, site build lifecycle

- clean lifecycle

  | phase             | function                                                     |
  | ----------------- | ------------------------------------------------------------ |
  | pre-clean execute | execute processes needed prior to the actual project cleaning |
  | clean             | remove all files generated by the previous build             |
  | post-clean        | execute processes needed to finalize the project cleaning    |

- default lifecycle

  | phase                        | function                                                     |
  | ---------------------------- | ------------------------------------------------------------ |
  | validate                     | validate the project is correct and all necessary information is available. |
  | initialize                   | initialize build state, e.g. set properties or create directories. |
  | generate-sources             | generate any source code for inclusion in compilation.       |
  | process-sources              | process the source code, for example to filter any values.   |
  | generate-resources           | generate resources for inclusion in the package.             |
  | process-resources            | copy and process the resources into the destination directory, ready for packaging. |
  | compile                      | compile the source code of the project.                      |
  | process-classes              | post-process the generated files from compilation, for example to do bytecode enhancement on Java classes |
  | generate-test-sources        | generate any test source code for inclusion in compilation.  |
  | process-test-sources         | process the test source code, for example to filter any values. |
  | generate-test-resources      | create resources for testing.                                |
  | process-test-resources       | copy and process the resources into the test destination directory. |
  | test-compile                 | compile the test source code into the test destination directory |
  | process-test-classes         | post-process the generated files from test compilation, for example to do bytecode enhancement on Java classes. For Maven 2.0.5 and above. |
  | test                         | run tests using a suitable unit testing framework. These tests should not require the code be packaged or deployed. |
  | prepare-package              | perform any operations necessary to prepare a package before the actual packaging. This often results in an unpacked, processed version of the package. (Maven 2.1 and above) |
  | package                      | take the compiled code and package it in its distributable format, such as a JAR. |
  | pre-integration-test perform | actions required before integration tests are executed. This may involve things such as setting up the required environment. |
  | integration-test             | process and deploy the package if necessary into an environment where integration tests can be run. |
  | post-integration-test        | perform actions required after integration tests have been executed. This may including cleaning up the environment. |
  | verify                       | run any checks to verify the package is valid and meets quality criteria. |
  | install                      | install the package into the local repository, for use as a dependency in other projects locally. |
  | deploy                       | done in an integration or release environment, copies the final package to the remote repository for sharing with other developers and projects. |

- site lifecycle

| phase       | function                                                     |
| ----------- | ------------------------------------------------------------ |
| pre-site    | execute processes needed prior to the actual project site generation |
| site        | generate the project's site documentation                    |
| post-site   | execute processes needed to finalize the site generation, and to prepare for site deployment |
| site-deploy | deploy the generated site documentation to the specified web server |

