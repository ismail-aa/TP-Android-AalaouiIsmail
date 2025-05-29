package com.example.emtyapp.ui.product.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.emtyapp.R
import com.example.emtyapp.data.entities.Product

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = when (product.imageRes.lowercase()) {
                    "tablet" -> R.drawable.tablet
                    "iphone" -> R.drawable.iphone
                    "tv" -> R.drawable.tv
                    "laptop" -> R.drawable.laptop
                    "headphone" -> R.drawable.headphone
                    else -> R.drawable.ic_launcher_background
                }),
                contentDescription = product.name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = product.price,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (product.quantity > 0) "In stock: ${product.quantity}" else "Out of stock",
                    color = if (product.quantity > 0) Color.Unspecified else Color.Red
                )
            }
        }
    }
}