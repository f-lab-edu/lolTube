package com.flab.loltube.ui.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.flab.loltube.databinding.ActivityMainBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, UserViewModelFactory())[UserViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
