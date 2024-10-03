package com.ibra.dev.android.storibankapp.home.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ibra.dev.android.storibankapp.R
import com.ibra.dev.android.storibankapp.core.utils.orZero
import com.ibra.dev.android.storibankapp.home.domain.models.MovementsDto
import com.ibra.dev.android.storibankapp.home.domain.models.TypeTransaction
import com.ibra.dev.android.storibankapp.ui.theme.mediumPadding
import com.ibra.dev.android.storibankapp.ui.theme.smallPadding
import com.ibra.dev.android.storibankapp.ui.theme.xLargePadding


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovementsScreen(navController: NavController, movement: MovementsDto) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.home_title_movements))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            movement.type?.let {
                MovementType(
                    modifier = Modifier.padding(
                        start = mediumPadding,
                        bottom = xLargePadding
                    ), type = it
                )
            }
            HowMuch(
                modifier = Modifier.padding(start = mediumPadding, bottom = xLargePadding),
                movement.amount.orZero()
            )
            WhenTransaction(
                modifier = Modifier.padding(
                    start = mediumPadding,
                    bottom = xLargePadding
                ), movement.date.orEmpty()
            )
            DescriptionTransaction(
                modifier = Modifier.padding(
                    start = mediumPadding,
                    bottom = xLargePadding
                ), movement.description.orEmpty()
            )
        }
    }
}

@Composable
fun MovementType(modifier: Modifier, type: TypeTransaction) {
    val resource = when (type) {
        TypeTransaction.DEPOSIT -> R.drawable.ic_deposit
        TypeTransaction.WITHDRAWAL -> R.drawable.ic_withdrawal
    }
    val text = when (type) {
        TypeTransaction.DEPOSIT -> stringResource(R.string.home_details_deposit_copy)
        TypeTransaction.WITHDRAWAL -> stringResource(R.string.home_details_withdrawal_copy)
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(resource),
            contentDescription = "Back"
        )
        Text(
            modifier = Modifier.padding(start = smallPadding),
            text = text
        )
    }
}

@Composable
fun HowMuch(
    modifier: Modifier = Modifier,
    amount: Double
) {
    Column(modifier) {
        if (amount < 0) return
        Text(modifier = Modifier.padding(bottom = smallPadding), text = "Cuanto?")
        Text(modifier = Modifier, text = amount.toString())
    }
}

@Composable
fun WhenTransaction(modifier: Modifier = Modifier, date: String) {
    if (date.isEmpty()) return
    Column(modifier) {
        Text(modifier = Modifier.padding(bottom = smallPadding), text = "Cuando?")
        Text(modifier = Modifier, text = date)
    }
}

@Composable
fun DescriptionTransaction(modifier: Modifier = Modifier, description: String) {
    if (description.isEmpty()) return
    Column(modifier) {
        Text(modifier = Modifier.padding(bottom = smallPadding), text = "Descripcion")
        Text(modifier = Modifier, text = description)
    }
}

@Preview(showBackground = true)
@Composable
fun MovementsScreenPreview() {
    MovementsScreen(
        navController = rememberNavController(), movement = MovementsDto(
            type = TypeTransaction.DEPOSIT,
            amount = 100.0,
            date = "2021-09-01",
            description = "Deposit"
        )
    )
}
