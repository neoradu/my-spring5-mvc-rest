package guru.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
