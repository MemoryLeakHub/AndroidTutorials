package com.vrashkov.shared_image

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.vrashkov.shared_image.ui.theme.AndroidTutorialsTheme
import kotlinx.coroutines.launch
import com.vrashkov.androidtutorials.R

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition", "UnrememberedGetBackStackEntry")
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            AndroidTutorialsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BoxWithConstraints(
                        modifier = Modifier.navigationBarsPadding().imePadding()
                    ) {

                        val navController = rememberAnimatedNavController()

                        AnimatedNavHost(
                            navController = navController,
                            startDestination = PRODUCT_ROUTE,
                            route = BASE_ROUTE
                        ) {
                            productNavGraph(
                                route = PRODUCT_ROUTE,
                                constraints = constraints,
                                navController = navController
                            )
                        }

                        val parentEntry = remember {
                            navController.getBackStackEntry(PRODUCT_ROUTE)
                        }
                        val sharedViewModel= viewModel<SharedViewModel>(parentEntry)

                        SharedImageTransition(
                            sharedImageFlow = sharedViewModel.sharedImageFlow,
                            sharedImageAnimateEventFlow = sharedViewModel.sharedImageAnimateEventFlow
                        )

                    }
                }
            }
        }

    }
}

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun ProductListScreen(navController: NavController) {
    val parentEntry = remember {
        navController.getBackStackEntry(PRODUCT_ROUTE)
    }
    val sharedViewModel = viewModel<SharedViewModel>(parentEntry)
    val coroutineScope = rememberCoroutineScope()

    val productsList = ProductsData().productList

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2)
    ) {
        items(productsList) { imageId ->
            var imageData = SharedImageData()
            Image(
                painterResource(id = imageId),
                modifier = Modifier.fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        val size = coordinates.size
                        val position = coordinates.positionInRoot()
                        imageData = SharedImageData(
                            height = size.height.toFloat(),
                            width = size.width.toFloat(),
                            positionX = position.x,
                            positionY = position.y
                        )
                    }
                    .clickable {
                        coroutineScope.launch {
                            sharedViewModel.sharedImageFlow.emit(
                                SharedImageDataEvent.ForwardImage(
                                imageData
                            ))

                            sharedViewModel.sharedImageFlow.emit(
                                SharedImageDataEvent.SetImage(
                                imageId
                            ))

                            sharedViewModel.sharedImageAnimateEventFlow.emit(SharedImageAnimateEvent.AnimateForward)
                        }

                        navController.navigate(Route.ProductDetails.link)
                    },
                contentDescription = null
            )
        }
    }
}

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun ProductDetailsScreen(navController: NavController) {

    val parentEntry = remember {
        navController.getBackStackEntry(PRODUCT_ROUTE)
    }
    val sharedViewModel = viewModel<SharedViewModel>(parentEntry)
    val coroutineScope = rememberCoroutineScope()


    BackHandler {

        coroutineScope.launch {
            sharedViewModel.sharedImageAnimateEventFlow.emit(SharedImageAnimateEvent.AnimateBackward)
            sharedViewModel.sharedImageAnimateEventFlow.emit(SharedImageAnimateEvent.StartAnimation)
        }
        navController.popBackStack()
    }

    var dataIsCalculated by remember { mutableStateOf(false) }

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(3)
    ) {
        repeat(20) {
            item (key = it){
                SingleImage(
                    placeAtPosition = (1..20).random(),
                    currentIndex = it,
                    onPlaceAtPosition = { placeHolderData ->
                        coroutineScope.launch {
                            if (placeHolderData.height > 0 && placeHolderData.width > 0) {
                                if (!dataIsCalculated) {
                                    sharedViewModel.sharedImageFlow.emit(
                                        SharedImageDataEvent.BackwardImage(
                                            placeHolderData
                                        )
                                    )

                                    sharedViewModel.sharedImageAnimateEventFlow.emit(
                                        SharedImageAnimateEvent.StartAnimation
                                    )
                                    dataIsCalculated = true
                                }
                            }
                        }
                    }
                )
            }
        }

    }

}

@Composable
fun SingleImage(placeAtPosition:Int = 0, currentIndex: Int, onPlaceAtPosition: (SharedImageData) -> Unit) {
    var placeHolderData = SharedImageData()
    Image(
        painterResource(id = R.drawable.placeholder),
        modifier = Modifier.fillMaxSize()
            .onGloballyPositioned { coordinates ->

                val size = coordinates.size
                val position = coordinates.positionInParent()
                placeHolderData = SharedImageData(
                    height = size.height.toFloat(),
                    width = size.width.toFloat(),
                    positionX = position.x,
                    positionY = position.y
                )

                if (placeAtPosition == currentIndex) {
                    onPlaceAtPosition(placeHolderData)
                }

            }
        ,contentDescription = null
    )
}