package me.matsumo.blog

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

lateinit var blogApplicationContext: Context
    private set

class BlogApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())

        initKoin()
        setupCoil()
    }
}

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
