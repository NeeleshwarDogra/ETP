package com.example.etp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors

class Comparison : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.compare)

        assert(
            supportActionBar != null //null check
        )
        val colorDrawable = ColorDrawable(Color.BLUE)
        supportActionBar!!.setBackgroundDrawable(colorDrawable)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        val data = intent.extras?.get("compare") as ArrayList<Player>

        val player1 = data[0]
        val player2 = data[1]

        val name1: TextView = findViewById(R.id.name1)
        val face1: ImageView = findViewById(R.id.face1)
        val age1: TextView = findViewById(R.id.age1)
        val country1: TextView = findViewById(R.id.country1)
        val club1: TextView = findViewById(R.id.club1)
        val height1: TextView = findViewById(R.id.height1)
        val wage1: TextView = findViewById(R.id.wage1)
        val value1: TextView = findViewById(R.id.value1)
        val overall1:TextView = findViewById(R.id.overall1)
        val foot1:TextView = findViewById(R.id.foot1)
        val position1:TextView = findViewById(R.id.position1)
        val pace1:TextView = findViewById(R.id.pace1)
        val shooting1:TextView = findViewById(R.id.shooting1)
        val dribble1:TextView = findViewById(R.id.dribble1)
        val defense1:TextView = findViewById(R.id.defense1)
        val passing1:TextView = findViewById(R.id.passing1)
        val physical1:TextView = findViewById(R.id.physical1)

        name1.text = player1.short_name
        age1.text = player1.age.toString()
        country1.text = player1.nationality_name
        club1.text = player1.club_name
        height1.text = player1.height_cm.toString()
        wage1.text = player1.wage_eur
        value1.text = player1.value_eur
        overall1.text = player1.overall.toString()
        foot1.text = player1.preferred_foot
        position1.text = player1.player_positions
        pace1.text = player1.pace.toString()
        shooting1.text = player1.shooting.toString()
        dribble1.text = player1.dribbling.toString()
        defense1.text = player1.defending.toString()
        passing1.text = player1.passing.toString()
        physical1.text = player1.physic.toString()

        val name2: TextView = findViewById(R.id.name2)
        val face2: ImageView = findViewById(R.id.face2)
        val age2: TextView = findViewById(R.id.age2)
        val country2: TextView = findViewById(R.id.country2)
        val club2: TextView = findViewById(R.id.club2)
        val height2: TextView = findViewById(R.id.height2)
        val wage2: TextView = findViewById(R.id.wage2)
        val value2: TextView = findViewById(R.id.value2)
        val overall2:TextView = findViewById(R.id.overall2)
        val foot2:TextView = findViewById(R.id.foot2)
        val position2:TextView = findViewById(R.id.position2)
        val pace2:TextView = findViewById(R.id.pace2)
        val shooting2:TextView = findViewById(R.id.shooting2)
        val dribble2:TextView = findViewById(R.id.dribble2)
        val defense2:TextView = findViewById(R.id.defense2)
        val passing2:TextView = findViewById(R.id.passing2)
        val physical2:TextView = findViewById(R.id.physical2)

        name2.text = player2.short_name
        age2.text = player2.age.toString()
        country2.text = player2.nationality_name
        club2.text = player2.club_name
        height2.text = player2.height_cm.toString()
        wage2.text = player2.wage_eur
        value2.text = player2.value_eur
        overall2.text = player2.overall.toString()
        foot2.text = player2.preferred_foot
        position2.text = player2.player_positions
        pace2.text = player2.pace.toString()
        shooting2.text = player2.shooting.toString()
        dribble2.text = player2.dribbling.toString()
        defense2.text = player2.defending.toString()
        passing2.text = player2.passing.toString()
        physical2.text = player2.physic.toString()

        var wa1 = player1.wage_eur?.drop(1)
        var wa2 = player2.wage_eur?.drop(1)
        wa1 = wa1?.replace(",", "")
        wa2 = wa2?.replace(",", "")

        val w1 = wa1?.toFloat()
        val w2 = wa2?.toFloat()

        if (w1 != null) {
            if (w1 > w2!!) {
                wage1.setBackgroundColor(Color.GREEN)
                wage2.setBackgroundColor(Color.RED)
                wage2.setTextColor(Color.parseColor("#cae8dc"))
            } else if (w1 < w2) {
                wage2.setBackgroundColor(Color.GREEN)
                wage1.setBackgroundColor(Color.RED)
                wage1.setTextColor(Color.parseColor("#cae8dc"))
            }
        }

        var va1 = player1.value_eur?.drop(1)
        var va2 = player2.value_eur?.drop(1)
        va1 = va1?.replace(",", "")
        va2 = va2?.replace(",", "")

        val v1 = va1?.toFloat()
        val v2 = va2?.toFloat()

        if (v1 != null) {
            if (v1 > v2!!) {
                value1.setBackgroundColor(Color.GREEN)
                value2.setBackgroundColor(Color.RED)
                value2.setTextColor(Color.parseColor("#cae8dc"))
            } else if (v1 < v2) {
                value2.setBackgroundColor(Color.GREEN)
                value1.setBackgroundColor(Color.RED)
                value1.setTextColor(Color.parseColor("#cae8dc"))
            }
        }

        val o1 = player1.overall as Long
        val o2 = player2.overall as Long

        if(o1>o2){
            overall1.setBackgroundColor(Color.GREEN)
            overall2.setBackgroundColor(Color.RED)
            overall2.setTextColor(Color.parseColor("#cae8dc"))
        }
        else if (o1<o2){
            overall1.setBackgroundColor(Color.RED)
            overall2.setBackgroundColor(Color.GREEN)
            overall1.setTextColor(Color.parseColor("#cae8dc"))
        }

        val pac1 = player1.pace as Long
        val sho1 = player1.shooting as Long
        val dri1 = player1.dribbling as Long
        val def1 = player1.defending as Long
        val pas1 = player1.passing as Long
        val phy1 = player1.physic as Long

        val pac2 = player2.pace as Long
        val sho2 = player2.shooting as Long
        val dri2 = player2.dribbling as Long
        val def2 = player2.defending as Long
        val pas2 = player2.passing as Long
        val phy2 = player2.physic as Long

        if(pac1 > pac2){
            pace1.setBackgroundColor(Color.GREEN)
            pace2.setBackgroundColor(Color.RED)
            pace2.setTextColor(Color.parseColor("#cae8dc"))
        }
        else if(pac1 < pac2){
            pace1.setBackgroundColor(Color.RED)
            pace2.setBackgroundColor(Color.GREEN)
            pace1.setTextColor(Color.parseColor("#cae8dc"))
        }

        if(sho1 > sho2){
            shooting1.setBackgroundColor(Color.GREEN)
            shooting2.setBackgroundColor(Color.RED)
            shooting2.setTextColor(Color.parseColor("#cae8dc"))
        }
        else if(sho1 < sho2){
            shooting1.setBackgroundColor(Color.RED)
            shooting2.setBackgroundColor(Color.GREEN)
            shooting1.setTextColor(Color.parseColor("#cae8dc"))
        }

        if(dri1 > dri2){
            dribble1.setBackgroundColor(Color.GREEN)
            dribble2.setBackgroundColor(Color.RED)
            dribble2.setTextColor(Color.parseColor("#cae8dc"))
        }
        else if(dri1 < dri2){
            dribble2.setBackgroundColor(Color.GREEN)
            dribble1.setBackgroundColor(Color.RED)
            dribble1.setTextColor(Color.parseColor("#cae8dc"))
        }

        if(def1 > def2){
            defense1.setBackgroundColor(Color.GREEN)
            defense2.setBackgroundColor(Color.RED)
            defense2.setTextColor(Color.parseColor("#cae8dc"))
        }
        else if(def1 < def2){
            defense1.setBackgroundColor(Color.RED)
            defense2.setBackgroundColor(Color.GREEN)
            defense1.setTextColor(Color.parseColor("#cae8dc"))
        }

        if(pas1 > pas2){
            passing1.setBackgroundColor(Color.GREEN)
            passing2.setBackgroundColor(Color.RED)
            passing2.setTextColor(Color.parseColor("#cae8dc"))
        }
        else if(pas1 < pas2){
            passing1.setBackgroundColor(Color.RED)
            passing2.setBackgroundColor(Color.GREEN)
            passing1.setTextColor(Color.parseColor("#cae8dc"))
        }

        if(phy1 > phy2){
            physical1.setBackgroundColor(Color.GREEN)
            physical2.setBackgroundColor(Color.RED)
            physical2.setTextColor(Color.parseColor("#cae8dc"))
        }
        else if(phy2 > phy1){
            physical2.setBackgroundColor(Color.GREEN)
            physical1.setBackgroundColor(Color.RED)
            physical1.setTextColor(Color.parseColor("#cae8dc"))
        }

        var faceImg1: Bitmap?
        var faceImg2: Bitmap?

        executor.execute {
            val face1Url = player1.player_face_url
            val face2Url = player2.player_face_url

            try {
                var `in1` = java.net.URL(face1Url).openStream()
                val `in2` = java.net.URL(face2Url).openStream()

                faceImg1 = BitmapFactory.decodeStream(`in1`)
                faceImg2 = BitmapFactory.decodeStream(`in2`)

                handler.post {
                    face1.setImageBitmap(faceImg1)
                    face2.setImageBitmap(faceImg2)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}