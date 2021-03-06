package me.kjgleh.springdatadynamodb.config

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.utility.DockerImageName

@Configuration
@Profile("local")
@EnableDynamoDBRepositories(basePackages = ["me.kjgleh.springdatadynamodb.order"])
class DynamoDBLocalConfig {

    private val localstackImage =
        DockerImageName.parse("localstack/localstack:latest")

    @Bean
    @Primary
    fun dynamoDBMapper(amazonDynamoDB: AmazonDynamoDB): DynamoDBMapper {
        return DynamoDBMapper(amazonDynamoDB, DynamoDBMapperConfig.DEFAULT)
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    fun localStackContainer(): LocalStackContainer {
        return LocalStackContainer(localstackImage).withServices(
            LocalStackContainer.Service.DYNAMODB
        )
    }

    @Bean
    fun amazonDynamoDB(localStackContainer: LocalStackContainer): AmazonDynamoDB {
        return AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(
                localStackContainer.getEndpointConfiguration(
                    LocalStackContainer.Service.DYNAMODB
                )
            )
            .withCredentials(localStackContainer.defaultCredentialsProvider)
            .build()
    }
}