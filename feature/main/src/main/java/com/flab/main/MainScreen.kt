package com.flab.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flab.home.LolTubeVideoListRoute
import com.flab.main.component.MainNavigationBar
import com.flab.main.component.NavigationItem
import com.flab.shorts.webview.ShortsPager

@Composable
fun MainRoute() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()

    MainScreen(
        selectedIndex = selectedIndex,
        onNavigationItemSelected = { index ->
            selectedIndex = index
            when (index) {
                0 -> {
                    if (navController.currentDestination?.route != "home") {
                        navController.navigate("home") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }

                1 -> {
                    if (navController.currentDestination?.route != "shorts") {
                        navController.navigate("shorts") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
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
                ShortsPager(
                    videoIds = listOf(
                        "Cmb2plgG328",
                        "_qvDfygshj4",
                        "41eQ4n4EtX8",
                        "fJKkakGuToQ",
                        "-_6v3Poz9oU"
                    )
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
