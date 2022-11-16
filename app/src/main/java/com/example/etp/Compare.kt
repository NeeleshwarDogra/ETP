package com.example.etp

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.Executors


class Compare : AppCompatActivity(), OnItemClickListener {

    override fun onItemClicked(player: Player) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        if (comparePlayer[0].short_name != "none" && comparePlayer[1].short_name != "none") {
            Toast.makeText(this, "Can only compare two players", Toast.LENGTH_SHORT).show()
            return
        }
        if (player in comparePlayer) {
            Toast.makeText(this, "Cant compare the same player", Toast.LENGTH_SHORT).show()
            return
        }
        if (comparePlayer[0].short_name == "none") {
            comparePlayer[0] = player
            val name = findViewById<TextView>(R.id.player1name)
            name.text = player.short_name

            val face = findViewById<ImageView>(R.id.player1img)
            var playerImg: Bitmap?
            executor.execute {
                val playerUrl = player.player_face_url
                try {
                    val `in` = java.net.URL(playerUrl).openStream()
                    playerImg = BitmapFactory.decodeStream(`in`)
                    handler.post {
                        face.setImageBitmap(playerImg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            comparePlayer[1] = player
            val name = findViewById<TextView>(R.id.player2name)
            name.text = player.short_name

            val face = findViewById<ImageView>(R.id.player2img)
            var playerImg: Bitmap?
            executor.execute {
                val playerUrl = player.player_face_url
                try {
                    val `in` = java.net.URL(playerUrl).openStream()
                    playerImg = BitmapFactory.decodeStream(`in`)
                    handler.post {
                        face.setImageBitmap(playerImg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: CustomAdapter
    private lateinit var p: ProgressDialog
    private var playerList = ArrayList<Player>()
    private var comparePlayer =
        arrayListOf(Player(short_name = "none"), Player(short_name = "none"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comparison)

        assert(
            supportActionBar != null //null check
        )
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //show back button
        val colorDrawable = ColorDrawable(Color.BLUE)
        supportActionBar!!.setBackgroundDrawable(colorDrawable)

        p = ProgressDialog(this@Compare)
        p.setMessage("Please wait while we fetch the data")
        p.setCancelable(false)
        p.show()

        recyclerView = findViewById(R.id.rv2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = CustomAdapter(playerList, this, this)
        recyclerView.adapter = recyclerAdapter

        if (playerList.isEmpty()) {
            playerListFill()
        }

        val rmvone = findViewById<Button>(R.id.one)
        rmvone.setOnClickListener {
            remone()
        }

        val rmvtwo = findViewById<Button>(R.id.two)
        rmvtwo.setOnClickListener {
            remtwo()
        }

        val compare = findViewById<Button>(R.id.compare)
        compare.setOnClickListener {
            compare()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // below line is to get our inflater
        val inflater = menuInflater

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search, menu)

        // below line is to get our menu item.
        val searchItem: MenuItem = menu.findItem(R.id.actionSearchView)

        // getting search view of our item.
        val searchView: SearchView = searchItem.actionView as SearchView

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(msg)
                return false
            }
        })

        return true
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredList: ArrayList<Player> = ArrayList()

        // running a for loop to compare elements.
        for (item in playerList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.long_name?.contains(text, ignoreCase = true) == true) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            recyclerAdapter.filterList(filteredList)
        }
    }


    private fun playerListFill() {
        database = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://etpproject-5deb3-default-rtdb.asia-southeast1.firebasedatabase.app/")
        database.child("Players").get().addOnSuccessListener {

            if (it.exists()) {
                val showname = it.value as ArrayList<HashMap<String, String>>
                var i = 0
                while (i < 2002) {
                    val player = Player(
                        short_name = showname[i]["short_name"]!!,
                        long_name = showname[i]["long_name"]!!,
                        overall = showname[i]["overall"] as Any,
                        player_face_url = showname[i]["player_face_url"],
                        club_logo_url = showname[i]["club_logo_url"],
                        nation_flag_url = showname[i]["nation_flag_url"],
                        pace = showname[i]["pace"] as Any,
                        passing = showname[i]["passing"] as Any,
                        player_positions = showname[i]["player_positions"],
                        shooting = showname[i]["shooting"] as Any,
                        dribbling = showname[i]["dribbling"] as Any,
                        defending = showname[i]["defending"] as Any,
                        physic = showname[i]["physic"] as Any,
                        nationality_name = showname[i]["nationality_name"],
                        value_eur = showname[i]["value_eur"],
                        wage_eur = showname[i]["wage_eur"],
                        age = showname[i]["age"] as Any,
                        height_cm = showname[i]["height_cm"] as Any,
                        club_name = showname[i]["club_name"],
                        preferred_foot = showname[i]["preferred_foot"]

                    )
                    i += 1
                    playerList.add(player)
                }
                recyclerView.adapter?.notifyDataSetChanged()
                p.hide()


            }
        }.addOnFailureListener {
        }
    }

    private fun remone() {
        comparePlayer[0] = Player(short_name = "none")
        val name = findViewById<TextView>(R.id.player1name)
        name.text = "Player 1"
        val face = findViewById<ImageView>(R.id.player1img)
        face.setImageDrawable(getDrawable(R.drawable.player_icon))
    }

    private fun remtwo() {
        comparePlayer[1] = Player(short_name = "none")
        val name = findViewById<TextView>(R.id.player2name)
        name.text = "Player 2"
        val face = findViewById<ImageView>(R.id.player2img)
        face.setImageDrawable(getDrawable(R.drawable.player_icon))
    }

    private fun compare(){
        if(comparePlayer[0].short_name == "none" || comparePlayer[1].short_name == "none"){
            Toast.makeText(this, "Choose two players to compare", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(this, Comparison::class.java)
        intent.putExtra("compare", comparePlayer)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
