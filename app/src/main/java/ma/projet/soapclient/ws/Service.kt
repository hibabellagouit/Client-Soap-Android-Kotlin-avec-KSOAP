package ma.projet.soapclient.ws

import ma.projet.soapclient.beans.Compte
import ma.projet.soapclient.beans.TypeCompte
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import java.text.SimpleDateFormat
import java.util.*

/**
 * Service pour la communication avec le serveur SOAP de gestion des comptes
 * Gère les opérations CRUD sur les comptes bancaires
 */
class Service {
    // Configuration du service SOAP
    private val NAMESPACE = "http://ws.soapAcount/"
    private val URL = "http://10.0.2.2:8082/services/ws"
    
    // Noms des méthodes du service web
    private val METHOD_GET_COMPTES = "getComptes"
    private val METHOD_CREATE_COMPTE = "createCompte"
    private val METHOD_DELETE_COMPTE = "deleteCompte"
    
    /**
     * Récupère la liste complète des comptes bancaires
     * @return Liste des comptes ou liste vide en cas d'erreur
     */
    fun getComptes(): List<Compte> {
        val request = SoapObject(NAMESPACE, METHOD_GET_COMPTES)
        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11).apply {
            dotNet = false
            setOutputSoapObject(request)
        }
        val transport = HttpTransportSE(URL)
        val comptes = mutableListOf<Compte>()
        
        try {
            transport.call("", envelope)
            val response = envelope.bodyIn as SoapObject
            
            // Conversion des données SOAP en objets Compte
            for (i in 0 until response.propertyCount) {
                val soapCompte = response.getProperty(i) as SoapObject
                val compte = Compte(
                    id = soapCompte.getPropertySafelyAsString("id")?.toLongOrNull(),
                    solde = soapCompte.getPropertySafelyAsString("solde")?.toDoubleOrNull() ?: 0.0,
                    dateCreation = SimpleDateFormat("yyyy-MM-dd").parse(
                        soapCompte.getPropertySafelyAsString("dateCreation")
                    ) ?: Date(),
                    type = TypeCompte.valueOf(soapCompte.getPropertySafelyAsString("type"))
                )
                comptes.add(compte)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        return comptes
    }
    
    /**
     * Crée un nouveau compte bancaire
     * @param solde Montant initial du compte
     * @param type Type de compte (COURANT ou EPARGNE)
     * @return true si la création a réussi, false sinon
     */
    fun createCompte(solde: Double, type: TypeCompte): Boolean {
        val request = SoapObject(NAMESPACE, METHOD_CREATE_COMPTE).apply {
            addProperty("solde", solde)
            addProperty("type", type.name)
        }
        
        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11).apply {
            dotNet = false
            setOutputSoapObject(request)
        }
        
        return try {
            HttpTransportSE(URL).call("", envelope)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    /**
     * Supprime un compte existant par son identifiant
     * @param id Identifiant unique du compte à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    fun deleteCompte(id: Long): Boolean {
        val request = SoapObject(NAMESPACE, METHOD_DELETE_COMPTE).apply {
            addProperty("id", id)
        }
        
        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11).apply {
            dotNet = false
            setOutputSoapObject(request)
        }
        
        return try {
            val transport = HttpTransportSE(URL)
            transport.call("", envelope)
            envelope.response as? Boolean ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

// Extension function pour obtenir une propriété SoapObject en toute sécurité
fun SoapObject.getPropertySafelyAsString(name: String): String? {
    return try {
        getProperty(name)?.toString()
    } catch (e: Exception) {
        null
    }
}
