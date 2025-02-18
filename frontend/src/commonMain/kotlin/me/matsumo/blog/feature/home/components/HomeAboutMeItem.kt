package me.matsumo.blog.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.aakira.napier.Napier
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.home_about_me_description
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.theme.LocalDevice
import me.matsumo.blog.core.ui.CodeTitle
import me.matsumo.blog.core.ui.NonLazyVerticalGrid
import me.matsumo.blog.core.ui.TagTitle
import me.matsumo.blog.core.ui.utils.enterAnimation
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HomeAboutMeItem(
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
                .enterAnimation(),
            text = "AboutMe",
        )

        NonLazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .enterAnimation(delayMillis = 300),
            columns = if (LocalDevice.current == Device.MOBILE) 1 else 2,
            horizontalSpacing = 56.dp,
            verticalSpacing = 40.dp,
        ) {
            SelectionContainer {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.home_about_me_description),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            SkillItems(
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SkillItems(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        TagTitle(
            text = "Technical Skills",
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                    shape = RoundedCornerShape(12.dp),
                )
                .background(MaterialTheme.colorScheme.background.copy(0.7f))
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            for (skill in Skill.entries) {
                SkillItem(skill)
            }
        }
    }
}

@Composable
private fun SkillItem(
    skill: Skill,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                shape = RoundedCornerShape(8.dp),
            )
            .background(MaterialTheme.colorScheme.background.copy(0.5f))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AsyncImage(
            modifier = Modifier.size(24.dp),
            model = skill.icon,
            contentDescription = skill.title,
            onError = {
                Napier.e(it.result.throwable) { "Failed to load image: ${skill.icon}" }
            },
        )

        Text(
            modifier = Modifier.padding(end = 4.dp),
            text = skill.title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

private enum class Skill(
    val title: String,
    val icon: String,
) {
    Android("Android", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/android/android-plain.svg"),
    Compose("Compose", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/jetpackcompose/jetpackcompose-original.svg"),
    Kotlin("Kotlin", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/kotlin/kotlin-original.svg"),
    Ktor("Ktor", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/ktor/ktor-original.svg"),
    Java("Java", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original.svg"),
    Gradle("Gradle", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/gradle/gradle-original.svg"),
    NextJs("Next.js", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/nextjs/nextjs-original.svg"),
    NodeJs("Node.js", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/nodejs/nodejs-original.svg"),
    HTML("HTML", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/html5/html5-original.svg"),
    Python("Python", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/python/python-original.svg"),
    React("React", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/react/react-original.svg"),
    Ruby("Ruby", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/ruby/ruby-original.svg"),
    Cplusplus("C++", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/cplusplus/cplusplus-original.svg"),
    Apple("Apple", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/apple/apple-original.svg"),
    Git("Git", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/git/git-original.svg"),
    GitHub("GitHub", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/github/github-original.svg"),
    Firebase("Firebase", "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/firebase/firebase-plain.svg"),
}
