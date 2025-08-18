package com.flab.main.component

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    navigationItems: List<NavigationItem>
) {
    NavigationBar {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        painter = painterResource(item.iconResId),
                        contentDescription = stringResource(item.titleResId),
                        tint = Color.Unspecified
                    )
                },
                label = {
                    Text(text = stringResource(item.titleResId))
                }
            )
        }
    }
}

@Preview
@Composable
fun MainNavigationBarPreview() {
    val navigationItems = listOf(
        NavigationItem.HOME,
        NavigationItem.SHORTS,
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    MaterialTheme {
        MainNavigationBar(
            selectedIndex = selectedIndex,
            onItemSelected = { selectedIndex = it },
            navigationItems = navigationItems
        )
    }
}
