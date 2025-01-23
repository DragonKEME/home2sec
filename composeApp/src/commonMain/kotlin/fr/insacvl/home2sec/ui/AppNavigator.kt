package fr.insacvl.home2sec.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.insacvl.home2sec.data.DeviceRepository
import fr.insacvl.home2sec.ui.deviceDetails.DeviceDetailScreen
import fr.insacvl.home2sec.ui.listScreen.HomeStateCheck

sealed class Page(val route: String){
    data object List: Page("list")
    data object DeviceDetail: Page("deviceDetail")
}


@Composable
fun HomeNavigation(deviceRepository: DeviceRepository){
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Page.List.route
    ) {
        composable(route = Page.List.route) {
            HomeStateCheck(navController, deviceRepository, modifier = Modifier)
        }
        composable(route = Page.DeviceDetail.route) {
            DeviceDetailScreen(navController, deviceRepository)
        }
    }

}