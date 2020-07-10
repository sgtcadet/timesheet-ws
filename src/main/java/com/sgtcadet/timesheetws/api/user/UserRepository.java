package com.sgtcadet.timesheetws.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT DISTINCT u FROM User u " +

            "INNER JOIN FETCH u.authorities AS authorities " +

            "WHERE u.username = :username")
//    @Query(value = "Select distinct from user inner join fetch user.authorities as authorities where user.username = :username", nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);
}
