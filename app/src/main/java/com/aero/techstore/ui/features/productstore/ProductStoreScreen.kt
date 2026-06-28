package com.aero.techstore.ui.features.productstore

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
import com.aero.techstore.ui.features.productstore.components.ProductCategoryButtons
import com.aero.techstore.ui.features.productstore.components.ProductCard
import com.aero.techstore.ui.features.productstore.components.SearchBar
import com.aero.techstore.ui.features.productstore.components.ThemeSelector

@Composable
fun ProductStoreScreen(
    navigationCallbacks: NavigationCallbacks,
    onThemeChange: (String) -> Unit,
    productStoreViewModel: ProductStoreViewModel,
) {
    val uiState by productStoreViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        ThemeSelector {
            onThemeChange(it.name)
        }
        Spacer(modifier = Modifier.height(12.dp))
        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = productStoreViewModel::onSearchQueryChanged
        )
        Spacer(modifier = Modifier.height(12.dp))
        ProductCategoryButtons(
            categories = uiState.categories,
            currentFilter = uiState.selectedCategory,
            onFilterChange = productStoreViewModel::onCategorySelected
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn {
            items(
                items = uiState.filteredProducts,
                key = { it.id }
            ) { product ->
                ProductCard(
                    product = product,
                    onViewDetail = { product ->
                        navigationCallbacks.navigateToDetail(product.id)
                    },
                    isFavorite = productStoreViewModel.isFavorite(product.id),
                    onFavoriteToggle = productStoreViewModel::toggleFavorite
                )
            }
        }
    }
}