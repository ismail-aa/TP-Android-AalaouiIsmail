package com.example.emtyapp.ui.profile.component

import android.R.attr.text
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.emtyapp.data.entities.User
import com.example.emtyapp.navigator.Routes
import com.example.emtyapp.ui.profile.ProfileViewModel

@Composable
fun UserInfoComponent(
    user: User,
    viewModel: ProfileViewModel,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Personal Information",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = {
                    navController.navigate(Routes.UserInfo)
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow("First Name", user.firstName)
            InfoRow("Last Name", user.lastName)
            InfoRow("Username", user.username)
            InfoRow("Email", user.email)
            InfoRow("Birth Date", user.birthDate)
            InfoRow("Address", user.address)
            InfoRow("Role", user.role)
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}