package com.app.backend.backend_service.service.specification;

import com.app.backend.backend_service.entities.Account;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class AccountSpecification {

    public static Specification<Account> hasUsername(String username) {
        return (root, query, criteriaBuilder) -> {
           if (!Objects.isNull(username)){
               return criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + username.toLowerCase() +"%");
           }
           return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Account> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if (!Objects.isNull(email)) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Account> hasFullname(String fullName) {
        return (root, query, criteriaBuilder) -> {
            if (!Objects.isNull(fullName)) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%");
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Account> hasPhoneNumber(String phoneNumber) {
        return (root, query, criteriaBuilder) -> {
            if (!Objects.isNull(phoneNumber)) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), "%" + phoneNumber.toLowerCase() + "%");
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Account> hasDob(Date dob){
        return (root, query, criteriaBuilder) -> {
            if (!Objects.isNull(dob)) {
                LocalDate localDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Date startOfDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date endOfDay = Date.from(localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().minusMillis(1));
                return criteriaBuilder.between(root.get("dob"), startOfDate, endOfDay);
            }
            return criteriaBuilder.conjunction();
        };
    }

    //delete account non include
    public static Specification<Account> hasDeleted(Integer delete){
        return (root, query, criteriaBuilder) -> {
            if (!Objects.isNull(delete)){
                return criteriaBuilder.equal(root.get("deleted"), delete);
            }
            return criteriaBuilder.conjunction();
        };
    }
}
