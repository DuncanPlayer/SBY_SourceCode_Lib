### MySQL常用命令行

```
1、登陆
mysql -uroot -pxxx   登陆
注意，如果是连接到另外的机器上，则需要加入一个参数-h机器IP

停止
mysqladmin -u root -p shutdown
service mysqld stop

启动
service mysqld start

2、新增用户
grant 权限 on 数据库.* to 用户名@登录主机 identified by "密码"
如，增加一个用户user1密码为password1，让其可以在本机上登录， 并对所有数据库有查询、插入、修改、删除的权限。首先用以root用户连入mysql，然后键入以下命令：

grant select,insert,update,delete on *.* to user1@localhost Identified by "password1";

如果希望该用户能够在任何机器上登陆mysql，则将localhost改为"%"。

grant all on mydb.* to NewUserName@HostName identified by "password" ;

3、导出数据
如：mysqldump -u root -p123456 --databases dbname > mysql.dbname
就是把数据库dbname导出到文件mysql.dbname中。

4、分析索引效率，在一般的SQL语句前加上explain
　　1）table：表名；
　　2）type：连接的类型，(ALL/Range/Ref)。其中ref是最理想的；
　　3）possible_keys：查询可以利用的索引名；
　　4）key：实际使用的索引；
　　5）key_len：索引中被使用部分的长度（字节）；
　　6）ref：显示列名字或者"const"（不明白什么意思）；
　　7）rows：显示MySQL认为在找到正确结果之前必须扫描的行数；
　　8）extra：MySQL的建议；
　　
5、更改MySQL目录
MySQL默认的数据文件存储目录为/var/lib/mysql。假如要把目录移到/home/data下需要进行下面几步：

　　 1、home目录下建立data目录
　　 cd /home
　　 mkdir data

　　 2、把MySQL服务进程停掉：
　　 mysqladmin -u root -p shutdown

　　 3、把/var/lib/mysql整个目录移到/home/data
　　 mv /var/-/mysql　/home/data/
　　 这样就把MySQL的数据文件移动到了/home/data/mysql下

　　 4、找到my.cnf配置文件
　　 如果/etc/目录下没有my.cnf配置文件，请到/usr/share/mysql/下找到*.cnf文件，拷贝其中一个到/etc/并改名为my.cnf)中。命令如下：
　　 [root@test1 mysql]# cp /usr/share/mysql/my-medium.cnf　/etc/my.cnf

　　 5、编辑MySQL的配置文件/etc/my.cnf
　　 为保证MySQL能够正常工作，需要指明mysql.sock文件的产生位置。 修改socket=/var/lib/mysql/mysql.sock一行中等号右边的值为：/home/mysql/mysql.sock 。操作如下：
　　 vi　 my.cnf　　　 (用vi工具编辑my.cnf文件，找到下列数据修改之)
　　 # The MySQL server
　　　 [mysqld]
　　　 port　　　= 3306
　　　 #socket　 = /var/lib/mysql/mysql.sock（原内容，为了更稳妥用“#”注释此行）
　　　 socket　 = /home/data/mysql/mysql.sock　　　（加上此行）

　　 6、修改MySQL启动脚本/etc/rc.d/init.d/mysql
　　 最后，需要修改MySQL启动脚本/etc/rc.d/init.d/mysql，把其中datadir=/var/lib/mysql一行中，等号右边的路径改成你现在的实际存放路径：home/data/mysql。
　　 [root@test1 etc]# vi　/etc/rc.d/init.d/mysql
　　 #datadir=/var/lib/mysql　　　　（注释此行）
　　 datadir=/home/data/mysql　　 （加上此行）

　　 7、重新启动MySQL服务
　　 /etc/rc.d/init.d/mysql　start
　　 或用reboot命令重启Linux
　　 如果工作正常移动就成功了，否则对照前面的7步再检查一下。
```

