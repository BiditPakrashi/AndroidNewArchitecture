package com.drexel.comcast.ui.navigation

import com.drexel.R

sealed class HomeNavigationItem(var route: String, var icon: Int, var title: String) {
    object ADD : HomeNavigationItem("add", R.drawable.ic_profile, "Add Review")
    object REVIEW : HomeNavigationItem("review", R.drawable.ic_movie, "Show Review")
    object UPDATE : HomeNavigationItem("update", R.drawable.ic_book, "Update Review")

}
