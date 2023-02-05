package com.gobang.jetpackresponsive.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gobang.jetpackresponsive.WindowSize

enum class ScreenType {
    Summary,
    Detail,
    SummaryWithDetail
}

@Composable
fun HomeScreen(windowSize: WindowSize) {
    val isExpanded = windowSize == WindowSize.Expanded
    var index by remember {
        mutableStateOf(0)
    }
    var isItemOpened by remember {
        mutableStateOf(false)
    }
    val homeScreenType = getScreenType(
        isExpanded = isExpanded,
        isDetailOpened = isItemOpened
    )
    val color = listOf(
        Color.Blue,
        Color.Green,
        Color.Yellow,
        Color.Red,
        Color.Cyan,
        Color.Magenta,
        Color.Gray,
        Color.DarkGray,
        Color.Black,
        Color.LightGray
    )

    when (homeScreenType) {
        ScreenType.Detail -> {
            DetailScreen(color = color[index]) {
                isItemOpened = false
            }
        }
        ScreenType.Summary -> {
            SummaryScreen(
                items = color,
                onItemSelected = {
                    index = it
                    isItemOpened = true
                })
        }
        ScreenType.SummaryWithDetail -> {
            SummaryWithDetailScreen(
                items = color,
                index = index,
                onIndexChanged = {
                    index = it
                })
        }
    }
}

@Composable
fun SummaryScreen(
    items: List<Color>, onItemSelected: (index: Int) -> Unit, modifier: Modifier = Modifier
) {
    LazyColumn(contentPadding = PaddingValues(top = 8.dp)) {
        itemsIndexed(items) { index, item ->
            SummaryItem(color = item) {
                onItemSelected(index)
            }
        }
    }
}

@Composable
fun SummaryItem(color: Color, onItemSelected: () -> Unit) {
    Surface(color = color,
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(8.dp)
            .clickable { onItemSelected.invoke() }) {

    }
}

@Composable
fun DetailScreen(
    color: Color,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        DetailItem(color = color)
    }
    BackHandler() {
        onBackPressed.invoke()
    }
}

@Composable
fun DetailItem(color: Color) {
    Surface(
        color = color, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp)
    ) {


    }
}

@Composable
fun SummaryWithDetailScreen(
    items: List<Color>,
    index: Int, onIndexChanged: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        SummaryScreen(
            items = items,
            onItemSelected = onIndexChanged,
            modifier = Modifier.width(334.dp)
        )
        DetailScreen(color = items[index]) {

        }
    }
}

@Composable
fun getScreenType(
    isExpanded: Boolean,
    isDetailOpened: Boolean
): ScreenType = when (isExpanded) {
    false -> {
        if (isDetailOpened) {
            ScreenType.Detail
        } else {
            ScreenType.Summary
        }
    }
    true -> {
        ScreenType.SummaryWithDetail
    }
}