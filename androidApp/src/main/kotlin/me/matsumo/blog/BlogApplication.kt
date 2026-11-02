package me.matsumo.blog

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class BlogApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())

        initKoin()
        setupCoil()
    }
}
