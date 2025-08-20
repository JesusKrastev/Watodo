package com.jesuskrastev.watodo.ui.features.webs.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesuskrastev.watodo.ui.composables.shimmerEffect
import com.jesuskrastev.watodo.ui.features.webs.WebState

@Composable
fun WebTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        modifier = modifier,
        fontSize = 18.sp,
        text = title,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}

@Composable
fun WebDescription(
    modifier: Modifier = Modifier,
    description: String,
) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge,
        text = description,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
fun WebShimmer(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .shimmerEffect(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                fontSize = 18.sp,
                text = "Example",
                color = Color.Transparent,
            )
        }
    }
}

@Composable
fun Web(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onOpenUrl: () -> Unit,
    expanded: Boolean,
    webState: WebState,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                WebTitle(
                    title = webState.title,
                )
                Icon(
                    modifier = Modifier.clickable(onClick = onOpenUrl),
                    imageVector = Icons.Filled.OpenInNew,
                    contentDescription = "Open url",
                )
            }
            if (expanded)
                WebDescription(
                    description = webState.description,
                )
        }
    }
}