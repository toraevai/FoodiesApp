package com.example.foodies.ui.parts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodies.R
import com.example.foodies.ui.theme.FoodiesTheme
import com.example.foodies.ui.theme.Orange

@Composable
fun Counter(
    counter: Int,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        ButtonForCounter(
            onButtonClick = onRemoveClick,
            icon = painterResource(id = R.drawable.baseline_remove_24),
            iconDescription = "Remove one",
            backgroundColor = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = "$counter",
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        ButtonForCounter(
            onButtonClick = onAddClick,
            icon = painterResource(id = R.drawable.baseline_add_24),
            iconDescription = "Add one",
            backgroundColor = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun ButtonForCounter(
    onButtonClick: () -> Unit,
    icon: Painter,
    iconDescription: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onButtonClick,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (backgroundColor == MaterialTheme.colorScheme.primary) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.primary
        ),
        contentPadding = PaddingValues(all = 0.dp),
        modifier = modifier.size(40.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = iconDescription,
            tint = Orange,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview
@Composable
fun CounterPreview() {
    FoodiesTheme {
        Counter(counter = 100, onAddClick = { /*TODO*/ }, onRemoveClick = { /*TODO*/ })
    }
}

@Preview
@Composable
fun ButtonForCounterPreview() {
    FoodiesTheme {
        ButtonForCounter(
            onButtonClick = {},
            icon = painterResource(id = R.drawable.baseline_remove_24),
            iconDescription = "",
            backgroundColor = MaterialTheme.colorScheme.primary
        )
    }
}