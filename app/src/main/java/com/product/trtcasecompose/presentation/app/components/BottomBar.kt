package com.product.trtcasecompose.presentation.app.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.product.trtcasecompose.R
import com.product.trtcasecompose.presentation.app.navigation.Screen

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val labelResId: Int
)

@Composable
fun BottomBar(
    currentRoute: String,
    onItemClick: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem(
            route = Screen.Home.route,
            icon = Icons.Default.Home,
            labelResId = R.string.bottom_nav_home
        ),
        BottomNavItem(
            route = Screen.Favorites.route,
            icon = Icons.Default.Favorite,
            labelResId = R.string.bottom_nav_favorites
        )
    )

    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = { onItemClick(item.route) },
                icon = { Icon(item.icon, contentDescription = stringResource(item.labelResId)) },
                label = { Text(text = stringResource(item.labelResId)) }
            )
        }
    }
}
