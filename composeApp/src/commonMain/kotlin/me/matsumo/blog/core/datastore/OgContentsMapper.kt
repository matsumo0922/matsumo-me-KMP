package me.matsumo.blog.core.datastore

import me.matsumo.blog.shared.entity.OgContentsEntity
import me.matsumo.blog.shared.model.OgContents

class OgContentsMapper {
    fun map(entity: OgContentsEntity): OgContents {
        return OgContents(
            title = entity.title,
            description = entity.description,
            imageUrl = entity.imageUrl,
            iconUrl = entity.iconUrl,
            siteName = entity.siteName,
        )
    }
}
