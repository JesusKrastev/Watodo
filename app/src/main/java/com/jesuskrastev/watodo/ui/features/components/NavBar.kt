package com.jesuskrastev.watodo.ui.features.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.jesuskrastev.watodo.ui.navigation.ActivitiesRoute
import com.jesuskrastev.watodo.ui.navigation.Destination
import com.jesuskrastev.watodo.ui.navigation.SavedRoute

@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    onNavigateTo: (Destination) -> Unit,
) {
    @Immutable
    data class NavOption(
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector,
        val description: String? = null,
        val title: String,
        val onClick: () -> Unit
    )

    var selectedPage: Int by remember { mutableIntStateOf(0) }
    val listNavOptions: List<NavOption> = listOf(
        NavOption(
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
            description = "Actividades",
            title = "Actividades",
            onClick = {
                onNavigateTo(ActivitiesRoute)
            },
        ),
        NavOption(
            selectedIcon = Icons.Filled.Language,
            unselectedIcon = Icons.Outlined.Language,
            description = "Webs",
            title = "Webs",
            onClick = {

            },
        ),
        NavOption(
            selectedIcon = Icons.Filled.Bookmark,
            unselectedIcon = Icons.Outlined.BookmarkBorder,
            description = "Guardados",
            title = "Guardados",
            onClick = {
                onNavigateTo(SavedRoute)
            },
        ),
    )

    NavigationBar(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listNavOptions.forEachIndexed { index, button ->
                val selected = selectedPage == index

                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = if(selected) button.selectedIcon else button.unselectedIcon,
                            contentDescription = button.description,
                        )
                    },
                    label = {
                        Text(
                            text = button.title,
                        )
                    },
                    selected = selected,
                    onClick = {
                        button.onClick()
                        selectedPage = index
                    }
                )
            }
        }
    }
}