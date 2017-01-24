package org.onetwo4j.sample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.onetwo.common.db.builder.Querys;
import org.onetwo.common.db.sqlext.ExtQuery.K;
import org.onetwo.common.db.sqlext.ExtQuery.K.IfNull;
import org.onetwo4j.sample.DbmSampleApplication.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/****
 * 为了方便测试示例，全部采用get方法
 * @author way
 *
 */
@RestController
@RequestMapping(value="user", produces="application/json; charset=UTF-8")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="page", method=RequestMethod.GET)
	public Object page(Integer pageNo){
		return userService.findPage(pageNo);
	}

	@RequestMapping(value="list", method=RequestMethod.GET)
	public Object list(String userName){
		//key value 形式的查询条件，K表示特别的属性
		List<User> users = User.findList("userName", userName, K.IF_NULL, IfNull.Ignore);
		//这个query dsl api的查询和上面一样意思
		users = Querys.from(User.class)
						.where()
							.field("userName").equalTo(userName)
							.ignoreIfNull()
						.end()
						.toSelect()
						.list();
		return users;
	}

	@RequestMapping(value="/save", method=RequestMethod.GET)
	public Object save(){
		User user = new User();
		user.setUserName("save-username-");
		user.setPassword("123456");
		user.save();
		return user;
	}

	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public Object update(@PathVariable("id")Long id){
		User user = User.loadById(id);
		user.setUpdateAt(new Date());
		user.update();
		return user;
	}

	@RequestMapping(value="/remove/{id}", method=RequestMethod.GET)
	public Object remove(@PathVariable("id")Long id){
		User user = User.removeById(id);
		return user;
	}

	@RequestMapping(value="batchInsert", method=RequestMethod.GET)
	public Object batchInser(int count){
		List<User> users = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			User user = new User();
			user.setUserName("batchInsert-username-"+i);
			user.setPassword("123456");
			users.add(user);
		}
		int insertCount = User.batchInsert(users);
		return "insert "+insertCount+" users.";
	}
}
