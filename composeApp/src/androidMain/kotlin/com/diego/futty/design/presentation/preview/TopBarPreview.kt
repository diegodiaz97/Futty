package com.diego.futty.design.presentation.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.diego.futty.core.presentation.theme.FuttyTheme
import com.diego.futty.home.design.presentation.component.topbar.TopBar
import com.diego.futty.home.design.presentation.component.topbar.TopBarActionType
import compose.icons.TablerIcons
import compose.icons.tablericons.Download
import compose.icons.tablericons.Logout
import compose.icons.tablericons.Settings

@Preview
@Composable
private fun TopBarNoBack() {
    FuttyTheme {
        TopBar(
            title = "Recibo",
            onBack = null,
            topBarActionType = TopBarActionType.Icon(icon = TablerIcons.Download, onClick = {})
        )
    }
}

@Preview
@Composable
private fun TopBarNone() {
    FuttyTheme {
        TopBar(
            title = "Reportes",
            onBack = {},
        )
    }
}

@Preview
@Composable
private fun TopBarButton() {
    FuttyTheme {
        TopBar(
            title = "Productos",
            onBack = {},
            topBarActionType = TopBarActionType.Button(text = "Ayuda", onClick = {})
        )
    }
}

@Preview
@Composable
private fun TopBarIcon() {
    FuttyTheme {
        TopBar(
            title = "Diego Díaz",
            onBack = {},
            topBarActionType = TopBarActionType.Icon(icon = TablerIcons.Logout, onClick = {})
        )
    }
}

@Preview
@Composable
private fun TopBarBoth() {
    FuttyTheme {
        TopBar(
            title = "Tus pagos",
            onBack = {},
            topBarActionType = TopBarActionType.BothAction(
                icon = TopBarActionType.Icon(icon = TablerIcons.Settings, onClick = {}),
                button = TopBarActionType.Button(text = "Ayuda", onClick = {})
            )
        )
    }
}

@Preview
@Composable
private fun TopBarImageProfile() {
    FuttyTheme {
        TopBar(
            title = "Hola Diego!",
            onBack = null,
            topBarActionType = TopBarActionType.Profile(
                imageUrl = "",
                onClick = {}
            )
        )
    }
}

@Preview
@Composable
private fun TopBarLoadingProfile() {
    FuttyTheme {
        TopBar(
            title = "Hola Diego!",
            onBack = null,
            topBarActionType = TopBarActionType.Profile(
                onClick = {}
            )
        )
    }
}

@Preview
@Composable
private fun TopBarIconProfile() {
    FuttyTheme {
        TopBar(
            title = "Hola Diego!",
            onBack = null,
            topBarActionType = TopBarActionType.Profile(
                initials = "JC",
                onClick = {}
            )
        )
    }
}
