package com.example.imageloaderpracticalnative

import com.example.imageloaderpracticalnative.model.ImageModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("content/misc/media-coverages")
    suspend fun getImages(
        @Query("limit") limit: Int,
    ): List<ImageModel>
}