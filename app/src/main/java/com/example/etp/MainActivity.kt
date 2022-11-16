package com.example.etp

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var listView: ListView
    private lateinit var listAdapter: CustomListAdapter
    var showplayer = ArrayList<Player>()
    var playerList = ArrayList<Player>()
    private lateinit var p: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        assert(
            supportActionBar != null //null check
        )
        val colorDrawable = ColorDrawable(Color.BLUE)
        supportActionBar!!.setBackgroundDrawable(colorDrawable)

        p = ProgressDialog(this@MainActivity)
        p.setMessage("Please wait while we fetch the data")
        p.setCancelable(false)
        p.show()

        listView = findViewById(R.id.lv1)
        listAdapter = CustomListAdapter(this, R.layout.player_card, showplayer)
        listView.adapter = listAdapter

        if (showplayer.isEmpty()) {
            playerListFill()
        }


        listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long ->
            val intent = Intent(this, PlayerDetails::class.java)
            intent.putExtra("name", showplayer[position])
            startActivity(intent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionSearch -> {
                val intent = Intent(this, Search::class.java)
                startActivity(intent)
                true
            }
            R.id.compare -> {
                val intent = Intent(this, Compare::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun playerListFill() {
        database = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://etpproject-5deb3-default-rtdb.asia-southeast1.firebasedatabase.app/")
        database.child("Players").get().addOnSuccessListener { it ->
            if (it.exists()) {

                val showname = it.value as ArrayList<HashMap<String, String>>

                var i = 0;
                while (i < 10) {

                    var player = Player(
                        short_name = showname[i].get("short_name")!!,
                        long_name = showname[i].get("long_name")!!,
                        overall = showname[i].get("overall") as Any,
                        player_face_url = showname[i].get("player_face_url"),
                        club_logo_url = showname[i].get("club_logo_url"),
                        nation_flag_url = showname[i].get("nation_flag_url"),
                        pace = showname[i].get("pace") as Any,
                        passing = showname[i].get("passing") as Any,
                        player_positions = showname[i].get("player_positions"),
                        shooting = showname[i].get("shooting") as Any,
                        dribbling = showname[i].get("dribbling") as Any,
                        defending = showname[i].get("defending") as Any,
                        physic = showname[i].get("physic") as Any,
                        nationality_name = showname[i].get("nationality_name"),
                        value_eur = showname[i].get("value_eur"),
                        wage_eur = showname[i].get("wage_eur"),
                        age = showname[i].get("age") as Any,
                        height_cm = showname[i].get("height_cm") as Any,
                        club_name = showname[i].get("club_name"),
                        preferred_foot = showname[i].get("preferred_foot")
                    )


                    i += 1
                    showplayer.add(player)
                }
                listAdapter.notifyDataSetChanged()
                p.hide()


            }
        }.addOnFailureListener {
        }
    }
}