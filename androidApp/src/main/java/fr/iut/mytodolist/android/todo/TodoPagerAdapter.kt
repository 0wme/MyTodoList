package fr.iut.mytodolist.android.todo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import fr.iut.mytodolist.android.fragment.TodoAFaireFragment
import fr.iut.mytodolist.android.fragment.TodoRealiseFragment
import fr.iut.mytodolist.android.fragment.TodoRetardFragment

class TodoPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TodoRetardFragment()
            1 -> TodoAFaireFragment()
            2 -> TodoRealiseFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Retard"
            1 -> "A Faire"
            2 -> "Réalisé"
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}