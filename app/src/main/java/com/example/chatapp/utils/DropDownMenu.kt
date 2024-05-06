package com.example.chatapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.addRoom.AddRoomViewModel
import com.example.chatapp.model.Category


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectRoomCategory(
    viewModel: AddRoomViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){

    var isFirstTimeChoosing by rememberSaveable{
       mutableStateOf(true)
    }

    ExposedDropdownMenuBox(
        expanded = viewModel.dropDownMenuState.value ,
        onExpandedChange = {

            viewModel.dropDownMenuState.value = it
        },
       ) {

        OutlinedTextField(
            value = if (isFirstTimeChoosing == true) "" else viewModel.selectedCategoryItem.value.name?:"" ,
            onValueChange = {
                },
            placeholder = { 
                Text(text = "Select Room Category") },
            // update compose version to solve bug that keyboard shows
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded =viewModel.dropDownMenuState.value)

            },
            modifier = Modifier.menuAnchor(),
        )
        ExposedDropdownMenu(
            expanded = viewModel.dropDownMenuState.value,
            onDismissRequest = { viewModel.dropDownMenuState.value = false },

            ) {

            viewModel.categoryList.forEach {
                DropdownMenuItem(
                    leadingIcon = {
                                  Image(painter = painterResource(id = it.image?:0),
                                      contentDescription ="dropdown_menu_Item",
                                      contentScale = ContentScale.Crop,
                                      modifier = Modifier
                                          .width(50.dp)
                                          .height(50.dp))
                    },
                    text = {
                        Text(text = it.name?:"")
                    }, onClick = {
                        isFirstTimeChoosing = false
                        viewModel.selectedCategoryItem.value = it
                        viewModel.dropDownMenuState.value = false

                    })
            }

        }
        
    }
}

