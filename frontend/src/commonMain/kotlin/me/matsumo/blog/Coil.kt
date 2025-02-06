package me.matsumo.blog

import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.crossfade
import coil3.svg.SvgDecoder

fun setupCoil() {
    SingletonImageLoader.setSafe {
        ImageLoader.Builder(it)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(it, 0.25)
                    .build()
            }
            .components {
                add(SvgDecoder.Factory())
            }
            .crossfade(true)
            .build()
    }
}
