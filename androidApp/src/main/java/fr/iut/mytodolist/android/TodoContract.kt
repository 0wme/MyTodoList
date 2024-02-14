import android.provider.BaseColumns

object TodoContract {
    object TodoEntry : BaseColumns {
        const val TABLE_NAME = "todo"
        const val COLUMN_TODO = "todo"
        const val COLUMN_COLOR = "color"
        const val COLUMN_APPROVED = "approved" // Nouvelle colonne pour l'état d'approbation
    }
}