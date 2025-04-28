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

    @Test
    fun testCrearPremioCamposYBoton() {
        composeTestRule.onNodeWithText("Continue").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Premios").fetchSemanticsNodes().isNotEmpty()
        }


        composeTestRule.onNodeWithContentDescription("addPremioButton").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Crear Premios").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Nombre").performTextInput("Latin Grammy Awards")
        composeTestRule.onNodeWithText("Descripción").performTextInput("Premios otorgados por la Academia Latina de la Grabación para reconocer la excelencia en la música latina.")
        composeTestRule.onNodeWithText("Organización").performTextInput("Latin Recording Academy")

        composeTestRule.onNodeWithText("Crear").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("🎉 Premio creado exitosamente").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("🎉 Premio creado exitosamente")
            .assertExists()
    }
    @Test
    fun testCrearPremioNombreVacio() {
        composeTestRule.onNodeWithText("Continue").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Premios").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithContentDescription("addPremioButton").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Crear Premios").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Descripción").performTextInput("Premios otorgados por la Academia Latina de la Grabación para reconocer la excelencia en la música latina.")
        composeTestRule.onNodeWithText("Organización").performTextInput("Latin Recording Academy")

        composeTestRule.onNodeWithText("Crear").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("El nombre no puede estar vacío").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("El nombre no puede estar vacío")
            .assertExists()
    }

    @Test
    fun testCrearPremioDescripcionVacio() {
        composeTestRule.onNodeWithText("Continue").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Premios").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithContentDescription("addPremioButton").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Crear Premios").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Nombre").performTextInput("Latin Grammy Awards")
        composeTestRule.onNodeWithText("Organización").performTextInput("Latin Recording Academy")

        composeTestRule.onNodeWithText("Crear").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("La descripción no puede estar vacía").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("La descripción no puede estar vacía")
            .assertExists()
    }


    @Test
    fun testCrearPremioorganizacionVacio() {
        composeTestRule.onNodeWithText("Continue").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Premios").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithContentDescription("addPremioButton").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Crear Premios").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Nombre").performTextInput("Latin Grammy Awards")
        composeTestRule.onNodeWithText("Descripción").performTextInput("Premios otorgados por la Academia Latina de la Grabación para reconocer la excelencia en la música latina.")

        composeTestRule.onNodeWithText("Crear").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("La organización no puede estar vacía").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("La organización no puede estar vacía")
            .assertExists()
    }

    @Test
    fun testCrearPremioCamposEspeciales() {
        composeTestRule.onNodeWithText("Continue").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Premios").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithContentDescription("addPremioButton").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Crear Premios").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Nombre").performTextInput("Latin Grammy Awards")
        composeTestRule.onNodeWithText("Descripción").performTextInput("Premios otorgados$ por %la Academia Latina de la Grabación para reconocer la excelencia en la música latina.")
        composeTestRule.onNodeWithText("Organización").performTextInput("$$%$%$")

        composeTestRule.onNodeWithText("Crear").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("🎉 Premio creado exitosamente").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("🎉 Premio creado exitosamente")
            .assertExists()
    }

    @Test
    fun testCrearPremioStrinVacio() {
        composeTestRule.onNodeWithText("Continue").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Premios").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithContentDescription("addPremioButton").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithText("Crear Premios").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Nombre").performTextInput(" ")
        composeTestRule.onNodeWithText("Descripción").performTextInput(" ")
        composeTestRule.onNodeWithText("Organización").performTextInput("  ")

        composeTestRule.onNodeWithText("Crear").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("La organización no puede estar vacía").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("La organización no puede estar vacía")
            .assertExists()
    }

}
