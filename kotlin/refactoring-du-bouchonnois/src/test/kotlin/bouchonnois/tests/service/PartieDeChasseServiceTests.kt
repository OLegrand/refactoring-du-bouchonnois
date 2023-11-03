package bouchonnois.tests.service

import bouchonnois.domain.PartieStatus
import bouchonnois.service.PartieDeChasseService
import bouchonnois.tests.doubles.PartieDeChasseRepositoryForTests
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class PartieDeChasseServiceTests : FeatureSpec({
    feature("démarrer une partie de chasse") {
        scenario("avec plusieurs chasseurs") {
            val repository = PartieDeChasseRepositoryForTests()
            val service = PartieDeChasseService(repository) { LocalDateTime.now() }
            val chasseurs = mapOf(
                ("Dédé" to 20),
                ("Bernard" to 8),
                ("Robert" to 12)
            )
            val terrainDeChasse = ("Pitibon sur Sauldre" to 3)

            val id = service.démarrer(
                terrainDeChasse,
                chasseurs
            )

            val savedPartieDeChasse = repository.savedPartieDeChasse!!
            savedPartieDeChasse.id shouldBe id
            savedPartieDeChasse.status shouldBe PartieStatus.EN_COURS
            savedPartieDeChasse.terrain!!.nom shouldBe "Pitibon sur Sauldre"
            savedPartieDeChasse.terrain!!.nbGalinettes shouldBe 3
            savedPartieDeChasse.chasseurs!! shouldHaveSize 3
            savedPartieDeChasse.chasseurs!![0].nom shouldBe "Dédé"
            savedPartieDeChasse.chasseurs!![0].ballesRestantes shouldBe 20
            savedPartieDeChasse.chasseurs!![0].nbGalinettes shouldBe 0
            savedPartieDeChasse.chasseurs!![1].nom shouldBe "Bernard"
            savedPartieDeChasse.chasseurs!![1].ballesRestantes shouldBe 8
            savedPartieDeChasse.chasseurs!![1].nbGalinettes shouldBe 0
            savedPartieDeChasse.chasseurs!![2].nom shouldBe "Robert"
            savedPartieDeChasse.chasseurs!![2].ballesRestantes shouldBe 12
            savedPartieDeChasse.chasseurs!![2].nbGalinettes shouldBe 0
        }
    }
})