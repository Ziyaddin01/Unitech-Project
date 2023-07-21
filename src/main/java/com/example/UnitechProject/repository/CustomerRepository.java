package com.example.UnitechProject.repository;

import com.example.UnitechProject.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByPin(String pin);

    boolean existsByPin(String pin);
}
