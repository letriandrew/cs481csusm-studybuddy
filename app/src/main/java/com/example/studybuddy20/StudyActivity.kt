package com.example.studybuddy20

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.Serializable

class StudyActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var selectedMethodTextView: TextView
    private lateinit var mediaPlayer : MediaPlayer



    private val techniques = listOf(
        Technique(
            "SQ3R", listOf(
                "Survey : skimming the chapter and taking notes",
                "Break",
                "Question : formulate questions around the chapter's content",
                "Break",
                "Read: read full chapter and look for answers to the questions you made",
                "Break",
                "Recite: summarize what you just read, recall and identify major points",
                "Break",
                "Review: review material, quiz yourself"
            )
        ),
        Technique(
            "Pomodoro", listOf(
                "Work: focus on a task",
                "Break",
                "Work: focus on a task",
                "Break",
                "Work: focus on a task",
                "Break",
                "Work: focus on a task",
                "Break"
            )
        ),
        Technique(
            "Feynman", listOf(
                "Study: focus on a topic",
                "Break",
                "Explain: explain what you just studied, as if you were teaching it to someone",
                "Break",
                "Identify gaps: identify areas you struggled with and revisit them",
                "Break",
                "Review: review material, quiz yourself"
            )
        )
    )

    val myTechniques = mapOf(
        "SQ3R" to listOf(
            "Survey \n\n Skimming the chapter and taking notes" to 1,
            "Break" to 1,
            "Question \n\n Formulate questions around the chapter's content" to 1,
            "Break" to 1,
            "Read \n\n Read full chapter and look for answers to the questions you made" to 1,
            "Break" to 1,
            "Recite \n\n Summarize what you just read, recall and identify major points" to 1,
            "Break" to 1,
            "Review\n\n Review material, quiz yourself" to 2
        ),
        "Pomodoro" to listOf(
            "Work \n\n Focus on a task" to 25,
            "Break" to 5,
            "Work \n" +
                    "\n" +
                    " Focus on a task" to 25,
            "Break" to 5,
            "Work \n" +
                    "\n" +
                    " Focus on a task" to 25,
            "Break" to 5,
            "Work \n" +
                    "\n" +
                    " Focus on a task" to 25,
            "Break" to 5
        ),
        "Feynman" to listOf(
            "Learn" to 30,
            "Break" to 5,
            "Explain" to 30,
            "Break" to 5,
            "Review" to 30
        )
    )




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studyconstraint)

        startButton = findViewById(R.id.button_start_studying)
        startButton.setBackgroundColor(Color.parseColor("#356859"))

        // Find the RecyclerView and set its layout manager
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the selected method TextView
        selectedMethodTextView = findViewById(R.id.text_view)

        // Set up the adapter with the list of techniques and a click listener
        val adapter = TechniqueAdapter(techniques) { technique ->
            selectedMethodTextView.text = "${technique.title} Method"
            startButton.isEnabled = true
            startButton.setOnClickListener {
                // Check if the selected technique is in the predefined techniques list
                if (myTechniques.containsKey(technique.title)) {
                    val steps = myTechniques.getValue(technique.title)
                    val stepDurations = steps.map { it.first to it.second.times(60).toLong() }

                    val intent = Intent(this, TimerActivity::class.java).apply {
                        putExtra("techniqueTitle", technique.title)

                        val stepsBundle = Bundle().apply {
                            putSerializable("steps", ArrayList(stepDurations))
                        }
                        putExtras(stepsBundle)
                    }
                    startActivity(intent)



                } else {
                    Toast.makeText(
                        this,
                        "Please select a valid technique. Selected technique: ${technique.title}, Available techniques: ${myTechniques.keys}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        recyclerView.adapter = adapter
        // Find the start button and disable it by default
        startButton = findViewById(R.id.button_start_studying)
        startButton.isEnabled = false
        selectedMethodTextView.text = "Select a study method"

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNav)
        bottomNavigation.setOnItemSelectedListener { item ->
            onNavigationItemSelected(item)
        }
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean{
        when (item.itemId){
            R.id.home ->{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.music ->{
                val intent = Intent(this, MusicPlayer::class.java)
                startActivity(intent)
            }
            R.id.logout ->{
                if(::mediaPlayer.isInitialized) {
                    mediaPlayer.stop()
                    mediaPlayer.release()

                }
                val intent = Intent(this, LoginActivity2::class.java)
                startActivity(intent)
                finish()
            }
        }
        return false;
    }

}
