package com.diego.futty.home.design.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diego.futty.core.presentation.theme.colorGrey0
import com.diego.futty.core.presentation.theme.colorGrey900
import com.diego.futty.home.design.presentation.component.banner.Banner
import com.diego.futty.home.design.presentation.component.banner.BannerStatus
import com.diego.futty.home.design.presentation.component.banner.ScrollBanner
import com.diego.futty.home.design.presentation.component.bottomsheet.BottomSheet
import com.diego.futty.home.design.presentation.component.flowrow.FlowList
import com.diego.futty.home.design.presentation.component.topbar.TopBar
import com.diego.futty.home.design.presentation.component.topbar.TopBarActionType
import com.diego.futty.home.design.presentation.viewmodel.DesignViewModel
import futty.composeapp.generated.resources.Res
import futty.composeapp.generated.resources.book_error_2
import futty.composeapp.generated.resources.girasoles
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DesignScreen(
    viewModel: DesignViewModel = koinViewModel(),
) {
    Scaffold(
        containerColor = colorGrey0(),
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp),
                title = "Descubre",
                topBarActionType = TopBarActionType.Profile(
                    initials = "DD",
                    tint = colorGrey0(),
                    background = colorGrey900(),
                    onClick = { viewModel.onProfileClicked() }
                )
            )
        },
        content = { paddingValues ->
            DesignContent(viewModel, paddingValues)
        },
    )
    BottomSheetContent(viewModel)
}

@Composable
private fun BottomSheetContent(viewModel: DesignViewModel) {
    if (viewModel.bottomsheetDismissed.value.not()) {
        BottomSheet(
            onDismiss = { viewModel.onBottomSheetDismissed() },
            onAction = { viewModel.onBottomSheetDismissed() },
        )
    }
}

@Composable
private fun DesignContent(viewModel: DesignViewModel, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(top = paddingValues.calculateTopPadding())
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FlowList(viewModel.chipItems.value, viewModel.selectedChip.value) { index ->
            viewModel.onChipSelected(index)
        }

        Banner.StatusBanner(
            title = "Dinero en cuenta:",
            subtitle = "$ 254.300,59 ARS\n\n$ 3.700 USD",
            status = BannerStatus.Border
        ).Draw()

        Banner.StatusBanner(
            title = "¡Listo!",
            subtitle = "Éste banner te avisará de situaciones positivas.",
            status = BannerStatus.Success
        ).Draw()

        Banner.StatusBanner(
            title = "Algo salió mal",
            subtitle = "Éste banner te avisará cuando algo salió mal.",
            status = BannerStatus.Error
        ).Draw()

        Banner.StatusBanner(
            title = "¡Cuidado!",
            subtitle = "Éste banner te avisará cuando algo pueda salir mal.",
            status = BannerStatus.Alert
        ).Draw()

        Banner.StatusBanner(
            title = "¿Sabías?",
            subtitle = "Éste banner te puede mostrar información.",
            status = BannerStatus.Info
        ).Draw()

        Banner.ClickableBanner(
            title = "Banner Accionable",
            subtitle = "Éste es un banner que se puede accionar.",
            onClick = { viewModel.onButtonClicked() }
        ).Draw()

        Banner.ClickableBanner(
            image = painterResource(Res.drawable.book_error_2),
            title = "Banner Accionable",
            subtitle = "Éste es un banner que se puede accionar.",
            onClick = { }
        ).Draw()

        val scrollableBanners = viewModel.getScrollableBanners()
        ScrollBanner(
            items = listOf(
                scrollableBanners,
                scrollableBanners.copy(
                    action = { viewModel.onScrollBannerClicked("accion girasoles") },
                    illustration = Res.drawable.girasoles
                ),
            ),
        )
    }
}

