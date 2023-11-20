import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
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
fun CpumarketImageFileFormatDropdown(
    value: ImageFileFormat,
    label: @Composable (() -> Unit)?,
    onValueChange: (ImageFileFormat) -> Unit,
) {
    var text by remember(value) { mutableStateOf(value.toString()) }
    var isExpanded by remember { mutableStateOf(false) }
    Box {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { },
                enabled = false,
                label = label
            )
            DropdownMenu(
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
        Modifier
            .fillMaxWidth()
    }
}