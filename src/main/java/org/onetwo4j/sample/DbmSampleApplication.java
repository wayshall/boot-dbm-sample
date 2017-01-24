package org.onetwo4j.sample;

import org.onetwo.common.db.BaseEntityManager;
import org.onetwo.common.utils.Page;
import org.onetwo.dbm.spring.EnableDbm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableDbm
@RestController
public class DbmSampleApplication {
	@Service
	@Transactional
	public static class UserService {
		@Autowired
		private BaseEntityManager baseEntityManager;
		
		public Page<User> findPage(Integer pageNo){
			Page<User> page = Page.create(pageNo);
			this.baseEntityManager.findPage(User.class, page);
			return page;
		}
	}
	/*****
	 * 批量插入10条数据: http://localhost:8080/user/batchInsert?count=10 
	 * 查看数据： http://localhost:8080/user/page
	 * 其他参见 {@linkplain UserController UserController}
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(DbmSampleApplication.class, args);
	}
}
