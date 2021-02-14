package me.kjgleh.springdatadynamodb.order

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import org.springframework.data.annotation.Id
import java.util.*

@DynamoDBTable(tableName = "order")
class Order {

    @Id
    @DynamoDBHashKey
    var id: String = UUID.randomUUID().toString()

    @DynamoDBAttribute
    var amount: Long = 0L
}