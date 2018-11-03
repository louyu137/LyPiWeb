package cn.louyu.service.jpa.repository;

import cn.louyu.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,String> {

}
