package co.petrin.pupilism.storage

import co.petrin.pupilism.Activity
import co.petrin.pupilism.Klass
import co.petrin.pupilism.Pupil

/**
 * A saved project.
 * @property classes All the classes involved in the planning
 * @property times The times each day for which we have to calculate the distributions
 */
class SavedProject(
    var classes: List<Klass>,
    var times: List<TimeInterval>,
    var activities: List<Activity>,
    var pupils: List<Pupil>
)