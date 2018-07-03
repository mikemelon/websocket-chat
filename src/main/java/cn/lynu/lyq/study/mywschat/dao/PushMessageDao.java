package cn.lynu.lyq.study.mywschat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.lynu.lyq.study.mywschat.entity.PushMessage;

public interface PushMessageDao extends JpaRepository<PushMessage,Long>{

}
