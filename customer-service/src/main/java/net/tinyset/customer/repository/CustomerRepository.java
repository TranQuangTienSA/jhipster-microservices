package net.tinyset.customer.repository;

import net.tinyset.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Spring Data JPA repository for the Customer entity.
 */
@SuppressWarnings("unused")
public interface CustomerRepository extends JpaRepository<Customer, Long>, QueryDslPredicateExecutor<Customer> {

}
