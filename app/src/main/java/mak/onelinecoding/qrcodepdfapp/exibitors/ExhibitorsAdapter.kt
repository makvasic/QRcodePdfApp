package mak.onelinecoding.qrcodepdfapp.exibitors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_exhibitor.view.*
import mak.onelinecoding.qrcodepdfapp.R

class ExhibitorsAdapter(private val listener: Listener) :
    ListAdapter<Exhibitor, ExhibitorsAdapter.ExhibitorViewHolder>(object :
        DiffUtil.ItemCallback<Exhibitor>() {
        override fun areItemsTheSame(oldItem: Exhibitor, newItem: Exhibitor): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Exhibitor, newItem: Exhibitor): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExhibitorViewHolder {
        return ExhibitorViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_exhibitor, parent, false), listener
        )
    }

    override fun onBindViewHolder(holder: ExhibitorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ExhibitorViewHolder(itemView: View, private val listener: Listener) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(exhibitor: Exhibitor) = with(itemView) {

            setOnClickListener {
                listener.onItemClicked(exhibitor)
            }

            if (exhibitor.logo.isEmpty()) {
                logoImageView.setImageResource(R.mipmap.ic_launcher)
            } else {
                Picasso.get().load(exhibitor.logo)
                    .error(R.mipmap.ic_launcher)
                    .into(logoImageView)
            }


            nameTextView.text = exhibitor.name

        }

    }

    interface Listener {
        fun onItemClicked(exhibitor: Exhibitor)
    }
}