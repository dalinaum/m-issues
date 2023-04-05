package io.github.dalinaum.m_issues.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.dalinaum.m_issues.api.GithubService
import io.github.dalinaum.m_issues.data.Item
import io.github.dalinaum.m_issues.dataSource.GithubDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


@HiltViewModel
class GithubViewModel @Inject constructor(
    private val githubService: GithubService
) : ViewModel() {
    private val currentQuery = MutableStateFlow("D")
    private val pageSize = 30

    private fun getRepositoriesFlow(query: String): Flow<PagingData<Item>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = true,
            prefetchDistance = 10
        ),
        initialKey = 1
    ) {
        GithubDataSource(githubService, query, pageSize)
    }.flow

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchedRepositories: Flow<PagingData<Item>> = currentQuery.flatMapLatest {
        getRepositoriesFlow(it).cachedIn(viewModelScope)
    }

    fun setQuery(query: String) {
        currentQuery.value = query
    }
}