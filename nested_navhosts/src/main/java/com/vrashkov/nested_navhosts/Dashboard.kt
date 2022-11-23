package com.vrashkov.nested_navhosts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Dashboard(mainContent: @Composable (ColumnScope.() -> Unit)? = null) {
    Column {
        Column(Modifier.weight(1f)) {
            mainContent?.invoke(this)
        }
        Row (Modifier.fillMaxWidth().height(40.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            SingleTab(
                text = "Tab 1",
                onClick = {
                    LocalNavigationState.baseNavigation.getNavController.navigate(Route.Base_tab_1.link) {
                        launchSingleTop = true
                    }
                }
            )
            SingleTab(
                text = "Tab 2",
                onClick = {
                    LocalNavigationState.baseNavigation.getNavController.navigate(Route.Base_tab_2.link) {
                        launchSingleTop = true
                    }
                }
            )
            SingleTab(
                text = "Tab 3",
                onClick = {
                    LocalNavigationState.baseNavigation.getNavController.navigate(Route.Base_tab_3.link) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
private fun SingleTab(text: String =  "", onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxHeight().clickable {
        onClick()
    }) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text
        )
    }
}