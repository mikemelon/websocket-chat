package cn.lynu.lyq.study.mywschat.wsserver;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.lynu.lyq.study.mywschat.config.GetHttpSessionConfigurator;
import cn.lynu.lyq.study.mywschat.config.MessageCodec;
import cn.lynu.lyq.study.mywschat.entity.PushMessage;
import cn.lynu.lyq.study.mywschat.entity.PushMessage.MessageType;
import cn.lynu.lyq.study.mywschat.entity.User;
import cn.lynu.lyq.study.mywschat.service.PushMessageService;
import cn.lynu.lyq.study.mywschat.util.WebSocketUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Scope("prototype")
@ServerEndpoint(value="/mychat",configurator=GetHttpSessionConfigurator.class,
decoders={MessageCodec.class}, encoders={MessageCodec.class})
public class MyChat{
	private static List<MyChat> chatList = new CopyOnWriteArrayList<>();
	private Session session;
	private User user;
	
	@Resource
	private PushMessageService pushMessageService;
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config){
		log.info("websocket onOpen");
		this.session = session;
		HttpSession httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
		user = (User)httpSession.getAttribute("USER_INFO");
		chatList.add(this);
		log.info("onOpen:"+chatList);
		log.info(WebSocketUtils.getProcessAndThreadID());
		log.info(user.getName()+"进入聊天");
	}
	
	@OnClose
	public void onClose(CloseReason reason){
		chatList.remove(this);
		log.info(user.getName()+"离开聊天");
		log.info("离开原因："+reason.getReasonPhrase());
	}
	
	@OnError
	public void onError(Throwable t){
		log.error("websocket onError:"+t.getMessage());
	}
	
	@OnMessage
	public void onTextMessage(String message){
		log.info("websocket get text message:"+message);
		PushMessage pushMessage = new PushMessage();
		pushMessage.setFromUser(user);
		pushMessage.setSendDate(new Date());
		pushMessage.setContent(message);
		pushMessage.setType(MessageType.TEXT);
//		log.info("pushMessageService=="+pushMessageService);
		pushMessageService.save(pushMessage);
		
		log.info("begin broadcasting text...");
		broadcastMessage(pushMessage);
	}
	
	@OnMessage
	public void onBinaryMessage(InputStream is){
		log.info("websocket get binary message:");
		File imageFile = new File("c:\\test1.jpg");
		try {
			FileOutputStream fos = new FileOutputStream(imageFile);
			BufferedInputStream bis = new BufferedInputStream(is);
			int b;
			while((b=bis.read())!=-1){
				fos.write(b);
			}
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PushMessage pushMessage = new PushMessage();
		pushMessage.setFromUser(user);
		pushMessage.setSendDate(new Date());
		pushMessage.setImagePath(imageFile.getAbsolutePath());
		pushMessage.setType(MessageType.IMAGE);
//		log.info("pushMessageService=="+pushMessageService);
		pushMessageService.save(pushMessage);
		
		log.info("begin broadcasting image...");
		broadcastImage(is,pushMessage);
	}
	
	private void broadcastMessage(PushMessage pushMessage){
		for(MyChat mychat:chatList){
			System.out.println("item username="+mychat.user.getName());
			mychat.sendMessage(pushMessage);
		}
	}
	
	private void sendMessage(PushMessage pushMessage){
		try {
			session.getBasicRemote().sendObject(pushMessage);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncodeException e) {
			e.printStackTrace();
		}
	}
	
	private void broadcastImage(InputStream is, PushMessage pushMessage){
		for(MyChat mychat:chatList){
			System.out.println("item username="+mychat.user.getName());
			mychat.sendMessage(pushMessage);//发图片前，先发一个pushMessage对象,用于显示 "发送人"
			mychat.sendImage(is);
		}
	}
	
	private void sendImage(InputStream is){
		try {
			is.reset();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while((len=is.read(buffer))!=-1){
	            bos.write(buffer,0,len);
	        }
			bos.close();
//			log.info("file size="+bos.toByteArray().length);
			// 使用读取图片后的字节数组创建ByteBuffer对象
			ByteBuffer byteBuffer = ByteBuffer.wrap(bos.toByteArray());
			session.getBasicRemote().sendBinary(byteBuffer);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
