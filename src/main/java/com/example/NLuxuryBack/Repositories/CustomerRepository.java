package com.example.NLuxuryBack.Repositories;

import com.example.NLuxuryBack.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
