package com.example.composeproject.designsysytem.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.composeproject.designsysytem.theme.BrandColor
import com.example.composeproject.designsysytem.theme.ComposeProjectTheme
import com.example.composeproject.designsysytem.theme.TitleLarge
import com.example.composeproject.designsysytem.theme.White

@Composable
fun CustomDialog(
    dialogType: DialogType,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    errorMessage: String? = null,
    isLoading: Boolean = false
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .then(
                    if (dialogType == DialogType.ERROR) {
                        Modifier.border(
                            width = 2.dp,
                            color = Color.Red,
                            shape = RoundedCornerShape(16.dp)
                        )
                    } else {
                        Modifier
                    }
                ),
            shape = RoundedCornerShape(16.dp),
            color = White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = when (dialogType) {
                        DialogType.CLEAR_BASKET -> "Sepeti Temizle"
                        DialogType.COMPLETE_ORDER -> "Siparişi Tamamla"
                        DialogType.ERROR -> "Hata"
                    },
                    style = TitleLarge,
                    color = if (dialogType == DialogType.ERROR) Color.Red else Color.Black,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Message
                Text(
                    text = when (dialogType) {
                        DialogType.CLEAR_BASKET -> "Sepetinizdeki tüm ürünleri silmek istediğinizden emin misiniz?"
                        DialogType.COMPLETE_ORDER -> "Siparişinizi tamamlamak istediğinizden emin misiniz?"
                        DialogType.ERROR -> errorMessage ?: "Bir hata oluştu. Lütfen tekrar deneyin."
                    },
                    color = if (dialogType == DialogType.ERROR) Color.Red else Color.Gray,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (dialogType == DialogType.ERROR) {
                        // Error dialog için sadece OK butonu
                        Button(
                            onClick = onDismiss,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Tamam",
                                color = White
                            )
                        }
                    } else {
                        // Cancel Button
                        Button(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            enabled = !isLoading,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "İptal",
                                color = White
                            )
                        }
                        
                        // Confirm Button
                        Button(
                            onClick = onConfirm,
                            modifier = Modifier.weight(1f),
                            enabled = !isLoading,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BrandColor
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            if (isLoading) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier.size(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        val infiniteTransition = rememberInfiniteTransition(label = "dialog_spinner")
                                        val rotation by infiniteTransition.animateFloat(
                                            initialValue = 0f,
                                            targetValue = 360f,
                                            animationSpec = infiniteRepeatable(
                                                animation = tween(1000, easing = LinearEasing),
                                                repeatMode = RepeatMode.Restart
                                            ),
                                            label = "dialog_rotation"
                                        )
                                        
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .rotate(rotation)
                                                .background(
                                                    color = White,
                                                    shape = CircleShape
                                                )
                                        )
                                    }
                                    Text(
                                        text = "Yükleniyor...",
                                        color = White
                                    )
                                }
                            } else {
                                Text(
                                    text = when (dialogType) {
                                        DialogType.CLEAR_BASKET -> "Temizle"
                                        DialogType.COMPLETE_ORDER -> "Tamamla"
                                        DialogType.ERROR -> "Tamam"
                                    },
                                    color = White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CustomDialogPreview() {
    ComposeProjectTheme {
        CustomDialog(
            dialogType = DialogType.CLEAR_BASKET,
            onDismiss = {},
            onConfirm = {}
        )
    }
}

@Preview
@Composable
private fun ErrorDialogPreview() {
    ComposeProjectTheme {
        CustomDialog(
            dialogType = DialogType.ERROR,
            onDismiss = {},
            onConfirm = {},
            errorMessage = "İnternet bağlantınızı kontrol edin"
        )
    }
} 