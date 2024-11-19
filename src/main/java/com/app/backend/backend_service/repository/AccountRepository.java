package com.app.backend.backend_service.repository;

import com.app.backend.backend_service.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>, JpaSpecificationExecutor<Account> {

    @Query(value = """
        select * from account where email = :email and deleted = 0
    """,nativeQuery = true)
    Optional<Account> findAccountByEmail(@Param("email")String email);

    @Query(value = """
        select * from account where username = :username and deleted = 0
    """, nativeQuery = true)
    Optional<Account> findAccountByUsername(@Param("username")String username);

    @Query(value = """
        select count(*) from account where email = :email and deleted = 0
    """, nativeQuery = true)
    Integer countByEmail(@Param("email")String email);

    @Query(value = """
        select count(*) from account where username = :username and deleted = 0
    """, nativeQuery = true)
    Integer countByUsername(@Param("username")String username);

    @Query(value = """
        select count(*) from account where phone_number = :phoneNumber and deleted = 0
    """, nativeQuery = true)
    Integer countByPhoneNumber(@Param("phoneNumber")String phoneNumber);
}
