package com.ditriminc.nopainnogain.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ditriminc.nopainnogain.R


@Composable
fun ReactionBlock(selectedIndex: MutableState<Int>) {
    val reactions = ArrayList<ReactionItem>()
    reactions.apply {
        add(ReactionItem(R.drawable.tired, false, REACTION_TIRED))
        add(ReactionItem(R.drawable.normal, false, REACTION_NORMAL))
        add(ReactionItem(R.drawable.fresh, false, REACTION_FRESH))
    }

    val listState = rememberLazyListState()

    LazyRow(
        state = listState,
        modifier = Modifier
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        items(reactions) {
            ReactionImage(
                resourceId = it.imgResource, reaction = it.reactionId, selectedIndex
            )
        }
    }
}


@Composable
fun ReactionImage(resourceId: Int, reaction: Int, selectedIndex: MutableState<Int>) {
    var description = ""
    when (reaction) {
        1 -> description = "tired"
        2 -> description = "normal"
        3 -> description = "fresh"
    }
    Image(
        painter = painterResource(id = resourceId),
        contentDescription = description,
        Modifier
            .padding(10.dp)
            .width(30.dp)
            .padding(5.dp)
            .background(
                if (reaction == selectedIndex.value) Color.LightGray else MaterialTheme.colorScheme.background
            )
            .selectable(selected = reaction == selectedIndex.value, onClick = {
                //todo выделеиние выбранного
                if (selectedIndex.value != reaction) {
                    selectedIndex.value = reaction
                } else {
                    selectedIndex.value = -1
                }
            })
    )
}


@Composable
fun PreviousReactionImage(reaction: Int) {
    var description = ""
    var resourceId = 0
    when (reaction) {
        1 -> {
            description = "tired"
            resourceId = R.drawable.tired
        }

        2 -> {
            description = "normal"
            resourceId = R.drawable.normal
        }

        3 -> {
            description = "fresh"
            resourceId = R.drawable.fresh
        }
        else -> {
            description = ""
            resourceId = -1
        }
    }
    if (resourceId != -1) {
        Image(
            painter = painterResource(id = resourceId),
            contentDescription = description,
            modifier = Modifier
                .width(30.dp)
                .wrapContentSize()
        )
    }
}
