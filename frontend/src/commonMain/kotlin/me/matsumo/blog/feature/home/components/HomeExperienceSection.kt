package me.matsumo.blog.feature.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.home_experience_bone
import matsumo_me_kmp.frontend.generated.resources.home_experience_bone_description
import matsumo_me_kmp.frontend.generated.resources.home_experience_cookpad
import matsumo_me_kmp.frontend.generated.resources.home_experience_cookpad_description
import matsumo_me_kmp.frontend.generated.resources.home_experience_keio
import matsumo_me_kmp.frontend.generated.resources.home_experience_keio_description
import matsumo_me_kmp.frontend.generated.resources.home_experience_ly
import matsumo_me_kmp.frontend.generated.resources.home_experience_ly_description
import matsumo_me_kmp.frontend.generated.resources.home_experience_yumemi
import matsumo_me_kmp.frontend.generated.resources.home_experience_yumemi_description
import matsumo_me_kmp.frontend.generated.resources.im_blog
import matsumo_me_kmp.frontend.generated.resources.im_experience_cookpad
import matsumo_me_kmp.frontend.generated.resources.im_experience_ly
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.theme.LocalDevice
import me.matsumo.blog.core.theme.bold
import me.matsumo.blog.core.theme.end
import me.matsumo.blog.core.theme.openUrl
import me.matsumo.blog.core.ui.CodeTitle
import me.matsumo.blog.core.ui.utils.enterAnimation
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

fun LazyListScope.homeExperienceSection(
    horizontalPadding: Dp
) {
    item {
        CodeTitle(
            modifier = Modifier
                .padding(top = horizontalPadding)
                .padding(horizontal = horizontalPadding)
                .fillMaxWidth()
                .enterAnimation(),
            text = "Experience",
        )
    }

    item {
        Spacer(modifier = Modifier.height(24.dp))
    }

    itemsIndexed(
        items = Experience.entries,
        key = { _, experience -> experience.title.key },
    ) { index, experience ->
        val isMobile = LocalDevice.current == Device.MOBILE

        ExperienceItem(
            modifier = Modifier
                .padding(horizontal = horizontalPadding)
                .fillMaxWidth(),
            experience = experience,
            isMobile = isMobile,
            isOdd = index % 2 == 1,
        )
    }
}

@Composable
private fun ExperienceItem(
    experience: Experience,
    isMobile: Boolean,
    isOdd: Boolean,
    modifier: Modifier = Modifier,
) {
    val topPadding = if (isMobile) 40.dp else 56.dp
    var direction = LocalLayoutDirection.current

    if (isOdd && !isMobile) {
        direction = if (direction == LayoutDirection.Ltr) LayoutDirection.Rtl else LayoutDirection.Ltr
    }

    CompositionLocalProvider(LocalLayoutDirection provides direction) {
        Row(
            modifier = modifier.height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            if (!isMobile) {
                Text(
                    modifier = Modifier
                        .padding(top = topPadding)
                        .weight(1f)
                        .enterAnimation(delayMillis = 200),
                    text = experience.period,
                    style = MaterialTheme.typography.titleLarge.bold().end(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            ExperienceDivider(
                modifier = Modifier.fillMaxHeight(),
                topPadding = topPadding,
            )

            Column(
                modifier = Modifier
                    .padding(top = topPadding)
                    .weight(1f)
                    .enterAnimation(delayMillis = 200),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(experience.title),
                    style = MaterialTheme.typography.titleLarge.bold(),
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = experience.date,
                    style = MaterialTheme.typography.bodyLarge.bold(),
                    color = MaterialTheme.colorScheme.primary,
                )

                Text(
                    modifier = Modifier.widthIn(max = 512.dp),
                    text = stringResource(experience.description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Row(
                    modifier = Modifier.heightIn(max = 128.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    if (experience.image != null) {
                        Image(
                            modifier = Modifier
                                .weight(1f, false)
                                .aspectRatio(16 / 9f)
                                .clip(RoundedCornerShape(8.dp)),
                            painter = painterResource(experience.image),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                        )
                    }

                    if (experience.ogLink != null && experience.ogImage != null) {
                        AsyncImage(
                            modifier = Modifier
                                .weight(1f, false)
                                .aspectRatio(16 / 9f)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { openUrl(experience.ogLink) },
                            model = "https://corsproxy.io/?url=${experience.ogImage}",
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExperienceDivider(
    topPadding: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
        )

        Box(
            modifier = Modifier
                .padding(top = topPadding + 10.dp)
                .size(16.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiary),
            )
        }
    }
}

@Immutable
private enum class Experience(
    val title: StringResource,
    val description: StringResource,
    val period: String,
    val date: String,
    val image: DrawableResource? = null,
    val ogImage: String? = null,
    val ogLink: String? = null,
) {
    Born(
        title = Res.string.home_experience_bone,
        description = Res.string.home_experience_bone_description,
        period = "2002",
        date = "SEP 2002"
    ),
    Keio(
        title = Res.string.home_experience_keio,
        description = Res.string.home_experience_keio_description,
        period = "2021",
        date = "APR 2021 - PRESENT"
    ),
    Cookpad(
        title = Res.string.home_experience_cookpad,
        description = Res.string.home_experience_cookpad_description,
        period = "2022",
        date = "APR 2022 - PRESENT",
        image = Res.drawable.im_experience_cookpad,
        ogImage = "https://cookpad.careers/ogp.png",
        ogLink = "https://cookpad.careers/"
    ),
    Ly(
        title = Res.string.home_experience_ly,
        description = Res.string.home_experience_ly_description,
        period = "2023",
        date = "AUG 2023 & SEP 2024",
        image = Res.drawable.im_experience_ly,
        ogImage = "https://www.lycorp.co.jp/assets/images/ogp_ly_jp_bk_1200_628.png",
        ogLink = "https://www.lycorp.co.jp/ja/"
    ),
    Yumemi(
        title = Res.string.home_experience_yumemi,
        description = Res.string.home_experience_yumemi_description,
        period = "2024",
        date = "AUG 2024",
        ogImage = "https://www.yumemi.co.jp/ogp.png",
        ogLink = "https://www.yumemi.co.jp/",
    ),
}
