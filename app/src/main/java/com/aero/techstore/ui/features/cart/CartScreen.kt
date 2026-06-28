package com.aero.techstore.ui.features.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aero.techstore.ui.navigation.NavigationCallbacks
import com.aero.techstore.ui.features.cart.components.CartActionButtons
import com.aero.techstore.ui.features.cart.components.CartItemCard
import com.aero.techstore.ui.features.cart.components.CartSummary
import com.aero.techstore.ui.features.cart.components.EmptyCartActionButton
import com.aero.techstore.ui.features.cart.components.EmptyCartSection

@Composable
fun CartScreen(
    navigationCallbacks: NavigationCallbacks,
    cartViewModel: CartViewModel,
    onCheckoutSuccess: () -> Unit
) {
    val uiState by cartViewModel.uiState.collectAsState()
    val cart = uiState.cart

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (cart.items.isEmpty()) {
            EmptyCartSection()

            Spacer(modifier = Modifier.height(24.dp))

//            EmptyCartActionButton(navigationCallbacks)
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(
                    items = cart.items,
                    key = { it.product.id }
                ) { cartItem ->
                    CartItemCard(
                        cartItem = cartItem,
                        onRemove = { cartViewModel.removeFromCart(cartItem.product.id) },
                        onQuantityChange = { newQuantity ->
                            cartViewModel.updateQuantity(cartItem.product.id, newQuantity)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CartSummary(
                totalPrice = cart.totalPrice,
                totalItems = cart.totalItems
            )

            Spacer(modifier = Modifier.height(16.dp))

            CartActionButtons(
                navigationCallbacks = navigationCallbacks,
                onCheckout = {
                    onCheckoutSuccess()
                    cartViewModel.clearCart()
                }
            )
        }
    }
}





