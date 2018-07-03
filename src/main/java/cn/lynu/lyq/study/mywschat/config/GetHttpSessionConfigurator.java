package cn.lynu.lyq.study.mywschat.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class GetHttpSessionConfigurator extends CustomSpringConfigurator {
	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		HttpSession httpSession =(HttpSession)request.getHttpSession();
		sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
	}
}
