package org.example.init_db

import org.springframework.stereotype.Component
import java.io.File

@Component
class Reader {
    val menFirstName: MutableList<String> = mutableListOf()
    val menLastName: MutableList<String> = mutableListOf()
    val womenFirstName: MutableList<String> = mutableListOf()
    val womenLastName: MutableList<String> = mutableListOf()
    val clinicName : MutableList<String> = mutableListOf()
    val address:MutableList<String> = mutableListOf()
    val specialization:MutableList<String> = mutableListOf()
    fun readeFile() {
        File("./src/test/resources/specialization.txt").bufferedReader().use {
            specialization.addAll(it.readLines())
        }
        File("./src/test/resources/address.txt").bufferedReader().use {
            address.addAll(it.readLines())
        }
        File("./src/test/resources/clinicName.txt").bufferedReader().use {
            clinicName.addAll(it.readLines())
        }
        File("./src/test/resources/womenFirstName.txt").bufferedReader().use {
            womenFirstName.addAll(it.readLines())
        }
        File("./src/test/resources/womenLastName.txt").bufferedReader().use {
            womenLastName.addAll(it.readLines())
        }
        File("./src/test/resources/menFirstName.txt").bufferedReader().use {
            menFirstName.addAll(it.readLines())
        }
        File("./src/test/resources/menLastName.txt").bufferedReader().use {
            menLastName.addAll(it.readLines())
        }


    }
}
