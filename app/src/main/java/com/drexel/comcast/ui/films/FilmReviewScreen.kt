package com.drexel.comcast.ui.films

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.transform.CircleCropTransformation
import com.drexel.R
import com.drexel.comcast.ui.login.Login
import com.drexel.comcast.ui.login.components.*
import com.drexel.comcast.ui.navigation.BottomNavigationBar
import com.drexel.comcast.ui.navigation.FilmBottomNavigation
import com.drexel.comcast.ui.navigation.HomeNavigationItem
import com.drexel.model.response.FlimReviewsItem


@Composable
fun MainScreen(token:String){

    val viewModel: FilmReviewViewModel = viewModel()
    val navController = rememberNavController()

    Scaffold(topBar = { AppBar() },
        bottomBar ={ BottomNavigationBar(viewModel,navController) },
        content = { padding -> // We have to pass the scaffold inner padding to our content. That's why we use Box.
            Box(modifier = Modifier.padding(padding)) {
                FilmBottomNavigation(navController = navController,viewModel,token)
            }
        }

    )



}



@Composable
fun AddReview(
    modifier: Modifier = Modifier,
    loadingProgressBar: Boolean,
    onclickSubmit: (title: String, rating: String,authtoken:String) -> Unit,
    authtoken:String
){
    var title by rememberSaveable { mutableStateOf(value = "") }
    var rating by rememberSaveable { mutableStateOf(value = -1.0f) }
    val isValidate by derivedStateOf { title.isNotBlank() && rating >=0 }
    val focusManager = LocalFocusManager.current
    val viewModel: FilmReviewViewModel = viewModel()
    val filmReviewsItems = viewModel.reviewState.value
    var showSuccessMessage = false
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       // ImageLogin()

        //TextLogin()

       // Spacer(modifier = modifier.height(15.dp))

        DataOutTextField(
            textValue = title,
            onValueChange = { title = it },
            onClickButton = { title = "" },
            onNext = { focusManager.moveFocus(FocusDirection.Down) },
            label = "FilmName",
            defaultText = " Film  I like or hate"
        )

        Spacer(modifier = modifier.height(15.dp))

        RatingBar(
            value = rating,
            config = RatingBarConfig()
                .style(RatingBarStyle.HighLighted),
            onValueChange = {
                rating = it
            },
            onRatingChanged = {
                rating = it
                Log.d("TAG", "onRatingChanged: $it")
            }
        )
        Spacer(modifier = modifier.height(35.dp))

        ButtonSubmit(
            onclick = { focusManager.clearFocus()
                showSuccessMessage = true
                onclickSubmit(title, rating.toString(),authtoken) },
            enabled = isValidate,
            buttonText= "Submit"
        )

        Spacer(modifier = modifier.height(20.dp))
        AnimatedVisibility(visible =filmReviewsItems.title.isNotEmpty()) {
            Text(text = " Review Successfully Submitted",color=Color.Green) }
        Spacer(modifier = modifier.height(20.dp))
    }

    ProgressBarLoading(isLoading = loadingProgressBar)

}

@Composable
fun FilmReviewDetailsScreen(viewModel: FilmReviewViewModel) {

    val filmReviewsItems = viewModel.filmsState.value
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(filmReviewsItems) { film ->
            FilmReviewCard(film)
        }
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                Icons.Default.Home,
                contentDescription = "Home",
                modifier = Modifier.padding(horizontal = 12.dp),
            )
        },
        title = { Text("Film Review") }
    )


}

@Composable
fun FilmReviewCard(reviewsItem: FlimReviewsItem) {
    var rating =  3.2f
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row {
            FilmPicture("https://cdn.mos.cms.futurecdn.net/KT5FfnGZzr5pmqSRhuTyKE-1920-80.jpg")
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(10.dp)
            ){ FilmContent(reviewsItem.title,reviewsItem.rating)}


        }
    }
}

@Composable
fun FilmPicture(imageurl: String) {
    Card(
        shape = CircleShape,
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colors.onPrimary
        ),
        modifier = Modifier.padding(16.dp),
        elevation = 4.dp
    ) {
        Image(
            painter = rememberImagePainter(
                data = imageurl,
                builder = {
                    transformations(CircleCropTransformation())
                },
            ),
            modifier = Modifier.size(72.dp),
            contentDescription = "Film Description",
        )
    }
}

@Composable
fun FilmContent(title: String,  rating: String) {
    var filmrating = rating.toFloat()
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides (
                if (filmrating > 3.0f)
                    1f else ContentAlpha.medium)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h5
            )
        }
        CompositionLocalProvider(LocalContentAlpha provides (ContentAlpha.medium)) {
            RatingBar(
                value = filmrating,
                config = RatingBarConfig()
                    .style(RatingBarStyle.HighLighted),
                onValueChange = {
                    filmrating = it
                },
                onRatingChanged = {
                    Log.d("TAG", "onRatingChanged: $it")
                }
            )
        }
    }

}


