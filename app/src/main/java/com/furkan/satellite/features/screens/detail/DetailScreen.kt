package com.furkan.satellite.features.screens.detail


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.furkan.satellite.R
import com.furkan.satellite.features.components.LoadingBar


@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBackPressed : () -> Unit,
)
{
    val viewState by viewModel.uiState.collectAsState()

    Content(
        viewState = viewState,
    )

}


@Composable
fun Content(
    viewState : DetailViewState
){

    LoadingBar(isDisplayed = viewState.isLoading)

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = viewState.shipName,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight(500)
            )
        )

        Spacer(modifier = Modifier.padding(12.dp))

        viewState.data?.firstFlight?.let { Text(text = it) }

        Spacer(modifier = Modifier.padding(16.dp))

        RowItem(
            title = stringResource(R.string.height_mass),
            value = viewState.data?.height.toString()+"/"+viewState.data?.mass.toString()
        )

        RowItem(
            title = stringResource(R.string.cost),
            value = viewState.data?.costPerLaunch.toString()
        )

        RowItem(
            title = stringResource(R.string.lastPosition),
            value = viewState.coordinate?.posX.toString()+","+viewState.coordinate?.posY.toString()
        )

    }
}

@Composable
fun RowItem(
    title : String,
    value : String
){
    Row {
        Text(
            text = title,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight(500)
            )
        )
        Text(
            text = value,
        )
    }

    Spacer(modifier = Modifier.padding(16.dp))
}
