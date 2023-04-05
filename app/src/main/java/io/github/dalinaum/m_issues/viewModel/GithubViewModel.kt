package io.github.dalinaum.m_issues.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.dalinaum.m_issues.api.GithubService
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GithubViewModel @Inject constructor(
    private val githubService: GithubService
) : ViewModel() {
    fun searchRepositories() {
        viewModelScope.launch {
            val result = githubService.searchRepositories("dalinaum")
            Log.d("!!!", result.toString())
        }
    }
}