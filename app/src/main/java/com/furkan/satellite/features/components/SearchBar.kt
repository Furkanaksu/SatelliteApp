package com.furkan.satellite.features.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.furkan.satellite.R
import com.furkan.satellite.ui.theme.Colors

@Composable
fun SearchBar(
    modifier: Modifier,
    query: String,
    rightImage: Int? = null,
    placeholderText: String = "",
    border: BorderStroke = BorderStroke(1.dp, Colors.DarkGray),
    backgroundColor: Color = Colors.White,
    shape: Shape =  RoundedCornerShape(12.dp),
    elevation: Dp = 0.dp,
    onClearClick: () -> Unit = {},
    onClick: () -> Unit = {},
    onSearch: (String) -> Unit = {},
    onQueryChanged: (String) -> Unit,
) {
    var showClearButton by remember { mutableStateOf(if (rightImage == null){query.isNotEmpty()}else{true} ) }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier
            .shadow(elevation)
            .background(backgroundColor, shape)
            .border(border, shape),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Icon(
            modifier = Modifier.padding(start = 16.dp),
            painter = painterResource(id = R.drawable.ic_search_black),
            contentDescription = "Search"
        )

        TextField(
            modifier = Modifier
                .heightIn(min = 40.dp)
                .fillMaxWidth()
                .background(Color.Transparent)
                .clickable {
                    onClick()
                },
            value = query,
            onValueChange = {
                showClearButton = if (rightImage == null) it.isNotBlank() else {
                    true
                }
                onQueryChanged(it)
            },
            placeholder = {
                Text(
                    text = placeholderText,
                    modifier = Modifier.wrapContentHeight(),
                    color = Colors.BLACK,
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
                textColor = Color.DarkGray
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(query)
                    focusManager.clearFocus()
                }
            ),
            trailingIcon = {
                AnimatedVisibility(
                    visible = showClearButton,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = {
                        showClearButton = rightImage != null
                        onClearClick()
                        focusManager.clearFocus()
                    }) {
                        Icon(
                            painter = painterResource(id = rightImage ?: R.drawable.ic_close_gray),
                            "Close"
                        )
                    }
                }
            },
            maxLines = 1,
            singleLine = true,
        )
    }
}

@Composable
@Preview
private fun SearchBarPreview() {
    SearchBar(query = "Android", modifier = Modifier) {}
}

@Composable
@Preview
private fun PreviewSearchBarPlaceHolder() {
    SearchBar(query = "", modifier = Modifier, placeholderText = "Place Holder") {}
}
