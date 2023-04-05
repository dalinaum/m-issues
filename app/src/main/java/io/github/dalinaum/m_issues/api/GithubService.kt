package io.github.dalinaum.m_issues.api

import io.github.dalinaum.m_issues.data.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubService {
    @Headers(
        "Accept: application/vnd.github+json",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") searchQuery: String,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
        @Query("per_page") perPage: Int? = null,
        @Query("page") page: Int? = null
    ): Response
}