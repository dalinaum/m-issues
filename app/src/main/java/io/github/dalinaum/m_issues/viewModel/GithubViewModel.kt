package io.github.dalinaum.m_issues.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.dalinaum.m_issues.api.GithubService
import io.github.dalinaum.m_issues.data.Item
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class GithubViewModel @Inject constructor(
    private val githubService: GithubService
) : ViewModel() {
    val searchedRepositories = getRepositoriesFlow("D").cachedIn(viewModelScope)

    private val pageSize = 30

    private fun getRepositoriesFlow(query: String): Flow<PagingData<Item>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = true,
            prefetchDistance = 10
        ),
        initialKey = 1
    ) {
        object : PagingSource<Int, Item>() {
            override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
                }
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
                return try {
                    val response = githubService.searchRepositories(
                        searchQuery = query,
                        page = params.key,
                        perPage = pageSize
                    )

                    LoadResult.Page(
                        data = response.items,
                        prevKey = if (params.key == 1) null else params.key?.minus(1),
                        nextKey = if (response.items.isEmpty()) null else params.key?.plus(1)
                    )
                } catch (e: Exception) {
                    Log.e("EEE", "error: $e")
                    e.printStackTrace()
                    LoadResult.Error(e)
                }
            }
        }
    }.flow
}