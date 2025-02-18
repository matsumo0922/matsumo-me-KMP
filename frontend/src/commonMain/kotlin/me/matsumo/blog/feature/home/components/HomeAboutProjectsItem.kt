package me.matsumo.blog.feature.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.home_about_project_blog
import matsumo_me_kmp.frontend.generated.resources.home_about_project_blog_description
import matsumo_me_kmp.frontend.generated.resources.home_about_project_fanbox
import matsumo_me_kmp.frontend.generated.resources.home_about_project_fanbox_description
import matsumo_me_kmp.frontend.generated.resources.home_about_project_kanade
import matsumo_me_kmp.frontend.generated.resources.home_about_project_kanade_description
import matsumo_me_kmp.frontend.generated.resources.home_about_project_see_more
import matsumo_me_kmp.frontend.generated.resources.home_about_project_translator
import matsumo_me_kmp.frontend.generated.resources.home_about_project_translator_description
import matsumo_me_kmp.frontend.generated.resources.im_blog
import matsumo_me_kmp.frontend.generated.resources.im_fanbox
import matsumo_me_kmp.frontend.generated.resources.im_kanade
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.theme.LocalDevice
import me.matsumo.blog.core.theme.bold
import me.matsumo.blog.core.theme.openUrl
import me.matsumo.blog.core.ui.CodeTitle
import me.matsumo.blog.core.ui.NonLazyVerticalGrid
import me.matsumo.blog.core.ui.utils.clickableWithPointer
import me.matsumo.blog.core.ui.utils.enterAnimation
import me.matsumo.blog.core.ui.utils.focusScale
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HomeAboutProjectsItem(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CodeTitle(
            modifier = Modifier
                .fillMaxWidth()
                .enterAnimation(delayMillis = 600),
            text = "Projects",
        )

        NonLazyVerticalGrid(
            modifier = Modifier
                .widthIn(max = 1024.dp)
                .enterAnimation(delayMillis = 800),
            columns = if (LocalDevice.current == Device.MOBILE) 1 else 2,
            horizontalSpacing = 40.dp,
            verticalSpacing = 40.dp,
        ) {
            for (project in Project.entries) {
                SelectionContainer {
                    ProjectItem(
                        modifier = Modifier.focusScale(),
                        project = project,
                        onLinkClicked = { url -> openUrl(url) },
                    )
                }
            }
        }

        SeeMoreProjectsButton(
            modifier = Modifier
                .padding(top = 16.dp)
                .enterAnimation(delayMillis = 1100),
            onClick = { openUrl("https://github.com/matsumo0922?tab=repositories") },
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProjectItem(
    project: Project,
    onLinkClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                shape = RoundedCornerShape(12.dp),
            )
            .background(MaterialTheme.colorScheme.background.copy(0.7f))
            .clickableWithPointer(
                interactionSource = null,
                indication = null,
                onClick = { (project.url ?: project.github)?.let(onLinkClicked) },
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (project.image != null) {
            Image(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(),
                painter = painterResource(project.image),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        } else {
            Box(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(80.dp),
                    imageVector = Icons.Default.Terminal,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null,
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(project.title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )

            if (project.url != null) {
                IconButton(
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                    onClick = { onLinkClicked.invoke(project.url) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Link,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null,
                    )
                }
            }

            if (project.github != null) {
                IconButton(
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                    onClick = { onLinkClicked.invoke(project.github) }
                ) {
                    AsyncImage(
                        modifier = Modifier.size(24.dp),
                        model = "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/github/github-original.svg",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                        contentDescription = "GitHub",
                    )
                }
            }
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(project.description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        FlowRow(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            for (tag in project.tags) {
                Text(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                            shape = CircleShape,
                        )
                        .background(MaterialTheme.colorScheme.background.copy(0.5f))
                        .padding(12.dp, 8.dp),
                    text = tag,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun SeeMoreProjectsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AsyncImage(
                modifier = Modifier.size(24.dp),
                model = "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/github/github-original.svg",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                contentDescription = null,
            )

            Text(
                text = stringResource(Res.string.home_about_project_see_more),
                style = MaterialTheme.typography.bodyLarge.bold(),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Immutable
private enum class Project(
    val title: StringResource,
    val description: StringResource,
    val image: DrawableResource?,
    val url: String?,
    val github: String?,
    val tags: List<String>,
) {
    Fanbox(
        title = Res.string.home_about_project_fanbox,
        description = Res.string.home_about_project_fanbox_description,
        image = Res.drawable.im_fanbox,
        url = "https://play.google.com/store/apps/details?id=caios.android.fanbox",
        github = "https://github.com/matsumo0922/PixiView-KMP",
        tags = listOf("Kotlin", "Swift", "Jetpack Compose", "Ktor", "Compose Multiplatform"),
    ),
    Kanade(
        title = Res.string.home_about_project_kanade,
        description = Res.string.home_about_project_kanade_description,
        image = Res.drawable.im_kanade,
        url = "https://play.google.com/store/apps/details?id=caios.android.kanade",
        github = "https://github.com/matsumo0922/Kanade",
        tags = listOf("Kotlin", "Jetpack Compose", "Ktor", "Material3", "Room", "Media3"),
    ),
    Blog(
        title = Res.string.home_about_project_blog,
        description = Res.string.home_about_project_blog_description,
        image = Res.drawable.im_blog,
        url = "https://matsumo.me",
        github = "https://github.com/matsumo0922/matsumo-me-KMP",
        tags = listOf("Kotlin", "Compose Multiplatform", "Ktor", "Wasm"),
    ),
    Translator(
        title = Res.string.home_about_project_translator,
        description = Res.string.home_about_project_translator_description,
        image = null,
        url = null,
        github = "https://github.com/matsumo0922/android-string-resource-translator",
        tags = listOf("Kotlin", "Ktor", "Open AI", "ChatGPT"),
    ),
}
