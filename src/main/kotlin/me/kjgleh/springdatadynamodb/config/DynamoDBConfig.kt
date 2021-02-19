package me.kjgleh.springdatadynamodb.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile

@Configuration
@Profile("prod")
@EnableDynamoDBRepositories(basePackages = ["me.kjgleh.springdatadynamodb.order"])
class DynamoDBConfig(
    @Value("\${amazon.aws.accessKey}")
    private var amazonAwsAccessKey: String,
    @Value("\${amazon.aws.secretKey}")
    private var amazonAwsSecretKey: String
) {

    @Bean
    @Primary
    fun dynamoDBMapper(amazonDynamoDB: AmazonDynamoDB): DynamoDBMapper {
        return DynamoDBMapper(amazonDynamoDB, DynamoDBMapperConfig.DEFAULT)
    }

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB {
        val credentialsProvider = AWSStaticCredentialsProvider(
            BasicAWSCredentials(amazonAwsAccessKey, amazonAwsSecretKey)
        )

        return AmazonDynamoDBClientBuilder.standard()
            .withCredentials(credentialsProvider)
            .withRegion(Regions.AP_NORTHEAST_2)
            .build()
    }
}