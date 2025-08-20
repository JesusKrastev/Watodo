package com.jesuskrastev.watodo.ui.features.webs

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jesuskrastev.watodo.ui.features.components.TopBar
import com.jesuskrastev.watodo.ui.features.webs.components.Web
import com.jesuskrastev.watodo.ui.features.webs.components.WebShimmer

@Composable
fun WebsListShimmer(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(20) {
            WebShimmer()
        }
    }
}

@Composable
fun WebsList(
    modifier: Modifier = Modifier,
    expandedWebs: List<String>,
    webs: List<WebState>,
    onExpand: (WebState) -> Unit,
    onOpenUrl: (WebState) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(webs) { web ->
            Web(
                onClick = { onExpand(web) },
                onOpenUrl = { onOpenUrl(web) },
                expanded = expandedWebs.contains(web.id),
                webState = web,
            )
        }
    }
}

@Composable
fun WebsContent(
    modifier: Modifier = Modifier,
    state: WebsState,
    onEvent: (WebsEvent) -> Unit,
) {

    val context: Context = LocalContext.current

    when {
        state.webs.isEmpty() -> {
            WebsListShimmer(
                modifier = modifier,
            )
        }
        else -> {
            WebsList(
                modifier = modifier,
                expandedWebs = state.expandedWebs,
                webs = state.webs,
                onExpand = { onEvent(WebsEvent.OnExpandWeb(it)) },
                onOpenUrl = { web ->
                    onEvent(
                        WebsEvent.OnOpenWeb(
                            web = web,
                            context = context,
                        )
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebsTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
) {
    TopBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = "Webs",
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebsScreen(
    modifier: Modifier = Modifier,
    state: WebsState,
    onEvent: (WebsEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            WebsTopAppBar()
        },
    ) { paddingValues ->
        WebsContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEvent = onEvent,
        )
    }
}