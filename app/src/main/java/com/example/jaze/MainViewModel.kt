package com.example.jaze

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {

    val api_key = "b57151d36fecd1b693da830a2bc5766f"

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build();

    val api = retrofit.create(Api::class.java)
    // à partir de là, on peut appeler api.lastMovies(...)

    val movies = MutableStateFlow<List<ModelFilm>>(listOf())

    fun getMovies() {
        viewModelScope.launch {
            movies.value = api.lastmovies(api_key).results
        /*withContext permet de changer le contexte dans lequel une coroutine s'exécute.
        *dédié aux opérations d'entrée/sortie (I/O), comme les appels réseau ou l'accès aux fichiers. */
        }
    }
}

