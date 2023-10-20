package br.com.gabrielh360.githubsearch.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.gabrielh360.githubsearch.R
import br.com.gabrielh360.githubsearch.data.GitHubService
import br.com.gabrielh360.githubsearch.databinding.ActivityMainBinding
import br.com.gabrielh360.githubsearch.domain.Repository
import br.com.gabrielh360.githubsearch.ui.adapter.RepositoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val sharedPref by lazy {
        this.getSharedPreferences(getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
    }

    lateinit var githubApi: GitHubService
    lateinit var repositoryAdapter: RepositoryAdapter

    companion object {
        val DATABASE_USERNAME = "UserName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRetrofit()
        setupListeners()
    }

    //metodo responsavel por configurar os listeners click da tela
    private fun setupListeners() {
        //@TODO 2 - colocar a acao de click do botao confirmar
        binding.btnConfirmar.setOnClickListener {
            saveUserLocal()
            Log.e("Eerro", binding.btnConfirmar.toString())
        }
    }


    // salvar o usuario preenchido no EditText utilizando uma SharedPreferences
    private fun saveUserLocal() {
        //@TODO 3 - Persistir o usuario preenchido na editText com a SharedPref no listener do botao salvar
        sharedPref.edit().apply() {
            putString(DATABASE_USERNAME, binding.etNomeUsuario.text.toString())
            apply()
        }
        showUserName()
    }

    private fun showUserName() {
        //@TODO 4- depois de persistir o usuario exibir sempre as informacoes no EditText  se a sharedpref possuir algum valor, exibir no proprio editText o valor salvo
        val savedUserName = sharedPref.getString(DATABASE_USERNAME, null)

        if (savedUserName != null) {
        binding.etNomeUsuario.setText(savedUserName)
        getAllReposByUserName(savedUserName)
            println(savedUserName)
        }
    }

    //Metodo responsavel por fazer a configuracao base do Retrofit
    fun setupRetrofit() {
        /*
           @TODO 5 -  realizar a Configuracao base do retrofit
           Documentacao oficial do retrofit - https://square.github.io/retrofit/
           URL_BASE da API do  GitHub= https://api.github.com/
           lembre-se de utilizar o GsonConverterFactory mostrado no curso
        */

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        githubApi = retrofit.create(GitHubService::class.java)
    }

    //Metodo responsavel por buscar todos os repositorios do usuario fornecido
    fun getAllReposByUserName(savedUserName: String) {
        // TODO 6 - realizar a implementacao do callback do retrofit e chamar o metodo setupAdapter se retornar os dados com sucesso
            githubApi.getAllRepositoriesByUser(savedUserName).enqueue(object : Callback<List<Repository>> {
                override fun onResponse(
                    call: Call<List<Repository>>,
                    response: Response<List<Repository>>
                ) {
                    setupAdapter(response.body())
                }

                override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                    println(t.message)
                }
            })
    }

    // Metodo responsavel por realizar a configuracao do adapter
    fun setupAdapter(list: List<Repository>?) {
        /*
            @TODO 7 - Implementar a configuracao do Adapter , construir o adapter e instancia-lo
            passando a listagem dos repositorios
         */

        if (list != null) {
            repositoryAdapter = RepositoryAdapter(list)
            binding.rvListaRepositories.adapter = repositoryAdapter

            repositoryAdapter.searchItemLister = {
                openBrowser(it.htmlUrl)
            }

            repositoryAdapter.btnShareLister = {
                shareRepositoryLink(it.htmlUrl)
            }
        }else {
            Toast.makeText(this, getString(R.string.error_requisicao_retrofit), Toast.LENGTH_LONG).show()
        }
    }


    // Metodo responsavel por compartilhar o link do repositorio selecionado
    // @Todo 11 - Colocar esse metodo no click do share item do adapter
    fun shareRepositoryLink(urlRepository: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, urlRepository)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    // Metodo responsavel por abrir o browser com o link informado do repositorio

    // @Todo 12 - Colocar esse metodo no click item do adapter
    fun openBrowser(urlRepository: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(urlRepository)
            )
        )

    }

}