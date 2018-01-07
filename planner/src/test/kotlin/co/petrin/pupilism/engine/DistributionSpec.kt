package co.petrin.pupilism.engine

import co.petrin.pupilism.*
import com.natpryce.hamkrest.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import com.natpryce.hamkrest.assertion.assert
import org.jetbrains.spek.api.dsl.given
import co.petrin.pupilism.DayOfWeek.*


object DistributionSpec: Spek({
    val klassA = Klass(3, 'A')
    val pupilA1 = Pupil("Pupil 1", klassA)
    val pupilA2 = Pupil("Pupil 2", klassA)
    val pupilA3 = Pupil("Pupil 3", klassA)
    var pupilsA = listOf(pupilA1, pupilA2, pupilA3)

    val klassB = Klass(3, 'B')
    val pupilB1 = Pupil("Pupil 1", klassB)
    val pupilB2 = Pupil("Pupil 2", klassB)
    val pupilB3 = Pupil("Pupil 3", klassB)
    var pupilsB = listOf(pupilB1, pupilB2, pupilB3)

    val klass4A = Klass(4, 'A')
    val pupil4A1 = Pupil("Pupil 1", klass4A)
    val pupil4A2 = Pupil("Pupil 2", klass4A)
    val pupil4A3 = Pupil("Pupil 3", klass4A)
    var pupils4A = listOf(pupil4A1, pupil4A2, pupil4A3)

    // time slots
    val mondaySlots = listOf(
            TimeSlot(day = Ponedeljek, from = Time("12:10"), to = Time("13:00")),
            TimeSlot(day = Ponedeljek, from = Time("13:10"), to = Time("14:00")),
            TimeSlot(day = Ponedeljek, from = Time("14:10"), to = Time("15:00"))
    )
    val tuesdaySlots = listOf(
            TimeSlot(day = Torek, from = Time("12:10"), to = Time("13:00")),
            TimeSlot(day = Torek, from = Time("13:10"), to = Time("14:00")),
            TimeSlot(day = Torek, from = Time("14:10"), to = Time("15:00"))
    )

    given("Functions to merge groups") {
        it("Merges groups when they are below a configured size") {
            val partition = listOf(PupilGroup(pupilsA, mondaySlots[0]), PupilGroup(pupilsB, mondaySlots[0]))
            assert.that(tryMerge(partition, 0, 1, Rules(maxPupilsSingleAge = 3)), absent())
            assert.that(tryMerge(partition, 0, 1, Rules(maxPupilsSingleAge = 10)), present())

            val merge = tryMerge(partition, 0, 1, Rules(maxPupilsSingleAge = 10))!!
            assert.that(merge, hasSize(equalTo(1)))
            assert.that(merge[0], has(PupilGroup::distinctPupils, equalTo(pupilsA.size + pupilsB.size)))

        }

        it("Doesn't fail when merging groups at different indices") {
            val partition = listOf(
                    PupilGroup(listOf(pupilA1), mondaySlots[0]),
                    PupilGroup(listOf(pupilA2), mondaySlots[0]),
                    PupilGroup(listOf(pupilA3), mondaySlots[0]),
                    PupilGroup(listOf(pupilB1), mondaySlots[0])
            )
            assert.that(tryMerge(partition, 0, 1, Rules(maxPupilsSingleAge = 10)), present())
            assert.that(tryMerge(partition, 0, 2, Rules(maxPupilsSingleAge = 10)), present())
            assert.that(tryMerge(partition, 0, 3, Rules(maxPupilsSingleAge = 10)), present())
            assert.that(tryMerge(partition, 1, 2, Rules(maxPupilsSingleAge = 10)), present())
            assert.that(tryMerge(partition, 1, 3, Rules(maxPupilsSingleAge = 10)), present())
            assert.that(tryMerge(partition, 2, 3, Rules(maxPupilsSingleAge = 10)), present())
        }
    }

    given("A distributor working on pupils enrolled in activities") {
        //activities
        var firstPeriod = Activity(1, listOf(mondaySlots[0], tuesdaySlots[0]))

        it("Produces one partition per given time slot") {
            assert.that(distribute(pupilsA + pupilsB, emptyMap(), mondaySlots), hasSize(equalTo(mondaySlots.size)))
        }

        it("Doesn't assign pupils to time slots they have activities on") {
            val activities = mapOf(pupilA1 to listOf(firstPeriod))
            assert.that(distribute(pupilsA, activities, mondaySlots)[0].pupils, hasElement(pupilA2) and !hasElement(pupilA1))
        }

        it("Knows when children leave school") {
            pupilA2.leaveTimes[Ponedeljek] = Time("10:00")
            assert.that(distribute(pupilsA, mapOf(), mondaySlots)[0].pupils, !hasElement(pupilA2) and hasElement(pupilA1))
        }

        it("Merges groups when they are below the max size") {
            assert.that(distribute(pupilsA + pupilsB, emptyMap(), mondaySlots.subList(0, 1), Rules(maxPupilsSingleAge = 4)), hasSize(equalTo(2)))
            assert.that(distribute(pupilsA + pupilsB, emptyMap(), mondaySlots.subList(0, 1), Rules(maxPupilsSingleAge = 10)), hasSize(equalTo(1)))
        }
    }


    given("A partition scoring function") {
        val timeSlot = mondaySlots[0]
        val twoAgeHomogenous = listOf(PupilGroup(pupilsA, timeSlot), PupilGroup(pupils4A, timeSlot))
        val ageHomogenous = listOf(PupilGroup(pupilsA + pupilsB, timeSlot))
        val ageHeterogenous = listOf(PupilGroup(pupilsA + pupils4A, timeSlot))

        it("Favors age-homogenous groups") {
            assert.that(partitionCost(ageHeterogenous), greaterThan(partitionCost(ageHomogenous)))
        }

        it("Favors less groups") {
            assert.that(partitionCost(twoAgeHomogenous), greaterThan(partitionCost(ageHomogenous)))
        }
    }

})

private val PupilGroup.distinctPupils get() = this.pupils.distinct().size
