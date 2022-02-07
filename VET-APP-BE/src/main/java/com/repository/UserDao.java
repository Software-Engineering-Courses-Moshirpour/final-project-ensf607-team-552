package com.repository;

import com.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends PagingAndSortingRepository<User, Long> {



    Optional<User> findByUsername(String userName);

    @Query(value=" SELECT * from user u inner join user_roles ur  where u.id = ur.user_id and ur.role_id=3", nativeQuery = true)
    public List<User> findUserByRoles();

    @Query("SELECT c FROM User c WHERE c.email = ?1")
    public User findByEmail(String email);

    public User findByResetPasswordToken(String token);

    Boolean existsByUsername(String userName);

    Boolean existsByEmail(String email);


}
