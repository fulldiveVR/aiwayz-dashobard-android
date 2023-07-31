package com.wize.dashobard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerLayout(isVisible: Boolean) {
    if (isVisible) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Shimmer(
                Modifier
                    .fillMaxWidth(1f)
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            Shimmer(
                Modifier
                    .fillMaxWidth(1f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(36.dp)
            )

            Shimmer(
                Modifier
                    .fillMaxWidth(1f)
                    .height(80.dp)
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            Shimmer(
                Modifier
                    .fillMaxWidth(1f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            Shimmer(
                Modifier
                    .fillMaxWidth(1f)
                    .height(128.dp)
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            Shimmer(
                Modifier
                    .fillMaxWidth(1f)
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            Shimmer(
                Modifier
                    .fillMaxWidth(1f)
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(36.dp)
            )

            Shimmer(
                Modifier
                    .fillMaxWidth(1f)
                    .height(80.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            Shimmer(
                Modifier
                    .fillMaxWidth(1f)
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
            )

            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            Shimmer(
                Modifier
                    .fillMaxWidth(1f)
                    .height(128.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
        }
    }
}