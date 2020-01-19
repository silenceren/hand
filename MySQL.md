###MySQL的常见命令
    1.查看当前所有的数据库
    show databases;
    2.打开指定的库
    use databaseName;
    3.查看当前库的所有表
    show tables;
    4.查看其它库的所有表
    show tables from databseName;
    5.创建表
    create table tableName{
        列名 列类型,
        列名 列类型,
        ...
    };
    6.查看表结构
    desc databaseName；
    
    7.查看服务器的版本
    方式一：登录到mysql服务器
    select version();
    方式二：没有登录到mysql服务端
    mysql --version
    或
    mysql --V