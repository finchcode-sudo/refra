package com.dot.gallery.feature_node.presentation.albums.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dot.gallery.R
import com.dot.gallery.feature_node.domain.model.Album

class AlbumSelection {
    val selected = mutableStateMapOf<Long, Album>()
    var sheetAlbum by mutableStateOf<Album?>(null)

    val active: Boolean get() = selected.isNotEmpty()

    fun isSelected(album: Album) = selected.containsKey(album.id)

    fun toggle(album: Album) {
        if (selected.containsKey(album.id)) selected.remove(album.id) else selected[album.id] = album
    }

    fun clear() = selected.clear()
}

val LocalAlbumSelection = compositionLocalOf<AlbumSelection?> { null }

@Composable
fun AlbumSelectionOverlay(album: Album, modifier: Modifier = Modifier) {
    val sel = LocalAlbumSelection.current ?: return
    if (!sel.isSelected(album)) return
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
    ) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
                .size(28.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumSelectionBar(
    selection: AlbumSelection,
    onDelete: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(R.string.s_items, selection.selected.size)) },
        navigationIcon = {
            IconButton(onClick = { selection.clear() }) {
                Icon(Icons.Outlined.Close, contentDescription = stringResource(R.string.close))
            }
        },
        actions = {
            if (selection.selected.size == 1) {
                IconButton(onClick = { selection.sheetAlbum = selection.selected.values.first() }) {
                    Icon(Icons.Outlined.MoreVert, contentDescription = null)
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Outlined.Delete, contentDescription = null)
            }
        }
    )
}
