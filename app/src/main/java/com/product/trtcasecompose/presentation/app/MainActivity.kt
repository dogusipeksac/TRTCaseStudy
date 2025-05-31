package com.product.trtcasecompose.presentation.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.product.trtcasecompose.presentation.app.navigation.AppNavGraph
import com.product.trtcasecompose.presentation.app.theme.TrtCaseComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrtCaseComposeTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}


