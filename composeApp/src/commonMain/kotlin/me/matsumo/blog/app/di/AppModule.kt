package me.matsumo.blog.app.di

import me.matsumo.blog.app.MMViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MMViewModel)
}
