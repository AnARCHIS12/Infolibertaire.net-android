package net.infolibertaire

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import net.infolibertaire.data.AppDatabase
import net.infolibertaire.data.Favorite

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var refreshButton: Button
    private lateinit var shareButton: Button
    private lateinit var favoriteButton: Button
    private lateinit var favoritesListButton: Button
    private val homeUrl = "https://infolibertaire.net"
    private lateinit var db: AppDatabase
    private var currentTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initializeViews()
        setupDatabase()
        setupWebView()
        setupButtons()
    }

    private fun initializeViews() {
        webView = findViewById(R.id.webview)
        refreshButton = findViewById(R.id.refreshButton)
        shareButton = findViewById(R.id.shareButton)
        favoriteButton = findViewById(R.id.favoriteButton)
        favoritesListButton = findViewById(R.id.favoritesListButton)
    }

    private fun setupDatabase() {
        db = AppDatabase.getDatabase(this)
    }

    private fun setupWebView() {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                url?.let { currentUrl ->
                    currentTitle = view?.title ?: ""
                    lifecycleScope.launch {
                        val isFavorite = db.favoriteDao().isFavorite(currentUrl)
                        favoriteButton.text = if (isFavorite) "★" else "☆"
                    }
                }
                refreshButton.isEnabled = true
            }
        }
        
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadsImagesAutomatically = true
            useWideViewPort = true
            loadWithOverviewMode = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
        }
        
        webView.loadUrl(homeUrl)
    }

    private fun setupButtons() {
        refreshButton.setOnClickListener {
            webView.reload()
        }

        shareButton.setOnClickListener {
            val url = webView.url ?: homeUrl
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, url)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, null))
        }

        setupFavoriteButtons()
    }

    private fun setupFavoriteButtons() {
        favoriteButton.setOnClickListener {
            val currentUrl = webView.url ?: return@setOnClickListener
            lifecycleScope.launch {
                val isFavorite = db.favoriteDao().isFavorite(currentUrl)
                if (isFavorite) {
                    db.favoriteDao().delete(Favorite(currentUrl, currentTitle))
                    favoriteButton.text = "☆"
                } else {
                    db.favoriteDao().insert(Favorite(currentUrl, currentTitle))
                    favoriteButton.text = "★"
                }
            }
        }
        
        favoritesListButton.setOnClickListener {
            showFavorites()
        }
    }
    
    private fun showFavorites() {
        var currentSort = "date"
        
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_favorites, null)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.favoritesRecyclerView)
        val sortButton = dialogView.findViewById<Button>(R.id.sortButton)
        
        val dialog = AlertDialog.Builder(this)
            .setTitle("Favoris")
            .setView(dialogView)
            .create()
            
        val adapter = FavoritesAdapter(
            onItemClick = { url ->
                webView.loadUrl(url)
                dialog.dismiss()
            },
            onDeleteClick = { favorite ->
                lifecycleScope.launch {
                    db.favoriteDao().delete(favorite)
                }
            }
        )
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        
        sortButton.setOnClickListener {
            currentSort = if (currentSort == "date") "title" else "date"
            sortButton.text = if (currentSort == "date") "Trier par titre" else "Trier par date"
            updateFavoritesList(currentSort, adapter)
        }
        
        lifecycleScope.launch {
            updateFavoritesList(currentSort, adapter)
        }
        
        dialog.show()
    }
    
    private fun updateFavoritesList(sort: String, adapter: FavoritesAdapter) {
        lifecycleScope.launch {
            val flow = if (sort == "date") {
                db.favoriteDao().getAllFavoritesByDate()
            } else {
                db.favoriteDao().getAllFavoritesByTitle()
            }
            
            flow.collect { favorites ->
                adapter.submitList(favorites)
            }
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}