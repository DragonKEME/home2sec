package fr.insacvl.home2sec.ui.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun Popup(
    modifier: Modifier = Modifier,
    onDismiss: (() -> Unit),
    background: @Composable (() -> Unit)? = null,
    content: @Composable (BoxScope.() -> Unit)
) {
    if (background != null){
        background()
    }
    Surface(
        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
        modifier = modifier.fillMaxSize().clickable(
            // Disallow click effect on surface (https://stackoverflow.com/questions/66703448/how-to-disable-ripple-effect-when-clicking-in-jetpack-compose)
            interactionSource = MutableInteractionSource(),
            indication = null
        ) {
            // Dismiss Name screen when click outside the box
            onDismiss()
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Box(modifier = Modifier.clip(RoundedCornerShape(10.dp))) {
                Box(
                    Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(20.dp),
                    contentAlignment = Alignment.Center,
                    content = content
                )
            }
        }
    }
}
