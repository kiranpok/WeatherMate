import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weathermate.R
import com.example.weathermate.model.WeatherItem
import com.example.weathermate.utils.formatDateTime

@Composable
fun SunsetSunriseRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.ic_sunrise),
                contentDescription = "sunrise icon",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDateTime(weather.sunrise),
                style = typography.caption,
                color = Color.White,
                modifier = Modifier.padding(4.dp)
            )
        }
        Row() {
            Text(
                text = formatDateTime(weather.sunset),
                style = typography.caption,
                color = Color.White,
                modifier = Modifier.padding(4.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_sunset),
                contentDescription = "sunset icon",
                modifier = Modifier.size(30.dp)
            )

        }

    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_humidity),
                contentDescription = "humidity icon",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = " ${weather.humidity}%",
                style = typography.body1,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

        }
        Row() {
            Icon(
                painter = painterResource(id = R.drawable.ic_windspeed),
                contentDescription = "wind icon",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "mph: ${weather.speed} m/s",
                style = typography.body1,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterVertically)

            )

        }
        Row() {

            Icon(
                painter = painterResource(id = R.drawable.ic_precitipation),
                contentDescription = "pressure icon",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "psi: ${weather.pressure}",
                style = typography.body1,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }


    }
}


@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl), contentDescription = "weather icon",
        modifier = Modifier.size(150.dp)
    )
}

