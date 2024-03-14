
package com.crosemont.dti.g26.stageavenirapi.DAO

import com.crosemont.dti.g26.stageavenirapi.Modèle.Candidature
import com.crosemont.dti.g26.stageavenirapi.Modèle.Document
import com.crosemont.dti.g26.stageavenirapi.Modèle.MappingEnum.MappageEnum
import com.crosemont.dti.g26.stageavenirapi.Modèle.OffreStage
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CandidatureDAOImplement(val bd : JdbcTemplate , val daoDoc :DocumentDAO , val daoOffre : OffreStageDAO, var daoUser : UtilisateurDAO ) : CandidatureDAO {

    private var mappage = MappageEnum()

    override fun ajouter(element: Candidature): Candidature? {
        TODO()
    }

    override fun chercherParCode(code: Int): Candidature? {
        var candidature: Candidature? = null

            var result = bd.query("SELECT * FROM candidature WHERE idcandidature = ?", arrayOf(code)) { response, _ ->

                     Candidature(
                        idCandidature = response.getInt("idcandidature"),
                        etat = mappage.mapToEtat(response.getString("etat")),
                        commentaire = response.getString("description"),
                        offre = daoOffre.chercherParCode(response.getInt("offreStage_idoffreStage")),
                        etudiant = daoUser.chercherUserParCode(response.getString("utilisateur_idutilisateur")) ,
                        documents =  daoDoc.chercherParCandidature(response.getInt("idcandidature"))

                    )

            }

        return result.firstOrNull()
    }

    override fun chercherTous(): List<Candidature> {
       val candidatures = mutableListOf<Candidature>()
       bd.query("SELECT * FROM candidature order by idcandidature") { response, _ ->
            while (response.next()) {
                val candidature =  Candidature(
                    idCandidature = response.getInt("idcandidature"),
                    etat = mappage.mapToEtat(response.getString("etat")),
                    commentaire = response.getString("description"),
                    offre = daoOffre.chercherParCode(response.getInt("offreStage_idoffreStage")),
                    etudiant = daoUser.chercherUserParCode(response.getString("utilisateur_idutilisateur")),
                    documents =  daoDoc.chercherParCandidature(response.getInt("idcandidature"))
                )
                candidatures.add(candidature)
            }
        }
        return candidatures
    }

    override fun modifier(id:Int, element: Candidature): Candidature {
        bd.update(
                "UPDATE candidature SET description = ? WHERE idcandidature = ?",
                element.commentaire,
                element.idCandidature
        )

        return chercherParCode(element.idCandidature)!!
    }

    override fun effacer(element: Int) {
        bd.update(
                "DELETE FROM candidature WHERE idcandidature = ?",
                element
        )

    }

    override fun chercherParEtudiant(code_etudiant: String): List<Candidature> {
        val candidatures = mutableListOf<Candidature>()

        bd.query(
                "SELECT * FROM candidature WHERE utilisateur_idutilisateur = ? AND etat != 'ANNULEE'",
                arrayOf(code_etudiant)
        ) { response, _ ->
            val candidature = Candidature(
                    idCandidature = response.getInt("idcandidature"),
                    etat = mappage.mapToEtat(response.getString("etat")),
                    commentaire = response.getString("description"),
                    offre = daoOffre.chercherParCode(response.getInt("offreStage_idoffreStage")),
                    etudiant = daoUser.chercherUserParCode(response.getString("utilisateur_idutilisateur")),
                    documents = daoDoc.chercherParCandidature(response.getInt("idcandidature"))
            )
            candidatures.add(candidature)
        }
        println(candidatures.toString())
        return candidatures
    }



    override fun chercherParOffreStage(code_offre: Int): List<Candidature> {
        var candidatures = mutableListOf<Candidature>()

        bd.query("SELECT * FROM candidature WHERE offreStage_idoffreStage = ?  AND etat != 'ANNULEE'", arrayOf(code_offre)) { response, _ ->
            if (response.next()) {
                var candidature = Candidature(
                    idCandidature = response.getInt("idcandidature"),
                    etat = mappage.mapToEtat(response.getString("etat")),
                    commentaire = response.getString("description"),
                    offre = daoOffre.chercherParCode(response.getInt("offreStage_idoffreStage")),
                    etudiant = daoUser.chercherUserParCode(response.getString("utilisateur_idutilisateur")),
                    documents =  daoDoc.chercherParCandidature(response.getInt("idcandidature"))
                )
                candidatures.add(candidature)
            }
        }

        return candidatures
    }

    override fun postulerPourUneOffre(candidature: Candidature, code_etudiant: String,idOffre:Int):Candidature? {

        var nouvelleCandidature = candidature
         var result = bd.update(
            "INSERT INTO candidature ( etat, description, utilisateur_idutilisateur, offreStage_idoffreStage) VALUES ( ?, ?, ?, ?)",

             candidature.etat?.name ?: "EN_ATTENTE",
            candidature.commentaire,
            code_etudiant,
            idOffre
        )
        var generatedId = chercherTous().get(chercherTous().size -1 ).idCandidature
        nouvelleCandidature.idCandidature = generatedId
        nouvelleCandidature = chercherTous().get(chercherTous().size -1 )
       return nouvelleCandidature

    }

    override fun annulerCandidature(candidature: Int): Candidature? {
        bd.update(
                "UPDATE candidature SET etat = 'ANNULEE' WHERE idcandidature = ?",

                candidature
        )

        return chercherParCode(candidature)
    }

    override fun accepterCandidature(candidature: Int): Candidature? {
        bd.update(
                "UPDATE candidature SET etat = 'acceptee' WHERE idcandidature = ?",

                candidature
        )
        return chercherParCode(candidature)
    }

    override fun refuserCandidature(candidature: Int): Candidature? {
        bd.update(
                "UPDATE candidature SET etat = 'refusee' WHERE idcandidature = ?",

                candidature
        )
        return chercherParCode(candidature)
    }


}

