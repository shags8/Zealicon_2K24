import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zealicon_2024.R
import com.zealicon_2024.TeamMember
import com.zealicon_2024.databinding.TeamItemBinding

class TeamAdapter(private val context: Context, private val teamList: List<TeamMember>) :
    RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = TeamItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.binding.name.text = teamList[position].name
        Glide.with(holder.itemView.context).load(teamList[position].image).into(holder.binding.eventImage)
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    inner class TeamViewHolder(val binding: TeamItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}
