package com.flab.loltube.ui.user

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.flab.data.repository.UserRepositoryImpl
import com.flab.loltube.R
import kotlinx.coroutines.launch

class UserActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userRepository = UserRepositoryImpl()
        val factory = UserViewModelFactory(userRepository)
        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        lifecycleScope.launch {
            viewModel.fetchUsers(10)
        }

        viewModel.users.observe(this) { users ->
            Log.d("aaaaa", "Fetched Users: $users")
        }
    }
}
