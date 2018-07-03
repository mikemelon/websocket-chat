package cn.lynu.lyq.study.mywschat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.lynu.lyq.study.mywschat.entity.User;

public interface UserDao extends JpaRepository<User,Integer>{
	public User findByNameAndPassword(String username, String password);
}
