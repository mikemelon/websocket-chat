package cn.lynu.lyq.study.mywschat.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cn.lynu.lyq.study.mywschat.dao.PushMessageDao;
import cn.lynu.lyq.study.mywschat.entity.PushMessage;

@Service
@Transactional
public class PushMessageService {
	@Resource
	private PushMessageDao pushMessageDao;
	
	public void save(PushMessage pushMessage){
		pushMessageDao.save(pushMessage);
	}
}
