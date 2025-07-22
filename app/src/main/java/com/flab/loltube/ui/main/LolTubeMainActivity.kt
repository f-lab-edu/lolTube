package com.flab.loltube.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.flab.loltube.ui.theme.LolTubeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LolTubeMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LolTubeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LolTubeVideoListRoute(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
