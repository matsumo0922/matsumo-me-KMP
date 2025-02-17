package me.matsumo.blog.shared.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object InstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Instant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Instant {
        val dateTimeString = decoder.decodeString()
        return dateTimeString.toInstantInTokyo()
    }
}

fun String.toInstantInTokyo(): Instant {
    if (listOf("Z", "+").any { it in this }) {
        return Instant.parse(this)
    } else {
        val localDateTime = LocalDateTime.parse(this)
        return localDateTime.toInstant(TimeZone.of("Asia/Tokyo"))
    }
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun Instant.toIsoDateTimeString(): String {
    val format = LocalDateTime.Format { byUnicodePattern("yyyy-MM-dd HH:mm:ss") }
    return format.format(toLocalDateTime(TimeZone.UTC))
}
