package com.example.navegacionejemplos.navegacion
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
@Composable
fun Space(){
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun TitleView(name: String){
    Text(text = name, fontSize = 40.sp, fontWeight = FontWeight.Bold)
}

@Composable
fun MainButton(name: String, backColor: Color, color: Color, onClick:() -> Unit){
    Button(onClick = onClick, colors = ButtonDefaults.buttonColors(
        contentColor = color,
        containerColor = backColor
    )){
        Text(text = name)
    }
}
@Composable
fun ActionButton(){
    FloatingActionButton(onClick = { /*TODO*/ },
        containerColor = Color.Red,
        contentColor = Color.White
        ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Agregar"
        )
    }
}
@Composable
fun TitleBar(name: String){
    Text(text = name, fontSize = 25.sp, color = Color.White)
}

@Composable
fun MainIconButton(icon: ImageVector, onClick: () -> Unit){
    IconButton(onClick = onClick) {
        Icon(imageVector = icon,
            contentDescription = null,
            tint = Color.White)
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController){
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { TitleBar(name = "Home View")},
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Red
                )
            )
        },
        floatingActionButton = {
            ActionButton()
        }
    ){
        ContentHomeView(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun ContentHomeView(navController: NavController){
    val id = 123
    var opcional by remember { mutableStateOf("")}
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TitleView(name = "Home View")
        Space()

        TextField(
            value = opcional,
            onValueChange = {opcional = it},
            label = { Text(text= "Opcional")}
        )
        MainButton(name = "Detail view", backColor = Color.Red, color = Color.White) {
            navController.navigate("Detail/${id}/?${opcional}")
        }
    }
 }
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailView(navController: NavController, id: Int, opcional:String?){
    Scaffold (
        topBar = {
            TopAppBar(
                title = { TitleBar(name = "Detail view")},
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Blue
                ),
                navigationIcon = {
                    MainIconButton(icon = Icons.Default.ArrowBack){
                        navController.popBackStack()
                    }
                }
            )
        }
    ){
        ContentDetailView(navController, id, opcional)
    }
}

@Composable
    fun ContentDetailView(navController: NavController, id: Int, opcional: String?){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TitleView(name = "Detail View")
            Space()
            TitleView(name = id.toString())
            Space()
            if (opcional == ""){
                Spacer(modifier = Modifier.height(0.dp))
            }else{
                TitleView(name = opcional.orEmpty())
            }
            MainButton(name = "Return home", backColor = Color.Blue, color = Color.White){
                navController.popBackStack()
        }
    }
}
@Composable
fun NavManager(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home"){
        composable("Home"){
            HomeView(navController)
        }
        composable("Detail/{id}/?{opcional}", arguments = listOf(
            navArgument("id"){type = NavType.IntType },
            navArgument("opcional"){ type = NavType.StringType},
        )){
            val id = it.arguments?.getInt("id") ?: 0
            val opcional = it.arguments?.getString("opcional") ?: ""
            DetailView(navController, id, opcional)
        }
    }
}