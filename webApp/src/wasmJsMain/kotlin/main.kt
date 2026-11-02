import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontFamily.Resolver
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.window.ComposeViewport
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.http.Url
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.matsumo.blog.app.BlogApp
import me.matsumo.blog.core.domain.Destinations
import me.matsumo.blog.core.theme.BlogTheme
import me.matsumo.blog.core.theme.color.DarkBlueColorScheme
import me.matsumo.blog.core.ui.WasmLoadingScreen
import me.matsumo.blog.core.ui.utils.suspendRunCatching
import me.matsumo.blog.initKoin
import me.matsumo.blog.setupCoil
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.w3c.fetch.Response
import kotlin.wasm.unsafe.UnsafeWasmMemoryApi
import kotlin.wasm.unsafe.wasmMemory
import kotlin.wasm.unsafe.withScopedMemoryAllocator

private enum class NotoSansJP(
    val fileName: String,
    val weight: FontWeight,
) {
    Black("NotoSansJP-Black", FontWeight.Black),

    // ExtraBold("NotoSansJP-ExtraBold", FontWeight.ExtraBold),
    Bold("NotoSansJP-Bold", FontWeight.Bold),
    SemiBold("NotoSansJP-SemiBold", FontWeight.SemiBold),
    Medium("NotoSansJP-Medium", FontWeight.Medium),
    Regular("NotoSansJP-Regular", FontWeight.Normal),
    // Light("NotoSansJP-Light", FontWeight.Light),
    // ExtraLight("NotoSansJP-ExtraLight", FontWeight.ExtraLight),
    // Thin("NotoSansJP-Thin", FontWeight.Thin),
}

/** Web 版で使う NotoSansJP の読み込み状態 */
private sealed interface FontLoadState {
    /** 初回表示に使うフォントを読み込み中 */
    data object Loading : FontLoadState

    /** 表示できる状態。[fontFamily] が null の場合はデフォルトのフォントで表示する */
    @Immutable
    data class Loaded(val fontFamily: FontFamily?) : FontLoadState
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    Napier.base(DebugAntilog())

    removeHtmlLoading()

    initKoin()
    setupCoil()

    // フォントの差し替えで再コンポーズしても、遷移後の URL から開始画面を作り直さないよう起動時に決める
    val startDestinations = Destinations.fromUrl(
        url = Url(window.location.toString()),
    ) ?: Destinations.Home

    ComposeViewport(document.body!!) {
        val fontFamilyResolver = LocalFontFamilyResolver.current
        val fontLoadState by produceState<FontLoadState>(
            initialValue = FontLoadState.Loading,
        ) {
            fontFamilyResolver.loadNotoSansJP().collect { value = FontLoadState.Loaded(it) }
        }

        AnimatedContent(
            modifier = Modifier.background(DarkBlueColorScheme.surface),
            targetState = fontLoadState is FontLoadState.Loaded,
            transitionSpec = { fadeIn().togetherWith(fadeOut()) },
        ) {
            if (it) {
                BlogApp(
                    modifier = Modifier.fillMaxSize(),
                    fontFamily = (fontLoadState as? FontLoadState.Loaded)?.fontFamily,
                    startDestinations = startDestinations,
                )
            } else {
                BlogTheme {
                    WasmLoadingScreen(
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

/**
 * 初回表示を早めるため、Regular だけの FontFamily を先に流し、初回表示のあとで残りのウェイトをそろえた FontFamily を流す。
 * Regular を読み込めなかった場合は null だけを流す。
 */
private fun Resolver.loadNotoSansJP(): Flow<FontFamily?> = flow {
    val regularFont = loadFont(NotoSansJP.Regular)

    emit(
        value = regularFont?.let { preloadOrNull(it) },
    )

    if (regularFont != null) {
        // 1 フレーム目で初回表示を組み立て、2 フレーム目で描画済みになってから残りのウェイトを読み込む
        repeat(2) { withFrameNanos {} }
        loadAllWeights(regularFont)?.let { emit(it) }
    }
}

/**
 * [regularFont] に残りのウェイトを加えた FontFamily を返す。追加できたウェイトがない場合は null を返す。
 * 記事や画像の取得との帯域の競合を抑えるため、残りのウェイトは 1 つずつ読み込む。
 */
private suspend fun Resolver.loadAllWeights(regularFont: Font): FontFamily? {
    val otherFonts = NotoSansJP.entries
        .filter { it != NotoSansJP.Regular }
        .mapNotNull { loadFont(it) }

    if (otherFonts.isEmpty()) return null

    val fonts = listOf(regularFont) + otherFonts

    return preloadOrNull(fonts)
}

private suspend fun loadFont(font: NotoSansJP): Font? = suspendRunCatching {
    val buffer = fetchFontBuffer("./composeResources/matsumo_me_kmp.composeapp.generated.resources/font/${font.fileName}.woff2")

    Font(
        identity = font.fileName,
        data = buffer.toByteArray(),
        weight = font.weight,
    )
}
    .onFailure { Napier.e(it) { "Failed to load font: ${font.fileName}" } }
    .getOrNull()

private suspend fun Resolver.preloadOrNull(font: Font): FontFamily? = preloadOrNull(
    fonts = listOf(font),
)

private suspend fun Resolver.preloadOrNull(fonts: List<Font>): FontFamily? = suspendRunCatching {
    FontFamily(fonts).also { preload(it) }
}
    .onFailure { Napier.e(it) { "Failed to preload fonts" } }
    .getOrNull()

private fun removeHtmlLoading() {
    val htmlLoading = document.getElementById("htmlLoading")

    htmlLoading?.let { element ->
        element.setAttribute("style", "opacity: 0;")

        window.setTimeout({
            element.remove()
            null
        }, 600)
    }
}

private suspend fun fetchFontBuffer(url: String): ArrayBuffer {
    val response = window.fetch(url).await<Response>()
    val contentType = response.headers.get("Content-Type").orEmpty()

    // 配信先は存在しないパスにも index.html を 200 で返すため、Content-Type でもフォントか確かめる
    check(response.ok && contentType.startsWith("font/")) { "Failed to fetch font: $url (${response.status} $contentType)" }

    return response.arrayBuffer().await()
}

fun ArrayBuffer.toByteArray(): ByteArray {
    val source = Int8Array(this, 0, byteLength)
    return jsInt8ArrayToKotlinByteArray(source)
}

@OptIn(ExperimentalWasmJsInterop::class)
private fun wasmMemoryBuffer(): ArrayBuffer = memoryBuffer(wasmMemory)

// UnusedParameter: 引数は js() の JavaScript コードから参照する
@Suppress("UnusedParameter")
private fun memoryBuffer(memory: JsAny): ArrayBuffer = js("memory.buffer")
private fun jsExportInt8ArrayToWasm(
    destination: ArrayBuffer,
    src: Int8Array,
    size: Int,
    dstAddr: Int,
) {
    val mem8 = Int8Array(destination, dstAddr, size)
    mem8.set(src)
}

internal fun jsInt8ArrayToKotlinByteArray(x: Int8Array): ByteArray {
    val size = x.length

    @OptIn(UnsafeWasmMemoryApi::class)
    return withScopedMemoryAllocator { allocator ->
        val memBuffer = allocator.allocate(size)
        val dstAddress = memBuffer.address.toInt()
        jsExportInt8ArrayToWasm(wasmMemoryBuffer(), x, size, dstAddress)
        ByteArray(size) { i -> (memBuffer + i).loadByte() }
    }
}
