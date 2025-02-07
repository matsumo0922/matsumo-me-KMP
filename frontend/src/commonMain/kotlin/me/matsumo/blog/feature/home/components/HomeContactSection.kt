package me.matsumo.blog.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import matsumo_me_kmp.frontend.generated.resources.Res
import matsumo_me_kmp.frontend.generated.resources.home_contact_email
import matsumo_me_kmp.frontend.generated.resources.home_contact_message
import matsumo_me_kmp.frontend.generated.resources.home_contact_name
import matsumo_me_kmp.frontend.generated.resources.home_contact_require_email
import matsumo_me_kmp.frontend.generated.resources.home_contact_require_message
import matsumo_me_kmp.frontend.generated.resources.home_contact_require_name
import matsumo_me_kmp.frontend.generated.resources.home_contact_send
import me.matsumo.blog.core.domain.Device
import me.matsumo.blog.core.theme.LocalDevice
import me.matsumo.blog.core.theme.mailTo
import me.matsumo.blog.core.ui.CodeTitle
import me.matsumo.blog.core.ui.utils.enterAnimation
import me.matsumo.blog.core.ui.utils.moveFocusOnTab
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HomeContactSection(
    modifier: Modifier = Modifier,
) {
    val padding = if (LocalDevice.current == Device.MOBILE) 24.dp else 40.dp

    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var addressError by remember { mutableStateOf(false) }
    var messageError by remember { mutableStateOf(false) }
    val error = remember(nameError, addressError, messageError) { nameError || addressError || messageError }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(name, address, message) {
        nameError = false
        addressError = false
        messageError = false
    }

    Column(
        modifier = modifier
            .padding(padding)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                shape = RoundedCornerShape(16.dp),
            )
            .background(MaterialTheme.colorScheme.surfaceContainer.copy(0.7f))
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CodeTitle(
            modifier = Modifier
                .fillMaxWidth()
                .enterAnimation(),
            text = "Contact",
        )

        TextField(
            modifier = Modifier
                .widthIn(max = 640.dp)
                .fillMaxWidth()
                .enterAnimation(100),
            value = name,
            onValueChange = { name = it },
            placeholder = { Text(stringResource(Res.string.home_contact_name)) },
            supportingText = { Text(stringResource(Res.string.home_contact_require_name).takeIf { nameError }.orEmpty()) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            singleLine = true,
            isError = nameError,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
            )
        )

        TextField(
            modifier = Modifier
                .widthIn(max = 640.dp)
                .fillMaxWidth()
                .enterAnimation(200),
            value = address,
            onValueChange = { address = it },
            placeholder = { Text(stringResource(Res.string.home_contact_email)) },
            supportingText = { Text(stringResource(Res.string.home_contact_require_email).takeIf { addressError }.orEmpty()) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            singleLine = true,
            isError = addressError,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
            )
        )

        TextField(
            modifier = Modifier
                .widthIn(max = 640.dp)
                .fillMaxWidth()
                .enterAnimation(300),
            value = message,
            onValueChange = { message = it },
            placeholder = { Text(stringResource(Res.string.home_contact_message)) },
            supportingText = { Text(stringResource(Res.string.home_contact_require_message).takeIf { messageError }.orEmpty()) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            isError = messageError,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
            )
        )

        Button(
            modifier = Modifier
                .moveFocusOnTab()
                .enterAnimation(400),
            onClick = {
                if (name.isEmpty()) nameError = true
                if (address.isEmpty()) addressError = true
                if (message.isEmpty()) messageError = true

                if (!error) {
                    mailTo(
                        name = name,
                        address = address,
                        message = message,
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = if (error) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary,
            ),
            enabled = !error,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = null,
            )

            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(Res.string.home_contact_send),
            )
        }
    }
}
