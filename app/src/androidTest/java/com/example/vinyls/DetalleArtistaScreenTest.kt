package com.example.vinyls

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetalleArtistaScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun navegarADetalleArtista() {
        // Paso 1: Ir al home
        composeTestRule.onNodeWithText("Continue").performClick()

        // Paso 2: Esperar que se cargue el home
        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Artistas").fetchSemanticsNodes().isNotEmpty()
        }

        // Paso 3: Ir a la lista de artistas
        composeTestRule.onNodeWithContentDescription("Ir a sección Artistas").performClick()

        // Paso 4: Esperar que aparezca el artista
        composeTestRule.waitUntil(timeoutMillis = 15_000) {
            composeTestRule.onAllNodesWithText("Rubén Blades Bellido de Luna").fetchSemanticsNodes().isNotEmpty()
        }

        // Paso 5: Ir al detalle (primer clickeable)
        composeTestRule
            .onAllNodesWithText("Rubén Blades Bellido de Luna")
            .filter(hasClickAction())
            .onFirst()
            .performClick()

        // Paso 6: Esperar que se cargue el detalle
        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithContentDescription("Foto del artista").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    // Paso 7: comprobar que tenga nombre
    fun testSeMuestraNombreDelArtista() {
        navegarADetalleArtista()
        composeTestRule.onNodeWithText("Rubén Blades Bellido de Luna").assertIsDisplayed()
    }

    @Test
    // Paso 8: comprobar que pueda ver toda la información
    fun testPuedeHacerScrollHastaElFinal() {
        navegarADetalleArtista()
        repeat(3) {
            composeTestRule.onRoot().performTouchInput { swipeUp() }
        }
        composeTestRule.onNodeWithText("← Volver a lista de artistas").assertExists()
    }

    @Test
    // Paso 9: que vea toda la imagen del artista
    fun testSeMuestraImagenDelArtista() {
        navegarADetalleArtista()
        composeTestRule.onNodeWithContentDescription("Foto del artista").assertExists()
    }
}




