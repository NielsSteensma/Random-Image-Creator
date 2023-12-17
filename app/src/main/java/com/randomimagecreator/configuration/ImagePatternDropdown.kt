import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.randomimagecreator.configuration.ImagePattern

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImagePatternDropdown(
    value: ImagePattern,
    label: @Composable (() -> Unit)?,
    onValueChange: (ImagePattern) -> Unit
) {
    var resourceId by remember(value) { mutableIntStateOf(value.localizationResourceId) }
    var isExpanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
    ) {
        OutlinedTextField(
            value = stringResource(resourceId),
            onValueChange = { },
            enabled = false,
            label = label,
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            ImagePattern.values().forEach {
                DropdownMenuItem(onClick = {
                    isExpanded = false
                    resourceId = it.localizationResourceId
                    onValueChange(it)
                }) {
                    Text(stringResource(it.localizationResourceId))
                }
            }
        }
    }
}