package com.ecommercestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommercestore.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
