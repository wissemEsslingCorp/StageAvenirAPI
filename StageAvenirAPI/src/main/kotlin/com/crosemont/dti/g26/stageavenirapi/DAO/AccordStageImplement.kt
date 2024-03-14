package com.crosemont.dti.g26.stageavenirapi.DAO

import com.crosemont.dti.g26.stageavenirapi.Modèle.AccordStage
import com.crosemont.dti.g26.stageavenirapi.Modèle.MappingEnum.MappageEnum
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class AccordStageImplement (val bd : JdbcTemplate, var daoUser : UtilisateurDAO) : AccordStageDAO {
    val mappage = MappageEnum()
    override fun ajouter(element: AccordStage): AccordStage? {
        bd.update(
                "INSERT INTO accordStage ( commentaire, etat, utilisateur_idutilisateur, offreStage_idoffreStage) VALUES (?, ?, ?, ?)",

                element.commentaire,
                element.etat.toString(),
                element.etudiant?.idutilisateur ?: 0,
                element.offre?.idOffreStage ?: 0,

        )

        return element
    }

    override fun chercherTous(): List<AccordStage> {
         var result = bd.query("SELECT * FROM accordStage ") { response, _ ->

            AccordStage(
                    idAccord = response.getInt("idaccordStage"),
                    commentaire = response.getString("commentaire"),
                    etat = mappage.mapToEtat(response.getString("etat")),
                    etudiant = daoUser.chercherUserParCode(response.getString("utilisateur_idutilisateur")) ,
                    offre = null,
            )

        }
        return result
    }

    override fun chercherParCode(code: Int): AccordStage? {
        var result = bd.query("SELECT * FROM accordStage WHERE idaccordStage = ?", arrayOf(code)) { response, _ ->

            AccordStage(
                    idAccord = response.getInt("idaccordStage"),
                    commentaire = response.getString("commentaire"),
                    etat = mappage.mapToEtat(response.getString("etat")),
                    etudiant = null ,
                    offre = null,
            )

        }
        return result.firstOrNull()
    }

    override fun modifier(id: Int, element: AccordStage): AccordStage? {
        TODO("Not yet implemented")
    }

    override fun approuverUnAccord(element: Int): AccordStage? {
        try {
            bd.update(
                    "UPDATE accordStage SET etat = 'ACCEPTEE' WHERE idaccordStage = ?", element
            )
            return chercherParCode(element)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    override fun désaprouverUnAccord(element: Int): AccordStage? {
        bd.update(
                "UPDATE accordStage SET etat = 'REFUSEE' WHERE idaccordStage = ?",

                element
        )
        return chercherParCode(element)
    }


    override fun selectionnerAccordParCategorie(categorieId: Int): List<AccordStage>? {
        var result = bd.query("SELECT * FROM accordStage WHERE utilisateur_idutilisateur = ?", arrayOf(categorieId)) { response, _ ->

            AccordStage(
                    idAccord = response.getInt("idaccordStage"),
                    commentaire = response.getString("commentaire"),
                    etat = mappage.mapToEtat(response.getString("etat")),
                    etudiant = null ,
                    offre = null,
            )

        }
        return result
    }



    override fun effacer(code: Int) {
        TODO("Not yet implemented")
    }
}