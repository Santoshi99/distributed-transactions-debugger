package org.distributeddebugger.orderservicev1.repository;

import org.distributeddebugger.orderservicev1.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {


}
