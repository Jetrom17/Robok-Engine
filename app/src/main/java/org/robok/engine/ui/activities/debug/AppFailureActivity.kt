package org.robok.engine.ui.activities.debug

/*
 *  This file is part of Robok © 2024.
 *
 *  Robok is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Robok is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Robok. If not, see <https://www.gnu.org/licenses/>.
 */

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.robok.engine.core.components.Screen
import org.robok.engine.core.components.preferences.base.PreferenceGroup
import org.robok.engine.core.components.utils.addIf
import org.robok.engine.strings.Strings
import org.robok.engine.ui.activities.base.RobokComposeActivity
import org.robok.engine.ui.theme.RobokTheme

@OptIn(ExperimentalMaterial3Api::class)
class AppFailureActivity : RobokComposeActivity() {

  private val exceptionType =
    listOf(
      "StringIndexOutOfBoundsException",
      "IndexOutOfBoundsException",
      "ArithmeticException",
      "NumberFormatException",
      "ActivityNotFoundException",
    )

  private val errMessage =
    listOf(
      "Invalid string operation\n",
      "Invalid list operation\n",
      "Invalid arithmetical operation\n",
      "Invalid toNumber block operation\n",
      "Invalid intent operation",
    )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent { RobokTheme { DebugScreen(intent.getStringExtra("error") ?: "") } }
  }

  @Composable
  fun DebugScreen(errorMessage: String) {
    var madeErrMsg by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(true) }

    LaunchedEffect(errorMessage) { madeErrMsg = processErrorMessage(errorMessage) }

    Screen(label = stringResource(id = Strings.title_un_error_ocurred), backArrowVisible = false) {
      PreferenceGroup(heading = stringResource(id = Strings.text_error_info)) {
        ErrorCard(madeErrMsg, false)
      }
    }

    if (showDialog) {
      AlertDialog(
        onDismissRequest = { showDialog = false },
        title = { Text(stringResource(id = Strings.title_un_error_ocurred)) },
        text = { ErrorCard(madeErrMsg) },
        confirmButton = {
          Button(
            onClick = {
              showDialog = false
              finish()
            }
          ) {
            Text(stringResource(id = Strings.common_word_end))
          }
        },
      )
    }
  }

  @Composable
  fun ErrorCard(madeErrMsg: String, scrollable: Boolean = true) {
    val scrollState = rememberScrollState()
    Card(
      modifier =
        Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp)).addIf(scrollable) {
          verticalScroll(scrollState)
        },
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
      shape = RoundedCornerShape(18.dp),
    ) {
      SelectionContainer {
        Text(
          text = madeErrMsg,
          modifier = Modifier.padding(16.dp),
          color = MaterialTheme.colorScheme.onErrorContainer,
        )
      }
    }
  }

  private fun processErrorMessage(errMsg: String): String {
    val splitMessage = errMsg.split("\n")
    if (splitMessage.isEmpty() || splitMessage[0].isEmpty()) {
      return errMsg
    }
    for (i in exceptionType.indices) {
      if (splitMessage[0].contains(exceptionType[i])) {
        val additionalInfo = splitMessage[0].substringAfter(exceptionType[i])
        return errMessage[i] + additionalInfo
      }
    }
    return errMsg
  }
}
