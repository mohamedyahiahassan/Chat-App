package com.example.chatapp.model

import com.example.chatapp.R

data class Category(val id: String? = null,
                    val name: String? = null,
                    val image: Int? = null,)
{

    companion object {
        fun getCategoriesList(): List<Category>{
        return listOf(
        Category("MOVIES","Movies", R.drawable.movies),
        Category ("SPORTS","Sports",R.drawable.sports),
        Category ("MUSIC","Music",R.drawable.music)

            )
        }
    }
}