package co.petrin.pupilism.engine

import co.petrin.pupilism.*
import kotlin.math.max
import kotlin.math.min


/**
 * Distributes [pupils] along time [slots] so that all slots comply with [rules].
 * @param activities A collection of activities during which the children do not need to be accounted for
 */
fun distribute(pupils: List<Pupil>, activities: Map<Pupil, List<Activity>>, slots: List<TimeSlot>, rules: Rules = Rules()): List<PupilGroup> {
    return slots.flatMap { atTime ->
        // group pupils who are still there into classes
        val slotClasses =
            pupils.filter { !it.isBusy(atTime, activities) && !it.leavesBefore(atTime, rules)  }
            .groupBy { it.klass }.mapValues { PupilGroup(it.value, atTime) }

        // choose most highly scored permutation for this time slot
        val explored = mutableListOf<List<PupilGroup>>()
        permuteMerges(mutableListOf(slotClasses.values.toList()), explored, rules)
        explored.minBy { partitionCost(it) }!!
    }
}

/** Computes all permutations from possible group merges of [candidates]. Explored groups are put into [explored]. */
tailrec fun permuteMerges(candidates: MutableList<List<PupilGroup>>, explored: MutableList<List<PupilGroup>>, rules: Rules) {
    val current = candidates.removeAt(0)
    explored.add(current)
    for (sourceIdx in 0..current.lastIndex) {
        for (destIdx in sourceIdx+1..current.lastIndex) {
            val merge = tryMerge(current, sourceIdx, destIdx, rules)
            if (merge != null) candidates.add(merge)
        }
    }
    if (candidates.isNotEmpty()) permuteMerges(candidates, explored, rules)
}

/**
 * Tries to merge the [src] and [dst] groups; if successful, returns a copy of all the groups with the two merged.
 * For simplicity, src must be smaller then dst!
 */
fun tryMerge(groups: List<PupilGroup>, src: Int, dst: Int, rules: Rules): List<PupilGroup>? {
    require(src < dst)
    val group1 = groups[src]
    val group2 = groups[dst]
    val ageGroups = max(group1.toYear, group2.toYear) - min(group1.fromYear, group2.fromYear)
    val maxPupils = when(ageGroups) {
        0    -> rules.maxPupilsSingleAge
        1    -> rules.maxPupilsDualAge
        else -> rules.maxPupilsMultipleAges
    }
    return if (maxPupils < group1.pupils.size + group2.pupils.size) null else {
        groups.subList(0, src) +
        groups.subList(src + 1, dst) +
        listOf(merge(groups[src], groups[dst])) +
        if (dst == groups.lastIndex) listOf() else groups.subList(dst + 1, groups.size)
    }
}

/** Merge two groups of the same time slot into one by merging their students */
private fun merge(group1: PupilGroup, group2: PupilGroup): PupilGroup {
    require(group1.timeSlot == group2.timeSlot)
    return PupilGroup(group1.pupils + group2.pupils, group1.timeSlot)
}


/** Determines if a pupil has an activity during the given time [slot] */
private fun Pupil.isBusy(slot: TimeSlot, activities: Map<Pupil, List<Activity>>): Boolean =
        activities.getOrElse(this, { emptyList() }).any { pupilActivity -> pupilActivity.timeSlots.any { it.overlaps(slot) } }

/** Does the pupil leave school before the given time slot? */
private fun Pupil.leavesBefore(slot: TimeSlot, rules: Rules) =
    this.leaveTimes.getOrElse(slot.day, { rules.defaultLeaveTime }) <= slot.from

/** Calculates cost of a given [partition] into groups */
fun partitionCost(partition: List<PupilGroup>) = partition.sumBy {
    if (it.fromYear != it.toYear) 12 else 10
}
