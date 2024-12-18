package days

import xyz.epicebic.aoc.util.extractNumbers
import xyz.epicebic.aoc.util.extractNumbersSeperated

class Day17 : Day(17) {
    override fun partOne(inputLines: List<String>): Any {
        val (registerA, program) = inputLines.let { it[0].extractNumbers().toInt() to it.last().extractNumbersSeperated() }

        val registers = mutableListOf(registerA, 0, 0)

        var index = 0
        val out = mutableListOf<Int>()

        while(index in program.indices && index + 1 in program.indices) {
            val op = program[index]
            val operand = program[index + 1]
            when (op) {
                0 -> registers[0] /= 1 shl registers.combo(operand)
                1 -> registers[1] = registers[1] xor operand
                2 -> registers[1] = registers.combo(operand) % 8
                3 -> if (registers[0] != 0) index = operand - 2
                4 -> registers[1] = registers[1] xor registers[2]
                5 -> out += registers.combo(operand) % 8
                6 -> registers[1] = registers[0] / (1 shl registers.combo(operand))
                7 -> registers[2] = registers[0] / (1 shl registers.combo(operand))
            }

            index += 2
        }

        return out.joinToString(",")
    }

    override fun partTwo(inputLines: List<String>): Any {
        val instructions = inputLines.last().extractNumbersSeperated()

        fun processInstructions(pointer: Int, result: Long, depth: Int): Long {
            if (pointer < 0) return result
            if (depth > 7) return -1

            val registers = mutableListOf((result shl 3).toInt() or depth, 0, 0)
            var instructionIndex = 0
            var exitCondition = 0

            while (instructionIndex < instructions.size) {
                val currentOperand = instructions[instructionIndex + 1]
                val operationResult = registers.combo(currentOperand)

                when (instructions[instructionIndex]) {
                    0 -> registers[0] = registers[0] shr operationResult
                    1 -> registers[1] = registers[1] xor instructions[instructionIndex + 1]
                    2 -> registers[1] = (operationResult and 7)
                    3 -> instructionIndex = if (registers[0] != 0) instructions[instructionIndex + 1] - 2 else instructionIndex
                    4 -> registers[1] = registers[1] xor registers[2]
                    5 -> {
                        exitCondition = operationResult and 7
                        break
                    }
                    6 -> registers[1] = registers[0] shr operationResult
                    7 -> registers[2] = registers[0] shr operationResult
                }
                instructionIndex += 2
            }

            if (exitCondition == instructions[pointer]) {
                val recursiveResult = processInstructions(pointer - 1, (result shl 3) or depth.toLong(), 0)
                if (recursiveResult != -1L) return recursiveResult
            }

            return processInstructions(pointer, result, depth + 1)
        }

        return processInstructions(instructions.size - 1, 0, 0)
    }

    private fun List<Int>.combo(operand: Int) = if (operand in 0..3) operand else get(operand - 4)
}
