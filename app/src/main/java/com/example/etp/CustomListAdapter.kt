package com.example.etp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.util.concurrent.Executors

class CustomListAdapter(var context: Context, var resources: Int, var list: ArrayList<Player>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val itemView: View = layoutInflater.inflate(resources, null)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        val short_name: TextView = itemView.findViewById(R.id.short_name)
//    private val long_name: TextView = itemView.findViewById(R.id.long_name)
        val overall: TextView = itemView.findViewById(R.id.overall)
        val player_face: ImageView = itemView.findViewById(R.id.player_face)
        val club_logo: ImageView = itemView.findViewById(R.id.club_logo)
        val country_log: ImageView = itemView.findViewById(R.id.country_logo)
        val position: TextView = itemView.findViewById(R.id.position)
        val pace: TextView = itemView.findViewById(R.id.pace)
        val shooting: TextView = itemView.findViewById(R.id.shooting)
        val passing: TextView = itemView.findViewById(R.id.passing)
        val dribbling: TextView = itemView.findViewById(R.id.dribbling)
        val defence: TextView = itemView.findViewById(R.id.defence)
        val physical: TextView = itemView.findViewById(R.id.physical)

        val player = list[p0]

        short_name.text = player.short_name
//        long_name.text = player.long_name
        overall.text = player.overall.toString()
        position.text = player.player_positions
        pace.text = context.getString(R.string.pace, player.pace)
        shooting.text = context.getString(R.string.shoot, player.shooting)
        passing.text = context.getString(R.string.pass, player.passing)
        dribbling.text = context.getString(R.string.dribble, player.dribbling)
        defence.text = context.getString(R.string.def, player.defending)
        physical.text = context.getString(R.string.phy, player.physic)
        country_log.tooltipText = player.nationality_name

        var playerImage: Bitmap? = null
        var clubImage: Bitmap? = null
        var countryImage: Bitmap? = null

        executor.execute {

            // Image URL
            val playerImageURL = player.player_face_url
            val clubImageURL = player.club_logo_url
            val countryImageURL = player.nation_flag_url

            // Tries to get the image and post it in the ImageView
            // with the help of Handler
            try {
                var `in` = java.net.URL(playerImageURL).openStream()
                playerImage = BitmapFactory.decodeStream(`in`)

                val `in3` = java.net.URL(countryImageURL).openStream()
                countryImage = BitmapFactory.decodeStream(`in3`)

                val `in2` = java.net.URL(clubImageURL).openStream()
                clubImage = BitmapFactory.decodeStream(`in2`)


                // Only for making changes in UI
                handler.post {
                    player_face.setImageBitmap(playerImage)
                    club_logo.setImageBitmap(clubImage)
                    country_log.setImageBitmap(countryImage)
                }
            }

            // If the URL doesnot point to
            // image or any other kind of failure
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    return itemView
    }
}