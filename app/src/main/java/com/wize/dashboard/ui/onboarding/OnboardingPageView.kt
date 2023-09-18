package com.wize.dashboard.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wize.dashboard.R
import com.wize.dashboard.extensions.NotoSansFont

@Composable
fun OnboardingPageView(modifier: Modifier, currentPage: OnboardPage) {
    Column(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo_with_title),
            contentDescription = null,
            modifier = Modifier
                .height(40.dp)
                .align(Alignment.Start),
            contentScale = ContentScale.FillHeight
        )
        Image(
            painter = painterResource(currentPage.imageRes),
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
                .padding(top = 24.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        Text(
            text = stringResource(currentPage.titleRes),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontFamily = NotoSansFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(currentPage.descriptionRes),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontFamily = NotoSansFont,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}