package com.example.amphibians.fake

import com.example.amphibians.network.Amphibians

object FakeDataSource {
    const val amphibianName1 = "Example Amphibian"
    const val amphibianType1 = "Example Type"
    const val amphibianDescription1 = "This is an example amphibian description"
    const val amphibianImgSrc1 = "https://example.com/image.jpg"

    const val amphibianName2 = "Another Amphibian"
    const val amphibianType2 = "Another Type"
    const val amphibianDescription2 = "This is another example amphibian description"
    const val amphibianImgSrc2 = "https://example.com/another_image.jpg"

    val amphibians = listOf(
        Amphibians(amphibianName1, amphibianType1, amphibianDescription1, amphibianImgSrc1),
        Amphibians(amphibianName2, amphibianType2, amphibianDescription2, amphibianImgSrc2)
    )
}