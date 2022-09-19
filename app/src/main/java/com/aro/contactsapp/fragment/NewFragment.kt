package com.aro.contactsapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.aro.contactsapp.MainActivity
import com.aro.contactsapp.data.*
import com.aro.contactsapp.databinding.FragmentNewBinding

class NewFragment : Fragment() {

    private var _binding: FragmentNewBinding? = null
    private val binding get() = _binding!!
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewBinding.inflate(inflater, container, false)
        val view = binding.root
        val contactList = arguments?.getStringArrayList("contacts")!!

        val contactRepository = ContactRepository(ContactRoomDatabase(requireContext()))
        val factory = ContactViewModelFactory(contactRepository)
        contactViewModel =
            ViewModelProvider(MainActivity._application, factory)[ContactViewModel::class.java]

        binding.saveBt.setOnClickListener {
            val name = binding.nameEt.text
            val number = binding.numberEt.text
            if (name.toString().isNotEmpty())
                if (!contactList.contains(name.toString()))
                    contactViewModel.insert(Contact(name.toString(), number.toString()))
                else
                    Toast.makeText(requireContext(), "Contact Already Exists!", Toast.LENGTH_SHORT)
                        .show()
            else
                Toast.makeText(requireContext(), "Couldn't save empty contact!", Toast.LENGTH_SHORT)
                    .show()

            view.findNavController().popBackStack()
        }

        binding.cancelBt.setOnClickListener { view.findNavController().popBackStack() }

        return view
    }

}