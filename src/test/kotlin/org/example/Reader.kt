package org.example

import org.springframework.stereotype.Component
import java.io.File

@Component
class Reader {
    val menFirstName: MutableList<String> = mutableListOf()
    val menLastName: MutableList<String> = mutableListOf()
    val womenFirstName: MutableList<String> = mutableListOf()
    val womenLastName: MutableList<String> = mutableListOf()

    fun readeFile() {
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
