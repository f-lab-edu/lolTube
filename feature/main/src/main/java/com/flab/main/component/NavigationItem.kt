package com.flab.main.component

import com.flab.main.R

enum class NavigationItem(
    val title: String,
    val route: String,
    val iconResId: Int
) {
    HOME(
        title = "홈",
        route = "home",
        iconResId = R.drawable.ic_home
    ),
    SHORTS(
        title = "쇼츠",
        route = "shorts",
        iconResId = R.drawable.ic_shorts
    );
}
