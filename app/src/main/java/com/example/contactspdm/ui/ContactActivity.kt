package com.example.contactspdm.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contactspdm.databinding.ActivityContactBinding
import com.example.contactspdm.model.Contact

class ContactActivity: AppCompatActivity() {
    private val acb: ActivityContactBinding by lazy{
        ActivityContactBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)

        acb.saveBt.setOnClickListener{
            val infos = Bundle()
            val resultadoIntent = Intent()
            infos.putString("name", acb.nameEt.text.toString())
            infos.putString("address", acb.addressEt.text.toString())
            infos.putString("phone", acb.phoneEt.text.toString())
            infos.putString("email", acb.emailEt.text.toString())
            resultadoIntent.putExtra("bundle", infos).also {
                setResult(RESULT_OK, it)
                finish()
            }
        }
    }
}