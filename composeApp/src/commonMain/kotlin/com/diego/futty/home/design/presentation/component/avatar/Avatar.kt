package com.diego.futty.home.design.presentation.component.avatar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.diego.futty.core.presentation.theme.Shimmer
import com.diego.futty.core.presentation.theme.colorGrey0
import com.diego.futty.core.presentation.theme.colorGrey900

sealed interface Avatar {
    @Composable
    fun Draw()

    class ProfileAvatar(
        val modifier: Modifier = Modifier,
        val imageUrl: String?,
        val initials: String?,
        val tint: Color? = null,
        val background: Color? = null,
        val avatarSize: AvatarSize = AvatarSize.Medium,
        val onClick: (() -> Unit)? = null,
    ) : Avatar {
        @Composable
        override fun Draw() {
            when {
                imageUrl != null -> {
                    FullImageAvatar(
                        modifier = modifier,
                        imageUrl = imageUrl,
                        avatarSize = avatarSize,
                        onClick = onClick,
                    ).Draw()
                }

                initials != null -> {
                    InitialsAvatar(
                        modifier = modifier,
                        initials = initials,
                        tint = tint,
                        background = background,
                        avatarSize = avatarSize,
                        onClick = onClick,
                    ).Draw()
                }

                else -> {
                    Shimmer(
                        modifier = modifier
                            .clip(CircleShape)
                            .size(avatarSize.size)
                    )
                }
            }
        }
    }

    class FullImageAvatar(
        val modifier: Modifier = Modifier,
        val imageUrl: String?,
        val avatarSize: AvatarSize = AvatarSize.Medium,
        val onClick: (() -> Unit)? = null,
    ) : Avatar {
        @Composable
        override fun Draw() {
            SubcomposeAsyncImage(
                modifier = modifier
                    .clip(CircleShape)
                    .clickable { onClick?.invoke() }
                    .background(colorGrey0())
                    .size(avatarSize.size),
                model = imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = "profile image",
                loading = {
                    Shimmer(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(avatarSize.size)
                    )
                }
            )
        }
    }

    class ImageAvatar(
        val modifier: Modifier = Modifier,
        val image: Painter,
        val avatarSize: AvatarSize = AvatarSize.Medium,
        val onClick: (() -> Unit)? = null,
    ) : Avatar {
        @Composable
        override fun Draw() = Image(
            modifier = modifier
                .clip(CircleShape)
                .clickable { onClick?.invoke() }
                .background(colorGrey0())
                .size(avatarSize.size)
                .padding(avatarSize.padding),
            painter = image,
            contentDescription = null
        )
    }

    class IconAvatar(
        val modifier: Modifier = Modifier,
        val icon: ImageVector,
        val tint: Color? = null,
        val background: Color? = null,
        val avatarSize: AvatarSize = AvatarSize.Medium,
        val onClick: (() -> Unit)? = null,
    ) : Avatar {
        @Composable
        override fun Draw() = Icon(
            modifier = modifier
                .clip(CircleShape)
                .clickable { onClick?.invoke() }
                .background(background ?: colorGrey0())
                .size(avatarSize.size)
                .padding(avatarSize.padding),
            imageVector = icon,
            tint = tint ?: colorGrey900(),
            contentDescription = null
        )
    }

    class InitialsAvatar(
        val modifier: Modifier = Modifier,
        val initials: String,
        val tint: Color? = null,
        val background: Color? = null,
        val avatarSize: AvatarSize = AvatarSize.Medium,
        val onClick: (() -> Unit)? = null,
    ) : Avatar {
        @Composable
        override fun Draw() = Text(
            modifier = modifier
                .clip(CircleShape)
                .clickable { onClick?.invoke() }
                .background(background ?: colorGrey0())
                .size(avatarSize.size)
                .padding(top = avatarSize.padding),
            text = initials,
            style = when (avatarSize) {
                AvatarSize.Small -> typography.titleSmall
                AvatarSize.Medium -> typography.bodyMedium
                AvatarSize.Big -> typography.headlineSmall
                AvatarSize.Extra -> typography.headlineLarge
            },
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = tint ?: colorGrey900(),
        )
    }
}

enum class AvatarSize(val size: Dp, val padding: Dp) {
    Small(size = 28.dp, padding = 3.dp),
    Medium(size = 36.dp, padding = 8.dp),
    Big(size = 50.dp, padding = 10.dp),
    Extra(size = 80.dp, padding = 22.dp)
}
