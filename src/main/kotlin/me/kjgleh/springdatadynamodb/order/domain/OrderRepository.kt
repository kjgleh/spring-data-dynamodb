package me.kjgleh.springdatadynamodb.order.domain

import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface OrderRepository : CrudRepository<Order, String>