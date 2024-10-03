package com.ibra.dev.android.storibankapp.home.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ibra.dev.android.storibankapp.R
import com.ibra.dev.android.storibankapp.core.data.entities.MovementsEntity
import com.ibra.dev.android.storibankapp.core.presentation.navigations.MovementsDetailsDestination
import com.ibra.dev.android.storibankapp.core.presentation.navigations.SingUpResultDestination
import com.ibra.dev.android.storibankapp.core.utils.orZero
import com.ibra.dev.android.storibankapp.home.domain.models.HomeUserDto
import com.ibra.dev.android.storibankapp.home.domain.models.MovementsDto
import com.ibra.dev.android.storibankapp.home.domain.models.TypeTransaction
import com.ibra.dev.android.storibankapp.home.presentation.contracts.HomeScreenState
import com.ibra.dev.android.storibankapp.home.presentation.viewmodels.HomeViewModel
import com.ibra.dev.android.storibankapp.register.presentations.screen.result.BobsitoState
import com.ibra.dev.android.storibankapp.ui.theme.defaultElevation
import com.ibra.dev.android.storibankapp.ui.theme.mediumPadding
import com.ibra.dev.android.storibankapp.ui.theme.myGreen
import com.ibra.dev.android.storibankapp.ui.theme.smallPadding
import com.ibra.dev.android.storibankapp.ui.theme.xLargePadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, email: String) {

    val homeViewModel: HomeViewModel = hiltViewModel()

    val homeState by homeViewModel.homeEventsStateFlow.collectAsState()

    var isLoading by remember { mutableStateOf(false) }

    var userDto: HomeUserDto? by remember { mutableStateOf(null) }

    LaunchedEffect(key1 = Unit) {
        homeViewModel.getUserInfo(email)
    }

    LaunchedEffect(key1 = homeState) {
        when (val state = homeState) {
            is HomeScreenState.Loading -> {
                isLoading = true
            }

            is HomeScreenState.Success -> {
                isLoading = false
                userDto = state.data
            }

            is HomeScreenState.Error -> {
                isLoading = false
                navController.navigate(SingUpResultDestination(state.message, BobsitoState.SAD))
            }

            HomeScreenState.Initial -> Unit
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.home_title))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                userDto?.let { dto ->
                    HomeBody(
                        userDto = dto
                    ) { index ->
                        navController.navigate(
                            MovementsDetailsDestination(
                                dto.movements[index].type
                                    ?: TypeTransaction.DEPOSIT,
                                dto.movements[index].amount.toString(),
                                dto.movements[index].date.orEmpty(),
                                dto.movements[index].description.orEmpty()
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeBody(
    modifier: Modifier = Modifier,
    userDto: HomeUserDto,
    onclickMovementItem: (Int) -> Unit
) {

    Column(
        modifier = modifier.padding(xLargePadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            shape = RoundedCornerShape(
                12.dp
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = defaultElevation),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Balance: ${userDto.balance}",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "Cliente: ${userDto.name} ${userDto.surname}",
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "Email: ${userDto.email}",
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodySmall
                )

                LoadImageWithCustomization(userDto.urlDniPicture)
            }
        }

        Text(
            modifier = Modifier.padding(top = xLargePadding),
            text = "Movimientos",
            style = MaterialTheme.typography.headlineLarge
        )

        if (userDto.movements.isEmpty()) {
            Text(
                modifier = Modifier.padding(top = mediumPadding),
                text = "No hay movimientos",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(
                modifier = Modifier.wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                itemsIndexed(userDto.movements) { index, movement ->
                    MovementsItem(movement) {
                        onclickMovementItem(index)
                    }
                }
            }
        }
    }

}

@Composable
fun MovementsItem(movement: MovementsDto, onclick: () -> Unit) {
    val color = if (movement.type == TypeTransaction.DEPOSIT) myGreen else Color.Red
    Column(
        modifier = Modifier
            .padding(vertical = smallPadding)
            .clickable {
                onclick()
            },
    ) {
        HorizontalDivider(
            color = color,
            thickness = 1.dp,
        )

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${movement.date}",
                color = color
            )

            Text(
                text = "${movement.amount}",
                color = color
            )
        }


        HorizontalDivider(
            color = color,
            thickness = 1.dp,
        )
    }
}

@Composable
fun LoadImageWithCustomization(imageUrl: String) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build()
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = "Loaded image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                Log.i("coil state", "HomeBody: Loading")
                CircularProgressIndicator()
            }

            is AsyncImagePainter.State.Error -> {
                Log.i("coil state", "HomeBody: Error")
                Text("Error loading image")
            }

            else -> {
                Log.i("coil state", "HomeBody: else ${painter.state}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    val dto = HomeUserDto(
        name = "Ibrahim",
        surname = "estanga",
        email = "ibra@gmail.com",
        balance = 100.0,
        movements = listOf(
            MovementsDto(
                type = TypeTransaction.DEPOSIT,
                amount = 100.0
            ),
            MovementsDto(
                type = TypeTransaction.WITHDRAWAL,
                amount = 50.0
            ),
            MovementsDto(
                type = TypeTransaction.WITHDRAWAL,
                amount = 100.0
            ),
            MovementsDto(
                type = TypeTransaction.WITHDRAWAL,
                amount = 50.0
            )
        )
    )
    HomeBody(userDto = dto) {}
}