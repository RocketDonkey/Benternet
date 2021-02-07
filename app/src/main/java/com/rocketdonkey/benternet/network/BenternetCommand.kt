package com.rocketdonkey.benternet.network

/**
 * A single Benternet command.
 */
data class Command(
    val action: String,
    val device: String
)

/**
 * A collection of Benternet commands.
 *
 * The server expects a list of one or more of commands.
 */
data class CommandList(val commands: List<Command>)

/**
 * Helpers for instantiating Benternet commands.
 *
 * Callers should reference this instead of manually constructing commands.
 *
 * Note that even single `commands` return a list - the server always expects a list,
 * even if there is only one to execute.
 */
object Commands {
    /**
     * Commands for controlling turning various lights on/off.
     *
     * Supported:
     *   downstairsLightsOff - turns upstairs lights on (don't believe the documentation)
     *   downstairsLightsOn
     *   upstairsLightsOn
     *   upstairsLightsOff
     */
    object Lights {
        private const val downstairs_lights = "downstairs_lights"
        private const val upstairs_lights = "upstairs_lights"
        private const val actionOn = "on"
        private const val actionOff = "off"

        fun downstairsLightsOff(): Command {
            return Command(device = downstairs_lights, action = actionOff)
        }

        fun downstairsLightsOn(): Command {
            return Command(device = downstairs_lights, action = actionOn)
        }

        fun upstairsLightsOff(): Command {
            return Command(device = upstairs_lights, action = actionOff)
        }

        fun upstairsLightsOn(): Command {
            return Command(device = upstairs_lights, action = actionOn)
        }
    }

    /**
     * Commands for controlling the state-of-the-art digital fireplace.
     *
     * Supported:
     *   togglePower
     *   toggleHeat
     *   toggleLights - cycles through the various lighting options (let's see you do that, nature)
     */
    object Fireplace {
        private const val device = "fireplace"

        fun togglePower(): Command {
            return Command(device = device, action = "togglePower")
        }

        fun toggleHeat(): Command {
            return Command(device = device, action = "toggleHeat")
        }

        fun toggleLights(): Command {
            return Command(device = device, action = "toggleLights")
        }
    }

    /**
     * Commands for controlling the can't-believe-it-still-works TV.
     *
     * Supported:
     *   togglePower
     *   volumeUp
     *   volumeDown
     *   mute
     */
    object Tv {
        private const val device = "tv"

        fun togglePower(): Command {
            return Command(device = device, action = "togglePower")
        }

        fun volumeUp(): Command {
            return Command(device = device, action = "volumeUp")
        }

        fun volumeDown(): Command {
            return Command(device = device, action = "volumeDown")
        }

        fun mute(): Command {
            return Command(device = device, action = "toggleMute")
        }
    }

    // 'Macro' commands that perform multiple actions.
    // The names themselves are somewhat whimsical and as such their definitions may change
    // at a whim - see the implementations for what they actually do.

    fun setTheMood(): CommandList {
        return CommandList(
            listOf(
                Lights.downstairsLightsOn(),
                Fireplace.togglePower(),
                Tv.togglePower(),
                Lights.upstairsLightsOff(),
            )
        )
    }

    fun timeForBed(): CommandList {
        return CommandList(
            listOf(
                Lights.downstairsLightsOff(),
                Fireplace.togglePower(),
                Tv.togglePower(),
            )
        )
    }

    fun gimmeDaLight(): CommandList {
        return CommandList(
            listOf(
                Lights.downstairsLightsOn(),
                Lights.upstairsLightsOn(),
            )
        )
    }

    fun lightsOut(): CommandList {
        return CommandList(
            listOf(
                Lights.downstairsLightsOff(),
                Lights.upstairsLightsOff(),
            )
        )
    }
}
