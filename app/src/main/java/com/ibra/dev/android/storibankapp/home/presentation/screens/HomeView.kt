package com.ibra.dev.android.storibankapp.home.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ibra.dev.android.storibankapp.R
import com.ibra.dev.android.storibankapp.core.data.entities.MovementsEntity
import com.ibra.dev.android.storibankapp.core.utils.orZero
import com.ibra.dev.android.storibankapp.home.domain.models.HomeUserDto
import com.ibra.dev.android.storibankapp.home.presentation.contracts.HomeScreenState
import com.ibra.dev.android.storibankapp.home.presentation.viewmodels.HomeViewModel
import com.ibra.dev.android.storibankapp.ui.theme.defaultElevation
import com.ibra.dev.android.storibankapp.ui.theme.mediumPadding
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
                        modifier = Modifier.fillMaxSize(),
                        userDto = dto
                    )
                }
            }
        }
    }
}

@Composable
fun HomeBody(modifier: Modifier = Modifier, userDto: HomeUserDto) {
    Column(
        modifier = modifier.padding(xLargePadding),
        verticalArrangement = Arrangement.Center,
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
            Box(modifier = Modifier.fillMaxSize()) {

                Text(
                    text = "Balance: ${userDto.balance}",
                    modifier = Modifier.align(Alignment.TopCenter),
                    style = MaterialTheme.typography.headlineLarge
                )

                Column(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(
                        text = "Cliente: ${userDto.name} ${userDto.surname}",
                        modifier = Modifier,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = "Email: ${userDto.email}",
                        modifier = Modifier,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }


            }
        }

        Text(
            modifier = modifier.padding(top = xLargePadding, bottom = xLargePadding),
            text = "Movimientos",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            modifier = modifier.padding(top = mediumPadding, bottom = xLargePadding),
            text = "Tipo - Monto",
            style = MaterialTheme.typography.titleLarge
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            items(userDto.movements) { movement ->
                Row {
                    Text(
                        modifier = modifier.padding(top = smallPadding),
                        text = "${movement.type.orEmpty()} - ${movement.amount.orZero()}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
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
            MovementsEntity(
                type = "Deposit",
                amount = 100.0
            ),
            MovementsEntity(
                type = "Withdraw",
                amount = 50.0
            ),
            MovementsEntity(
                type = "Deposit",
                amount = 100.0
            ),
            MovementsEntity(
                type = "Withdraw",
                amount = 50.0
            )
        )
    )
    HomeBody(userDto = dto)
}