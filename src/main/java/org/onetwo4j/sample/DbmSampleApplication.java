package org.onetwo4j.sample;

import java.util.ArrayList;
import java.util.List;

import org.onetwo.common.db.sqlext.ExtQuery.K;
import org.onetwo.common.db.sqlext.ExtQuery.K.IfNull;
import org.onetwo.dbm.spring.EnableDbm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDbm
@RestController("user")
public class DbmSampleApplication {

	@RequestMapping(value="", method=RequestMethod.GET)
	public Object list(String userName){
		List<User> users = User.findList("userName", userName, K.IF_NULL, IfNull.Ignore);
		return users;
	}

	@RequestMapping(value="/view/{id}", method=RequestMethod.GET)
	public Object view(@PathVariable("id")Long id){
		User user = User.findById(id);
		return user;
	}

	@RequestMapping(value="batchInsert", method=RequestMethod.GET)
	public Object batchInser(int count){
		List<User> users = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			User user = new User();
			user.setUserName("test-username-"+i);
			user.setPassword("123456");
			users.add(user);
		}
		int insertCount = User.batchInsert(users);
		return "insert "+insertCount+" users.";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DbmSampleApplication.class, args);
	}
}
