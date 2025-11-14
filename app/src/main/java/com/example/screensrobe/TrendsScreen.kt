import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.screensrobe.MenuDesplegable

@Composable
fun TrendsScreen(navController: NavController, modifier: Modifier = Modifier) {
    val trends = listOf(
        "Tecnológico de Aguascalientes",
        "Academia U.A.",
        "KYUNITY"
    )

    Scaffold(
        topBar = {
            Surface(
                color = Color(0xFFF4F2EB),
                shadowElevation = 2.dp,
                modifier = Modifier.statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Caja de búsqueda (simulada)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp)
                            .background(Color.White, RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Buscar",
                            color = Color.LightGray,
                            modifier = Modifier.padding(start = 14.dp),
                            fontSize = 15.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    MenuDesplegable(navController)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("public") },
                containerColor = Color(0xFF7EB5AB),
                shape = CircleShape,
                modifier = Modifier
                    .padding(bottom = 80.dp, end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar",
                    tint = Color.White
                )
            }
        },
        containerColor = Color(0xFFF4F2EB)
    ) { padding ->

        // ✅ Este Box principal permite usar .align() correctamente
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF4F2EB))
        ) {
            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Encabezado "Tendencias"
                Text(
                    text = "Tendencias",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .background(Color(0xFFCF5858), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )

                // Tarjetas de tendencias
                trends.forEach { trend ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF3D3AC), RoundedCornerShape(10.dp))
                            .padding(horizontal = 12.dp, vertical = 14.dp)
                    ) {
                        Text(text = trend, color = Color.Black, fontSize = 15.sp)
                    }
                }
            }

            // ✅ Botón flotante (ya sin error de align)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            ) {
                IconButton(
                    onClick = { navController.navigate("public")},
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color(0xFF7EB5AB), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
