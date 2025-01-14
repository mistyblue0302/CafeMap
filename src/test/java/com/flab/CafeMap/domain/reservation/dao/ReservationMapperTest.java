package com.flab.CafeMap.domain.reservation.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.CafeMap.domain.reservation.Reservation;
import com.flab.CafeMap.domain.reservation.ReservationStatus;
import com.flab.CafeMap.domain.user.User;
import com.flab.CafeMap.domain.user.UserAddress;
import com.flab.CafeMap.domain.user.dao.UserMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * @SpringBootTest : SpringBoot 통합테스트에 사용되는 애노테이션으로 @SpringBootApplication을 찾아가 하위의 모든 빈을 스캔한다.
 * @ActiveProfiles : 스프링 컨테이터 실행 시 실행 환경을 지정해주는 애노테이션으로, 테스트 시 특정 빈을 로드
 * @Transactional : 선언적 트랜잭션에 사용되는 애노테이션으로 테스트 이후 롤백
 */

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReservationMapperTest {

    @Autowired
    ReservationMapper reservationMapper;

    @Autowired
    UserMapper userMapper;

    @Test
    @DisplayName("예약 insert 쿼리 테스트")
    void insertReservation() {
        //given
        User user = createUser("testId", "testName", "testPassword", "01012345678",
            "testCreatedBy");
        userMapper.insertUser(user);
        UserAddress userAddress = createUserAddress(user.getLoginId());

        Reservation reservation = Reservation.builder()
            .id(1L)
            .userId(user.getId())
            .cafeId(1L)
            .address(userAddress.getDetailAddress())
            .reservationStatus(ReservationStatus.CONFIRMED)
            .createdAt(LocalDateTime.now())
            .build();

        //when
        int count = 0;
        count += reservationMapper.insertReservation(reservation);

        //then
        assertThat(count).isEqualTo(1);
    }


    private User createUser(String testId, String testName, String testPassword,
        String testPhoneNumber, String testCreatedBy) {
        return User.builder()
            .loginId(testId)
            .name(testName)
            .password(testPassword)
            .phoneNumber(testPhoneNumber)
            .createdBy(testCreatedBy).createdAt(LocalDateTime.now())
            .build();
    }

    private UserAddress createUserAddress(String loginId) {
        return UserAddress.builder()
            .loginId(loginId)
            .streetAddress("서울시 강남구")
            .detailAddress("논현동 207")
            .latitude(37.513863998587)
            .longitude(127.0312783056)
            .createdBy(loginId)
            .createdAt(LocalDateTime.now())
            .build();
    }
}