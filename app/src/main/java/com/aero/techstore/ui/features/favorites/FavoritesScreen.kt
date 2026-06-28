package com.aero.techstore.ui.features.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aero.techstore.ui.navigation.NavigationCallbacks
import com.aero.techstore.ui.features.favorites.components.EmptyFavoritesSection
import com.aero.techstore.ui.features.productstore.components.ProductCard

@Composable
fun FavoritesScreen(
    navigationCallbacks: NavigationCallbacks,
    favoritesViewModel: FavoritesViewModel
) {
    val uiState by favoritesViewModel.uiState.collectAsState()
    val favoriteProducts = uiState.favoriteProducts

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        if (favoriteProducts.isEmpty()) {
            EmptyFavoritesSection()
        } else {
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn {
                items(
                    items = favoriteProducts,
                    key = { it.id }
                ) { product ->
                    ProductCard(
                        product = product,
                        onViewDetail = { product ->
                            navigationCallbacks.navigateToDetail(product.id)
                        },
                        isFavorite = favoritesViewModel.isFavorite(product.id),
                        onFavoriteToggle = { productId ->
                            favoritesViewModel.toggleFavorite(productId)
                        }
                    )
                }
            }
        }
    }
}
