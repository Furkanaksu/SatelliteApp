package com.furkan.satellite.features.screens.main

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.furkan.satellite.data.model.Satellite
import com.furkan.satellite.features.components.LoadingBar
import com.furkan.satellite.features.components.SearchBar
import com.furkan.satellite.ui.theme.Colors

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onClickItem : (String) -> Unit,
)
{
    val viewState by viewModel.uiState.collectAsState()

    Content(
        viewState = viewState,
        onChangeText = viewModel::setTextQuery,
        onClearSearch = viewModel::clearTextQuery,
        onClickItem = onClickItem
    )

}

@Composable
fun Content(
    onChangeText : (String) -> Unit,
    onClearSearch : () -> Unit,
    onClickItem : (String) -> Unit,
    viewState : HomeViewState
){

    LoadingBar(isDisplayed = viewState.isLoading)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.padding(16.dp))

        SearchBar(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            query = viewState.query,
            onQueryChanged = onChangeText,
            onClearClick = onClearSearch,
        )

        Spacer(modifier = Modifier.padding(8.dp))

        LazyColumn(){
            itemsIndexed(viewState.data?: listOf()){index, item ->
                if (item != null) {
                    StarShipItem(
                        data = item,
                        onClickItem = {
                            onClickItem(it.id.toString())
                        },
                        isLastItem = index == viewState.data?.size?.minus(1)
                    )
                }
            }
        }
    }
}


@Composable
fun StarShipItem(
    data: Satellite,
    onClickItem: (Satellite) -> Unit,
    isLastItem : Boolean = false
) {

    val color = if (data.active){
        Colors.LimeGreen
    }
    else{
        Colors.Red
    }

    Column(
        modifier = Modifier
            .clickable {
                onClickItem(data)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(color = color)
            )
            Spacer(modifier = Modifier.padding(12.dp))

            Column {
                Text(
                    text = data.name,
                    color = if (data.active) Colors.BLACK else Colors.DarkGray
                )

                Spacer(modifier = Modifier.padding(2.dp))

                Text(
                    text = data.active.toString(),
                    color = if (data.active) Colors.BLACK else Colors.DarkGray
                )

            }
        }

        Spacer(modifier = Modifier.padding(2.dp))


        Divider(
            color = if (isLastItem) Colors.Trans else Colors.DarkGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp)
        )


    }
}
