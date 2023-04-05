package io.github.dalinaum.m_issues.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.github.dalinaum.m_issues.viewModel.GithubViewModel

@Composable
fun ResultScreen(
    query: String,
    navController: NavController,
    viewModel: GithubViewModel = hiltViewModel(),
) {
    viewModel.setQuery(query)
    val lazyPagingItems = viewModel.searchedRepositories.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "뒤로 가기"
                        )
                    }
                },
                title = {
                    Text(text = "Result: $query")
                })
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues.calculateBottomPadding())
        ) {
            LazyColumn {
                if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
                    item {
                        Text(
                            text = "Waiting for items to load from the backend",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
                items(lazyPagingItems) { item ->
                    if (item != null) {
                        Text(item.name)
                    } else {
                        Text("가져오는 중입니다.")
                    }
                }
                if (lazyPagingItems.loadState.append == LoadState.Loading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}