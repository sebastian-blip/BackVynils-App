package com.example.vinyls

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListarArtistasScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun navegarAListaDeArtistas() {
        // Paso 1: Ir al Home
        composeTestRule.onNodeWithText("Continue").performClick()

        // Paso 2: Esperar a que aparezca el botón para ir a lista de artistas
        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithContentDescription("addArtisButton").fetchSemanticsNodes().isNotEmpty()
        }

        // Paso 3: Ir a ListarArtistasScreen
        composeTestRule.onNodeWithContentDescription("addArtisButton").performClick()

        // Paso 4: Esperar a que se cargue la lista
        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Rubén Blades Bellido de Luna").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun testSeMuestraGridConArtistas() {
        navegarAListaDeArtistas()
        composeTestRule.onAllNodesWithText("Rubén Blades Bellido de Luna")[0].assertIsDisplayed()
    }

    @Test
    fun testBotonesDePaginacionFuncionan() {
        navegarAListaDeArtistas()

        // Verifica que el botón siguiente exista
        composeTestRule.onNodeWithText("▶").assertExists()

        // Si hay más de 1 página, verifica navegación
        composeTestRule.onNodeWithText("▶").performClick()
        composeTestRule.onNodeWithText("◀").assertExists()
    }

    @Test
    fun testClickEnArtistaNavegaADetalle() {
        navegarAListaDeArtistas()

        composeTestRule
            .onAllNodesWithText("Rubén Blades Bellido de Luna")
            .filter(hasClickAction())
            .onFirst()
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithContentDescription("Foto del artista").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithContentDescription("Foto del artista").assertExists()
    }
}
