package com.aro.contactsapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.aro.contactsapp.adapter.RecyclerViewAdapter
import com.aro.contactsapp.MainActivity
import com.aro.contactsapp.data.*
import com.aro.contactsapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), RecyclerViewAdapter.OnContactClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var contactList: List<Contact>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        contactList = listOf()
        recyclerView = binding.contactRv

        val contactRepository = ContactRepository(ContactRoomDatabase(requireContext()))
        val factory = ContactViewModelFactory(contactRepository)
        contactViewModel = ViewModelProvider(MainActivity._application,factory)[ContactViewModel::class.java]

        binding.newFab.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToNewFragment()
            var charArray = arrayListOf<String>()
            for (cont in contactList) charArray.add(cont.name.toString())
            action.arguments.putStringArrayList("contacts", charArray)
            view.findNavController().navigate(action)
        }

        recyclerViewAdapter = RecyclerViewAdapter(contactList, requireContext(),this)
        recyclerView.adapter = recyclerViewAdapter
        contactViewModel.allContacts().observe(viewLifecycleOwner) {
            contactList = it
            recyclerViewAdapter.dataChanged(it)
            recyclerView.adapter = recyclerViewAdapter
        }
        return view
    }

    override fun onContactClick(position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle(contactList[position].name)
            .setMessage(contactList[position].number)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setNegativeButton("Delete") {_,_ -> contactViewModel.delete(contactList[position])}
            .show()
    }
}