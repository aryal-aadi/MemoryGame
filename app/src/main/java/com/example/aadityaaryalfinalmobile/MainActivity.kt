package com.example.aadityaaryalfinalmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import com.example.aadityaaryalfinalmobile.ui.theme.AadityaaryalfinalmobileTheme
import com.example.aadityaaryalfinalmobile.screens.WelcomeScreen
import com.example.aadityaaryalfinalmobile.screens.GameScreen
import com.example.aadityaaryalfinalmobile.screens.HighScoresScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

//first point of the app
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AadityaaryalfinalmobileTheme { //custom theme
                AppNavigator()
            }
        }
    }
}

// sealed class to define all navigation pages and their nav details
sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Welcome : Screen("welcome", "Home", Icons.Filled.Home)
    object Game : Screen("game", "Game", Icons.Filled.Star)
    object HighScores : Screen("highscores", "Scores", Icons.Filled.Star)
}

//main composable that handles overall app navigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigator() {
    val navController = rememberNavController() //defining navigation controller
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    //bottom nav items
    val bottomNavItems = listOf(
        Screen.Welcome,
        Screen.HighScores
    )

    Scaffold( //for top bar, bottom bar, and screen content
        topBar = {
            TopAppBar(
                title = {
                    Text("Memory Game", color = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6A1B9A)
                )
            )
        },
        bottomBar = {
            if (currentRoute in bottomNavItems.map { it.route }) {
                NavigationBar(
                    containerColor = Color(0xFF6A1B9A)
                ) {
                    bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(screen.icon, contentDescription = screen.label, tint = Color.White)
                            },
                            label = {
                                Text(screen.label, color = Color.White)
                            },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(0) { inclusive = true }
                                    launchSingleTop = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.Yellow,
                                selectedTextColor = Color.Yellow,
                                indicatorColor = Color(0xFF8E24AA),
                                unselectedIconColor = Color.White.copy(alpha = 0.7f),
                                unselectedTextColor = Color.White.copy(alpha = 0.7f)
                            )
                        )
                    }
                }
            }
        }
    )
    { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Welcome.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Welcome.route) {
                WelcomeScreen(onNavigate = { navController.navigate(it) })
            }
            composable(Screen.Game.route) {
                GameScreen(onNavigate = { navController.navigate(it) })
            }
            composable(Screen.HighScores.route) {
                HighScoresScreen(onNavigate = { navController.navigate(it) })
            }
        }
    }
}


