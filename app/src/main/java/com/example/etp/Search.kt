package com.example.etp

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Search : AppCompatActivity(),OnItemClickListener {

    override fun onItemClicked(player: Player) {
        val intent = Intent(this, PlayerDetails::class.java)
        intent.putExtra("name", player)
        startActivity(intent)
    }

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var recylerAdapter: CustomAdapter
    private lateinit var p:ProgressDialog
    var playerList = ArrayList<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)


        assert(
            supportActionBar != null //null check
        )
        val colorDrawable = ColorDrawable(Color.BLUE)
        supportActionBar!!.setBackgroundDrawable(colorDrawable)

        p = ProgressDialog(this@Search)
        p.setMessage("Please wait while we fetch the data")
        p.setCancelable(false)
        p.show()

        recyclerView = findViewById(R.id.rv2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recylerAdapter = CustomAdapter(playerList, this, this)
        recyclerView.adapter = recylerAdapter



        if (playerList.isEmpty()) {
            playerListFill()
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
        val searchView: SearchView = searchItem.getActionView() as SearchView

        searchItem.expandActionView()

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
        val filteredlist: ArrayList<Player> = ArrayList()

        // running a for loop to compare elements.
        for (item in playerList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.long_name?.contains(text, ignoreCase = true) == true) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            recylerAdapter.filterList(filteredlist)
        }
    }

    private fun playerListFill() {
        database = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://etpproject-5deb3-default-rtdb.asia-southeast1.firebasedatabase.app/")
        database.child("Players").get().addOnSuccessListener { it ->
            if (it.exists()) {
                val showname = it.value as ArrayList<HashMap<String, String>>
                var i = 0;
                while (i < 2002) {
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
                    playerList.add(player)

                }
                recyclerView?.adapter?.notifyDataSetChanged()
                p.hide()


            }
        }.addOnFailureListener {
        }
    }
}