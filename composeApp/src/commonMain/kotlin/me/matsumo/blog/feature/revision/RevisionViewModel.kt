package me.matsumo.blog.feature.revision

import androidx.lifecycle.ViewModel
import me.matsumo.blog.core.domain.BlogConfig

class RevisionViewModel(blogConfig: BlogConfig) : ViewModel() {
    val revision = blogConfig.revision
}
