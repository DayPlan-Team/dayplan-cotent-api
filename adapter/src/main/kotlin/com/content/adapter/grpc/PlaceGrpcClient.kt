package com.content.adapter.grpc

import com.content.util.share.Logger
import io.grpc.ManagedChannel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import place.Place
import place.PlaceServiceGrpc

@Component
class PlaceGrpcClient(
    @Qualifier("userManagedChannel") private val channel: ManagedChannel,
) {
    fun getPlaceResponse(placeIds: List<Long>): List<Place.PlaceItem> {
        val stub = PlaceServiceGrpc.newBlockingStub(channel)

        val request =
            Place.GetPlaceRequest.newBuilder()
                .addAllPlaceIds(placeIds)
                .build()

        val response = stub.getPlace(request)
        return response.placesList
    }

    companion object : Logger()
}
