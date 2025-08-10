package com.flab.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.flab.main.component.MainNavigationBar
import com.flab.main.component.NavigationItem

@Composable
fun MainScreen() {
    val navigationItems = listOf(
        NavigationItem.HOME,
        NavigationItem.SHORTS
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            MainNavigationBar(
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it },
                navigationItems = navigationItems
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when (selectedIndex) {
                    0 -> "홈 화면"
                    1 -> "쇼츠 화면"
                    else -> "알 수 없는 화면"
                },
                fontSize = 24.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        Surface {
            MainScreen()
        }
    }
}
