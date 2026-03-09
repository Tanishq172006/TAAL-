package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding


@Composable
fun ProfileIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(32.dp)

    ) {

        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = "Profile",
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )
    }
}
//@Preview
//@Composable
//fun ProfileIconPreview() {
//    Box(modifier = Modifier.padding(20.dp).background(Color.Black)) {
//        ProfileIcon(onClick = {})
//    }
//}