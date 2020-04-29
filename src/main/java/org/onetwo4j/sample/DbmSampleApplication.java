package org.onetwo4j.sample;

import javax.annotation.PostConstruct;

import org.onetwo.common.db.spi.BaseEntityManager;
import org.onetwo.common.utils.Page;
import org.onetwo.dbm.spring.EnableDbm;
import org.onetwo.ext.apiclient.wechat.EnableWechatClient;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage.TextMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReplyMessage.TextReplyMessage;
import org.onetwo.ext.apiclient.wechat.serve.spi.MessageRouterService;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableDbm
@RestController
@EnableWechatClient(enableOAuth2Interceptor=true)
public class DbmSampleApplication {

	@Autowired
	MessageRouterService messageRouterService;
	

	@PostConstruct
	public void init(){
		this.messageRouterService.register(MessageType.TEXT, (TextMessage text)->{
			return TextReplyMessage.builder()
									.fromUserName(text.getToUserName())
									.toUserName(text.getFromUserName())
									.content("行了，行了，我收到了~")
									.build();
		});
	}
	
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
