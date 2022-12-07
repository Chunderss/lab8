package com.example.lab8

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.lab8.ui.theme.Lab8Theme
import com.example.lab8.ui.theme.greenColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab8Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(backgroundColor = greenColor,
                                title = {
                                    Text(
                                        text = "My Application with Database",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                    )
                                })
                        }
                    ) {
                        addDataToDatabase(LocalContext.current)
                    }
                }
            }
        }
    }
}

@Composable
fun addDataToDatabase(
    context: Context
) {
    val activity = context as Activity

    val name = remember {
        mutableStateOf(TextFieldValue())
    }
    val contact = remember {
        mutableStateOf(TextFieldValue())
    }
    val dob = remember {
        mutableStateOf(TextFieldValue())
    }

    val popupVal = remember {
        mutableStateOf(false)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        var dbHandler: DBHandler = DBHandler(context)

        Text(
            text = "Please enter the details below",
            color = greenColor, fontSize = 20.sp, fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            placeholder = { Text(text = "Name") },

            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = contact.value,
            onValueChange = { contact.value = it },
            placeholder = { Text(text = "Contact") },

            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = dob.value,
            onValueChange = { dob.value = it },
            placeholder = { Text(text = "Date of birth") },

            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                dbHandler.addNewUser(
                    name.value.text,
                    contact.value.text,
                    dob.value.text,
                )
                Toast.makeText(context, "Added User to Database", Toast.LENGTH_SHORT).show()
            }) {
            Text(
                text = "INSERT",
                Modifier.width(100.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )

        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                dbHandler.updateUser(
                    name.value.text,
                    name.value.text,
                    contact.value.text,
                    dob.value.text,
                )
                Toast.makeText(context, "Updated User", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(
                text = "UPDATE",
                Modifier.width(100.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                dbHandler.removeUsers(name.value.text)
                Toast.makeText(context, "Removed User", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(
                text = "DELETE",
                Modifier.width(100.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                popupVal.value = true
            }
        ) {
            Text(
                text = "SHOW USERS",
                Modifier.width(100.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        if (popupVal.value) {
            val myList: List<CourseModel> = myRead(context)
            Popup {
                Box(
                    Modifier
                        .padding(10.dp)
                        .width(350.dp)
                        .background(color = greenColor, RoundedCornerShape(10.dp))
                ) {
                    Column(
                        Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "User Entries",
                            Modifier.padding(10.dp),
                            color = Color.White,
                        )
                        LazyColumn {
                            itemsIndexed(myList) { index, item ->
                                Card(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    elevation = 5.dp
                                ) {
                                    Text(
                                        text = myList[index].Name + " " + myList[index].Contact + " " + myList[index].Dob,
                                        Modifier.background(color = greenColor),
                                        color = Color.White,
                                        fontSize = 20.sp,
                                    )
                                }
                            }
                        }
                        Row (
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                                ) {
                            Button(
                                onClick = {
                                    popupVal.value = false
                                }
                            ) {
                                Text(
                                    text = "Close",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun myRead(
    context: Context?,
): ArrayList<CourseModel> {
    lateinit var userList: List<CourseModel>
    userList = ArrayList<CourseModel>()

    val dbHandler: DBHandler = DBHandler(context)
    userList = dbHandler.readUsers()
    return userList
}

