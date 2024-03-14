package com.crosemont.dti.g26.stageavenirapi.DAO

import com.crosemont.dti.g26.stageavenirapi.Modèle.Candidature
import com.crosemont.dti.g26.stageavenirapi.Modèle.DemandeStage
import com.crosemont.dti.g26.stageavenirapi.Modèle.Document
import com.crosemont.dti.g26.stageavenirapi.Modèle.MappingEnum.MappageEnum
import com.crosemont.dti.g26.stageavenirapi.Modèle.Utilisateur
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class DocumentDAOImplement (val bd : JdbcTemplate, var daoEtudiant : UtilisateurDAO, var daoDemande : DemandeStageDAO, var daoCategorie : CategorieDAO ): DocumentDAO {
    private var mappage = MappageEnum()


    override fun ajouterDocumentACandidature(element: Document, code: Int): Document? {
        println("CODE" + code)
        var idDocument = bd.update(
                "INSERT INTO document (nom,type,contenu,candidature_idcandidature) VALUES (?, ?, ?, ?)",
                element.nom,
                element.type.toString(),
                element.contenu,
                code
        )

        if (idDocument > 0){
            return  chercherParCode(idDocument)
            println("DAO DOCUMENT :" + idDocument)
        }else{
            return null
        }
    }

    override fun ajouterDocumentADemandeStage(element: Document, code: Int): Document? {
        var idDocument = bd.update(
                "INSERT INTO document (nom,type,contenu,demandeStage_iddemandeStage) VALUES (?, ?, ?, ?)",
                element.nom,
                element.type.toString(),
                element.contenu,
                code
        )

        if (idDocument > 0){
            return  chercherParCode(idDocument)
        }else{
            return null
        }
    }

    override fun chercherParCandidature(candidature: Int): List<Document>? {
        var documents = mutableListOf<Document>()

        bd.query("SELECT * FROM document   WHERE candidature_idcandidature = ?", arrayOf(candidature)) { response, _ ->
            while (response.next()) {

                var  document = Document(
                        idDocument = response.getInt("iddocument"),
                        nom = response.getString("nom"),
                        type = mappage.mapToType(response.getString("type")),
                        contenu = response.getBytes("contenu"),
                        etudiant = null,
                        demande = null,
                        candidature = null
                )

                documents.add(document)
            }
        }

        return documents
    }

    override fun chercherParDemandeStage(demandeStage: Int): List<Document> {
        var documents = mutableListOf<Document>()
        var demande = chercherDemandeDeStageParCode(demandeStage )
        bd.query("SELECT * FROM document WHERE demandeStage_iddemandeStage = ?", arrayOf(demandeStage)) { response, _ ->
            if (response.next()) {
                var  document = Document(
                        idDocument = response.getInt("iddocument"),
                        nom = response.getString("nom"),
                        type = mappage.mapToType(response.getString("type")),
                        contenu = response.getBytes("description"),
                        etudiant = null,
                        demande = demande,
                        candidature = null
                )

                documents.add(document)
            }
        }

        return documents
    }

    override fun ajouterCv(cv: Document, idEtudiant: String): Document? {
        var idDocument = bd.update(
                "INSERT INTO document (nom,type,contenu,utilisateur_idutilisateur) VALUES (?, ?, ?, ?)",
                cv.nom,
                "cv",
                cv.contenu,
                idEtudiant
        )

        if (idDocument > 0){
            return  chercherParCode(idDocument)
        }else{
            return null
        }
    }

    override fun modifierCv(cv: Document): Document? {
        val rowsAffected = bd.update(
                "UPDATE document SET nom = ?, type = ?, contenu = ? WHERE  iddocument = ?",
                cv.nom,
                "cv",
                cv.contenu,
                cv.idDocument
        )

        if (rowsAffected > 0) {
            return chercherParCode(cv.idDocument)
        } else {
            return null
        }
    }

    override fun obtenirCvParEtudiant(idEtudiant: String): Document? {
        var result = bd.query("SELECT * FROM document WHERE utilisateur_idutilisateur = ?", arrayOf(idEtudiant)) { response, _ ->

             Document(
                idDocument = response.getInt("iddocument"),
                nom = response.getString("nom"),
                type = mappage.mapToType(response.getString("type")),
                contenu = response.getBytes("contenu"),
                etudiant = null,
                demande = null,
                candidature = null
            )

        }
        return result.firstOrNull()
    }


    override fun chercherParCode(code: Int): Document? {
        var document : Document? = null

        bd.query("SELECT * FROM document WHERE iddocument = ?", arrayOf(code)) { response, _ ->
            if (response.next()) {
                var  document = Document(
                        idDocument = response.getInt("iddocument"),
                        nom = response.getString("nom"),
                        type = mappage.mapToType(response.getString("type")),
                        contenu = response.getBytes("description"),
                        etudiant = daoEtudiant.chercherUserParCode(response.getString("idutilisateur")),
                        demande = null,
                        candidature = null
                )
            }
        }

        return document
    }

    override fun chercherTous(): List<Document> {
        var documents = mutableListOf<Document>()

        bd.query("SELECT * FROM document ") { response, _ ->
            if (response.next()) {
                var  document = Document(
                        idDocument = response.getInt("iddocument"),
                        nom = response.getString("nom"),
                        type = mappage.mapToType(response.getString("type")),
                        contenu = response.getBytes("contenu"),
                        etudiant = daoEtudiant.chercherUserParCode(response.getString("idutilisateur")),
                        demande = null,
                        candidature = null
                )
                documents.add(document)
            }
        }
        return documents
    }


    private fun chercherCandidatureParCode(code: Int): Candidature? {
        var candidature: Candidature? = null

        bd.query("SELECT * FROM candidature WHERE idcandidature = ?", arrayOf(code)) { response, _ ->
            if (response.next()) {
                candidature = Candidature(
                        idCandidature = response.getInt("id"),
                        etat = mappage.mapToEtat(response.getString("etat")),
                        commentaire = response.getString("description"),
                        offre = null,
                        etudiant = null,
                        documents = mutableListOf<Document>()

                )
            }
        }

        return candidature
    }

    private fun chercherDemandeDeStageParCode(code: Int): DemandeStage? {
        var demande: DemandeStage? = null
        /*
        bd.query("SELECT * FROM demandeStage WHERE iddemandeStage = ?", arrayOf(code)) { response, _ ->
            if (response.next()) {
                demande = DemandeStage(
                        idDemandeStage = response.getInt("iddemandeStage"),
                        titre = response.getString("titre"),
                        description = response.getString("description"),
                        posteDemandé = response.getString("poste"),
                        etudiant = daoEtudiant.chercherUserParCode(response.getString("utilisateur_idutilisateur")),
                        catégorie = daoCategorie.chercherParCode(response.getInt("categorie_idcategorie"))
                )
            }
        }
        */
        return demande
    }


    //inutiles

    override fun modifier(id :Int, element: Document): Document {
        TODO("Not yet implemented")
    }

    override fun effacer(element: Int) {
        TODO("Not yet implemented")
    }

    override fun ajouter(element: Document): Document? {
        TODO("Not yet implemented")
    }



}