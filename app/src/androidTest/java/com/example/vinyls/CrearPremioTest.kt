package com.example.vinyls

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vinyls.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.compose.ui.test.*

@RunWith(AndroidJUnit4::class)
class CrearPremioScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun navegarACrearPremio() {
        composeTestRule.onNodeWithText("Continue").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Premios").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithContentDescription("addPremioButton").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Crear Premios").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun testCrearPremioCamposYBoton() {
        navegarACrearPremio()

        composeTestRule.onNodeWithText("Nombre").performTextInput("Latin Grammy Awards")
        composeTestRule.onNodeWithText("Descripción").performTextInput("Premios otorgados por la Academia Latina de la Grabación para reconocer la excelencia en la música latina.")
        composeTestRule.onNodeWithText("Organización").performTextInput("Latin Recording Academy")

        composeTestRule.onNodeWithText("Crear").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("🎉 Premio creado exitosamente", useUnmergedTree = true).fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithText("🎉 Premio creado exitosamente", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun testCrearPremioNombreVacio() {
        navegarACrearPremio()

        composeTestRule.onNodeWithText("Descripción").performTextInput("Premio anual por excelencia.")
        composeTestRule.onNodeWithText("Organización").performTextInput("Latin Academy")

        composeTestRule.onNodeWithText("Crear").performClick()

        composeTestRule.onNodeWithText("El nombre no puede estar vacío").assertExists()
    }

    @Test
    fun testCrearPremioDescripcionVacio() {
        navegarACrearPremio()

        composeTestRule.onNodeWithText("Nombre").performTextInput("Premio Música")
        composeTestRule.onNodeWithText("Organización").performTextInput("Academia Música")

        composeTestRule.onNodeWithText("Crear").performClick()

        composeTestRule.onNodeWithText("La descripción no puede estar vacía").assertExists()
    }

    @Test
    fun testCrearPremioOrganizacionVacio() {
        navegarACrearPremio()

        composeTestRule.onNodeWithText("Nombre").performTextInput("Premio Música")
        composeTestRule.onNodeWithText("Descripción").performTextInput("Reconocimiento musical.")

        composeTestRule.onNodeWithText("Crear").performClick()

        composeTestRule.onNodeWithText("La organización no puede estar vacía").assertExists()
    }

    @Test
    fun testCrearPremioCamposEspeciales() {
        navegarACrearPremio()

        composeTestRule.onNodeWithText("Nombre").performTextInput("Premio%Especial")
        composeTestRule.onNodeWithText("Descripción").performTextInput("Reconocimiento#a la música.")
        composeTestRule.onNodeWithText("Organización").performTextInput("Academia@2025")

        composeTestRule.onNodeWithText("Crear").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("🎉 Premio creado exitosamente", useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("🎉 Premio creado exitosamente", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun testCrearPremioStringsConEspacios() {
        navegarACrearPremio()

        composeTestRule.onNodeWithText("Nombre").performTextInput("   ")
        composeTestRule.onNodeWithText("Descripción").performTextInput("  ")
        composeTestRule.onNodeWithText("Organización").performTextInput(" ")

        composeTestRule.onNodeWithText("Crear").performClick()

        composeTestRule.onNodeWithText("El nombre no puede estar vacío").assertExists()
    }
}
