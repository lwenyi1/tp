# User Guide

## Introduction

MindExpander is a desktop app for Primary School students to practice questions in Mathematics, Sciences and English.
It is designed for use via a Command Line Interface.

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 17 or above installed.
2. Down the latest version of `MindExpander` from [here](http://link.to/duke).

## Features
The list of features and how to use them can be found below.
User input lines will be of the form `[USER_INPUT]`.
For example, an input which requires a question number from the user will be of the form `[QUESTION_NUMBER]`.
For features with multiple separate inputs, the different inputs will be separated by a | symbol. For example,
an input with 3 different input steps will be of the form `STEP 1` | `STEP 2` | `STEP 3`.

### Viewing the help sheet: `help`
Displays the list of commands, along with the format of the command, and what each command will return.

Format: `help` 

Example usage: 

`help`

### Adding a question: `add`
Adds a question to the question bank. Follows a series of steps which require separate inputs each.

Format: `add` | `[QUESTION_TYPE]` | `[QUESTION_DETAILS]` | `[QUESTION_ANSWER]`

Question types (as of this version): `FITB`

Example usage: #TODO

**Note**: This program is designed to take inputs in **Roman Alphabet** (i.e. English characters), 
please do not enter characters from other languages, for example Chinese characters.

### Listing questions added: `list`
Lists all the questions currently in the question bank.

Format: `list`

Example usage:

`list`

### Solving questions: `solve`
Solves a question that was previously added to the question bank.

**Multistep usage**
`solve` can be used in a "one command at a time" manner. This method is easier for new users and guides the user
through the process.

Format: `solve` | `[QUESTION INDEX]` | `[QUESTION ANSWER]`
`[QUESTION_INDEX]`: The question number of the question to be solved.
`[QUESTION_ANSWER]`: The answer to the question.

Example usage:
1. `solve`
    > Please enter the question number you would like to solve.
2. `2`
    > Attempting question 2: What are fries made of? Enter your answer:
3. `Potato`
    > Correct!

**Note**:
* It is recommended to run `list` before `solve` to check the index of the question you intend to solve.
* Entering the wrong answer will result in the below message, enter Y to try again and N to give up and exit:
   > Wrong answer, would you like to try again? [Y/N]

**One-step usage**
`solve` can also be used by entering all the arguments in one line, this method is faster but must follow the format
correctly.

Format: `solve /q [QUESTION_INDEX] /a [QUESTION_ANSWER]`

Example usage:
`solve /q 2 /q Potato`

**Note**:
* It is recommended to run `list` before `solve` to check the index of the question you intend to solve.
* Follow the command format as specified above and ensure that question indexes are within 1 to the number of questions,
entering otherwise will result in errors.

### Exiting the program: `exit`
Exits the program.

Format: `exit`

Example usage: `exit`

### Additional notes
* Inputting unrecognised commands will result in an error message.
* Saving and Loading: The question bank is automatically saved to a file named MindExpander.txt in the ./data/ folder. 
## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: #TODO

## Command Summary

* View help sheet `help`
* Add question `add` | `[QUESTION_TYPE]` | `[QUESTION_DETAILS]` | `[QUESTION_ANSWER]`
* List question bank `list`
* Solve question
  * Multistep `solve` | `[QUESTION_INDEX]` | `[QUESTION_ANSWER]`
  * One-step `solve /q [QUESTION__INDEX] /a [QUESTION_ANSWER]`
* Exit program `exit`
