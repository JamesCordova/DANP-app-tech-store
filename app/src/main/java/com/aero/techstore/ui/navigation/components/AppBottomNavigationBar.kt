package com.aero.techstore.ui.navigation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.aero.techstore.ui.navigation.NavScreens

@Composable
fun AppBottomNavigationBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == NavScreens.HOME.route,
            onClick = { onNavigate(NavScreens.HOME.route) },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text(NavScreens.HOME.label) }
        )
        NavigationBarItem(
            selected = currentRoute == NavScreens.FAVORITES.route,
            onClick = { onNavigate(NavScreens.FAVORITES.route) },
            icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
            label = { Text(NavScreens.FAVORITES.label) }
        )
        NavigationBarItem(
            selected = currentRoute == NavScreens.CART.route,
            onClick = { onNavigate(NavScreens.CART.route) },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
            label = { Text(NavScreens.CART.label) }
        )
        NavigationBarItem(
            selected = currentRoute == NavScreens.ACCOUNT.route,
            onClick = { onNavigate(NavScreens.ACCOUNT.route) },
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text(NavScreens.ACCOUNT.label) }
        )
    }
}
