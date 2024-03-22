package com.mk.moviemania.navigation.feed.ui

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mk.moviemania.R
import com.mk.moviemania.common.ui.ktx.clickableWithoutRipple
import com.mk.moviemania.common.ui.ktx.displayCutoutWindowInsets
import com.mk.moviemania.common.ui.ktx.gridColumnsCount
import com.mk.moviemania.common.ui.ktx.isErrorOrEmpty
import com.mk.moviemania.common.ui.ktx.isFailure
import com.mk.moviemania.common.ui.ktx.isLoading
import com.mk.moviemania.common.ui.ktx.isNotEmpty
import com.mk.moviemania.common.ui.ktx.isPagingFailure
import com.mk.moviemania.common.ui.ktx.isPagingLoading
import com.mk.moviemania.common.ui.ktx.isPortrait
import com.mk.moviemania.common.ui.ktx.refreshThrowable
import com.mk.moviemania.common.ui.placeholder.PlaceholderHighlight
import com.mk.moviemania.common.ui.placeholder.placeholder
import com.mk.moviemania.navigation.feed.list.MovieList
import com.mk.moviemania.navigation.feed.viewmodel.FeedViewModel
import com.mk.moviemania.persistence.database.entity.MovieDb
import com.mk.moviemania.exceptions.ApiKeyNotNullException
import com.mk.moviemania.exceptions.PageEmptyException
import com.mk.moviemania.navigation.feed.accesibility.MoviesContentDescription
import com.mk.moviemania.network.formatBackdropImage
import com.mk.moviemania.network.isTmdbApiKeyEmpty
import com.mk.moviemania.network.model.MovieResponse
import com.mk.moviemania.resources.MoviesIcons
import com.mk.networkinglib.internetConnectivity.NetworkStatus
import java.net.UnknownHostException
import kotlinx.coroutines.launch
import com.mk.moviemania.common.ui.placeholder.material3.fade
import com.mk.moviemania.network.formatPosterImage
import com.mk.moviemania.common.ui.ktx.context
import com.mk.moviemania.navigation.feed.accesibility.MoviesContentDescription.SearchIcon
import com.mk.moviemania.navigation.feed.accesibility.MoviesContentDescription.SettingsIcon
import com.mk.moviemania.navigation.feed.ktx.titleText

@Composable
fun FeedRoute(
    onNavigateToDetails: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val currentFeedView by viewModel.currentFeedView.collectAsStateWithLifecycle()
    val currentMovieList by viewModel.currentMovieList.collectAsStateWithLifecycle()
    val networkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()

    FeedScreenContent(
        pagingItems = pagingItems,
        networkStatus = networkStatus,
        currentFeedView = currentFeedView,
        currentMovieList = currentMovieList,
        onMovieListSelect = viewModel::selectMovieList,
        onNavigateToDetails = onNavigateToDetails,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeedScreenContent(
    pagingItems: LazyPagingItems<MovieDb>,
    networkStatus: NetworkStatus,
    currentFeedView: FeedView,
    currentMovieList: MovieList,
    onMovieListSelect: (MovieList) -> Unit,
    onNavigateToDetails: (String, Int) -> Unit,
    modifier: Modifier
) {
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val lazyGridState = rememberLazyGridState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()


    if (networkStatus == NetworkStatus.Available && pagingItems.isFailure && pagingItems.refreshThrowable is UnknownHostException) {
        pagingItems.retry()
    }

    val onScrollToTop: () -> Unit = {
        scope.launch { lazyListState.animateScrollToItem(0) }
        scope.launch { lazyGridState.animateScrollToItem(0) }
        scope.launch { lazyStaggeredGridState.animateScrollToItem(0) }
    }

    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            FeedToolbar(
                title = currentMovieList.titleText,
                modifier = Modifier.clickableWithoutRipple(onScrollToTop),
                onMovieListSelect = onMovieListSelect,
                topAppBarScrollBehavior = topAppBarScrollBehavior,
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding ->


        when {
            pagingItems.isLoading -> {
                PageLoading(
                    feedView = currentFeedView,
                    modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets),
                    paddingValues = innerPadding
                )
            }

            pagingItems.isFailure -> {
                if (pagingItems.refreshThrowable is PageEmptyException) {
                    FeedEmpty(
                        modifier = Modifier
                            .padding(innerPadding)
                            .windowInsetsPadding(displayCutoutWindowInsets)
                            .fillMaxSize()
                    )
                } else {
                    PageFailure(
                        modifier = Modifier
                            .padding(innerPadding)
                            .windowInsetsPadding(displayCutoutWindowInsets)
                            .fillMaxSize()
                            .clickableWithoutRipple(pagingItems::retry)
                    )
                }
            }

            else -> {
                PageContent(
                    feedView = currentFeedView,
                    lazyListState = lazyListState,
                    lazyGridState = lazyGridState,
                    lazyStaggeredGridState = lazyStaggeredGridState,
                    pagingItems = pagingItems,
                    onMovieClick = onNavigateToDetails,
                    contentPadding = innerPadding,
                    modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets)
                )
            }
        }
    }

}

@Composable
fun PageLoading(
    feedView: FeedView,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    when (feedView) {
        is FeedView.FeedList -> {
            if (isPortrait) {
                PageLoadingColumn(
                    modifier = modifier,
                    paddingValues = paddingValues
                )
            } else {
                PageLoadingGrid(
                    modifier = modifier,
                    paddingValues = paddingValues
                )
            }
        }

        is FeedView.FeedGrid -> {
            PageLoadingStaggeredGrid(
                modifier = modifier,
                paddingValues = paddingValues
            )
        }
    }
}

@Composable
private fun PageLoadingColumn(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    LazyColumn(
        modifier = modifier.padding(top = 4.dp),
        contentPadding = paddingValues,
        userScrollEnabled = false
    ) {
        items(MovieResponse.DEFAULT_PAGE_SIZE.div(2)) {
            MovieRow(
                movie = MovieDb.Empty,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .placeholder(
                        visible = true,
                        color = MaterialTheme.colorScheme.inversePrimary,
                        shape = MaterialTheme.shapes.small,
                        highlight = PlaceholderHighlight.fade()
                    )
            )
        }
    }
}

@Composable
private fun PageLoadingGrid(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp),
        contentPadding = paddingValues,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = false
    ) {
        items(MovieResponse.DEFAULT_PAGE_SIZE.div(2)) {
            MovieRow(
                movie = MovieDb.Empty,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .placeholder(
                        visible = true,
                        color = MaterialTheme.colorScheme.inversePrimary,
                        shape = MaterialTheme.shapes.small,
                        highlight = PlaceholderHighlight.fade()
                    )
            )
        }
    }
}

@Composable
private fun PageLoadingStaggeredGrid(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(gridColumnsCount),
        modifier = modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
        contentPadding = paddingValues,
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = false
    ) {
        items(MovieResponse.DEFAULT_PAGE_SIZE.div(2)) {
            MovieColumn(
                movie = MovieDb.Empty,
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(
                        visible = true,
                        color = MaterialTheme.colorScheme.inversePrimary,
                        shape = MaterialTheme.shapes.small,
                        highlight = PlaceholderHighlight.fade()
                    )
            )
        }
    }
}

@Composable
fun PageFailure(
    modifier: Modifier = Modifier
) {
    val settingsPanelContract =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    val onCheckConnectivityClick: () -> Unit = {
        settingsPanelContract.launch(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))
    }

    ConstraintLayout(
        modifier = modifier
    ) {
        val (image, text, button) = createRefs()

        Icon(
            imageVector = MoviesIcons.Info,
            contentDescription = MoviesContentDescription.None,
            modifier = Modifier.constrainAs(image) {
                width = Dimension.value(36.dp)
                height = Dimension.value(36.dp)
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, 8.dp)
            },
            tint = MaterialTheme.colorScheme.error
        )

        Text(
            text = stringResource(R.string.error_loading),
            modifier = Modifier.constrainAs(text) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(parent.start, 16.dp)
                top.linkTo(image.bottom, 8.dp)
                end.linkTo(parent.end, 16.dp)
            },
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )

        if (Build.VERSION.SDK_INT >= 29) {
            OutlinedButton(
                onClick = onCheckConnectivityClick,
                modifier = Modifier.constrainAs(button) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(text.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                }
            ) {
                Text(
                    text = stringResource(R.string.error_check_internet_connectivity)
                )
            }
        }
    }
}

@Composable
fun PageContent(
    feedView: FeedView,
    lazyListState: LazyListState,
    lazyGridState: LazyGridState,
    lazyStaggeredGridState: LazyStaggeredGridState,
    pagingItems: LazyPagingItems<MovieDb>,
    onMovieClick: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    when (feedView) {
        is FeedView.FeedList -> {
            if (isPortrait) {
                PageContentColumn(
                    lazyListState = lazyListState,
                    pagingItems = pagingItems,
                    onMovieClick = onMovieClick,
                    contentPadding = contentPadding,
                    modifier = modifier
                )
            } else {
                PageContentGrid(
                    lazyGridState = lazyGridState,
                    pagingItems = pagingItems,
                    onMovieClick = onMovieClick,
                    contentPadding = contentPadding,
                    modifier = modifier
                )
            }
        }

        is FeedView.FeedGrid -> {
            PageContentStaggeredGrid(
                lazyStaggeredGridState = lazyStaggeredGridState,
                pagingItems = pagingItems,
                onMovieClick = onMovieClick,
                contentPadding = contentPadding,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun PageContentColumn(
    lazyListState: LazyListState,
    pagingItems: LazyPagingItems<MovieDb>,
    onMovieClick: (String, Int) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(top = 4.dp),
        state = lazyListState,
        contentPadding = contentPadding
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey(),
            contentType = pagingItems.itemContentType()
        ) { index ->
            val movieDb = pagingItems[index]
            if (movieDb != null) {
                MovieRow(
                    movie = movieDb,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .clickable { onMovieClick(movieDb.movieList, movieDb.movieId) }
                )
            }
        }
        if (isTmdbApiKeyEmpty && pagingItems.isNotEmpty) {
            /*item {
                ApiKeyBox(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                )
            }*/
        }
        pagingItems.apply {
            when {
                isPagingLoading -> {
                    item {
                        PagingLoadingBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                        )
                    }
                }

                isPagingFailure -> {
                    item {
                        PagingFailureBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .padding(start = 8.dp, top = 4.dp, end = 8.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickable { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PageContentGrid(
    lazyGridState: LazyGridState,
    pagingItems: LazyPagingItems<MovieDb>,
    onMovieClick: (String, Int) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp),
        state = lazyGridState,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey(),
            contentType = pagingItems.itemContentType()
        ) { index ->
            val movieDb = pagingItems[index]
            if (movieDb != null) {
                MovieRow(
                    movie = movieDb,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .clickable { onMovieClick(movieDb.movieList, movieDb.movieId) }
                )
            }
        }
        pagingItems.apply {
            when {
                isPagingLoading -> {
                    item {
                        PagingLoadingBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)

                        )
                    }
                }

                isPagingFailure -> {
                    item {
                        PagingFailureBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickable { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PageContentStaggeredGrid(
    lazyStaggeredGridState: LazyStaggeredGridState,
    pagingItems: LazyPagingItems<MovieDb>,
    onMovieClick: (String, Int) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(gridColumnsCount),
        modifier = modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
        state = lazyStaggeredGridState,
        contentPadding = contentPadding,
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey(),
            contentType = pagingItems.itemContentType()
        ) { index ->
            val movieDb = pagingItems[index]
            if (movieDb != null) {
                MovieColumn(
                    movie = movieDb,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .clickable { onMovieClick(movieDb.movieList, movieDb.movieId) }
                )
            }
        }
        pagingItems.apply {
            when {
                isPagingLoading -> {
                    item {
                        PagingLoadingBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)

                        )
                    }
                }

                isPagingFailure -> {
                    item {
                        PagingFailureBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickable { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieRow(
    movie: MovieDb,
    modifier: Modifier = Modifier,
    maxLines: Int = 10
) {
    var isNoImageVisible by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = modifier
    ) {
        val (image, noImageText, text) = createRefs()

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(movie.backdropPath.formatBackdropImage)
                .crossfade(true)
                .build(),
            contentDescription = MoviesContentDescription.None,
            modifier = Modifier.constrainAs(image) {
                width = Dimension.fillToConstraints
                height = Dimension.value(220.dp)
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            },
            onState = { state ->
                isNoImageVisible = state.isErrorOrEmpty
            },
            contentScale = ContentScale.Crop
        )

        AnimatedVisibility(
            visible = isNoImageVisible,
            modifier = Modifier.constrainAs(noImageText) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(text.top)
            },
            enter = fadeIn()
        ) {
            Text(
                text = stringResource(R.string.no_image),
                style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.secondary)
            )
        }

        Text(
            text = movie.title,
            modifier = Modifier.constrainAs(text) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(parent.start, 16.dp)
                top.linkTo(image.bottom, 16.dp)
                end.linkTo(parent.end, 16.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            },
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
}

@Composable
fun MovieColumn(
    movie: MovieDb,
    modifier: Modifier = Modifier
) {
    var isNoImageVisible by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = modifier
    ) {
        val (image, noImageText, text) = createRefs()

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(movie.posterPath.formatPosterImage)
                .crossfade(true)
                .build(),
            contentDescription = MoviesContentDescription.None,
            modifier = Modifier.constrainAs(image) {
                width = Dimension.fillToConstraints
                height = Dimension.value(220.dp)
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            },
            onState = { state ->
                isNoImageVisible = state.isErrorOrEmpty
            },
            contentScale = ContentScale.Crop
        )

        AnimatedVisibility(
            visible = isNoImageVisible,
            modifier = Modifier.constrainAs(noImageText) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(text.top)
            },
            enter = fadeIn()
        ) {
            Text(
                text = stringResource(R.string.no_image),
                style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.secondary)
            )
        }

        Text(
            text = movie.title,
            modifier = Modifier.constrainAs(text) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(parent.start, 16.dp)
                top.linkTo(image.bottom, 16.dp)
                end.linkTo(parent.end, 16.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            },
            maxLines = 10,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
}

@Composable
fun PagingLoadingBox(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun PagingFailureBox(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.retry),
            style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedToolbar(
    title: String,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onMovieListSelect: (MovieList) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        },
        modifier = modifier, // Make sure this modifier doesn't hide the IconButton
        actions = {
            IconButton( // Ensure this modifier doesn't hide the IconButton
                onClick = { expanded = true },
                modifier = Modifier.then( // Try adding some padding for better visibility
                    Modifier.padding(horizontal = 8.dp)
                )
            ) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More Options")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                MovieList.VALUES.forEach { item ->
                    DropdownMenuItem(onClick = {
                        onMovieListSelect(item)
                        expanded = false
                    },
                        text = { Text(item.titleText) }
                    )
                }

            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary
        ),
        scrollBehavior = topAppBarScrollBehavior
    )
}