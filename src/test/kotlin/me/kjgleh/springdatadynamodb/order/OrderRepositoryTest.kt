package me.kjgleh.springdatadynamodb.order

import com.appmattus.kotlinfixture.decorator.recursion.NullRecursionStrategy
import com.appmattus.kotlinfixture.decorator.recursion.recursionStrategy
import com.appmattus.kotlinfixture.kotlinFixture
import me.kjgleh.springdatadynamodb.config.DynamoDBLocalConfig
import me.kjgleh.springdatadynamodb.order.domain.Order
import me.kjgleh.springdatadynamodb.order.domain.OrderLine
import me.kjgleh.springdatadynamodb.order.domain.OrderRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [DynamoDBLocalConfig::class])
class OrderRepositoryTest constructor(
    @Autowired
    private var orderRepository: OrderRepository
) {

    companion object {
        private val fixture = kotlinFixture {
            recursionStrategy(NullRecursionStrategy)
        }
    }

    @Test
    fun `find order correctly`() {
        // Arrange
        val orderLine = fixture<OrderLine>()
        val order = fixture<Order> {
            property(Order::orderLines) { listOf(orderLine) }
        }
        val id = orderRepository.save(order).id

        // Act
        val sut = orderRepository
        val orderSaved = sut.findById(id).orElseThrow { Exception() }

        // Assert
        assertThat(orderSaved).usingRecursiveComparison()
            .ignoringFields("createdAt").isEqualTo(order)
    }
}