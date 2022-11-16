package com.example.etp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors

class PlayerDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_detail_card)

        assert(
            supportActionBar != null //null check
        )
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //show back button
        val colorDrawable = ColorDrawable(Color.BLUE)
        supportActionBar!!.setBackgroundDrawable(colorDrawable)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        val data = intent.extras?.get("name") as Player

        var short_name: TextView = findViewById(R.id.short_name)
        var long_name: TextView = findViewById(R.id.long_name)
        var overall: TextView = findViewById(R.id.overall)
        var value_eur:TextView = findViewById(R.id.value_eur)
        var wage_eur: TextView = findViewById(R.id.wage_eur)
        var country:TextView = findViewById(R.id.country_name)
        var age:TextView = findViewById(R.id.age)
        var height:TextView = findViewById(R.id.height)
        var clubName: TextView = findViewById(R.id.club_name)
        var preferredFoot: TextView = findViewById(R.id.preferred_foot)
        var position:TextView = findViewById(R.id.position)
        var pace:TextView = findViewById(R.id.pace)
        var shooting:TextView = findViewById(R.id.shooting)
        val dribbling: TextView = findViewById(R.id.dribbling)
        val defense:TextView = findViewById(R.id.defence)
        var passing:TextView = findViewById(R.id.passing)
        var physical:TextView = findViewById(R.id.physical)
        var player_face:ImageView = findViewById(R.id.player_face)
        var country_flag:ImageView = findViewById(R.id.nation_flag)
        var club_logo:ImageView = findViewById(R.id.club_logo)

        short_name.text = data.short_name
        long_name.text = data.long_name
        overall.text = data.overall.toString()
        value_eur.text = data.value_eur
        wage_eur.text = data.wage_eur
        country.text = data.nationality_name
        age.text = data.age.toString()
        height.text = data.height_cm.toString()
        clubName.text = data.club_name
        preferredFoot.text = data.preferred_foot
        position.text = data.player_positions
        pace.text = data.pace.toString()
        shooting.text = data.shooting.toString()
        dribbling.text = data.dribbling.toString()
        defense.text = data.defending.toString()
        passing.text = data.passing.toString()
        physical.text = data.physic.toString()

        var playerImage:Bitmap ?= null
        var countryImage:Bitmap?=null
        var clubImage:Bitmap?=null

        executor.execute{
            val playerFaceUrl = data.player_face_url
            val countryFlagUrl = data.nation_flag_url
            val clubLogoUrl = data.club_logo_url

            try{
                var `in` = java.net.URL(playerFaceUrl).openStream()
                var `in2` = java.net.URL(countryFlagUrl).openStream()
                var `in3` = java.net.URL(clubLogoUrl).openStream()

                playerImage = BitmapFactory.decodeStream(`in`)
                countryImage = BitmapFactory.decodeStream(`in2`)
                clubImage = BitmapFactory.decodeStream(`in3`)

                handler.post{
                    player_face.setImageBitmap(playerImage)
                    country_flag.setImageBitmap(countryImage)
                    club_logo.setImageBitmap(clubImage)
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}