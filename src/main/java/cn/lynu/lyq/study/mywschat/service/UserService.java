package cn.lynu.lyq.study.mywschat.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cn.lynu.lyq.study.mywschat.dao.UserDao;
import cn.lynu.lyq.study.mywschat.entity.User;

@Service
@Transactional
public class UserService {
	@Resource
	private UserDao userDao;
	
	public User findByNameAndPassword(String username, String password){
		return userDao.findByNameAndPassword(username, password);
	}
}
