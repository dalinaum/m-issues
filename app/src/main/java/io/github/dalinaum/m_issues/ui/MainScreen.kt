package io.github.dalinaum.m_issues.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.dalinaum.m_issues.viewModel.GithubViewModel

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: GithubViewModel = hiltViewModel(),
) {
    Column {
        Text("Hello")
        Button(onClick = {
            navController.navigate("Result")
        }) {
            Text("이동")
        }
    }
}