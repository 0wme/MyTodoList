package fr.iut.mytodolist.android
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.iut.mytodolist.android.fragment.HomeFragment
import fr.iut.mytodolist.android.fragment.NotificationFragment
import fr.iut.mytodolist.android.fragment.SettingsFragment

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment? = null
        when (item.itemId) {
            R.id.naviguation_todo -> {
                selectedFragment = HomeFragment()
            }
            R.id.navigation_settings -> {
                selectedFragment = SettingsFragment()
            }
            R.id.navigation_notifications -> {
                selectedFragment = NotificationFragment()
            }
        }

        if (selectedFragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, selectedFragment)
            transaction.commit()
        }

        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_mainactivity)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, HomeFragment())
        transaction.commit()

        navView.selectedItemId = R.id.naviguation_todo
    }
}