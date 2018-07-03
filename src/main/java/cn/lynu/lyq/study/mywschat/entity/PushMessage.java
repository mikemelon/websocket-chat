package cn.lynu.lyq.study.mywschat.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class PushMessage {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String content;
	private String imagePath;
	@ManyToOne
	private User fromUser;
	@ManyToOne
	private User toUser; // 接收人为NULL或空，表示“广播（全体群发）”
	
	private MessageType type;
	private Date sendDate;
	
	public enum MessageType {TEXT, IMAGE}
}
