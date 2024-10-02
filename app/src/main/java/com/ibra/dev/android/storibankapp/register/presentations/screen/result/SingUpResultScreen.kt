package com.ibra.dev.android.storibankapp.register.presentations.screen.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ibra.dev.android.storibankapp.R
import com.ibra.dev.android.storibankapp.core.presentation.navigations.LoginDestination
import com.ibra.dev.android.storibankapp.core.presentation.navigations.SingUpResultDestination
import com.ibra.dev.android.storibankapp.core.presentation.widgets.MyButton
import com.ibra.dev.android.storibankapp.ui.theme.xLargePadding

@Composable
fun SingUpResultScreen(
    navController: NavController,
    successMsg: String,
    state: BobsitoState
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .wrapContentSize(),
                painter = painterResource(id = state.id),
                contentDescription = stringResource(R.string.generic_content_descriptions),
                contentScale = ContentScale.None,
            )
            Text(
                modifier = Modifier,
                text = successMsg,
                fontWeight = FontWeight.Bold
            )
            MyButton(
                text = stringResource(R.string.accept_copy),
                modifier = Modifier
                    .padding(xLargePadding)
                    .fillMaxWidth(0.8f)
            ) {
                if (state == BobsitoState.SAD) {
                    navController.popBackStack<SingUpResultDestination>(inclusive = true)
                } else {
                    navController.navigate(LoginDestination) {
                        popUpTo<SingUpResultDestination> {
                            inclusive = true
                        }
                    }
                }
            }
        }

    }
}

@Composable
@Preview(heightDp = 600, widthDp = 300, showBackground = true)
fun GenericSuccessScreenPreview() {
    val navController = rememberNavController()
    SingUpResultScreen(
        navController,
        "Success",
        BobsitoState.HAPPY
    )
}