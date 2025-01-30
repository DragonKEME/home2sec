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
import fr.insacvl.home2sec.ui.login.LoginScreen
import fr.insacvl.home2sec.utils.DateUtils

sealed class Page(val route: String){
    data object List: Page("list")
    data object DeviceDetail: Page("deviceDetail")
    data object Login: Page("login")
}


@Composable
fun HomeNavigation(deviceRepository: DeviceRepository, dateUtils: DateUtils){
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Page.Login.route
    ) {
        composable(route = Page.List.route) {
            HomeStateCheck(navController, deviceRepository, dateUtils, modifier = Modifier)
        }
        composable(route = Page.DeviceDetail.route) {
            DeviceDetailScreen(navController, deviceRepository, dateUtils)
        }
        composable(route = Page.Login.route) {
            LoginScreen(navController, deviceRepository)
        }
    }

}