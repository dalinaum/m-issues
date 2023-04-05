package io.github.dalinaum.m_issues.dataSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.dalinaum.m_issues.api.GithubService
import io.github.dalinaum.m_issues.data.Item

class GithubDataSource(
    private val githubService: GithubService,
    private val query: String,
    private val pageSize: Int
) : PagingSource<Int, Item>() {
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