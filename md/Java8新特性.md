```java
    // 模拟写了一段逻辑
    // 用户VIP升级
    User leo = new User(1, "leo",5);
    Function<User,Integer> up1 = user -> user.levelVip  + (user.useAge + 1);
    Integer upgrade = upgrade(up1, leo);
    System.out.println(upgrade);
    
    private  Integer upgrade(Function<User,Integer> mn,User user) {
        return mn.apply(user);
    }
```

### 为什么使用 Lambda 表达式

```
Lambda 是一个匿名函数，我们可以把 Lambda 表达式理解为是一段可以传递的代码（将代码
像数据一样进行传递）。可以写出更简洁、更灵活的代码。作为一种更紧凑的代码风格，使Java的语言表达能力得到了提升。
```

### 基本案例

```
Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");      
            }
        };

//	等效于上面
Runnable runnable= () -> System.out.println("hello");

TreeSet<String> treeSet=new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(),o2.length());
            }
});

TreeSet<String> treeSet=new TreeSet<>((o1, o2) -> Integer.compare(o1.length(),o2.length()));

TreeSet<String> treeSet=new TreeSet<>(Comparator.comparingInt(String::length));
```

### Lambda 表达式语法

```
Lambda 表达式在Java 语言中引入了一个新的语法元 素和操作符。这个操作符为 “->” ，
该操作符被称 为 Lambda 操作符或剪头操作符。它将 Lambda 分为 两个部分：
```

## 函数式接口

- 只包含一个抽象方法的接口，称为函数式接口
- 可以通过 Lambda 表达式来创建该接口的对象。（若 Lambda
  表达式抛出一个受检异常，那么该异常需要在目标接口的抽象方
  法上进行声明）
- 可以在任意函数式接口上使用 @FunctionalInterface 注解,这样做可以检查它是否是一个函数式接口，同时 javadoc 也会包含一条声明，说明这个接口是一个函数式接口。
- 之所以Lambda必须和函数式接口配合是因为，接口如果多个函数，则Lambda表达式无法确定实现的是哪个

```
@FunctionalInterface
public interface User {
     double upgradeMoney();
}
```

### Java 内置四大核心函数式接口

| 函数式接口                | 参数类型 | 返回类型 | 用途                                                         |
| :------------------------ | -------- | -------- | :----------------------------------------------------------- |
| Consumer<T> 消费型接口    | T        | void     | 对类型为T的对象应用操 作，包含方法： void accept(T t)        |
| Supplier<T> 供给型接口    | 无       | T        | 返回类型为T的对象，包 含方法：T get();                       |
| Function<T, R> 函数型接口 | T        | R        | 对类型为T的对象应用操 作，并返回结果。结果 是R类型的对象。包含方 法：R apply(T t); |
| Predicate<T> 断言型接口   | T        | boolean  | 确定类型为T的对象是否 满足某约束，并返回 boolean 值。包含方法 boolean test(T t); |

### 其他接口

| 函数式接口                                             | 参数类型                                               | 返回类型        | 用途                                                         |
| ------------------------------------------------------ | ------------------------------------------------------ | --------------- | ------------------------------------------------------------ |
| BiFunction<T, U, R>                                    | T,U                                                    | R               | 对类型为 T, U 参数应用 操作，返回 R 类型的结 果。包含方法为 R apply(T t, U u); |
| UnaryOperator<T> (Function子接口)                      | T                                                      | T               | 对类型为T的对象进行一 元运算，并返回T类型的 结果。包含方法为 T apply(T t) |
| BinaryOperator<T> (BiFunction 子接口)                  | T,T                                                    | T               | 对类型为T的对象进行二 元运算，并返回T类型的 结果。包含方法为 T apply(T t1, T t2); |
| BiConsumer<T, U>                                       | T,U                                                    | void            | 对类型为T, U 参数应用 操作。包含方法为 void accept(T t, U u) |
| ToIntFunction<T> ToLongFunction<T> ToDoubleFunction<T> | ToIntFunction<T> ToLongFunction<T> ToDoubleFunction<T> | int long double | 分 别 计 算 int 、 long 、 double、值的函数                  |
| IntFunction<R> LongFunction<R> DoubleFunction<R>       | int long double                                        | R               | 参数分别为int、long、double 类型的函数                       |

# 方法引用与构造器引用

## 方法引用

当要传递给Lambda体的操作，已经有实现的方法了，可以使用方法引用！
 （实现抽象方法的参数列表，必须与方法引用方法的参数列表保持一致！）
 方法引用：使用操作符 “::” 将方法名和对象或类的名字分隔开来。 如下三种主要使用情况：

- 对象::实例方法
- 类::静态方法
- 类::实例方法

```
//	例如：此时Consumer参数类型和返回值和println方法一致
//	对象::实例方法名
PrintStream printStream=System.out;
Consumer<String> con= printStream::println;
con.accept("haha");


//	类::静态方法名
Comparator<Integer> com=Integer::compare;
Comparator<Integer> com1=(x,y)->Integer.compare(x,y);

//	类::实例方法
//	注意：当需要引用方法的第一个参数是调用对象，并且第二个参数是需要引
//	用方法的第二个参数(或无参数)时：ClassName::methodName
BiPredicate<String,String> biPredicate=(x,y)->x.equals(y);
BiPredicate<String,String> biPredicate1= String::equals;
biPredicate.test("a","b");
biPredicate1.test("a","b");
//BiPredicate是Predicate 断言型接口的一个具体实现
```

## 构造器引用

&emsp;与函数式接口相结合，自动与函数式接口中方法兼容。 可以把构造器引用赋值给定义的方法，与构造器参数 列表要与接口中抽象方法的参数列表一致

```
Function<Integer,Integer[]> fun=n->new MyClass();
Function<Integer,Integer[]> fun1=MyClass::new;
fun.apply(args...)
```

## 数组引用

格式： type[] :: new

```
Function<Integer,Integer[]> fun=n->new Integer[n];
Function<Integer,Integer[]> fun1=Integer[]::new;
fun.apply(10);
```

# Stream

  &emsp;Java8中有两大最为重要的改变。第一个是 Lambda 表达式；另外一 个则是 Stream       API(java.util.stream.*)。
 &emsp;Stream 是 Java8 中处理集合的关键抽象概念，它可以指定你希望对
 集合进行的操作，可以执行非常复杂的查找、过滤和映射数据等操作。
 使用Stream API 对集合数据进行操作，就类似于使用 SQL 执行的数
 据库查询。也可以使用 Stream API 来并行执行操作。简而言之，
 Stream API 提供了一种高效且易于使用的处理数据的方式。

## 什么是 Stream

&emsp;是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列。
&emsp;“集合讲的是数据，流讲的是计算！”

注意：

- ① Stream 自己不会存储元素。
- ② Stream 不会改变源对象。相反，他们会返回一个持有结果的新Stream。
- ③ Stream 操作是延迟执行的。这意味着他们会等到需要结果的时候才执行。

![img](https://upload-images.jianshu.io/upload_images/1334027-51fb7e36485056fa.png?imageMogr2/auto-orient/strip|imageView2/2/w/921/format/webp)

## Stream 的中间操作

&emsp;多个中间操作可以连接起来形成一个流水线，除非流水 线上触发终止操作，否则中间操作不会执行任何的处理！ 而在终止操作时一次性全部处理，称为“惰性求值”。

### 筛选和切片

| 方法                | 描述                                                         |
| ------------------- | ------------------------------------------------------------ |
| filter(Predicate p) | 接收 Lambda ， 从流中排除某些元素。                          |
| distinct()          | 筛选，通过流所生成元素的 hashCode() 和 equals() 去 除重复元素 |
| limit(long maxSize) | 截断流，使其元素不超过给定数量。                             |
| skip(long n)        | 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素 不足 n 个，则返回一个空流。与 limit(n) 互补 |

### 映射

| 方法                            | 描述                                                         |
| ------------------------------- | ------------------------------------------------------------ |
| map(Function f)                 | 接收一个函数作为参数，该函数会被应用到每个元 素上，并将其映射成一个新的元素。 |
| mapToDouble(ToDoubleFunction f) | 接收一个函数作为参数，该函数会被应用到每个元 素上，产生一个新的 DoubleStream。 |
| mapToInt(ToIntFunction f)       | 接收一个函数作为参数，该函数会被应用到每个元 素上，产生一个新的 IntStream。 |
| mapToLong(ToLongFunction f)     | 接收一个函数作为参数，该函数会被应用到每个元 素上，产生一个新的 LongStream。 |
| flatMap(Function f)             | 接收一个函数作为参数，将流中的每个值都换成另 一个流，然后把所有流连接成一个流 |

&emsp;**flatMap**是解决流中嵌套流比如{{a,a,a},{b,b,b}},它会把这些流转换成一个流{a,a,a,b,b,b}方便处理

### 常用方法总结

| List<Employee> emps= list.stream().collect(Collectors.toList()); | 把流中元素收集到List                    |
| :----------------------------------------------------------- | --------------------------------------- |
| list.stream().collect(Collectors.counting());                | 计算流中元素的个数                      |
| list.stream().collect(Collectors.averagingInt(Employee::getSalary)); | 计算流中元素Integer属性的平均 值        |
| list.stream().map(Employee::getName).collect(Collectors.joining()); | 连接流中每个字符串                      |
| list.stream().collect(Collectors.maxBy(comparingInt(Employee::getSalary))); | 根据比较器选择最大值                    |
| Map<Emp.Status, List<Emp>> map= list.stream().collect(Collectors.groupingBy(Employee::getStatus)); | 根据某属性值对流分组，属 性为K，结果为V |
| Map<Boolean,List<Emp>>vd= list.stream().collect(Collectors.partitioningBy(Employee::getManage)); | 根据true或false进行分区                 |

# Optional 类

```
Optional<T> 类(java.util.Optional) 是一个容器类，代表一个值存在或不存在，原来用 null 表示一个值不存在，现在 Optional 可以更好的表达这个概念。并且可以避免空指针异常。
```

常用方法：

- Optional.of(T t) : 创建一个 Optional 实例
- Optional.empty() : 创建一个空的 Optional 实例
- Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
- isPresent() : 判断是否包含值
- orElse(T t) : 如果调用对象包含值，返回该值，否则返回t
- orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
- map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
- flatMap(Function mapper):与 map 类似，要求返回值必须是Optional



```
public static void main(String[] args) {
        /**
         * of接收的参数，可能外部传入，可能为空，但是之前的方式真正报空指针还不知道在哪里
         * 但是如果Optional.of则始终都会报在这里，方便快速确定空指针的位置
         */
        Optional<Student> op=Optional.of(null);


        /**
         * 该种方式是构建一个null，在第一行不会报错，get时候才报错
         */
        Optional<Student> op1=Optional.empty();
        System.out.println(op1.get());

        /**
         * 传递进来能构建就构建对象，否则就构建null
         * 报错也是在get的时候
         * 其实就是of和empty的综合
         */
        Optional<Student> op2=Optional.ofNullable(null);
        System.out.println(op2.get());

        //代表有值才get，等价于非空判断
        if (op2.isPresent()) {
            System.out.println(op2.get());
        }

        //如果op有值则获取，否则调用orElse的构造并返回该对象
        Student student = op.orElse(new Student());
        //这个功能类似，只是参数是函数式接口，则可以内部写很复杂的逻辑，甚至根据条件返回不同的结果
        Student student1 =op.orElseGet(()->new Student());
        Student student2 =op.orElseGet(Student::new);


        Optional<String> str=  op.map(e->e.getName());
        System.out.println(str.get());//此时获取的就是name而不是全部对象属性


        //和map基本相同，只是返回的必须是Optional,说白了，进一步避免空指针异常
        Optional<String> str2=op.flatMap(e->Optional.of(e.getName()));
        System.out.println(str2.get());

    }
```

# 新时间日期 API

## 使用 LocalDate、LocalTime、LocalDateTime

```
LocalDate、LocalTime、LocalDateTime 类的实 例是不可变的对象，分别表示使用 ISO-8601日 历系统的日期、时间、日期和时间。它们提供
了简单的日期或时间，并不包含当前的时间信
息。也不包含与时区相关的信息。
```

| 方法                                               | 描述                                                         | 示例                                                         |
| -------------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| now()                                              | 静态方法，根据当前时间创建对象                               | LocalDate localDate = LocalDate.now(); LocalTime localTime = LocalTime.now(); LocalDateTime localDateTime = LocalDateTime.now(); |
| of()                                               | 静态方法，根据指定日期/时间创建 对象                         | LocalDate localDate = LocalDate.of(2016, 10, 26); LocalTime localTime = LocalTime.of(02, 22, 56); LocalDateTime localDateTime = LocalDateTime.of(2016, 10, 26, 12, 10, 55); |
| plusDays, plusWeeks, plusMonths, plusYears         | 向当前 LocalDate 对象添加几天、 几周、几个月、几年           |                                                              |
| minusDays, minusWeeks, minusMonths, minusYears     | 从当前 LocalDate 对象减去几天、 几周、几个月、几年           |                                                              |
| plus, minus                                        | 添加或减少一个 Duration或 Period                             |                                                              |
| withDayOfMonth, withDayOfYear, withMonth, withYear | 将月份天数、年份天数、月份、年 份修改为指定的值并返回新的 LocalDate对象 |                                                              |
| getDayOfMonth                                      | 获得月份天数(1-31)                                           |                                                              |
| getDayOfYear                                       | 获得年份天数(1-366)                                          |                                                              |
| getDayOfWeek                                       | 获得星期几(返回一个 DayOfWeek 枚举值)                        |                                                              |
| getMonth                                           | 获得月份, 返回一个 Month枚举值                               |                                                              |
| getMonthValue                                      | 获得月份(1-12)                                               |                                                              |
| getYear                                            | 获得年份                                                     |                                                              |
| until                                              | 获得两个日期之间的 Period 对象， 或者指定 ChronoUnits的数字  |                                                              |
| isBefore, isAfter                                  | 比较两个 LocalDate                                           |                                                              |
| isLeapYear                                         | 判断是否是闰年                                               |                                                              |

