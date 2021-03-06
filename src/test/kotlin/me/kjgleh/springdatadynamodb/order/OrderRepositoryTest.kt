package me.kjgleh.springdatadynamodb.order

import com.appmattus.kotlinfixture.decorator.recursion.NullRecursionStrategy
import com.appmattus.kotlinfixture.decorator.recursion.recursionStrategy
import com.appmattus.kotlinfixture.kotlinFixture
import me.kjgleh.springdatadynamodb.config.DynamoDBLocalConfig
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
        val order = fixture<Order>()
        val id = orderRepository.save(order).id

        // Act
        val sut = orderRepository
        val orderSaved = sut.findById(id).orElseThrow { Exception() }

        // Assert
        assertThat(orderSaved).usingRecursiveComparison().isEqualTo(order)
    }
}