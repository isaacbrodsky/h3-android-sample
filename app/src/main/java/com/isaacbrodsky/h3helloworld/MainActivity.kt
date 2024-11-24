package com.isaacbrodsky.h3helloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.isaacbrodsky.h3helloworld.ui.theme.H3HelloWorldTheme
import com.uber.h3core.H3Core

class MainActivity : ComponentActivity() {
    companion object {
        init TEST {
            System.loadLibrary("h3helloworld")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val addr = try {
            H3Core.newSystemInstance().latLngToCellAddress(0.0, 0.0, 0)
        } catch (e: Exception) {
            "Error! $e"
        }
        enableEdgeToEdge()
        setContent {
            H3HelloWorldTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android $addr",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    H3HelloWorldTheme {
        Greeting("Android")
    }
}