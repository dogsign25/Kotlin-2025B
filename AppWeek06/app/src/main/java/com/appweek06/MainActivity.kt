package com.appweek06

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate : AppWeek05 started")
        //function
        setupViews()
        setupListViews()
        setupListeners()
        addInitialData()
    }
    // UI component (widget)
    private lateinit var buttonAdd : Button
    private lateinit var buttonClear : Button
    private lateinit var editTextStudent : EditText
    private lateinit var textViewCount : TextView
    private lateinit var listView : ListView

    // Collection
    private lateinit var studentList : ArrayList<String>
    private lateinit var adapter : ArrayAdapter<String>

    companion object{
        private const val TAG = "KotlinWeek06App"
    }

    // Functions
    private fun setupViews(){
        listView = findViewById(R.id.listViewStudents)
        editTextStudent = findViewById(R.id.editTextStudent)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonClear = findViewById(R.id.buttonClear)
        textViewCount = findViewById(R.id.textViewCount)

        studentList = ArrayList()
        Log.d(TAG,"Views initialized")
    }

    private fun setupListViews(){
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentList)
        listView.adapter = adapter
        Log.d(TAG,"ListViews and Adapter setup completed")
    }

    private fun setupListeners(){
        buttonAdd.setOnClickListener{
            addStudent()
        }
        buttonClear.setOnClickListener{
            clearAllStudent()
        }
        listView.setOnItemLongClickListener{
                _, _, position, _ -> removeStudent(position) //parent, view, position, id -> ...
            true
        }
        listView.setOnItemClickListener{
                _, _, position, _ -> val studentName = studentList[position]
            Toast.makeText(
                this,
                "Selected: $studentName (Position: ${position+1}",
                Toast.LENGTH_SHORT
            ).show()
            Log.d(TAG, "Selected: $studentName at Position: $position")
        }
        Log.d(TAG, "Event Listener setup completed")
    }

    private fun addStudent(){
        val studentName = editTextStudent.text.toString().trim()

        if(studentName.isEmpty()){
            Toast.makeText(this,"Please Enter Student name", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Attempted to add empty student name")
            return
        }
        if(studentList.contains(studentName)){
            Toast.makeText(this,"Student '$studentName' already exists", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Attempted to add duplicated student : $studentName")
            return
        }

        studentList.add(studentName)
        adapter.notifyDataSetChanged() //데이터 바뀐거 알림
        editTextStudent.text.clear()
        updateStudentCount()
        Toast.makeText(this, "Added: $studentName", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Added Student: $studentName (Total: ${studentList.size})")
    }

    private fun clearAllStudent(){
        if(studentList.isEmpty()){
            Toast.makeText(this,"List is already empty",Toast.LENGTH_SHORT).show()
            return
        }
        val count = studentList.size
        studentList.clear()
        adapter.notifyDataSetChanged() //리스트 뷰에 반영
        updateStudentCount()
        Toast.makeText(this,"Cleared all $count students",Toast.LENGTH_SHORT).show()
        Log.d(TAG,"Cleared all students (Total Cleared: $count)")
    }

    private fun removeStudent(position : Int){
        if(position >= 0 && position < studentList.size){
            val removedStudent = studentList.removeAt(position)
            adapter.notifyDataSetChanged()
            updateStudentCount()
            Toast.makeText(this,"Removed : $removedStudent",Toast.LENGTH_SHORT).show()
            Log.d(TAG,"Removed Student : $removedStudent (Remaining ${studentList.size})")
        }
    }

    private fun updateStudentCount(){
        textViewCount.text = "Total Student : ${studentList.size}"
    }

    private fun addInitialData(){
        val initialStudent = listOf("Kim","Lee","Park")
        studentList.addAll(initialStudent)
        updateStudentCount()
        Log.d(TAG, "Added Initial Data : $initialStudent")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Current Student Count ${studentList.size}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Saving State with ${studentList.size} studentS")
    }
}