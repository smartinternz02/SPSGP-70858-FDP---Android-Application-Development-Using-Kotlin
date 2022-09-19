package com.aro.contactsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aro.contactsapp.data.Contact
import com.aro.contactsapp.R
import java.util.*

class RecyclerViewAdapter(
    contactList: List<Contact>,
    context: Context,
    listener: OnContactClickListener
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val oCCListener: OnContactClickListener
    private var contactList: List<Contact>
    private val context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact, parent, false)
        return ViewHolder(view, oCCListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact: Contact = Objects.requireNonNull(contactList[position])
        holder.nameView.setText(contact.name)
    }

    override fun getItemCount(): Int {
        return Objects.requireNonNull(contactList.size)
    }

    fun dataChanged(contacts: List<Contact>) {
        this.contactList = contacts
    }

    interface OnContactClickListener {
        fun onContactClick(position: Int)
    }

    inner class ViewHolder(itemView: View, oCCListener: OnContactClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val oCCListener: OnContactClickListener
        var nameView: TextView
        override fun onClick(view: View) {
            oCCListener.onContactClick(adapterPosition)
        }

        init {
            nameView = itemView.findViewById(R.id.contact_name)
            this.oCCListener = oCCListener
            itemView.setOnClickListener(this)
        }
    }

    init {
        this.contactList = contactList
        this.context = context
        oCCListener = listener
    }
}
