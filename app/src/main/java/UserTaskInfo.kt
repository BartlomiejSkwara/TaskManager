import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime
import kotlin.random.Random

data class UserTaskInfo(
    val topic:String,
    val description:String,
    val deadline:LocalDateTime,
    val color:Color = Color.hsv(Random.nextInt(0,360).toFloat(),0.885f,0.718f)
)
