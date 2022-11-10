package ua.dimkas71.test

import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.readLines

fun readPathAsText(p: Path): String {
    return when {
        !p.exists() -> ""
        p.isDirectory() -> ""
        else -> p.readLines().joinToString("\n")
    }
}