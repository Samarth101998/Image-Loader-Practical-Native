package com.example.imageloaderpracticalnative

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.imageloaderpracticalnative.adapter.ImageAdapter
import com.example.imageloaderpracticalnative.databinding.ActivityMainBinding
import com.example.imageloaderpracticalnative.helper.Const
import com.example.imageloaderpracticalnative.viewModel.ImageViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ImageViewModel
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ImageViewModel::class.java]
        if (Const.isOnline(this@MainActivity)) {
            binding.recyclerViewImages.visibility = View.VISIBLE
            binding.tvNoInternet.visibility = View.GONE
            Const.showDialog(this@MainActivity)
            initRecyclerView()
            fetchData()
        } else {
            binding.recyclerViewImages.visibility = View.GONE
            binding.tvNoInternet.visibility = View.VISIBLE
        }
    }

    private fun initRecyclerView() {
        adapter = ImageAdapter()
        binding.recyclerViewImages.adapter = adapter
    }

    private fun fetchData() {
        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest {
                Const.dismissDialog()
                adapter.submitData(it)
            }
        }
    }

    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }
}