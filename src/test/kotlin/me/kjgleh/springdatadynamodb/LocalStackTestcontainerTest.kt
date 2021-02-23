package me.kjgleh.springdatadynamodb

import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.shaded.org.apache.commons.io.IOUtils
import org.testcontainers.utility.DockerImageName

@Testcontainers
internal class LocalStackTestcontainerTest {

    private val localstackImage =
        DockerImageName.parse("localstack/localstack:latest");

    @Container
    var container: LocalStackContainer = LocalStackContainer(localstackImage)
        .withServices(LocalStackContainer.Service.S3)

    @Test
    fun test() {
        val s3 = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(
                container.getEndpointConfiguration(
                    LocalStackContainer.Service.S3
                )
            )
            .withCredentials(container.defaultCredentialsProvider)
            .build()
        val bucketName = "test-s3"
        s3.createBucket(bucketName)
        println("버킷을 생성했습니다. bucketName=${bucketName}")

        val content = "Hello World"
        val key = "s3-key"
        s3.putObject(bucketName, key, content)
        println("버킷을 파일을 업로드하였습니다. bucketName=${bucketName}, key=${key}, content=${content}")

        val results = IOUtils.readLines(
            s3.getObject(bucketName, key).objectContent,
            "utf-8"
        )
        println("파일을 가져왔습니다. bucketName=${bucketName}, key=${key}, results=${results}")

        assertThat(results).hasSize(1)
        assertThat(results[0]).isEqualTo(content)
    }
}