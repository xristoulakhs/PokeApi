package com.example.pokemonexplorerapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(navController: NavController){

    val itemsList = List(10) { it }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokemon Results") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) {
        paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues).padding(start = 10.dp, end = 10.dp)) {
            items(itemsList) { item ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(135.dp)
                        .padding(top = 10.dp)
                        .clickable {  },
                    shape = RoundedCornerShape(10.dp),
                    shadowElevation = 4.dp,
                    color = Color.White,
                ){

                }
            }
        }
    }
}