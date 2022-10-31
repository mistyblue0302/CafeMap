package com.flab.CafeMap.domain.user.dao;

import com.flab.CafeMap.domain.user.User;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Mapper : MyBatis 매핑 xml의 SQL을 호출하기 위한 인터페이스
 */
@Mapper
public interface UserMapper {

    int insertUser(User user);

    Optional<User> selectUserByLoginId(String loginId);

    Optional<User> selectUserById(Long id);

    int updateUser(User user);
}
