package com.content.adapter.place

import com.content.adapter.client.PlaceRetrofitClient
import com.content.adapter.grpc.PlaceGrpcClient
import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@Ignored
@SpringBootTest
class PlaceAdapterTest(
    @Autowired private val placeRetrofitClient: PlaceRetrofitClient,
    @Autowired private val placeGrpcClient: PlaceGrpcClient,
) : BehaviorSpec({

    given("placeClient로 사용할 Retrofit, Grpc 성능 테스트를 위한 placeIds가 주어져요") {

        val placeIds = listOf(1L, 2L, 3L, 4L, 5L, 6L, 7L)

        `when`("PlaceClient의 100회 순차 요청을 보내면") {

            /* Warm-up */
            repeat(10) {
                val call = placeRetrofitClient.getPlaceResponse(placeIds = placeIds)

                val response = call.execute()
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.places
                }
            }

            val startTime = System.currentTimeMillis()
            repeat(100) {
                val call = placeRetrofitClient.getPlaceResponse(placeIds = placeIds)

                val response = call.execute()
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.places
                }
            }
            val endTime = System.currentTimeMillis()

            then("시간을 확인해요") {
                println("[PlaceClient 순차 100회]\n ${endTime - startTime}\n")
            }
            Thread.sleep(1000L)
        }

        `when`("PlaceGrpcClient로 100회 순차 요청을 보내면") {

            /* Warm-up */
            repeat(10) {
                placeGrpcClient.getPlaceResponse(placeIds)
            }

            val startTime = System.currentTimeMillis()
            repeat(100) {
                placeGrpcClient.getPlaceResponse(placeIds)
            }
            val endTime = System.currentTimeMillis()

            then("PlaceGrpcClient의 시간을 확인해요") {
                println("[PlaceGrpcClient 순차 100회]\n ${endTime - startTime}\n")
            }
            Thread.sleep(1000L)
        }

        `when`("PlaceClient의 100회 병렬 요청을 보내면") {
            val threadCount = 3
            val latch = CountDownLatch(threadCount)
            val executorService = Executors.newFixedThreadPool(32)

            /* Warm-up */
            repeat(10) {
                val call = placeRetrofitClient.getPlaceResponse(placeIds = placeIds)

                val response = call.execute()
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.places
                }
            }

            val startTime = System.currentTimeMillis()
            repeat(100) {
                executorService.submit {
                    try {
                        val call = placeRetrofitClient.getPlaceResponse(placeIds = placeIds)
                        val response = call.execute()
                        if (response.isSuccessful && response.body() != null) {
                            response.body()!!.places
                        }
                    } finally {
                        latch.countDown()
                    }
                }
            }

            latch.await()
            val endTime = System.currentTimeMillis()

            then("시간을 확인해요") {
                println("[PlaceClient 병렬 100회]\n ${endTime - startTime}\n")
            }
            Thread.sleep(1000L)
        }

        `when`("PlaceGrpcClient로 100회 병렬 요청을 보내면") {
            val threadCount = 3
            val latch = CountDownLatch(threadCount)
            val executorService = Executors.newFixedThreadPool(32)


            /* Warm-up */
            repeat(10) {
                placeGrpcClient.getPlaceResponse(placeIds)
            }

            val startTime = System.currentTimeMillis()
            repeat(100) {
                executorService.submit {
                    try {
                        placeGrpcClient.getPlaceResponse(placeIds)
                    } finally {
                        latch.countDown()
                    }
                }
            }

            latch.await()
            val endTime = System.currentTimeMillis()

            then("시간을 확인해요") {
                println("[PlaceGrpcClient 병렬 100회]\n ${endTime - startTime}\n")
            }
            Thread.sleep(1000L)
        }
    }

})