package com.example.contactspdm.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.contactspdm.R
import com.example.contactspdm.databinding.ActivityMainBinding
import com.example.contactspdm.model.Contact

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var paddarl: ActivityResultLauncher<Intent>

    //Data source
    private val contactList: MutableList<Contact> = mutableListOf()

    //Adapter
    private val contactAdapter: ArrayAdapter<Contact> by lazy {
        ArrayAdapter(this, android.R.layout.simple_list_item_1, contactList)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        fillContacts()

        amb.contactsLv.adapter = contactAdapter

        amb.toolbarIn.toolbar.apply {
            setSupportActionBar(this)
        }

        paddarl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val lastId = if (contactList.isNotEmpty()) {
                    contactList.last().id
                } else {
                    1
                }

                result.data?.getBundleExtra("bundle")?.let {
                    val name = it.getString("name")
                    val address = it.getString("address")
                    val phone = it.getString("phone")
                    val email = it.getString("email")
                    if (!name.isNullOrBlank() && !phone.isNullOrBlank() && !email.isNullOrBlank() && !address.isNullOrBlank()) {
                        contactList.add(
                            Contact(lastId + 1, name, address, phone, email)
                        )
                        (contactAdapter as? ArrayAdapter<Contact>)?.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this, "Há dados obrigatórios que não foram preenchidos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.viewMi) {
            Intent(this, ContactActivity::class.java).also {
                paddarl.launch(it)
            }
        }
        return true
    }

    private fun fillContacts(){
        for (i in 1..50){
            contactList.add(
                Contact(
                    i,
                    "Name $i",
                    "Address $i",
                    "Phone $i",
                    "Email $i"
                )
            )
        }
    }
}