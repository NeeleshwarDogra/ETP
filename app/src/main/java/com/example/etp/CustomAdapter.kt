package com.example.etp

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val executor = Executors.newSingleThreadExecutor()
    val handler = Handler(Looper.getMainLooper())

//    private val short_name: TextView = itemView.findViewById(R.id.short_name)
    private val long_name: TextView = itemView.findViewById(R.id.long_name)
//    private val overall: TextView = itemView.findViewById(R.id.overall)
    private val player_face: ImageView = itemView.findViewById(R.id.player_face)
//    private val club_logo:ImageView = itemView.findViewById(R.id.club_logo)
    private val country_log:ImageView = itemView.findViewById(R.id.country_logo)
//    private val position: TextView = itemView.findViewById(R.id.position)
//    private val pace: TextView = itemView.findViewById(R.id.pace)
//    private val shooting: TextView = itemView.findViewById(R.id.shooting)
//    private val passing: TextView = itemView.findViewById(R.id.passing)
//    private val dribbling: TextView = itemView.findViewById(R.id.dribbling)
//    private val defence: TextView = itemView.findViewById(R.id.defence)
//    private val physical: TextView = itemView.findViewById(R.id.physical)

    fun bind(player: Player, clickListener:com.example.etp.OnItemClickListener,) {
//        short_name.text = player.short_name
        long_name.text = player.long_name
//        overall.text = player.overall.toString()
//        position.text = player.player_positions
//        pace.text = context.getString(R.string.pace, player.pace)
//        shooting.text = context.getString(R.string.shoot, player.shooting)
//        passing.text = context.getString(R.string.pass, player.passing)
//        dribbling.text = context.getString(R.string.dribble, player.dribbling)
//        defence.text = context.getString(R.string.def, player.defending)
//        physical.text = context.getString(R.string.phy, player.physic)

        var playerImage:Bitmap?=null
//        var clubImage:Bitmap?=null
        var countryImage:Bitmap?=null

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



                // Only for making changes in UI
                handler.post {
                    player_face.setImageBitmap(playerImage)
                    country_log.setImageBitmap(countryImage)
                }
            }

            // If the URL doesnot point to
            // image or any other kind of failure
            catch (e: Exception) {
                e.printStackTrace()
            }
        }

        itemView.setOnClickListener{
            clickListener.onItemClicked(player)
        }

    }

}

class CustomAdapter(var players: ArrayList<Player>, var context: Context, var itemClickListener: com.example.etp.OnItemClickListener) :
    RecyclerView.Adapter<MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_card, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val player = players[position]
        holder.bind(player,itemClickListener)
    }

    override fun getItemCount(): Int {
        return players.size
    }


    fun filterList(filterList: ArrayList<Player>): Boolean {
        players = filterList
        notifyDataSetChanged()
        return true
    }

}

interface OnItemClickListener{
    fun onItemClicked(player:Player)
}
