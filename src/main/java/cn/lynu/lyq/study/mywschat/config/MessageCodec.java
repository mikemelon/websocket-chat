package cn.lynu.lyq.study.mywschat.config;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.alibaba.fastjson.JSON;

import cn.lynu.lyq.study.mywschat.entity.PushMessage;

public class MessageCodec implements Encoder.Text<PushMessage>, Decoder.Text<PushMessage> {

	@Override
	public void init(EndpointConfig endpointConfig) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public PushMessage decode(String jsonMessage) throws DecodeException {
		return JSON.parseObject(jsonMessage, PushMessage.class);
	}

	@Override
	public boolean willDecode(String jsonMessage) {
		try{
			JSON.parseObject(jsonMessage, PushMessage.class);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public String encode(PushMessage pushMessage) throws EncodeException {
		return JSON.toJSONString(pushMessage);
	}

}
