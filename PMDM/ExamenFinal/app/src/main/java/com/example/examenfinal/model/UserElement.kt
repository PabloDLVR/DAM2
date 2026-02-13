package com.example.examenfinal.model

data class UserElement (
    val fairings: Fairings? = null,
    val links: Links,
    val staticFireDateUTC: String? = null,
    val staticFireDateUnix: Long? = null,
    val net: Boolean,
    val window: Long? = null,
    val rocket: Rocket,
    val success: Boolean? = null,
    val failures: List<Failure>,
    val details: String? = null,
    val crew: List<Crew>,
    val ships: List<String>,
    val capsules: List<String>,
    val payloads: List<String>,
    val launchpad: Launchpad,
    val flightNumber: Long,
    val name: String,
    val dateUTC: String,
    val dateUnix: Long,
    val dateLocal: String,
    val datePrecision: DatePrecision,
    val upcoming: Boolean,
    val cores: List<Core>,
    val autoUpdate: Boolean,
    val tbd: Boolean,
    val launchLibraryID: String? = null,
    val id: String
)

data class Core (
    val core: String? = null,
    val flight: Long? = null,
    val gridfins: Boolean? = null,
    val legs: Boolean? = null,
    val reused: Boolean? = null,
    val landingAttempt: Boolean? = null,
    val landingSuccess: Boolean? = null,
    val landingType: LandingType? = null,
    val landpad: Landpad? = null
)

enum class LandingType {
    Asds,
    Ocean,
    Rtls
}

enum class Landpad {
    The5E9E3032383Ecb267A34E7C7,
    The5E9E3032383Ecb554034E7C9,
    The5E9E3032383Ecb6Bb234E7CA,
    The5E9E3032383Ecb761634E7Cb,
    The5E9E3032383Ecb90A834E7C8,
    The5E9E3033383Ecb075134E7CD,
    The5E9E3033383Ecbb9E534E7Cc
}

data class Crew (
    val crew: String,
    val role: String
)

enum class DatePrecision {
    Day,
    Hour,
    Month
}

data class Failure (
    val time: Long,
    val altitude: Long? = null,
    val reason: String
)

data class Fairings (
    val reused: Boolean? = null,
    val recoveryAttempt: Boolean? = null,
    val recovered: Boolean? = null,
    val ships: List<String>
)

enum class Launchpad {
    The5E9E4501F509094Ba4566F84,
    The5E9E4502F509092B78566F87,
    The5E9E4502F509094188566F88,
    The5E9E4502F5090995De566F86
}

data class Links (
    val patch: Patch,
    val reddit: Reddit,
    val flickr: Flickr,
    val presskit: String? = null,
    val webcast: String? = null,
    val youtubeID: String? = null,
    val article: String? = null,
    val wikipedia: String? = null
)

data class Flickr (
    val small: List<Any?>,
    val original: List<String>
)

data class Patch (
    val small: String? = null,
    val large: String? = null
)

data class Reddit (
    val campaign: String? = null,
    val launch: String? = null,
    val media: String? = null,
    val recovery: String? = null
)

enum class Rocket {
    The5E9D0D95Eda69955F709D1Eb,
    The5E9D0D95Eda69973A809D1Ec,
    The5E9D0D95Eda69974Db09D1Ed
}
