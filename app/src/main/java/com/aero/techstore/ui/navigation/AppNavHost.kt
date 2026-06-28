package com.aero.techstore.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aero.techstore.MainViewModel
import com.aero.techstore.ui.features.account.AccountScreen
import com.aero.techstore.ui.features.addCategory.AddCategoryScreen
import com.aero.techstore.ui.features.editCategory.EditCategoryScreen
import com.aero.techstore.ui.features.addProduct.AddProductScreen
import com.aero.techstore.ui.features.editProduct.EditProductScreen
import com.aero.techstore.ui.features.manageProducts.ManageProductsScreen
import com.aero.techstore.ui.features.manageCategories.ManageCategoriesScreen
import com.aero.techstore.ui.features.cart.CartScreen
import com.aero.techstore.ui.features.favorites.FavoritesScreen
import com.aero.techstore.ui.features.productDetail.DetailScreen
import com.aero.techstore.ui.features.productstore.ProductStoreScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    navigationCallbacks: NavigationCallbacks,
    mainViewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavScreens.HOME.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(NavScreens.HOME.route) {
            ProductStoreScreen(
                navigationCallbacks = navigationCallbacks,
                onThemeChange = mainViewModel::onThemeChange,
                productStoreViewModel = hiltViewModel()
            )
        }
        composable(NavScreens.FAVORITES.route) {
            FavoritesScreen(
                navigationCallbacks = navigationCallbacks,
                favoritesViewModel = hiltViewModel()
            )
        }
        composable(
            "detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) {
            DetailScreen(
                navigationCallbacks = navigationCallbacks,
                detailViewModel = hiltViewModel()
            )
        }
        composable(NavScreens.CART.route) {
            CartScreen(
                navigationCallbacks = navigationCallbacks,
                cartViewModel = hiltViewModel(),
                onCheckoutSuccess = {
                    navigationCallbacks.navigateToHome()
                }
            )
        }
        composable(NavScreens.ACCOUNT.route) {
            AccountScreen(
                navigationCallbacks = navigationCallbacks,
                viewModel = hiltViewModel()
            )
        }
        composable(NavScreens.ADD_CATEGORY.route) {
            AddCategoryScreen(
                navigationCallbacks = navigationCallbacks,
                viewModel = hiltViewModel()
            )
        }
        composable(NavScreens.ADD_PRODUCT.route) {
            AddProductScreen(
                navigationCallbacks = navigationCallbacks,
                viewModel = hiltViewModel()
            )
        }
        composable(NavScreens.MANAGE_PRODUCTS.route) {
            ManageProductsScreen(
                navigationCallbacks = navigationCallbacks,
                viewModel = hiltViewModel()
            )
        }
        composable(NavScreens.MANAGE_CATEGORIES.route) {
            ManageCategoriesScreen(
                navigationCallbacks = navigationCallbacks,
                viewModel = hiltViewModel()
            )
        }
        composable(
            "edit_product/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) {
            EditProductScreen(
                navigationCallbacks = navigationCallbacks,
                viewModel = hiltViewModel()
            )
        }
        composable(
            "edit_category/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) {
            EditCategoryScreen(
                navigationCallbacks = navigationCallbacks,
                viewModel = hiltViewModel()
            )
        }
    }
}
