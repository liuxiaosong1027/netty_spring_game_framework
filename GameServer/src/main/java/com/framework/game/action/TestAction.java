package com.framework.game.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.common.mapper.user.DistShardHostBean;
import com.db.common.repository.CommonRepository;
import com.framework.game.dispatcher.XDRDispatcherTemplate;
import com.framework.game.message.TestMessage;
import com.framework.game.net.model.Player;
import com.framework.game.service.OnlineService;
import com.game.core.net.exception.MessageParseException;

@Component
public class TestAction extends XDRDispatcherTemplate<TestMessage> {
	@Autowired private CommonRepository commonRepository;
	@Autowired private OnlineService onlineService;

	@Override
	public void processMessage(TestMessage msg, Player sender) throws MessageParseException {
//		System.out.println("TestAction->" + msg.content);
//		if(!"hello world!".equals(msg.content)) {
//			System.out.println("error");
//		}
//		sender.sendMsg(msg);
		
		List<DistShardHostBean> beans = commonRepository.getDistShardDBInfo();
		System.out.println(beans);
		msg.content = beans.toString();
		sender.sendMsg(msg);
		
		System.out.println("----------" + onlineService.getCurrentChannelNum());
	}

	@Override
	public boolean needUserLock() {
		return true;
	}
	
}
