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
        composeTestRule.onNodeWithText("Continue").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Artistas").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithContentDescription("addArtisButton").performClick()

        composeTestRule.waitUntil(timeoutMillis = 15_000) {
            composeTestRule.onAllNodesWithText("Rubén Blades Bellido de Luna").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun testSeMuestraNombreDelArtista() {
        navegarADetalleArtista()
        composeTestRule.onNodeWithText("Rubén Blades Bellido de Luna").assertIsDisplayed()
    }

    @Test
    fun testPuedeHacerScrollHastaElFinal() {
        navegarADetalleArtista()

        repeat(3) {
            composeTestRule.onRoot().performTouchInput { swipeUp() }
        }

        composeTestRule.onNodeWithText("← Volver a lista de artistas").assertExists()
    }


    @Test
    fun testSeMuestraImagenDelArtista() {
        navegarADetalleArtista()
        composeTestRule.onNodeWithContentDescription("Foto del artista").assertExists()
    }
}



