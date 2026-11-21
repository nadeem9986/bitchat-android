package com.bitchat.android.ui.theme

import androidx.compose.ui.graphics.Color
import com.bitchat.android.ui.PeerIdentity
import org.junit.Assert.*
import org.junit.Test

class MonochromeThemeTest {
    private fun assertNeutral(color: Color) {
        assertEquals(color.red, color.green, 0.0001f)
        assertEquals(color.green, color.blue, 0.0001f)
    }

    @Test fun materialRolesAreNeutralInBothModes() {
        for (scheme in listOf(DarkBitchatColorScheme, LightBitchatColorScheme)) {
            listOf(scheme.primary, scheme.secondary, scheme.tertiary,
                scheme.background, scheme.surface, scheme.surfaceVariant,
                scheme.onSurface, scheme.onSurfaceVariant, scheme.outline,
                scheme.primaryContainer, scheme.secondaryContainer,
                scheme.tertiaryContainer, scheme.surfaceTint,
                scheme.surfaceContainer, scheme.surfaceContainerLow,
                scheme.surfaceContainerHigh, scheme.surfaceContainerHighest,
                scheme.inverseSurface, scheme.inversePrimary).forEach(::assertNeutral)
            assertTrue(scheme.error.red > scheme.error.green)
        }
    }

    @Test fun peerGreysAreStableAndReadable() {
        for (palette in listOf(DarkBitchatPalette, LightBitchatPalette)) {
            val samples = (1..20).map { colorForPeer(PeerIdentity.nickname("sample-$it"), palette) }
            assertTrue(samples.distinct().size > 1)
            samples.forEach { color ->
                assertNeutral(color)
                if (palette == DarkBitchatPalette) assertTrue(color.red in 0.449f..0.881f)
                else assertTrue(color.red in 0.059f..0.421f)
            }
            assertEquals(colorForPeer(PeerIdentity.nickname("sample-1"), palette), samples.first())
            listOf(palette.accentOrange, palette.accentPurple, palette.textTertiary,
                palette.inputOutline, palette.inputOutlineFocused).forEach(::assertNeutral)
        }
    }
}
