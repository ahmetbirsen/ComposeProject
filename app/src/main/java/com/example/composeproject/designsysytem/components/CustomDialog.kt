package com.example.composeproject.designsysytem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    onConfirm: () -> Unit
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
                .padding(32.dp),
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
                    },
                    style = TitleLarge,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Message
                Text(
                    text = when (dialogType) {
                        DialogType.CLEAR_BASKET -> "Sepetinizdeki tüm ürünleri silmek istediğinizden emin misiniz?"
                        DialogType.COMPLETE_ORDER -> "Siparişinizi tamamlamak istediğinizden emin misiniz?"
                    },
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Cancel Button
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
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
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrandColor
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = when (dialogType) {
                                DialogType.CLEAR_BASKET -> "Temizle"
                                DialogType.COMPLETE_ORDER -> "Tamamla"
                            },
                            color = White
                        )
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