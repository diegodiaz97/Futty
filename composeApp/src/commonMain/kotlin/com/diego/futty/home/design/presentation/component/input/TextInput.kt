package com.diego.futty.home.design.presentation.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.diego.futty.core.presentation.theme.colorGrey100
import com.diego.futty.core.presentation.theme.colorGrey400
import com.diego.futty.core.presentation.theme.colorGrey900
import compose.icons.TablerIcons
import compose.icons.tablericons.Eye
import compose.icons.tablericons.EyeOff
import compose.icons.tablericons.X

sealed interface TextInput {
    @Composable
    fun Draw()

    class Input(
        val modifier: Modifier = Modifier.fillMaxWidth(),
        val input: String,
        val label: String? = null,
        val maxLength: Int? = null,
        val placeholder: String = "",
        val onTextChangeAction: (String) -> Unit,
        val onFocusChanged: () -> Unit,
    ) : TextInput {
        @Composable
        override fun Draw() {
            val focusRequester = remember { FocusRequester() }
            val isFocused = remember { mutableStateOf(false) }

            Column(modifier = modifier) {
                if (label != null) {
                    Text(
                        text = label,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        textAlign = TextAlign.Start,
                        style = typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = colorGrey900()
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .clip(RoundedCornerShape(12.dp))
                        /* .border(
                             width = 1.dp,
                             color = colorGrey300(),
                             shape = RoundedCornerShape(12.dp)
                         )*/
                        .background(colorGrey100())
                        .focusRequester(focusRequester)
                        .onFocusChanged { isFocused.value = it.hasFocus },
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (input.isEmpty() && !isFocused.value) {
                        Text(
                            modifier = Modifier.padding(bottom = 4.dp, start = 16.dp),
                            text = placeholder,
                            style = typography.titleMedium,
                            fontWeight = FontWeight.Normal,
                            color = colorGrey400()
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 10.dp)
                            .padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = input,
                            onValueChange = { onTextChangeAction(it) },
                            singleLine = true,
                            textStyle = TextStyle(
                                color = colorGrey900(),
                                fontSize = typography.titleMedium.fontSize,
                                fontWeight = FontWeight.Normal
                            ),
                            cursorBrush = SolidColor(colorGrey400()),
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(focusRequester)
                                .onFocusChanged { isFocused.value = it.hasFocus }
                        )

                        if (input.isNotEmpty()) {
                            IconButton(onClick = { onTextChangeAction("") }) {
                                Icon(
                                    imageVector = TablerIcons.X,
                                    tint = colorGrey400(),
                                    contentDescription = "Borrar texto"
                                )
                            }
                        }
                    }
                }

                if (maxLength != null) {
                    Text(
                        text = "${input.length}/$maxLength",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        textAlign = TextAlign.End,
                        style = typography.titleSmall,
                        fontWeight = FontWeight.Normal,
                        color = colorGrey900()
                    )
                }
            }
        }
    }

    class FullScreenInput(
        val modifier: Modifier = Modifier.fillMaxWidth(),
        val input: String,
        val placeholder: String = "",
        val onTextChangeAction: (String) -> Unit,
        val onFocusChanged: () -> Unit,
    ) : TextInput {
        @Composable
        override fun Draw() {
            val focusRequester = remember { FocusRequester() }
            val isFocused = remember { mutableStateOf(true) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { isFocused.value = it.hasFocus },
                contentAlignment = Alignment.CenterStart
            ) {
                if (input.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = typography.titleLarge,
                        fontWeight = FontWeight.Normal,
                        color = colorGrey400()
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    BasicTextField(
                        value = input,
                        onValueChange = { onTextChangeAction(it) },
                        textStyle = TextStyle(
                            color = colorGrey900(),
                            fontSize = typography.titleLarge.fontSize,
                            fontWeight = FontWeight.Normal
                        ),
                        cursorBrush = SolidColor(colorGrey400()),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                            .focusRequester(focusRequester)
                            .onFocusChanged { isFocused.value = it.hasFocus }
                    )

                    if (input.isNotEmpty()) {
                        Icon(
                            modifier = Modifier.clickable { onTextChangeAction("") },
                            imageVector = TablerIcons.X,
                            tint = colorGrey400(),
                            contentDescription = "Borrar texto"
                        )
                    }
                }
            }
        }
    }

    class PasswordInput(
        val input: String,
        val onTextChangeAction: (String) -> Unit,
        val placeholder: String = "",
        val onFocusChanged: () -> Unit,
    ) : TextInput {
        @Composable
        override fun Draw() {
            val maxLength = 16
            val (visible, setVisible) = remember { mutableStateOf(false) }

            val focusRequester = remember { FocusRequester() }
            val isFocused = remember { mutableStateOf(false) }

            Column(modifier = Modifier) {
                Text(
                    text = "Contraseña",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    textAlign = TextAlign.Start,
                    style = typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = colorGrey900()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .clip(RoundedCornerShape(12.dp))
                        /*.border(
                            width = 1.dp,
                            color = colorGrey300(),
                            shape = RoundedCornerShape(12.dp)
                        )*/
                        .background(colorGrey100())
                        .focusRequester(focusRequester)
                        .onFocusChanged { isFocused.value = it.hasFocus },
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (input.isEmpty() && !isFocused.value) {
                        Text(
                            modifier = Modifier.padding(bottom = 4.dp, start = 16.dp),
                            text = placeholder,
                            style = typography.titleMedium,
                            fontWeight = FontWeight.Normal,
                            color = colorGrey400()
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 10.dp)
                            .padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = input,
                            onValueChange = {
                                if (it.length <= maxLength) {
                                    onTextChangeAction(it)
                                }
                            },
                            singleLine = true,
                            textStyle = TextStyle(
                                color = colorGrey900(),
                                fontSize = typography.titleMedium.fontSize,
                                fontWeight = FontWeight.Normal
                            ),
                            cursorBrush = SolidColor(colorGrey400()),
                            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(focusRequester)
                                .onFocusChanged { isFocused.value = it.hasFocus }
                        )

                        if (input.isNotEmpty()) {
                            IconButton(onClick = { setVisible(visible.not()) }) {
                                Icon(
                                    imageVector = if (visible) TablerIcons.Eye else TablerIcons.EyeOff,
                                    tint = colorGrey400(),
                                    contentDescription = "password visibility"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
