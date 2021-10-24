package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.apache.commons.io.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // Remove item from list
                listOfTasks.removeAt(position)
                // Notify adapter that our data has changed
                adapter.notifyDataSetChanged()
                // save data since we removed an item
                saveItems()
            }
        }

        // Detect when user clicks on add button
//        findViewById<Button>(R.id.button).setOnClickListener {
            // the event listener
//            Log.i("lol", "HAHAH")
  //      }

        // overrides listOfTasks variable
        loadItems()

        // look up recyclerVew in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        // set up button and input field so user can enter a task and add to list
        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // set event handler on button
        findViewById<Button>(R.id.button).setOnClickListener {
            // grab text from field
            val userInputtedTask = inputTextField.text.toString()

            // add string to our list of tasks
            listOfTasks.add(userInputtedTask)

            // notify adapter that data has mutated and indicate which position to insert data
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // clear text field after adding to list
            inputTextField.setText("")

            // save the data
            saveItems()
        }
    }

    // save data the user has inputted

    // save data by writing and reading from a file

    // get the file we need
    fun getDataFile() : File {
        return File(filesDir, "data.txt")
    }

    // load the items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // save item to file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}