# Android monochrome adaptation

Status: Kotlin compilation and two targeted JVM regression tests pass. The app has NOT been run or visually verified on Android. Do not treat this branch as a tested release.

## Scope and source

This branch adapts the presentation work in the iOS fork from base `04995f6` through `e2d4d0d`: monochrome colors, Departure Mono, grey peer labels, header/composer/people/status accents, media indicators and globe colors. It does not copy Swift platform APIs, the iOS-only Liquid Glass mode or an unrelated cache implementation.

Android base: `0594b60ff4adc4e8ed5dbc07ec163d0aedfc1607`. The `main` branch is unchanged as the backup. No upstream repository was changed.

## Android implementation

- Material roles and the app palette use black/grey/white in dark and light modes, including Material container defaults.
- The bundled Departure Mono OTF replaces the shared UI font family. SIL OFL is included with the font assets. Existing font resources remain available; platform glyph fallback is retained.
- Peer colors use the existing normalized identity hash and a stable grey range per mode. No new peer cache is added.
- Header, composer, sidebar/people markers and links inherit the central roles. Additional sending, audio, attachment and security-status colors use neutral status ink. Failures and destructive/security actions retain red and existing labels/icons.
- Globe land and oceans are neutral; its other colors inherit the theme. User image/video content is not changed.
- Existing Android layout choices and dark/light/system preferences are unchanged. Radio, Nostr/Tor, encryption, permissions, storage and routing code are unchanged.
- Developer-only diagnostic screens retain their upstream diagnostic colors; this adaptation does not redesign them.

## Verification and remaining work

Passed: `:app:compileDebugKotlin` and `:app:testDebugUnitTest --tests com.bitchat.android.ui.theme.MonochromeThemeTest` with JDK 21 and Android SDK 37. Two tests check neutral Material/app tokens, retained error red, and deterministic peer greys within the intended ranges. This is not the entire test suite.

Not done: actual Android screen inspection, large-text layout, full instrumented tests, real-device BLE/Wi-Fi behavior. The available environment lacks hardware acceleration and its emulator rejected normal software CPU execution. No installation on a user device, APK release or upstream submission was made.

Before merging: run this branch on Android; inspect chat, people sheets, composer, media, security and globe in dark/light modes and at larger text sizes. Check font fallback for emoji and non-Latin text. Fix any contrast/spacing issues and rerun tests. Keep this branch separate until that review passes.
