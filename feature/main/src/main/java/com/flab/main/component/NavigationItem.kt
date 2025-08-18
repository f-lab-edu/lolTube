package com.flab.main.component

import com.flab.main.R

enum class NavigationItem(
    val titleResId: Int,
    val route: String,
    val iconResId: Int
) {
    HOME(
        titleResId = com.flab.designsystem.R.string.navigation_home,
        route = "home",
        iconResId = R.drawable.ic_home
    ),
    SHORTS(
        titleResId = com.flab.designsystem.R.string.navigation_shorts,
        route = "shorts",
        iconResId = R.drawable.ic_shorts
    );
}
