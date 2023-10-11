package com.parking82.api.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking82.api.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByPlate(String plate);

}
