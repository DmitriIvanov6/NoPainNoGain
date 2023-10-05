package com.ditriminc.nopainnogain.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ditriminc.nopainnogain.R



@Composable
fun ReactionBlock1() {
    data class TrainingReaction(
        val reactionId: Int,
        val imageId: Int
    )

    val reactions: ArrayList<TrainingReaction>

    fun composeReactionList() {
        reactions.add()
    }

    val listState = rememberLazyListState()
    var selectedIndex by remember { mutableStateOf(-1) }

    LazyColumn(state = listState) {
        items(reactions) {



        }

    }
}


@Preview
@Composable
fun ReactionBlock(reaction: MutableState<Int> = mutableStateOf(0)) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .selectableGroup(),
        horizontalArrangement = Arrangement.Center
    ) {


        ReactionImage(
            resourceId = R.drawable.tired,
            description = "tired",
            reaction = reaction

        )
        ReactionImage(
            resourceId = R.drawable.normal,
            description = "normal",
            reaction = reaction
        )
        ReactionImage(
            resourceId = R.drawable.fresh,
            description = "fresh",
            reaction = reaction
        )
    }
}

@Composable
fun ReactionImage(resourceId: Int, description: String, reaction: MutableState<Int>) {
    Image(
        painter = painterResource(id = resourceId),
        contentDescription = description,
        Modifier
            .padding(10.dp)
            .width(30.dp)
            .padding(5.dp)
            .selectable(selected = selected, onClick = {
                se
                when (description) {
                    "tired" -> reaction.value = REACTION_TIRED
                    "fresh" -> reaction.value = REACTION_FRESH
                    else -> reaction.value = REACTION_NORMAL
                }
            })
    )


}
