package org.robok.engine.ui.activities

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
 *   along with Robok.  If not, see <https://www.gnu.org/licenses/>.
 */ 

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme

import soup.compose.material.motion.animation.materialSharedAxisXIn
import soup.compose.material.motion.animation.materialSharedAxisXOut

import org.robok.engine.BuildConfig
import org.robok.engine.Drawables
import org.robok.engine.strings.Strings
import org.robok.engine.ui.theme.RobokTheme
import org.robok.engine.models.project.ProjectTemplate
import org.robok.engine.ui.screens.home.HomeScreen
import org.robok.engine.core.utils.base.RobokActivity
import org.robok.engine.ui.screens.project.create.CreateProjectScreen
import org.robok.engine.feature.settings.compose.screens.ui.SettingsScreen
import org.robok.engine.feature.settings.compose.screens.ui.app.SettingsAppScreen
import org.robok.engine.feature.settings.compose.screens.ui.editor.SettingsCodeEditorScreen
import org.robok.engine.feature.settings.compose.screens.ui.libraries.LibrariesScreen
import org.robok.engine.feature.settings.compose.screens.ui.about.AboutScreen
import org.robok.engine.feature.settings.compose.screens.ui.rdkmanager.ConfigureRDKScreen
import org.robok.engine.ui.screens.project.manage.ManageProjects

class MainActivity : RobokActivity() {

    companion object {
        const val MSAX_SLIDE_DISTANCE: Int = 100
        const val MSAX_DURATION: Int = 700
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        val defaultTemplate = ProjectTemplate(
               name = getString(Strings.template_name_empty_game),
               packageName = "com.robok.empty",
               zipFileName = "empty_game.zip",
               javaSupport = true,
               kotlinSupport = false,
               imageResId = Drawables.ic_empty_game
        )
        setContent {
            RobokTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        enterTransition = { materialSharedAxisXIn(forward = true, slideDistance = MSAX_SLIDE_DISTANCE, durationMillis = MSAX_DURATION) },
                        exitTransition = { materialSharedAxisXOut(forward = true, slideDistance = MSAX_SLIDE_DISTANCE, durationMillis = MSAX_DURATION) },
                        popEnterTransition = { materialSharedAxisXIn(forward = false, slideDistance = MSAX_SLIDE_DISTANCE, durationMillis = MSAX_DURATION) },
                        popExitTransition = { materialSharedAxisXOut(forward = false, slideDistance = MSAX_SLIDE_DISTANCE, durationMillis = MSAX_DURATION)  }
                    ) {
                         composable("home") { HomeScreen(navController = navController, actContext = this@MainActivity) }
                         composable("settings") { SettingsScreen(navController = navController) }
                         composable("settings/app") { SettingsAppScreen(navController = navController) }
                         composable("settings/codeeditor") { SettingsCodeEditorScreen(navController = navController) }
                         composable("settings/libraries") { LibrariesScreen(navController = navController) }
                         composable("settings/configure_rdk") { ConfigureRDKScreen(navController = navController) }
                         composable("settings/about") { AboutScreen(navController = navController, version = BuildConfig.VERSION_NAME) }
                         composable("project/create") { CreateProjectScreen(navController = navController, projectTemplate = defaultTemplate) }
                        composable("project/manage"){ ManageProjects()}
                    }
                }
            }
        }
    }
}