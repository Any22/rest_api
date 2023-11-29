package com.demo.rest_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.rest_demo.entity.Customer;
//DDL to create the table and DML to populate the data
// the interface extends JPAREpository and pass Entity class and type of primary key
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer>  {
	
}
