import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.randomimagecreator.configuration.ImageFileFormat

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageFileFormatDropdown(
    value: ImageFileFormat,
    label: @Composable (() -> Unit)?,
    onValueChange: (ImageFileFormat) -> Unit
) {
    var text by remember(value) { mutableStateOf(value.toString()) }
    var isExpanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { },
            enabled = false,
            label = label,
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            ImageFileFormat.values().forEach {
                DropdownMenuItem(onClick = {
                    isExpanded = false
                    text = it.name
                    onValueChange(it)
                }) {
                    Text(it.name)
                }
            }
        }
    }
}
