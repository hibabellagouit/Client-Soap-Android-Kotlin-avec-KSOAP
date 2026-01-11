package ma.projet.soapclient.beans

import java.util.Date

    /**
      * hibabellagouit
    */
data class Compte(
    val id: Long?,              // Identifiant unique
    val solde: Double,          // Solde du compte
    val dateCreation: Date,     // Date de création
    val type: TypeCompte        // Type de compte (COURANT ou EPARGNE)
)

    /**
      * hibabellagouit
    */
enum class TypeCompte {
    COURANT, EPARGNE
}
