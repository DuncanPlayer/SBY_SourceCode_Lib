### Redis常用命令指南
###### 1、对value操作的命令

```java
    exists  key   确认一个key是否存在
    del   key   删除一个key
    type   key  返回值类型
    rename oldKeyName newKeyName
    dbsize  返回当前数据库中key的数目
    expire key  设置一个key的过期时间
    ttl  key  获得一个key的活动时间
    select 0  按索引查询
    move key  dbindex 将当前数据库中的key转移到有dbindex索引的数据库
    flushdb  删除当前选择数据库中的所有key
    flushall   删除所有数据库中的所有key
```

###### 2、对String操作的命令

```java
    set key value   给数据库中名称为key的string赋予值value
    get key   获取key值
    getset  获取key值并重新赋值
    mset  key1 value1  key2 value2...  批量set
    mget  key1 key2...  批量get
    setnx  key value   key不存在，则添加key,value
    setex  key  time  value   为key设置过期时间
    incr  为字符整数加一
    decr  为字符整数减一
    incrby key  integer    加多少
    decrby  key  integer  减多少
    append  key  value   同Java中的append
    substr  key  start  end   字符串截取
```

###### 3、对List操作的命令

```java
    rpush  key  value  从尾部添加
    lpush   key  value	从头部添加
    llen  key  返回list长度
    lrange key start end  返回list中start到end的元素
    ltrim  key  start  end 截取名称为key的list，保留start至end之间的元素
    lindex  key  index  返回名称为key的list中index位置的元素
    lset key  index value  给名称为key的list中index位置的元素赋值为value
    lpop key  返回并删除名称为key的list中的首元素
    rpop  key  返回并删除名称为key的list中的尾元素
```

###### 4、对Set操作的命令

 ```java
     sadd key member  向名称为key的set中添加元素member
     srem key member  删除名称为key的set中的元素member
     spop key  随机返回并删除名称为key的set中一个元素
     smove  srckey  destKey  memeber  将member元素从名称为srckey的集合移到名称为dstkey的集合
     scard	返回名称为key的set的size
     sismember key member  测试member是否是名称为key的set的元素
     sinter key1  key2 ...  求交集
     sinterstore  destKey  key1 key2 ... 求交集并将交集保存到destKey的集合
     sunion  key1  key2 ...  求并集
     sunionstore  destKey  key1 key2 ... 求并集并将并集保存到destKey的集合
     sdiff key1 key2 ... 求差集
     sdiffstore destKey  key1 key2 ...求差集并将差集保存到destKey的集合
     smembers  key  返回名称为key的set的所有元素
     srandmember  key  随机返回名称为key的set的一个元素
 ```

###### 5、对zset（sorted set）操作的命令

```java
    zadd  key  score member  score用于排序
    zrem  key  member  删除名称为key的zset中的元素member
    zincrby  key  increment  member  如果在名称为key的zset中已经存在元素member，则该元素的score增加increment；否则向集合中添加该元素，其score的值为increment
    zrank  key member 返回名称为key的zset（元素已按score从小到大排序）中member元素的rank（即index，从0开始），若没有member元素，返回“nil”
    zrevrank  key  member  返回名称为key的zset（元素已按score从大到小排序）中member元素的rank（即index，从0开始），若没有member元素，返回“nil”
    zcard key 返回名称为key的zset的size
    zscore  key member  返回名称为key的zset中元素member的score
    zrange key  start  end 返回名称为key的zset（元素已按score从小到大排序）中的index从start到end的所有元素
    zrevrange  key  start  end  返回名称为key的zset（元素已按score从大到小排序）中的index从start到end的所有元素
    zrangebyscore  key min  max  返回名称为key的zset中score >= min且score <= max的所有元素
    zremrangebyrank  key min max  删除名称为key的zset中rank >= min且rank <= max的所有元素
    zremrangebyscore  key  min  max  删除名称为key的zset中score >= min且score <= max的所有元素
    zunionstore  
    zinterstore
```

###### 6、对Hash操作的命令

```java
   hset  key field value  向名称为key的hash中添加元素field<—>value
   hget  key field  返回名称为key的hash中field对应的value
   hmset  key   filed1 value1  filed2 value2...   向名称为key的hash中添加元素field i<—>value i
   hmget  key filed1  field2...   返回名称为key的hash中field i对应的value
   hincrby  key field integer   将名称为key的hash中field的value增加integer
   hexists   key  field  名称为key的hash中是否存在键为field的域
   hdel   key  field   删除名称为key的hash中键为field的域
   hlen  key  返回名称为key的hash中元素个数
   hkeys  key  返回名称为key的hash中所有键
   hvals   key  返回名称为key的hash中所有键对应的value
   hgetall  key  返回名称为key的hash中所有的键（field）及其对应的value
```





