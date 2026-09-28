package com.proyecto.apprickmorty.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.proyecto.apprickmorty.ui.screens.CharacterDetailScreen
import com.proyecto.apprickmorty.ui.screens.HomeScreen
import com.proyecto.apprickmorty.ui.screens.SplashScreen
import com.proyecto.apprickmorty.ui.viewmodel.CharacterViewModel

private object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val DETAIL = "detail/{characterId}"
    fun detail(id: Int) = "detail/$id"
}

@Composable
fun RickverseNavGraph() {
    val navController = rememberNavController()
    val characterViewModel: CharacterViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onFinished = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.HOME) {
            HomeScreen(
                viewModel = characterViewModel,
                onCastMemberClick = { id ->
                    navController.navigate(Routes.detail(id))
                }
            )
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: 1
            CharacterDetailScreen(
                characterId = characterId,
                onBack = { navController.popBackStack() },
                viewModel = characterViewModel
            )
        }
    }
}
