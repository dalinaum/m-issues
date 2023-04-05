package io.github.dalinaum.m_issues.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.github.dalinaum.m_issues.ui.theme.MIssuesTheme

@Composable
fun MainScreen(
    navController: NavController,
) {
    val (query, setQuery) = remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Github Repositories")
            })
        }, modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues.calculateBottomPadding())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Search Github Repositories!!!",
                    color = Color(0xffff9944),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Cursive,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.size(32.dp))

                TextField(
                    value = query, onValueChange = setQuery, modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        navController.navigate("Result/$query")
                    }, enabled = query.isNotEmpty(), modifier = Modifier.fillMaxWidth()
                ) {
                    Text("검색")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MIssuesTheme {
        MainScreen(rememberNavController())
    }
}