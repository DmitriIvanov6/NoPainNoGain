package com.ditriminc.nopainnogain.ui.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun CustomAlertDialog(onDismissRequest: () -> Unit = {},
                      onConfirmation: () -> Unit ={},
                      dialogTitle: String = "Title",
                      dialogText: String = "Text",
                      confirmText : String = "Confirm",
                      cancelText: String = "Cancel",
                      icon: ImageVector = Icons.Rounded.Warning) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(cancelText)
            }
        }
    )

}