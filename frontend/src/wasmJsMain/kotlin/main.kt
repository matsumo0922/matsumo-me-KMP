import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.window.ComposeViewport
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.asDeferred
import kotlinx.coroutines.await
import me.matsumo.blog.app.BlogApp
import me.matsumo.blog.initKoin
import me.matsumo.blog.setupCoil
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.koin.compose.KoinContext
import org.w3c.fetch.Response
import kotlin.wasm.unsafe.UnsafeWasmMemoryApi
import kotlin.wasm.unsafe.withScopedMemoryAllocator

private val fonts = listOf(
    "NotoSansJP-Black",
    "NotoSansJP-Bold",
    "NotoSansJP-ExtraBold",
    "NotoSansJP-ExtraLight",
    "NotoSansJP-Light",
    "NotoSansJP-Medium",
    "NotoSansJP-Regular",
    "NotoSansJP-SemiBold",
    "NotoSansJP-Thin",
)

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    Napier.base(DebugAntilog())

    initKoin()
    setupCoil()

    ComposeViewport(document.body!!) {
        KoinContext {
            val fontFamilyResolver = LocalFontFamilyResolver.current
            var fontFamily by remember { mutableStateOf<FontFamily?>(null) }
            var fontsLoaded by remember { mutableStateOf(false) }

            LaunchedEffect(true) {
                runCatching {
                    val fontsMap = fonts.map { it to loadRes("./composeResources/matsumo_me_kmp.frontend.generated.resources/font/$it.ttf") }
                    fontFamily = FontFamily(fontsMap.map { Font(it.first, it.second.toByteArray()) })
                    fontFamilyResolver.preload(fontFamily!!)
                }.onFailure {
                    Napier.e(it) { "Failed to load fonts" }
                }

                fontsLoaded = true
            }

            AnimatedContent(
                targetState = fontsLoaded,
                transitionSpec = { fadeIn().togetherWith(fadeOut()) },
            ) {
                if (it) {
                    BlogApp(
                        modifier = Modifier.fillMaxSize(),
                        fontFamily = fontFamily,
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Cyan),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

private suspend fun loadResAsync(url: String): Deferred<ArrayBuffer> {
    return window.fetch(url).await<Response>().arrayBuffer().asDeferred()
}

suspend fun loadRes(url: String): ArrayBuffer {
    return loadResAsync(url).await()
}

fun ArrayBuffer.toByteArray(): ByteArray {
    val source = Int8Array(this, 0, byteLength)
    return jsInt8ArrayToKotlinByteArray(source)
}

private fun wasmExportsMemoryBuffer(): ArrayBuffer = js("wasmExports.memory.buffer")
private fun jsExportInt8ArrayToWasm(destination: ArrayBuffer, src: Int8Array, size: Int, dstAddr: Int) {
    val mem8 = Int8Array(destination, dstAddr, size)
    mem8.set(src)
}

internal fun jsInt8ArrayToKotlinByteArray(x: Int8Array): ByteArray {
    val size = x.length

    @OptIn(UnsafeWasmMemoryApi::class)
    return withScopedMemoryAllocator { allocator ->
        val memBuffer = allocator.allocate(size)
        val dstAddress = memBuffer.address.toInt()
        jsExportInt8ArrayToWasm(wasmExportsMemoryBuffer(), x, size, dstAddress)
        ByteArray(size) { i -> (memBuffer + i).loadByte() }
    }
}
