package io.github.dalinaum.m_issues.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.dalinaum.m_issues.viewModel.GithubViewModel

@Composable
fun ResultScreen(
    navController: NavController,
    viewModel: GithubViewModel = hiltViewModel(),
) {
    Text("Result")
}