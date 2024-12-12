import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weathermate.R
import com.example.weathermate.model.WeatherItem
import com.example.weathermate.utils.formatDateTime
import com.example.weathermate.utils.formatDay
import com.example.weathermate.utils.formatDecimals
import androidx.compose.material.Icon
import com.example.weathermate.utils.ActivityMappingUtils
import com.example.weathermate.utils.formatHour
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun TodayWeatherSection(hourlyWeatherList: List<WeatherItem>) {
    val currentTime = LocalDateTime.now()
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(items = hourlyWeatherList) { index, item ->
            val itemTime = currentTime.plusHours(index.toLong())
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(Color(0xFF003366), shape = RoundedCornerShape(12.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = itemTime.format(timeFormatter),
                    style = MaterialTheme.typography.caption,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                WeatherStateImage(imageUrl = "https://openweathermap.org/img/wn/${item.weather[0].icon}.png")
                Text(
                    text = formatDecimals(item.temp.day),
                    style = typography.body1,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun NextWeekWeatherSection(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"
    val (activitySuggestion, activityIconRes) = ActivityMappingUtils.mapWeatherToActivity( weather.weather[0].description, weather.temp.max)

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formatDay(weather.dt),
            style = typography.body1,
            color = Color.White,
            modifier = Modifier.padding(4.dp)
        )
        WeatherStateImage(imageUrl = imageUrl)
        Text(
            text = weather.weather[0].description,
            style = typography.caption,
            color = Color.White,
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(formatDecimals(weather.temp.max))
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                    append(" ${formatDecimals(weather.temp.min)}")
                }
            },
            style = typography.body1,
            color = Color.White,
            modifier = Modifier.padding(4.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(id = activityIconRes),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = activitySuggestion,
                style = typography.body2,
                color = Color.White
            )
        }
    }

    Divider(
        color = Color(0xFF3A83D6), thickness = 1.dp,
    )
}
@Composable
fun SunsetSunriseRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
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
        modifier = Modifier.size(70.dp)
    )
}