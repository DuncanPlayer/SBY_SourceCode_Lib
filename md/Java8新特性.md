```java
    User leo = new User(1, "leo",5);
    Function<User,Integer> up1 = user -> user.levelVip  * (user.useAge + 1);
    Integer upgrade = upgrade(up1, leo);
    System.out.println(upgrade);
    
    private static Integer upgrade(Function<User,Integer> mn,User user) {
        return mn.apply(user);
    }
```

