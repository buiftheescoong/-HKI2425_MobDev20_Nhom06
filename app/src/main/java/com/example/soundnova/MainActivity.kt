package com.example.soundnova

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.background2), // Tải ảnh từ tài nguyên
            contentDescription = null, // Miêu tả cho ảnh (đặt null nếu không cần thiết)
            contentScale = ContentScale.Crop, // Để ảnh lấp đầy Box mà không bị méo
            modifier = Modifier.matchParentSize() // Để ảnh chiếm toàn bộ kích thước của Box
        )
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            // Welcome Text
            Text(
                text = stringResource(R.string.textWelcome),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 40.dp)
            )

            // Feel Like Today Text
            Text(
                text = stringResource(R.string.textFeelLikeToday),
                fontSize = 16.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 4.dp)
            )

            // Search Bar
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(
                        text = stringResource(R.string.editTextSearch),
                        color = Color.LightGray
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.icon_search),
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(50.dp),
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color.Transparent,
//                    unfocusedBorderColor = Color.Transparent
//                )
            )

//
//                    Text(
//                        text = stringResource(R.string.tabRecent),
//                        color =  Color.White,
//                        fontSize = 16.sp,
//                        modifier = Modifier.padding(end = 20.dp)
//                    )
//            RecommendedSongsList()
//            // Recommended Songs RecyclerView
//            Spacer(modifier = Modifier.height(20.dp))
//            Text(
//                text = stringResource(R.string.textFavorite),
//                fontSize = 18.sp,
//                color = Color.White,
//                modifier = Modifier.padding(start = 2.dp)
//            )
//            FavoriteSongsList()
        }

//        // Bottom Menu
//        Box(
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .fillMaxWidth()
//                .height(71.dp)
//        ) {
//            MenuContent()
//        }
    }
}

@Composable
fun RecommendedSongsList() {
    LazyRow {
        items(10) { index ->
            Text("Song $index", modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun FavoriteSongsList() {
    LazyColumn {
        items(10) { index ->
            Text("Favorite Song $index", modifier = Modifier.padding(8.dp))
        }
    }
}

//@Composable
//fun MenuContent() {
//    // Thay thế bằng menu thực tế của bạn
//    Text("Menu", color = Color.White, modifier = Modifier.fillMaxSize())
//}

@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    // Gọi hàm giao diện chính của bạn tại đây
MainScreen()
}
