package com.drexel.comcast.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.drexel.comcast.ui.films.AddReview
import com.drexel.comcast.ui.films.FilmReviewDetailsScreen
import com.drexel.comcast.ui.films.FilmReviewViewModel

@Composable
fun FilmBottomNavigation(navController: NavHostController, viewModel : FilmReviewViewModel, token:String) {
    NavHost(navController, startDestination = HomeNavigationItem.ADD.route) {
        composable(HomeNavigationItem.ADD.route) {
                    AddReview(
            loadingProgressBar = viewModel.progressBar.value,
            onclickSubmit = viewModel::addFilmrating,
            authtoken = token

        )
        }
        composable(HomeNavigationItem.REVIEW.route) {
            FilmReviewDetailsScreen(viewModel)
        }
        composable(HomeNavigationItem.UPDATE.route) {
            AddReview(
                loadingProgressBar = viewModel.progressBar.value,
                onclickSubmit = viewModel::updateFilmrating,
                authtoken = token

            )
        }

    }
}
