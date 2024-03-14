package com.crosemont.dti.g26.stageavenirapi.DAO

import com.crosemont.dti.g26.stageavenirapi.Exceptions.RessourceInexistanteException
import com.crosemont.dti.g26.stageavenirapi.Modèle.*
import com.crosemont.dti.g26.stageavenirapi.Modèle.MappingEnum.MappageEnum
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class OffreStageDAOImplement(val db: JdbcTemplate, var daoCategorie : CategorieDAO, var daoEntreprise : EntrepriseDAO): OffreStageDAO {
    private var mappage = MappageEnum()
    override fun ajouter(offre: OffreStage): OffreStage? {

        val sql = "INSERT INTO OffreStage ( titreOffre, posteOffert, description, estRémunéré, dateDébut, dateFin, estVisible,catégorieId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"

        db.update(
                sql,

                offre.titreOffre,
                offre.posteOffert,
                offre.description,
                offre.estRémunéré,
                offre.datePost,
                offre.estVisible,
                offre.catégorie?.idCatégorie ?: 0
        )

        return offre

    }

    override fun chercherTous(): List<OffreStage> {
        val sql = "SELECT * FROM OffreStage"
        return db.query(sql) { résultat, _ ->
            OffreStage(
                idOffreStage = résultat.getInt("idoffreStage"),
                titreOffre = résultat.getString("titre"),
                posteOffert = résultat.getString("poste_offert"),
                description = résultat.getString("description"),
                estRémunéré = résultat.getBoolean("remunere"),
                datePost = résultat.getDate("date").toLocalDate(),
                estVisible = résultat.getBoolean("visible"),
                etat = mappage.mapToEtat(résultat.getString("etat")),
                entreprise = daoEntreprise.chercherParCode(résultat.getInt("entreprise_identreprise")) ,
                catégorie = daoCategorie.chercherParCode(résultat.getInt("categorie_idcategorie"))
            )
        }
    }

    override fun ajouterUneOffre(codeEntreprise: Int, offre: OffreStage): OffreStage? {
        println("l'Offre en question :" + offre)
        db.update(
                "INSERT INTO offrestage (titre, poste_offert, description, remunere, date,visible,categorie_idcategorie,entreprise_identreprise) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                offre.titreOffre,
                offre.posteOffert,
                offre.description,
                offre.estRémunéré,
                offre.datePost,
                false,
                offre.catégorie?.idCatégorie ?: null,
                codeEntreprise
        );

        return offre
    }


    override fun chercherParCode(code: Int): OffreStage? {
        val sql = "SELECT * FROM offreStage WHERE idoffreStage = ?"
        val result =  db.query(sql, arrayOf(code)) { résultat, _ ->
            OffreStage(

                idOffreStage = résultat.getInt("idoffreStage"),
                titreOffre = résultat.getString("titre"),
                posteOffert = résultat.getString("poste_offert"),
                description = résultat.getString("description"),
                estRémunéré = résultat.getBoolean("remunere"),
                datePost = résultat.getDate("date").toLocalDate(),
                estVisible = résultat.getBoolean("visible"),
                etat = mappage.mapToEtat(résultat.getString("etat")),
                entreprise = daoEntreprise.chercherParCode(résultat.getInt("entreprise_identreprise")) ,
                catégorie = daoCategorie.chercherParCode(résultat.getInt("categorie_idcategorie"))
            )
        }

        return result.firstOrNull()
    }


    override fun modifier(id: Int, offre: OffreStage): OffreStage? {

        val sql = "UPDATE offeStage SET titre = ?, posteOffert = ?, description = ?, estRémunéré = ?, dateDébut = ?, dateFin = ?, estVisible = ?,catégorieId = ? " +
                " WHERE idoffreStage = ?"

        db.update(
                sql,
                offre.titreOffre,
                offre.posteOffert,
                offre.description,
                offre.estRémunéré,
                offre.datePost,
                offre.estVisible,
                offre.catégorie?.idCatégorie ?: 0,
                id
        )

        return chercherParCode(id)
    }

    override fun modifierVisibilité(id: Int, visibilité :Boolean, état : String): OffreStage? {

        val sql = "UPDATE OffreStage SET visible = ? , etat = ?  WHERE idoffreStage = ?"

        val affectedRows = db.update(sql, visibilité,état , id)

        if (affectedRows > 0) {
            return chercherParCode(id)
        } else {

            return chercherParCode(id)
        }
    }

    override fun chercherParCodeCatégorie(code_categorie: Int): List<OffreStage> {
        val sql = "SELECT * FROM offreStage WHERE categorie_idcategorie = ?"
        val result =  db.query(sql, arrayOf(code_categorie)) { résultat, _ ->
            OffreStage(
                    idOffreStage = résultat.getInt("idoffreStage"),
                    titreOffre = résultat.getString("titre"),
                    posteOffert = résultat.getString("poste_offert"),
                    description = résultat.getString("description"),
                    estRémunéré = résultat.getBoolean("remunere"),
                    datePost = résultat.getDate("date").toLocalDate(),
                    estVisible = résultat.getBoolean("visible"),
                    etat = mappage.mapToEtat(résultat.getString("etat")),
                    entreprise = daoEntreprise.chercherParCode(résultat.getInt("entreprise_identreprise")) ,
                    catégorie = daoCategorie.chercherParCode(résultat.getInt("categorie_idcategorie"))
            )
        }

        return result.toList()

    }

    override fun obtenirOffresEnCoursApprobation(): List<OffreStage> {
        val sql = "SELECT * FROM offreStage WHERE visible = false and etat='EN_COURS'"
        return db.query(sql) { résultat, _ ->
            OffreStage(
                idOffreStage = résultat.getInt("idoffreStage"),
                titreOffre = résultat.getString("titre"),
                posteOffert = résultat.getString("poste_offert"),
                description = résultat.getString("description"),
                estRémunéré = résultat.getBoolean("remunere"),
                datePost = résultat.getDate("date").toLocalDate(),
                estVisible = résultat.getBoolean("visible"),
                etat = mappage.mapToEtat(résultat.getString("etat")),
                entreprise = daoEntreprise.chercherParCode(résultat.getInt("entreprise_identreprise")) ,
                catégorie = daoCategorie.chercherParCode(résultat.getInt("categorie_idcategorie"))

            )
        }
    }

    override fun effacer(code: Int) {
        val sql = "DELETE FROM OffreStage WHERE idOffreStage = ?"
        val affectedRows = db.update(sql, code)

        if (affectedRows == 0) {
            throw RessourceInexistanteException("L'offre $code n'est pas inscrite au service.")
        }

    }
}