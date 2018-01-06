package co.petrin.pupilism

/**
 * The rules of pupil distribution.
 */
class Rules(
    val maxPupilsSingleAge: Int = 28,
    val maxPupilsDualAge: Int = 25,
    val maxPupilsMultipleAges: Int = 21,

    /** If not specified otherwise, kids leave at this time */
    val defaultLeaveTime: Time = Time("18:00")
)