package me.matsumo.blog

import android.content.Context
import androidx.startup.Initializer

lateinit var blogApplicationContext: Context
    private set

internal object BlogContext

internal class BlogInitializer : Initializer<BlogContext> {

    override fun create(context: Context): BlogContext {
        blogApplicationContext = context
        return BlogContext
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
