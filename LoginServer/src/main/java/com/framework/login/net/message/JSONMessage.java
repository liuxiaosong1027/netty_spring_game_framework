package com.framework.login.net.message;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.framework.login.model.RequestModel;
import com.game.common.consts.ConstValue;
import com.game.core.net.exception.MessageParseException;
import com.game.core.net.http.message.AbstractMessage;

/**
 * json消息
 * @author lxs
 *
 */
public abstract class JSONMessage extends AbstractMessage{
	protected RequestModel requestModel;
	private Object messageData;

	public RequestModel getRequestModel() {
		return requestModel;
	}

	public void setRequestModel(RequestModel requestModel) {
		this.requestModel = requestModel;
	}
	
	@Override
	public void load(Object messageData) throws MessageParseException {
		this.messageData = messageData;
		JSONObject jsonObject = new JSONObject(JSON.toJSONString(messageData));
		loadJson(jsonObject);
	}

	@Override
	public Object save() throws MessageParseException {
		JSONObject json = saveJson();
		if(json == null) {
			json = new JSONObject();
		}
		return JSON.parseObject(json.toString());
	}

	@Override
	public String toSignString() {
		String signString = JSON.toJSONString(messageData, SerializerFeature.SortField);
		return signString + ConstValue.KEY;
	}
	
	public abstract void loadJson(JSONObject messageData) throws MessageParseException;
	
	public abstract JSONObject saveJson() throws MessageParseException;
}
