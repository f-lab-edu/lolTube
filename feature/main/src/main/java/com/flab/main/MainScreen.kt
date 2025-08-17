package com.flab.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flab.home.LolTubeVideoListRoute
import com.flab.main.component.MainNavigationBar
import com.flab.main.component.NavigationItem

@Composable
fun MainRoute() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()

    MainScreen(
        selectedIndex = selectedIndex,
        onNavigationItemSelected = { index ->
            selectedIndex = index
            when (index) {
                0 -> navController.navigate("home")
                1 -> navController.navigate("shorts")
            }
        },
        navController = navController
    )
}

@Composable
fun MainScreen(
    selectedIndex: Int,
    onNavigationItemSelected: (Int) -> Unit,
    navController: NavHostController
) {
    val navigationItems = listOf(
        NavigationItem.HOME,
        NavigationItem.SHORTS
    )

    Scaffold(
        bottomBar = {
            MainNavigationBar(
                selectedIndex = selectedIndex,
                onItemSelected = onNavigationItemSelected,
                navigationItems = navigationItems
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            composable("home") {
                LolTubeVideoListRoute()
            }
            composable("shorts") {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    text = stringResource(com.flab.designsystem.R.string.shorts_screen_title),
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        Surface {
            MainRoute()
        }
    }
}
