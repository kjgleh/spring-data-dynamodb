package me.kjgleh.springdatadynamodb.order.domain

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument

@DynamoDBDocument
class OrderLine(
    @DynamoDBAttribute
    var quantity: Int = 0
)
