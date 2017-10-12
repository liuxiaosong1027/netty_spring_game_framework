package com.framework.login.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.common.mapper.user.DistShardHostBean;
import com.db.common.repository.CommonRepository;
import com.framework.login.dispatcher.XDRDispatcherTemplate;
import com.framework.login.message.TestMessage;
import com.game.common.enumeration.ErrorCode;
import com.game.core.net.exception.MessageParseException;

@Component
public class TestAction extends XDRDispatcherTemplate<TestMessage> {
	@Autowired private CommonRepository commonRepository;

	@Override
	public int processMessage(TestMessage msg) throws MessageParseException {
		List<DistShardHostBean> beans = commonRepository.getDistShardDBInfo();
		msg.content = beans.toString();
		return ErrorCode.ERR_SUCCESS.getValue();
	}

	@Override
	public boolean needUserLock() {
		return false;
	}
	
}
