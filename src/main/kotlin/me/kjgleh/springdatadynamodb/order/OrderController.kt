package me.kjgleh.springdatadynamodb.order

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val orderRepository: OrderRepository,
) {

    @PostMapping("/api/orders/place-order")
    fun placeOrder() {
        val order = Order(
            amount = 1
        )
        orderRepository.save(order)
    }

    @GetMapping("/api/orders")
    fun orders(): MutableIterable<Order> {
        return orderRepository.findAll()
    }
}