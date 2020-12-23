package com.appdeveloperblog.app.ws.io.Repository;

import com.appdeveloperblog.app.ws.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUserId(String userId);

    @Query(value = "select * from Users u where u.EMAIL_VERIFICATION_STATUS = 'true'",
            countQuery = "select count(*) from Users u where u.EMAIL_VERIFICATION_STATUS = 'true'",
            nativeQuery = true)
    Page<UserEntity> findAllUsersWithConfirmedEmail(Pageable pageable);

    @Query(value = "select * from Users u where u.first_Name= ?1 and u.last_name = ?2",
            nativeQuery = true)
    List<UserEntity> findUserbyFirstNameAndLastName(String firstName, String lastName);

    @Query(value = "select * from Users u where u.last_name = :LAST_NAME",
            nativeQuery = true)
    List<UserEntity> findUserbyLastName(@Param("LAST_NAME") String lastName);

    @Query(value = "select * from users u where u.first_name LIKE %:KEYWORD% OR u.last_name LIKE %:KEYWORD%", nativeQuery = true)
    List<UserEntity> findByKeyword(@Param("KEYWORD") String keyword);

    @Query(value = "select u.first_name, u.last_name from users u where u.first_name LIKE %:KEYWORD% OR u.last_name LIKE %:KEYWORD%", nativeQuery = true)
    List<Object[]> findNamesByKeyword(@Param("KEYWORD") String keyword);

    @Transactional
    @Modifying
    @Query(value= "update users u set u.EMAIL_VERIFICATION_STATUS = :status WHERE USER_ID = :id ", nativeQuery = true)
    void updateUserVerificationStatus(@Param("status") boolean emailVerificationStatus, @Param("id") String userId);

    @Query("select user from UserEntity user where user.userId = :userId")
    UserEntity findUserEntitiyByUserId(@Param("userId") String userId);

    @Transactional
    @Modifying
    @Query("update  UserEntity u set u.emailVerificationStatus = :status WHERE u.userId = :id")
    void updateUserVerificationStatusJPQL(@Param("status") boolean emailVerificationStatus, @Param("id") String userId);
}
