package io.github.dalinaum.m_issues.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import io.github.dalinaum.m_issues.data.Item
import io.github.dalinaum.m_issues.data.Owner
import io.github.dalinaum.m_issues.ui.theme.MIssuesTheme
import io.github.dalinaum.m_issues.viewModel.GithubViewModel
import java.util.*

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
                items(lazyPagingItems) {
                    it?.let { item ->
                        RepositoryRow(item)
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

@Composable
fun RepositoryRow(item: Item) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Card(
            elevation = 16.dp,
            modifier = Modifier.padding(24.dp, 8.dp)
        ) {
            val placeHolderColor = Color(0x330000ff)
            Column(modifier = Modifier.padding(8.dp)) {
                Row {
                    Column(
                        modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp)
                    ) {
                        if (item.owner.avatarUrl.isNotEmpty()) {
                            AsyncImage(
                                model = item.owner.avatarUrl,
                                contentScale = ContentScale.Crop,
                                placeholder = ColorPainter(color = placeHolderColor),
                                contentDescription = item.owner.login,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(40.dp)
                            )
                        }

                        Text(
                            text = item.owner.login,
                            fontSize = 8.sp
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(0.dp, 8.dp)
                    )
                    Text(
                        text = item.fullName,
                        fontSize = 8.sp,
                        modifier = Modifier.padding(0.dp, 8.dp)
                    )
                }

                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = item.htmlUrl,
                    fontSize = 8.sp
                )
                Text(
                    text = item.homepage ?: "",
                    fontSize = 8.sp
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = item.description ?: "",
                    fontSize = 8.sp
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    MIssuesTheme {
        RepositoryRow(
            Item(
                id = 1111,
                name = "dalinaum",
                fullName = "dalinaum/dalinaum",
                owner = Owner(
                    login = "dalinaum",
                    id = 2222,
                    avatarUrl = "https://avatars.githubusercontent.com/u/145585?v=4",
                    htmlURl = ""
                ),
                htmlUrl = "https://github.com/dalinaum/dalinaum",
                description = "나이스",
                createdAt = Date(100000),
                updatedAt = Date(100000),
                homepage = "https://dalinaum.github.io"
            )
        )
    }
}