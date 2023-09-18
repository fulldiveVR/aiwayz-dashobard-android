package com.wize.dashboard.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wize.dashboard.ui.theme.WizeColor

@Composable
fun OnboardingTabSelector(
    modifier: Modifier,
    onboardPages: List<OnboardPage>,
    currentPage: Int,
    onTabSelected: (Int) -> Unit
) {

    TabRow(
        selectedTabIndex = currentPage,
        contentColor = WizeColor.Primary,
        modifier = modifier
            .width(width = 100.dp),
        indicator = {},
        divider = {}
    ) {
        onboardPages.forEachIndexed { index, _ ->
            Tab(
                selected = index == currentPage,
                onClick = {
                    onTabSelected(index)
                },
                modifier = modifier.height(32.dp),
                selectedContentColor = WizeColor.Primary,
                unselectedContentColor = WizeColor.Primary,
                content = {
                    Box(
                        modifier = modifier
                            .size(6.dp)
                            .background(
                                color = if (index == currentPage) {
                                    WizeColor.Primary
                                } else {
                                    WizeColor.Background
                                },
                                shape = RoundedCornerShape(6.dp)
                            )
                    )
                })
        }
    }
}