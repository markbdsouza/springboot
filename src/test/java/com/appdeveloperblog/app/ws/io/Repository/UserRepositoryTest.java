package com.appdeveloperblog.app.ws.io.Repository;

import com.appdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appdeveloperblog.app.ws.io.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

static boolean createdRecords = false ;

    @BeforeEach
    void setUp() {
        if (!createdRecords){
            UserEntity userEntity = new UserEntity();
            userEntity.setLastName("last");
            userEntity.setFirstName("first");
            userEntity.setUserId("asd123");
            userEntity.setEmail("asd@asd.com");
            userEntity.setEncryptedPassword("asd");
            userEntity.setEmailVerificationStatus(true);

            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setAddressId("123zxc");
            addressEntity.setCity("zxc");
            addressEntity.setType("test");
            addressEntity.setCountry("India");
            addressEntity.setPinCode("pin");
            List<AddressEntity> addressEntityList = new ArrayList<>();
            addressEntityList.add(addressEntity);
            userEntity.setAddresses(addressEntityList);

            userRepository.save(userEntity);
            createdRecords= true;
        }

    }

    @Test
    void findAllUsersWithConfirmedEmail() {
        Pageable pageableRequest = PageRequest.of(0, 2);
        Page<UserEntity> pages = userRepository.findAllUsersWithConfirmedEmail(pageableRequest);
        assertNotNull(pages);

        List<UserEntity> userEntityList = pages.getContent();
        assertNotNull(userEntityList);
        assertTrue(userEntityList.size() >= 1);
    }

    @Test
    final void findUserByFirstAndLastName() {
        String firstName = "first";
        String lastName = "last";
        List<UserEntity> result = userRepository.findUserbyFirstNameAndLastName(firstName, lastName);
        assertNotNull(result);
        assertTrue(result.size() == 1 );
        assertEquals(result.get(0).getFirstName(), "first");
    }

    @Test
    final void findUserByKeyword(){
        String keyword = "rs";
        List<UserEntity> result = userRepository.findByKeyword(keyword);
        UserEntity user = result.get(0);
        assert(result.size()>0);
        assertTrue(user.getFirstName().contains(keyword) || user.getLastName().contains(keyword));
    }

    @Test
    final void testUpdate(){

        userRepository.updateUserVerificationStatus(false, "asd123");
        UserEntity user = userRepository.findByUserId("asd123");
        assertEquals(user.isEmailVerificationStatus(), false);
    }


//    @Test
//    final void testJPQL(){
//        UserEntity user = userRepository.findUserEntitiyByUserId("asd123");
//        assertNotNull(user);
//        assertEquals(user.getUserId(), "asd123");
//    }

    @Test
    final void testUpdateJPQL(){

        userRepository.updateUserVerificationStatusJPQL(true, "asd123");
        UserEntity user = userRepository.findByUserId("asd123");
        assertEquals(user.isEmailVerificationStatus(), true);
    }

}