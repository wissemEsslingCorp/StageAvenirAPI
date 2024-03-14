package com.crosemont.dti.g26.stageavenirapi.DAO


import com.crosemont.dti.g26.stageavenirapi.Modèle.Catégorie
import com.crosemont.dti.g26.stageavenirapi.Modèle.DemandeStage

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CategorieDAOImplement(val bd: JdbcTemplate): CategorieDAO {
    override fun ajouter(catégorie: Catégorie): Catégorie? {
        val sql = "INSERT INTO categorie (idcategorie,nom) " +
                "VALUES (?, ?)"

        bd.update(
            sql,
            catégorie.idCatégorie,
            catégorie.cursus
        )

        return catégorie
    }

    override fun chercherTous(): List<Catégorie> {
        TODO("Not yet implemented")
    }

    override fun modifier(id: Int, catégorie: Catégorie): Catégorie? {
        TODO("Not yet implemented")
    }



    override fun effacer(id: Int ) {
        TODO("Not yet implemented")
    }

    override fun chercherParCode(code: Int): Catégorie? {
        var catégorie: Catégorie? = null

        try {
            bd.query("SELECT * FROM categorie WHERE idcategorie = ?", arrayOf(code)) { response, _ ->
                catégorie =  Catégorie(
                    idCatégorie= response.getInt("idcategorie"),
                    cursus =  response.getString("nom"),
                )
            }
        }catch (e: Exception){
            println("ERREUR DAO :" + e)
        }

        return catégorie
    }
}