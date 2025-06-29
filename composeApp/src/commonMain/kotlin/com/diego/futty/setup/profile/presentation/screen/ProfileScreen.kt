package com.diego.futty.setup.profile.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.diego.futty.core.presentation.photos.PermissionCallback
import com.diego.futty.core.presentation.photos.PermissionStatus
import com.diego.futty.core.presentation.photos.PermissionType
import com.diego.futty.core.presentation.photos.createPermissionsManager
import com.diego.futty.core.presentation.photos.rememberCameraManager
import com.diego.futty.core.presentation.photos.rememberGalleryManager
import com.diego.futty.core.presentation.theme.colorAlertLight
import com.diego.futty.core.presentation.theme.colorError
import com.diego.futty.core.presentation.theme.colorErrorLight
import com.diego.futty.core.presentation.theme.colorGrey0
import com.diego.futty.core.presentation.theme.colorGrey100
import com.diego.futty.core.presentation.theme.colorGrey500
import com.diego.futty.core.presentation.theme.colorGrey700
import com.diego.futty.core.presentation.theme.colorGrey900
import com.diego.futty.core.presentation.theme.colorInfo
import com.diego.futty.core.presentation.theme.colorInfoLight
import com.diego.futty.core.presentation.theme.toColor
import com.diego.futty.core.presentation.utils.PlatformInfo
import com.diego.futty.core.presentation.utils.UserTypes.USER_TYPE_BASIC
import com.diego.futty.core.presentation.utils.UserTypes.USER_TYPE_PRO
import com.diego.futty.home.design.presentation.component.avatar.Avatar
import com.diego.futty.home.design.presentation.component.avatar.AvatarSize
import com.diego.futty.home.design.presentation.component.banner.Banner
import com.diego.futty.home.design.presentation.component.banner.BannerStatus
import com.diego.futty.home.design.presentation.component.banner.BannerUIData
import com.diego.futty.home.design.presentation.component.button.PrimaryButton
import com.diego.futty.home.design.presentation.component.button.SecondaryButton
import com.diego.futty.home.design.presentation.component.card.SimpleInfoCard
import com.diego.futty.home.design.presentation.component.flowrow.MultipleFlowList
import com.diego.futty.home.design.presentation.component.image.AsyncImage
import com.diego.futty.home.design.presentation.component.pro.VerifiedIcon
import com.diego.futty.home.design.presentation.component.progressbar.CircularProgressBar
import com.diego.futty.home.design.presentation.component.progressbar.LinearProgressBar
import com.diego.futty.home.design.presentation.component.topbar.TopBar
import com.diego.futty.home.design.presentation.component.topbar.TopBarActionType
import com.diego.futty.setup.profile.presentation.viewmodel.ProfileViewModel
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import compose.icons.TablerIcons
import compose.icons.tablericons.Calendar
import compose.icons.tablericons.Settings
import compose.icons.tablericons.X
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    Scaffold(
        containerColor = colorGrey0(),
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp),
                title = "Perfil",
                topBarActionType = TopBarActionType.Icon(
                    icon = TablerIcons.Settings,
                    onClick = { viewModel.onSettingsClicked() }
                ),
                onBack = { viewModel.onBackClicked() }
            )
        },
        content = { paddingValues ->
            ProfileContent(viewModel, paddingValues)
        },
    )
    ImageHandler(viewModel)
}

@Composable
private fun ProfileContent(viewModel: ProfileViewModel, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        MainInfo(viewModel)
        SecondaryInfo(viewModel)
        Upgrade(viewModel)
        Desires(viewModel)
        Levels()
        viewModel.modal.value?.Draw()
    }
}

@Composable
private fun SecondaryInfo(viewModel: ProfileViewModel) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SimpleInfoCard(
            modifier = Modifier.weight(1f),
            title = "Publicaciones",
            description = viewModel.postsCant.value?.toString() ?: "",
        )
        SimpleInfoCard(
            modifier = Modifier.weight(1f),
            title = "Siguiendo",
            description = viewModel.followsCant.value?.toString() ?: "",
        )
        SimpleInfoCard(
            modifier = Modifier.weight(1f),
            title = "Seguidores",
            description = viewModel.followersCant.value?.toString() ?: "",
        )
    }
}

@Composable
fun Upgrade(viewModel: ProfileViewModel) {
    Column(
        Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        if (viewModel.user.value?.userType == USER_TYPE_BASIC && viewModel.userId.value.isEmpty()) {
            Banner.FullImageBanner(
                BannerUIData(
                    title = "Todavía no tienes PRO",
                    description = "Descubre las mejores funcionalidades por solo $1 USD.",
                    label = "Conoce MockClubPRO",
                    illustration = "https://cdn.pixabay.com/photo/2022/09/21/17/02/blue-background-7470781_1280.jpg",
                    action = { viewModel.onVerifyClicked() },
                ),
                page = 0,
                state = rememberPagerState { 1 },
            ).Draw()
        }

        AnimatedVisibility(viewModel.showFollowButton.value) {
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                color = if (viewModel.followingUser.value) {
                    colorError()
                } else {
                    colorInfo()
                },
                title = if (viewModel.followingUser.value) {
                    "Dejar de seguir"
                } else {
                    "Seguir"
                },
                onClick = { viewModel.onFollowOrUnfollowClicked() }
            )
        }
    }
}

@Composable
fun Levels() {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            text = "Niveles",
            style = typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = colorGrey900()
        )
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.width(8.dp))
            CircularProgressBar(progress = 0.2f, text = "Nivel 1")
            CircularProgressBar(progress = 0.4f, text = "Nivel 2", color = colorErrorLight())
            CircularProgressBar(progress = 0.6f, text = "Nivel 3", color = colorAlertLight())
            CircularProgressBar(progress = 0.8f, text = "Nivel 4", color = colorInfoLight())
            CircularProgressBar(progress = 1.0f, text = "Nivel 5", color = colorGrey700())
            Spacer(Modifier.width(8.dp))
        }
        LinearProgressBar(
            modifier = Modifier.padding(horizontal = 20.dp),
            progress = 0.4f,
            color = colorGrey900()
        )
    }
}

@Composable
private fun MainInfo(viewModel: ProfileViewModel) {
    val user = viewModel.user.value
    AnimatedVisibility(user != null) {
        Row(
            modifier = Modifier.padding(top = 12.dp).padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Avatar.ProfileAvatar(
                    imageUrl = viewModel.urlImage.value,
                    initials = viewModel.initials.value,
                    background = user?.profileImage?.background?.toColor(),
                    avatarSize = AvatarSize.Extra,
                    onClick = { viewModel.showUpdateImage() }
                ).Draw()
                if (viewModel.userId.value.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .clickable { viewModel.onEditClicked() },
                        text = "Editar",
                        style = typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = colorGrey900()
                    )
                }
            }

            Column(
                modifier = Modifier.align(Alignment.Top),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 1.dp),
                        text = "${user?.name} ${user?.lastName}",
                        style = typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = colorGrey900()
                    )
                    if (user?.userType == USER_TYPE_PRO) {
                        VerifiedIcon(Modifier.padding(top = 4.dp))
                    }
                }
                if (user?.description != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(start = 1.dp),
                        text = user.description,
                        style = typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = colorGrey500()
                    )
                }
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = TablerIcons.Calendar,
                        tint = colorGrey500(),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "En MockClub desde el ${user?.creationDate}",
                        style = typography.titleSmall,
                        fontWeight = FontWeight.Normal,
                        color = colorGrey500()
                    )
                }
            }
        }
    }
}

@Composable
private fun Desires(viewModel: ProfileViewModel) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Intereses",
            style = typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = colorGrey900()
        )

        val chips = viewModel.chipItems.value
        if (chips != null) {
            MultipleFlowList(chips, viewModel.selectedChips.value) { index ->
                viewModel.onChipSelected(index)
            }
        } else {
            // SHIMMERS
        }
    }
}

/*TODO - MEJORAR ESTO*/
@Composable
private fun ImageHandler(viewModel: ProfileViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var imageByteArray by remember { mutableStateOf<ByteArray?>(null) }
    var launchSetting by remember { mutableStateOf(value = false) } // LANZA SETTINGS
    var permissionRationalDialog by remember { mutableStateOf(value = false) }
    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.CAMERA -> viewModel.launchCamera()
                        PermissionType.GALLERY -> viewModel.launchGallery()
                    }
                }

                else -> {
                    permissionRationalDialog = true
                }
            }
        }
    })

    val cameraManager = rememberCameraManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                it?.toImageBitmap()
            }
            val byteArray = withContext(Dispatchers.Default) {
                it?.toByteArray()
            }
            imageBitmap = bitmap
            imageByteArray = byteArray
        }
    }

    val galleryManager = rememberGalleryManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                it?.toImageBitmap()
            }
            val byteArray = withContext(Dispatchers.Default) {
                it?.toByteArray()
            }
            imageBitmap = bitmap
            imageByteArray = byteArray
        }
    }

    // MOSTRAR OPCIONES (CAMARA Y GALERIA)
    if (viewModel.showUpdateImage.value) {
        imageBitmap = null
        OpenedImage(viewModel)
    }
    if (viewModel.launchGallery.value) { // ABRIR GALERÍA
        if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
            galleryManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.GALLERY)
        }
        viewModel.launchGallery()
    }
    if (viewModel.launchCamera.value) { // ABRIR CAMARA
        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
            cameraManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.CAMERA)
        }
        viewModel.launchCamera()
    }
    if (launchSetting) { // IR A SETTINGS
        permissionsManager.launchSettings()
        launchSetting = false
    }
    if (permissionRationalDialog) { // ERROR NO ACEPTO PERMISOS
        Banner.StatusBanner(
            bannerUIData = BannerUIData(
                title = "Algo salió mal",
                description = "Brinda servicios de  Configuración del dispositivo.",
                status = BannerStatus.Error,
                action = {
                    permissionRationalDialog = false
                    launchSetting = true
                }
            ),
        ).Draw()
    }
    if (imageBitmap != null) {
        viewModel.updateImage(imageBitmap!!, imageByteArray!!)
    }
}

@Composable
private fun OpenedImage(viewModel: ProfileViewModel) {
    FlexibleBottomSheet(
        containerColor = colorGrey0().copy(alpha = 0.9f),
        scrimColor = Color.Transparent,
        onDismissRequest = { viewModel.showUpdateImage() },
        sheetState = rememberFlexibleBottomSheetState(
            flexibleSheetSize = FlexibleSheetSize(
                fullyExpanded = 1f
            ),
            isModal = true,
            skipIntermediatelyExpanded = true,
            skipSlightlyExpanded = true,
        ),
        windowInsets = WindowInsets(0.dp),
        dragHandle = {},
    ) {
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                when {
                    viewModel.urlImage.value?.isNotEmpty() == true -> {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .aspectRatio(1f)
                                .clip(CircleShape),
                            shimmerModifier = Modifier
                                .padding(horizontal = 24.dp)
                                .clip(CircleShape),
                            contentDescription = "profile image",
                            image = viewModel.urlImage.value
                        )
                    }

                    viewModel.initials.value != null -> {
                        val color = viewModel.user.value?.profileImage?.background?.toColor()
                            ?: colorGrey900()
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 50.dp)
                                .aspectRatio(1f)
                                .clip(CircleShape)
                                .background(color),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = viewModel.initials.value ?: "",
                                fontWeight = FontWeight.Bold,
                                style = typography.headlineLarge,
                                color = colorGrey900(),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                if (viewModel.userId.value.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        SecondaryButton(
                            title = "Elegir de la galería",
                            color = colorGrey900(),
                            onClick = {
                                viewModel.showUpdateImage()
                                viewModel.launchGallery()
                            }
                        )
                        SecondaryButton(
                            title = "Tomar foto",
                            color = colorGrey900(),
                            onClick = {
                                viewModel.showUpdateImage()
                                viewModel.launchCamera()
                            }
                        )
                    }
                }
            }
            Avatar.IconAvatar(
                modifier = Modifier
                    .padding(top = if (PlatformInfo.isAndroid) 12.dp else 60.dp, end = 16.dp)
                    .align(Alignment.TopEnd),
                icon = TablerIcons.X,
                background = colorGrey100(),
                onClick = { viewModel.showUpdateImage() }
            ).Draw()
        }
    }
}
