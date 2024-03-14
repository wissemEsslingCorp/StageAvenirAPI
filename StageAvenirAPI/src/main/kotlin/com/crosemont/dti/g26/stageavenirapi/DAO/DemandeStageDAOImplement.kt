
package com.crosemont.dti.g26.stageavenirapi.DAO

import com.crosemont.dti.g26.stageavenirapi.Modèle.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class DemandeStageDAOImplement(val bd : JdbcTemplate,val daoCategorie: CategorieDAOImplement,
                               val daoUtilisateur: UtilisateurDAOImplement,
                               val daoCompetence: CompétenceDAOImplement): DemandeStageDAO {




    override fun ajouter(demande: DemandeStage): DemandeStage? {
        val sql = "INSERT INTO demandeStage (iddemandeStage, titre, description, remunere, poste, visible, categorie_idcategorie, utilisateur_idutilisateur) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"

        bd.update(
            sql,
            demande.idDemandeStage,
            demande.titre,
            demande.description,
            demande.remunere,
            demande.poste,
            demande.visible,
            demande.catégorie?.idCatégorie,
            demande.utilisateur?.idutilisateur
        )

        return demande
    }

    override fun chercherParCode(code: Int): DemandeStage? {
        var demande_de_stage: DemandeStage? = null

        try {
            bd.query("SELECT * FROM demandeStage WHERE iddemandeStage = ?", arrayOf(code)) { response, _ ->
                //if (response.next()) {
                    demande_de_stage =  DemandeStage(
                        idDemandeStage = response.getInt("iddemandeStage"),
                        titre = response.getString("titre"),
                        description = response.getString("description"),
                        remunere = response.getBoolean("remunere"),
                        poste = response.getString("poste"),
                        visible = response.getBoolean("visible"),
                        compétence = daoCompetence.chercherTousCompétenceParDemandeStage(response.getInt("iddemandeStage")),
                        catégorie = daoCategorie.chercherParCode(response.getInt("categorie_idcategorie")),
                        utilisateur = daoUtilisateur.chercherUserParCode(response.getString("utilisateur_idutilisateur"))
                   )
               // }
            }

        }catch (e: Exception){
            println("ERREUR DAO :" + e)
        }
        return demande_de_stage
    }




    override fun chercherTous(): List<DemandeStage> {
        val demande_stage = mutableListOf<DemandeStage>()
        bd.query("SELECT * FROM demandeStage") { response, _ ->
           // while (response.next())
                val demandeStage =  DemandeStage(
                    idDemandeStage = response.getInt("iddemandeStage"),
                    titre = response.getString("titre"),
                    description = response.getString("description"),
                    remunere = response.getBoolean("remunere"),
                    poste = response.getString("poste"),
                    visible = response.getBoolean("visible"),
                    compétence = daoCompetence?.chercherTousCompétenceParDemandeStage(response.getInt("iddemandeStage")),
                    catégorie = daoCategorie?.chercherParCode(response.getInt("categorie_idcategorie")),
                    utilisateur = daoUtilisateur?.chercherUserParCode(response.getString("utilisateur_idutilisateur"))
                )
                demande_stage.add(demandeStage)
           // }

        }
        return demande_stage
    }

    override fun modifier(id: Int, demande: DemandeStage): DemandeStage? {
        val sql ="UPDATE demandeStage SET titre = ?,description = ?,remunere = ?, poste = ?,visible = ?,categorie_idcategorie = ?,utilisateur_idutilisateur = ?" +" WHERE iddemandeStage = ?"
        bd.update(
            sql,
            demande.titre,
            demande.description,
            demande.remunere,
            demande.poste,
            demande.visible,
            demande.catégorie?.idCatégorie,
            demande.utilisateur?.idutilisateur,
            id
        )

        return chercherParCode(demande.idDemandeStage)!!
    }

    override fun modifierStatus(id: Int, demande: DemandeStage): DemandeStage? {
        bd.update(
            "UPDATE demandeStage SET visible = ? WHERE iddemandeStage = ?",
            demande.visible,
            id
        )
        return chercherParCode(id)!!
    }


    override fun effacer(id: Int) {
        bd.update(
            "DELETE FROM demandeStage WHERE iddemandeStage = ?",
            id
        )

    }

}