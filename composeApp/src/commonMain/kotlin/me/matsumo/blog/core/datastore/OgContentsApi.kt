package me.matsumo.blog.core.datastore

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import me.matsumo.blog.shared.entity.OgContentsEntity

interface OgContentsApi {

    @GET("og_contents")
    suspend fun getOgContents(
        @Query("url") url: String,
    ): OgContentsEntity
}
