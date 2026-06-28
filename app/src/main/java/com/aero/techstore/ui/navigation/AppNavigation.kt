package com.aero.techstore.ui.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aero.techstore.MainViewModel
import com.aero.techstore.ui.features.productDetail.DetailViewModel
import com.aero.techstore.ui.navigation.components.AppBottomNavigationBar
import com.aero.techstore.ui.navigation.components.AppToolbar
import com.aero.techstore.ui.navigation.components.DetailHeader

@Composable
fun AppNavigation(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("/") ?: NavScreens.HOME.route

    val navigationCallbacks = rememberNavigationCallbacks(navController)

    Scaffold(
        topBar = {
            AppTopBarSelector(currentRoute, navBackStackEntry, navigationCallbacks)
        },
        bottomBar = {
            if (shouldShowBottomBar(currentRoute)) {
                AppBottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = { route -> navigationCallbacks.navigateToRoute(route) }
                )
            }
        }
    ) { paddingValues ->
        AppNavHost(
            navController = navController,
            paddingValues = paddingValues,
            navigationCallbacks = navigationCallbacks,
            mainViewModel = mainViewModel
        )
    }
}

@Composable
private fun AppTopBarSelector(
    currentRoute: String,
    navBackStackEntry: NavBackStackEntry?,
    callbacks: NavigationCallbacks
) {
    when (currentRoute) {
        NavScreens.HOME.route -> AppToolbar(title = NavScreens.HOME.label)
        NavScreens.FAVORITES.route -> AppToolbar(title = NavScreens.FAVORITES.label)
        NavScreens.CART.route -> AppToolbar(
            title = NavScreens.CART.label,
            onBack = callbacks.navigateBack
        )
        NavScreens.ACCOUNT.route -> AppToolbar(title = NavScreens.ACCOUNT.label)
        NavScreens.ADD_CATEGORY.route -> AppToolbar(
            title = NavScreens.ADD_CATEGORY.label,
            onBack = callbacks.navigateBack
        )
        NavScreens.ADD_PRODUCT.route -> AppToolbar(
            title = NavScreens.ADD_PRODUCT.label,
            onBack = callbacks.navigateBack
        )
        NavScreens.MANAGE_PRODUCTS.route -> AppToolbar(
            title = NavScreens.MANAGE_PRODUCTS.label,
            onBack = callbacks.navigateBack
        )
        "edit_product" -> AppToolbar(
            title = NavScreens.EDIT_PRODUCT.label,
            onBack = callbacks.navigateBack
        )
        NavScreens.MANAGE_CATEGORIES.route -> AppToolbar(
            title = NavScreens.MANAGE_CATEGORIES.label,
            onBack = callbacks.navigateBack
        )
        "edit_category" -> AppToolbar(
            title = NavScreens.EDIT_CATEGORY.label,
            onBack = callbacks.navigateBack
        )
        "detail" -> {
            navBackStackEntry?.let { entry ->
                val detailViewModel: DetailViewModel = hiltViewModel(entry)
                val state by detailViewModel.uiState.collectAsState()
                DetailHeader(
                    isFavorite = state.isFavorite,
                    onBack = callbacks.navigateBack,
                    onFavoriteToggle = { detailViewModel.toggleFavorite() }
                )
            }
        }
    }
}

@Composable
fun rememberNavigationCallbacks(navController: NavController): NavigationCallbacks {
    return remember(navController) {
        NavigationCallbacks(
            navigateToDetail = { id -> navController.navigate("detail/$id") },
            navigateBack = { navController.popBackStack() },
            navigateToHome = {
                navController.navigate(NavScreens.HOME.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            navigateToFavorites = {
                navController.navigate(NavScreens.FAVORITES.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            navigateToCart = {
                navController.navigate(NavScreens.CART.route) {
                    launchSingleTop = true
                }
            },
            navigateToAccount = {
                navController.navigate(NavScreens.ACCOUNT.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            navigateToAddCategory = {
                navController.navigate(NavScreens.ADD_CATEGORY.route)
            },
            navigateToAddProduct = {
                navController.navigate(NavScreens.ADD_PRODUCT.route)
            },
            navigateToManageProducts = {
                navController.navigate(NavScreens.MANAGE_PRODUCTS.route)
            },
            navigateToEditProduct = { productId ->
                navController.navigate("edit_product/$productId")
            },
            navigateToManageCategories = {
                navController.navigate(NavScreens.MANAGE_CATEGORIES.route)
            },
            navigateToEditCategory = { categoryId ->
                navController.navigate("edit_category/$categoryId")
            }
        )
    }
}

private fun shouldShowBottomBar(route: String): Boolean {
    return route != "detail" && route != NavScreens.CART.route && 
           route != NavScreens.ADD_CATEGORY.route && route != NavScreens.ADD_PRODUCT.route &&
           route != NavScreens.MANAGE_PRODUCTS.route && !route.startsWith("edit_product") &&
           route != NavScreens.MANAGE_CATEGORIES.route && !route.startsWith("edit_category")
}

private fun NavigationCallbacks.navigateToRoute(route: String) {
    when (route) {
        NavScreens.HOME.route -> navigateToHome()
        NavScreens.FAVORITES.route -> navigateToFavorites()
        NavScreens.CART.route -> navigateToCart()
        NavScreens.ACCOUNT.route -> navigateToAccount()
    }
}
