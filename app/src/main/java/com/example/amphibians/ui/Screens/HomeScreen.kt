package com.example.amphibians.ui.Screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.network.Amphibians
import com.example.amphibians.ui.theme.AmphibiansTheme

@Composable
fun HomeScreen(
    amphibiansUiState: AmphiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    when (amphibiansUiState) {
        is AmphiUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxWidth())
        is AmphiUiState.Success -> AmphibiansList(amphibians = amphibiansUiState.amphibians, modifier = modifier, contentPadding = contentPadding)
        is AmphiUiState.Error -> ErrorScreen(retryAction = retryAction, modifier = modifier.fillMaxWidth())
    }
}

@Composable
fun AmphibiansList(
    amphibians: List<Amphibians>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding
    ) {
        items(amphibians.size) { index ->
            AmphibiansCard(amphibian = amphibians[index])
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier) {
    Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier) {
    Column (
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
                painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = null
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun AmphibiansCard(
    amphibian: Amphibians,
    modifier: Modifier = Modifier) {
    // State to manage the expanded state of the card
    var expanded by remember { mutableStateOf(false) }
    Card(
            modifier = modifier
                .clickable(onClick = { expanded = !expanded }) // Toggle expanded state on click
                .padding(8.dp)
                .fillMaxWidth()
    ) {
        Column (
                modifier = Modifier.animateContentSize(
                        animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMedium
                        )
                )
        ){
            // Display the amphibian's name
            Text(
                    text = "${amphibian.name} (${amphibian.type})",
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
                    style = MaterialTheme.typography.headlineSmall
            )
            //Display the amphibian's image
            AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(amphibian.imgSrc)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.amphibian_image),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop,
            )
            // Display the amphibian's description
            if (expanded) {
                Text(
                        text = amphibian.description,
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AmphibiansCardPreview() {
    AmphibiansTheme {
        val mockData = List(6) { Amphibians("$it", "","","") }
        AmphibiansList(amphibians = mockData)
    }
}