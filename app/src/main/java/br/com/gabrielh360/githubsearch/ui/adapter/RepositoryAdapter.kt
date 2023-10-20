package br.com.gabrielh360.githubsearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.gabrielh360.githubsearch.databinding.RepositoryItemBinding
import br.com.gabrielh360.githubsearch.domain.Repository

class RepositoryAdapter(private val repositories: List<Repository>) :
    RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    var searchItemLister: (Repository) -> Unit = {}
    var btnShareLister: (Repository) -> Unit = {}

    // Cria uma nova view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflateView = LayoutInflater.from(parent.context)
        val binding = RepositoryItemBinding.inflate(inflateView, parent, false)

        return ViewHolder(binding)
    }

    // Pega o conteudo da view e troca pela informacao de item de uma lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //@TODO 8 -  Realizar o bind do viewHolder

        val repository = repositories[position]
        holder.search(repository, searchItemLister, btnShareLister)

        //Exemplo de Bind
        //  holder.preco.text = repositories[position].atributo

        // Exemplo de click no item
        //holder.itemView.setOnClickListener {
        // carItemLister(repositores[position])
        //}

        // Exemplo de click no btn Share
        //holder.favorito.setOnClickListener {
        //    btnShareLister(repositores[position])
        //}
    }

    // Pega a quantidade de repositorios da lista

    //@TODO 9 - realizar a contagem da lista
    override fun getItemCount(): Int = repositories.size

    class ViewHolder(private val binding: RepositoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        //@TODO 10 - Implementar o ViewHolder para os repositorios

        fun search(
            repository: Repository,
            searchItemLister: (Repository) -> Unit,
            btnShareLister: (Repository) -> Unit
        ) {
            binding.tvPerfilName.text = repository.name

            binding.ivShare.setOnClickListener {
                btnShareLister(repository)
            }
            binding.clCardContent.setOnClickListener {
                searchItemLister(repository)
            }

        }

        //Exemplo:
        //val atributo: TextView

        //init {
        //    view.apply {
        //        atributo = findViewById(R.id.item_view)
        //    }

    }
}


