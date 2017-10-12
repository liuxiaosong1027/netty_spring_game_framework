package com.framework.login.dispatcher;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSON;
import com.db.common.lock.RedisLock;
import com.framework.login.model.RequestModel;
import com.framework.login.model.ResponseModel;
import com.framework.login.net.message.JSONMessage;
import com.game.common.enumeration.ErrorCode;
import com.game.common.util.GameUtil;
import com.game.core.dispatcher.Dispatcher;
import com.game.core.dispatcher.DispatcherTemplate;
import com.game.core.dispatcher.IMessage;

@Component
public class XDRDispatcher extends Dispatcher {
	private static final Logger LOGGER = LoggerFactory.getLogger(XDRDispatcher.class);
	
	@Autowired(required = false) 
	private List<DispatcherTemplate<? extends IMessage>> messageActions;
	@Autowired private RedisLock redisLock;
	
	public XDRDispatcher() {
		super("MSG_TYPE");
	}
	
	@PostConstruct
	public void setup() {
		register(messageActions);
	}
	
	/**
	 * 请求分发
	 * @param reqModel
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ResponseModel handler(@RequestBody RequestModel reqModel) throws Exception {
		int messageId = reqModel.getMessage_no();
		XDRDispatcherTemplate handler = (XDRDispatcherTemplate)messageActionMap.get(reqModel.getMessage_no());
		if(handler == null) {
			LOGGER.error("消息未注册！{}", messageId);
			return null;
		}
		JSONMessage jsonMsg = (JSONMessage) handler.getMsg().newInstance();
		
		ResponseModel resp = new ResponseModel();
		resp.setMessage_no(messageId);
		jsonMsg.setRequestModel(reqModel);
		LOGGER.info(reqModel.toString());
		jsonMsg.load(reqModel.getMessage_data());
		String requestStr = jsonMsg.toSignString();
		if(!reqModel.getSign().equals(GameUtil.getMd5String(requestStr))) {
			LOGGER.error("错误的请求！！发来的sign->{}，生成后的sign->{}", reqModel.getSign(), GameUtil.getMd5String(requestStr));
			jsonMsg = null;
			resp = null;
			return null;
		}
		long time1 = System.currentTimeMillis();
		int errorCode = 0;
		if(handler.needUserLock()) {
			if(redisLock.tryLock(String.valueOf(reqModel.getUser_no()))) {
				try {
					errorCode = handler.processMessage(jsonMsg);
				} catch (Exception e) {
					resp.setError_code(ErrorCode.ERR_FAILED.getValue());
					e.printStackTrace();
				} finally {
					redisLock.unlock(String.valueOf(reqModel.getUser_no()));
				}
			} else {
				errorCode = ErrorCode.ERR_SERVER_IS_BUSY.getValue();
			}
		} else {
			try {
				errorCode = handler.processMessage(jsonMsg);
			} catch (Exception e) {
				resp.setError_code(ErrorCode.ERR_FAILED.getValue());
				e.printStackTrace();
			}
		}
		resp.setError_code(errorCode);
		if(errorCode == ErrorCode.ERR_SUCCESS.getValue()) {
			resp.setMessage_data(jsonMsg.save());
		}
		long time2 = System.currentTimeMillis();
		LOGGER.info("{}---- handler time is {}.", resp.toString(), (time2 - time1));
		jsonMsg = null;
		return resp;
	}
	
	/**
	 * 请求分发
	 * @param requestContent
	 * @return
	 */
	public String requestHandler(String requestContent) {
		String responseContent = "";
		RequestModel reqModel = null;
		ResponseModel responseModel = null;
		try {
			reqModel = JSON.parseObject(requestContent, RequestModel.class);
			responseModel = handler(reqModel);
	    	if(responseModel != null) {
	    		responseContent = responseModel.toJson().toString();
	    	}
		} catch (Exception e) {
			LOGGER.error("错误的请求->", e);
		} finally {
			reqModel = null;
			responseModel = null;
		}
		return responseContent;
	}
}
