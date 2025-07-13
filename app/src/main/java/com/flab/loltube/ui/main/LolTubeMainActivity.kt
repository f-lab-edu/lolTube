package com.flab.loltube.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flab.loltube.ui.theme.LolTubeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LolTubeMainActivity : ComponentActivity() {

    private val viewModel by viewModels<LolTubeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LolTubeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val videoList by viewModel.videos.collectAsStateWithLifecycle()

                    LolTubeVideoListScreen(
                        videos = videoList,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
