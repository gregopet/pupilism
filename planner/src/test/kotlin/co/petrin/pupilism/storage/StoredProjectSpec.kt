package co.petrin.pupilism.storage

import co.petrin.pupilism.DayOfWeek
import co.petrin.pupilism.Klass
import co.petrin.pupilism.Time
import co.petrin.pupilism.TimeSlot
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import com.natpryce.hamkrest.*
import com.natpryce.hamkrest.assertion.assert

object StoredProjectSpec: Spek({

    given("A project saved as YAML") {
        val projectYaml = """
        ---
        classes:
        - 1A
        - 3B
        times:
        - from: 14:30
          to: 15:00
        - from: 10:00
          to: 11:00
        activities:
        - id: Judo 1
          timeSlots:
          - day: Ponedeljek
            from: 14:30
            to: 12:00
        pupils:
        - name: Janez Novak
          klass: 1A
          activities:
          - Judo 1
          leaveTimes:
            Ponedeljek: 14:30
        """.trimIndent() + "\n"


        it("Serializes the YAML back identically after deserialization") {
            // Not the best test since we have to match Jackson's output, but works for now
            val deserialized = deserialize(projectYaml)
            assert.that(serializeString(deserialized), equalTo(projectYaml))
        }

        it("Parses the classes correctly") {
            assert.that(deserialize(projectYaml).classes, hasElement(Klass(1, 'A')) and hasElement(Klass(3, 'B')))
        }

        it("Parses the times correctly") {
            assert.that(deserialize(projectYaml).times, hasElement(TimeInterval(from = Time("14:30"), to = Time("15:00"))))
        }

        it("Parses activities correctly") {
            val activities = deserialize(projectYaml).activities
            assert.that(activities, hasSize(equalTo(1)))
            assert.that(activities[0].id, equalTo("Judo 1"))
            assert.that(activities[0].timeSlots, hasElement(TimeSlot(DayOfWeek.Ponedeljek, Time("14:30"), Time("12:00"))))
        }

        it("Parses the pupils correctly") {
            val pupils = deserialize(projectYaml).pupils
            assert.that(pupils, hasSize(equalTo(1)))
            assert.that(pupils[0].name, equalTo("Janez Novak"))
            assert.that(pupils[0].klass, equalTo(Klass(1, 'A')))
            assert.that(pupils[0].leaveTimes[DayOfWeek.Ponedeljek], equalTo(Time("14:30")))
        }

    }

})