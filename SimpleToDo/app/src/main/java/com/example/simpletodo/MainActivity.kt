package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils.writeLines
import org.apache.commons.io.IOUtils.writeLines
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    lateinit var adapter: TaskItemAdapter
    var listOfTasks = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                //remove item from list
                listOfTasks.removeAt(position)
                //Notify adapter that data has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

       // findViewById<Button>(R.id.button).setOnClickListener {
       //     Log.i("Michael", "User clicked on Button")
       // }

        loadItems()

        // Lookup the recyclerview in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        // Set up button and input field so user can enter a task
        findViewById<Button>(R.id.button).setOnClickListener {
            //grab the text user has inputted
            val userInputtedTask = inputTextField.text.toString()
            //add string to list of tasks
            listOfTasks.add(userInputtedTask)
            // notify adapter data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)
            //reset text field
            inputTextField.setText("")

            saveItems()

        }
}
    // save the data user has inputted

    //save data by writing and reading a file

    // get the file we need
    fun getDataFile() : File {

        // every line is going to be a specific task
        return File(filesDir, "tasks.txt")
    }
    //load the items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks =
                org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioException : IOException) {
            ioException.printStackTrace()
        }
    }

    //save items by writing them into ou data file
    fun saveItems() {
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        }catch (ioException : IOException) {
            ioException.printStackTrace()
        }
    }
}