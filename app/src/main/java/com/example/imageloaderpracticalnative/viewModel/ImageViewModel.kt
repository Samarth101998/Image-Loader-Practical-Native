package com.example.imageloaderpracticalnative.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.imageloaderpracticalnative.ApiService
import com.example.imageloaderpracticalnative.RetrofitHelper
import com.example.imageloaderpracticalnative.model.ImageModel
import com.example.imageloaderpracticalnative.paging.ImagesPagingSource
import kotlinx.coroutines.flow.Flow

class ImageViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
    val pagingDataFlow: Flow<PagingData<ImageModel>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { ImagesPagingSource(apiService) }
    ).flow.cachedIn(viewModelScope)
}