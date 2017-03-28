# boot-dbm-sample
基于spring boot集成dbm的示例项目。

1、在本地mysql新建一个web-sample的数据库；
2、在web-sample数据库执行\src\main\resources\schema.sql 脚本；
3、如果本地数据库非默认配置，请到\src\main\resources\application.yaml 修改数据库配置
4、执行命令：mvn spring-boot:run
5、访问  [http://localhost:8080/user/batchInsert?count=10](http://localhost:8080/user/batchInsert?count=10) 会批量插入10条数据；   
  查看数据： [http://localhost:8080/user/page](http://localhost:8080/user/page)   
  其他参见 UserController   