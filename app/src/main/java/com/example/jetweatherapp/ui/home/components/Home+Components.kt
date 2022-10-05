package com.example.jetweatherapp.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jetweatherapp.data.model.Location
import com.example.jetweatherapp.ui.theme.TypographyLight


@Preview
@Composable
fun BottomInfoCard(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(16.dp)
            .border(2.dp, Color.White, RoundedCornerShape(12.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InfoDetailsRow(information = "64 %", label = "Humidity")
        InfoDetailsRow(information = "4 km", label = "Visibility")
        InfoDetailsRow(information = "Low 0", label = "UV Index")
    }
}

@Preview
@Composable
fun InfoDetailsRow(information: String = "78%", label: String = "Humidity") {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = information, style = TypographyLight.body1)
        Text(text = label, style = TypographyLight.body2)
    }
}

@Composable
fun TemperatureInformationHeader(modifier: Modifier = Modifier, location: Location?) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .background(Color.Transparent)
    ) {
        val (name, temperature, description) = createRefs()
        Text(
            text = location?.name ?: "NEW ZELAND",
            Modifier.constrainAs(name) { top.linkTo(parent.top, margin = 16.dp) },
            style = TypographyLight.h2
        )
        Text(
            text = "${location?.temperature?.temperature?.toInt() ?: 19} °C",
            Modifier.constrainAs(temperature) { top.linkTo(name.bottom) },
            style = TypographyLight.h1
        )
        Text(
            text = location?.currentWeather?.description ?: "Clear skies",
            Modifier
                .rotate(-90f)
                .constrainAs(description) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(temperature.end)
                },
            style = TypographyLight.body1
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }
    var isHintDisplayed by remember { mutableStateOf(text != "") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
            },
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, RoundedCornerShape(8.dp))
                .background(Color.Transparent, RoundedCornerShape(16.dp))
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { isHintDisplayed = !it.isFocused },
            textStyle = TextStyle(color = Color.White),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(text)
                    keyboardController?.hide()
                    text = ""
                    focusManager.clearFocus()
                })
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
            )
        }
    }
}
