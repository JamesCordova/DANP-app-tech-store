package com.aero.techstore.ui.navigation

enum class NavScreens(val label: String, val route: String) {
    HOME("Modular Store", "home"),
    FAVORITES("Favoritos", "favorites"),
    CART("Carrito", "cart"),
    ACCOUNT("Mi Cuenta", "account"),
    ADD_CATEGORY("Nueva Categoría", "add_category"),
    ADD_PRODUCT("Vender Producto", "add_product"),
    MANAGE_PRODUCTS("Gestionar Productos", "manage_products"),
    EDIT_PRODUCT("Editar Producto", "edit_product/{productId}"),
    MANAGE_CATEGORIES("Gestionar Categorías", "manage_categories"),
    EDIT_CATEGORY("Editar Categoría", "edit_category/{categoryId}")
}

data class NavigationCallbacks(
    val navigateToDetail: (Int) -> Unit,
    val navigateBack: () -> Unit,
    val navigateToHome: () -> Unit,
    val navigateToFavorites: () -> Unit,
    val navigateToCart: () -> Unit,
    val navigateToAccount: () -> Unit,
    val navigateToAddCategory: () -> Unit,
    val navigateToAddProduct: () -> Unit,
    val navigateToManageProducts: () -> Unit,
    val navigateToEditProduct: (Int) -> Unit,
    val navigateToManageCategories: () -> Unit,
    val navigateToEditCategory: (Int) -> Unit
)
