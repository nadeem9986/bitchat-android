package com.bitchat.android.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.bitchat.android.ui.PeerIdentity

/**
 * Theme-specific chroma applied after a peer's stable hue is derived.
 *
 * Hue stays identity-stable across themes (and byte-identical to iOS). Only saturation
 * and value change so peer labels remain readable on each background.
 *
 * Guidelines when adding a future theme:
 * - Dim / dark backgrounds: keep [value] high so colors are not lost against the surface;
 *   prefer muted [saturation] over neon.
 * - Light backgrounds: keep [value] moderate-low so colors are not blinding; avoid
 *   near-full saturation.
 */
@Immutable
data class PeerColorStyle(
    val saturation: Float,
    val value: Float,
) {
    companion object {
        /** Soft pastels that stay bright enough on near-black chat surfaces. */
        val Dark = PeerColorStyle(saturation = 0.55f, value = 0.82f)

        /** Deeper, less saturated tones that stay readable on near-white surfaces. */
        val Light = PeerColorStyle(saturation = 0.70f, value = 0.42f)
    }
}

/**
 * The single identity-to-color boundary used by chat, people sheets, and mentions.
 *
 * The djb2 seed stays unchanged; the fork maps it to a stable grey rather than a hue.
 */
fun colorForPeer(identity: PeerIdentity, palette: BitchatPalette): Color {
    var hash = 5381UL
    for (byte in identity.stableKey.toByteArray()) {
        hash = ((hash shl 5) + hash) + byte.toUByte().toULong()
    }

    // Same stable seed as upstream, mapped to readable neutral ink per peer.
    val t = ((hash shr 17) and 1023UL).toFloat() / 1023f
    val dark = palette.peerColors.value > 0.5f
    val high = if (dark) 0.88f else 0.42f
    val low = if (dark) 0.45f else 0.06f
    val grey = high - (high - low) * t
    return Color(red = grey, green = grey, blue = grey)
}
