package com.crosemont.dti.g26.stageavenirapi.DAO

import com.crosemont.dti.g26.stageavenirapi.Modèle.Compétence
import com.crosemont.dti.g26.stageavenirapi.Modèle.DemandeStage
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CompétenceDAOImplement (val bd: JdbcTemplate): CompétenceDAO {
    val daoDemandeStage: DemandeStageDAOImplement? = null
    override fun chercherTousCompétenceParDemandeStage(code: Int): List<Compétence> {
        val compétences = mutableListOf<Compétence>()

        try {
            bd.query("SELECT * FROM competence WHERE demandeStage_iddemandeStage = ?", arrayOf(code)) { response, _ ->
                //while (response.next()) {

                    val compétence = Compétence(
                        idCompétence = response.getInt("idcompetence"),
                        demandeStage = daoDemandeStage?.chercherParCode(response.getInt("demandeStage_iddemandeStage")),
                        nom = response.getString("nom"),

                        )
                    compétences.add(compétence)
               // }
            }

        }catch (e: Exception){

        }
        return compétences
    }

    override fun chercherTous(): List<Compétence> {
        TODO("Not yet implemented")
    }

    override fun chercherParCode(code: Int): Compétence? {
        TODO("Not yet implemented")
    }

    override fun ajouter(element: Compétence): Compétence? {
        TODO("Not yet implemented")
    }

    override fun modifier(id: Int, element: Compétence): Compétence? {
        TODO("Not yet implemented")
    }

    override fun effacer(code: Int) {
        TODO("Not yet implemented")
    }

}