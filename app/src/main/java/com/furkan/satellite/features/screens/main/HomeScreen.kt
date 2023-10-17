package com.furkan.satellite.features.screens.main

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.furkan.satellite.features.screens.navigation.NavScreen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onClickItem : (String) -> Unit,
)
{
    val scope = rememberCoroutineScope()
    val viewState by viewModel.uiState.collectAsState()


    Content(viewState = viewState)



}

@Composable
fun Content(
    viewState : HomeViewState
){

    val context = LocalContext.current

    Toast.makeText(context, viewState.data?.firstOrNull()?.name, Toast.LENGTH_SHORT).show()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Screen")
    }
}
