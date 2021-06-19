package mak.onelinecoding.qrcodepdfapp.exibitors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_member.view.*
import mak.onelinecoding.qrcodepdfapp.R

class MembersAdapter(private val listener: Listener) :
    ListAdapter<Member, MembersAdapter.MemberViewHolder>(object : DiffUtil.ItemCallback<Member>() {
        override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        return MemberViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_member, parent, false), listener
        )
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class MemberViewHolder(itemView: View, private val listener: Listener) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(member: Member) = with(itemView) {

            setOnClickListener {
                listener.onItemClicked(member)
            }

            if (member.photo.isEmpty()) {
                photoImageView.setImageResource(R.mipmap.ic_launcher)
            } else {
                Picasso.get().load(member.photo)
                    .error(R.mipmap.ic_launcher)
                    .into(photoImageView)
            }

            nameTextView.text = member.name

        }

    }

    interface Listener {
        fun onItemClicked(member: Member)

    }
}