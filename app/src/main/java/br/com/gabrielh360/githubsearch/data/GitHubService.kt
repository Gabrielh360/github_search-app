package br.com.gabrielh360.githubsearch.data

import br.com.gabrielh360.githubsearch.domain.Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("users/{user}/repos")
    fun getAllRepositoriesByUser(@Path("user") user: String): Call<List<Repository>>

    companion object {
//        val setupRetrofit: GitHubService by lazy {
//            //Metodo responsavel por fazer a configuracao base do Retrofit
//
//                //@TODO 5 -  realizar a Configuracao base do retrofit
//                val retrofit = Retrofit.Builder()
//                    .baseUrl("https://github.com/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//
//                retrofit.create(GitHubService::class.java)
//            }
                /*
                   Documentacao oficial do retrofit - https://square.github.io/retrofit/
                   URL_BASE da API do  GitHub= https://api.github.com/
                   lembre-se de utilizar o GsonConverterFactory mostrado no curso
                */

//            fun getInstance() : GitHubService {
//                return setupRetrofit
//            }
        }

    }
