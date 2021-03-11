package me.kjgleh.springdatadynamodb.order.domain

import com.amazonaws.services.dynamodbv2.datamodeling.*
import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import java.util.*

@DynamoDBTable(tableName = "order")
class Order(
    @Id
    @DynamoDBHashKey
    var id: String = UUID.randomUUID().toString(),

    @DynamoDBAttribute
    var userId: String = "",

    @DynamoDBAttribute
    var orderLines: List<OrderLine> = listOf(),

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter::class)
    var createdAt: LocalDateTime = LocalDateTime.now()
)