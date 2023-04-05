package io.github.dalinaum.m_issues

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.dalinaum.m_issues.ui.MainScreen
import io.github.dalinaum.m_issues.ui.ResultScreen
import io.github.dalinaum.m_issues.ui.theme.MIssuesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MIssuesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TopLevel()
                }
            }
        }
    }
}

@Composable
fun TopLevel(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        "Home",
        modifier = modifier
    ) {
        composable("Home") {
            MainScreen(
                navController = navHostController
            )
        }
        composable("Result/{query}") { entry ->
            val query = entry.arguments?.getString("query") ?: ""
            ResultScreen(
                query = query,
                navController = navHostController
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MIssuesTheme {
        TopLevel()
    }
}