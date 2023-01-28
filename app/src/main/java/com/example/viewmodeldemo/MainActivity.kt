package com.example.viewmodeldemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.viewmodeldemo.ui.theme.ViewModelDemoTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScreenSetup()
                }
            }
        }
    }
}

@Composable
fun ScreenSetup(viewModel: DemoViewModel = viewModel()) {
    MainScreen(
        isFahrenheit = viewModel.isFahrenheit,
        result = viewModel.result,
        convertTemp = { viewModel.convertTemp(it) },
        switchChange = { viewModel.switchChange() }
    )
}

@Composable
fun MainScreen(
    isFahrenheit: Boolean,
    result: String,
    convertTemp: (String) -> Unit,
    switchChange: () -> Unit
) {

}

@Composable
fun InputRow(
    isFahrenheit: Boolean,
    textState: String,
    switchChange: () -> Unit,
    onTextChange: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = isFahrenheit,
            onCheckedChange = { switchChange() }
        )

        OutlinedTextField(
            value = textState,
            onValueChange = { onTextChange(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            label = { Text(text = "Enter temperature") },
            modifier = Modifier.padding(10.dp),
            textStyle = TextStyle(fontWeight = FontWeight.Bold,
                                    fontSize = 30.sp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_ac_unit_24), 
                    contentDescription = "frost",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        )
        
        Crossfade(
            targetState = isFahrenheit,
            animationSpec = tween(2000)
        ) { visible ->
            when(visible) {
                true -> Text(text = "\u2109", style = MaterialTheme.typography.h4)
                false -> Text(text = "\u2103", style = MaterialTheme.typography.h4)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview(model: DemoViewModel = viewModel()) {
    ViewModelDemoTheme {
        MainScreen(
            isFahrenheit = model.isFahrenheit,
            result = model.result,
            convertTemp = {
                model.convertTemp(it)
        }, switchChange = {model.switchChange()}
        )
    }
}













