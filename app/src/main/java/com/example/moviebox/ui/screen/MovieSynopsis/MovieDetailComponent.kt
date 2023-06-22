package com.example.moviebox.ui.screen.MovieSynopsis

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviebox.R

@Composable
fun MovieDetail(movieId: Int) {
    val availablity = true//from API
    val message:String
    if(availablity){
        message = "Available in Cinemas"
    }else{
        message = "Coming Soon In Cinemas"
    }

    Column(

    ) {
        Card(
            modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(16.dp),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Box(modifier = Modifier) {
                Image(
                    painterResource(id = R.drawable.nature),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
//                Box(modifier = Modifier
//                    .fillMaxSize()
//                    .background(
//                        Brush.verticalGradient(
//                            colors = listOf(
//                                Color.Transparent,
//                                Color.Black
//                            ),
//                            startY = 300f
//                        )
//                    ))
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        text = message,
                        style = TextStyle(color = Color.White, fontSize = 16.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Row(Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Star, contentDescription = null, tint = Color.Red )
            Text( text = "Rating" ,modifier = Modifier.padding(start = 10.dp) )
            Text( text = "Votes" ,modifier = Modifier.padding(start = 10.dp) , fontSize = 10.sp )
        }

        Row(Modifier.padding(horizontal = 16.dp).padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) {
            Text( text = "2D, 3D" ,modifier = Modifier.padding(start = 0.dp).background(Color.LightGray) ,fontSize = 10.sp)
            Text( text = "Lanuages" ,modifier = Modifier.padding(start = 10.dp).background(Color.LightGray) , fontSize = 10.sp )
        }
        Text( text = "run time and genres and release date" ,modifier = Modifier.padding(start = 16.dp) , fontSize = 10.sp )
        Text( text = "Movie summary and description I guess" ,modifier = Modifier.padding(start = 16.dp).padding(vertical = 5.dp) , fontSize = 10.sp )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewMovieDetailScreen(){
    MovieDetail(123)
}