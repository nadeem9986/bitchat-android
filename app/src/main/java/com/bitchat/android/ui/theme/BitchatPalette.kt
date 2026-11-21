package com.bitchat.android.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Bitchat-specific color tokens that do not have a faithful Material 3 semantic role.
 *
 * Standard backgrounds, surfaces, text, outlines, primary/secondary accents, and errors belong
 * to [androidx.compose.material3.MaterialTheme.colorScheme]. Keeping only the extra app semantics
 * here lets Material components inherit correct defaults without losing Bitchat's identity.
 */
@Immutable
data class BitchatPalette(
    // MARK: - Form controls
    /**
     * Resting border for text inputs. Deliberately a neutral grey rather than the upstream tinted
     * Material outline: the composer is the one surface the user stares at while typing.
     */
    val inputOutline: Color,
    /** Border for a focused text input. A step brighter, still neutral. */
    val inputOutlineFocused: Color,
    /**
     * Fill for text inputs. Near-black / near-white and completely untinted, for the same reason
     * as [inputOutline] — and because the composer sits on top of a upstream tinted scrim, so any
     * tint of its own compounds into something muddy.
     */
    val inputSurface: Color,
    /** Fill for a focused text input. A barely perceptible lift. */
    val inputSurfaceFocused: Color,
    /** Resting disc behind the composer's action glyphs. Neutral grey. */
    val inputButton: Color,

    // MARK: - Extra semantics
    /** Timestamps, placeholders, section labels, disabled states. */
    val textTertiary: Color,
    /** Self, mentions targeting you, unread DMs. */
    val accentOrange: Color,
    /** Nostr reachability. */
    val accentPurple: Color,

    // MARK: - Deterministic peer colors
    /**
     * Saturation/value applied after deriving a peer's stable hue. Swap this when adding a
     * new theme — see [PeerColorStyle] for contrast guidelines.
     */
    val peerColors: PeerColorStyle,
)

val DarkBitchatPalette = BitchatPalette(
    inputOutline = Color(0xFF353535),
    inputOutlineFocused = Color(0xFF5F5F5F),
    inputSurface = Color(0xFF0B0B0B),
    inputSurfaceFocused = Color(0xFF151515),
    inputButton = Color(0xFF1E1E1E),
    textTertiary = Color(0xFF747474),
    accentOrange = Color(0xFFF2F2F2),
    accentPurple = Color(0xFF7A7A7A),
    peerColors = PeerColorStyle.Dark,
)

val LightBitchatPalette = BitchatPalette(
    inputOutline = Color(0xFFD2D2D2),
    inputOutlineFocused = Color(0xFF929292),
    inputSurface = Color(0xFFFAFAFA),
    inputSurfaceFocused = Color(0xFFF2F2F2),
    inputButton = Color(0xFFE8E8E8),
    textTertiary = Color(0xFF7C7C7C),
    accentOrange = Color(0xFF141414),
    accentPurple = Color(0xFF707070),
    peerColors = PeerColorStyle.Light,
)

val LocalBitchatPalette = staticCompositionLocalOf { DarkBitchatPalette }

/**
 * Motion tokens. The redesign leans on short, snappy transitions: long durations read as
 * sluggish on a chat surface where the user is scanning quickly.
 */
object BitchatMotion {
    /** Icon tints, text colors, small fills. */
    const val QUICK_MS = 120

    /** Tab indicators, pill growth, chip reveals. */
    const val STANDARD_MS = 180

    /** Sheet-level fades and scroll-driven top bars. */
    const val EMPHASIZED_MS = 240
}

/** Neutral status ink with usable contrast on both black and white backgrounds. */
val MonochromeStatusInk = Color(0xFF767676)
