# Author: Brad Westhafer
# E-mail: bdw5204@psu.edu 
# Course: CMPSC 312 
# Assignment: MIPS Programming Project 
# Due date: 4/19/2017 
# File: hw2.asm 
# Purpose: This program prompts a user to enter a positive integer, tests   
# if the integer is perfect, and reports the result of the test. 
# Simulator: MARS 4.5 
# Operating # system: Ubuntu 16.04 LTS
# References: Class demo programs

# This program has been tested and produces correct input, in a reasonable amount of time,
# for inputs up to approximately 1 million. Extremely large values may be extremely slow
# and may also be unsafe since I've only reserved space for 100,000 integers.
		
		.data #Declare Variables
newline:	.asciiz "\n" #newline character
prompt:		.asciiz "Please enter a positive integer (0 to quit): " #prompt to enter data
invalid:	.asciiz "Invalid Entry!" #Output for invalid entries
factors:	.asciiz "The factors are:\n" #Output factors
sum:		.asciiz "The sum is " #Output sum
perfect:	.asciiz "The number is perfect.\n\n" #Output if perfect
imperfect:	.asciiz "The number is NOT perfect.\n\n" #Output if imperfect
counter:	.word 1 #Counter variable
numfact:	.word 0 #Number of factors variable
s:		.word 0 #Sum of factors variable
temp:		.word 0 #Temporary variable
array:		.space 400000 #reserve enough space for 100,000 integers

		.text
		.globl main
main:		li $v0, 4 #Load syscall to print string
		la $a0, prompt #Load prompt's address into memory
		syscall #do a syscall to print prompt
		li $v0, 5 #Load syscall to read integer
		syscall #do a syscall to read integer
		move $s1, $v0 #move the integer read into $s1
		bltz $s1, badinput #jump to badinput if input < 0
		beqz $s1, quit #jump to quit if input = 0
		beq $s1, 1, one #jump to one if input = 1
		li $v0, 4 #Load syscall to print string
		la $a0, factors #Load factors's address into memory
		syscall #do a syscall to print string factors
		la $t0, array #load array into $t0
		lw $s2, counter #load counter into $s2
		lw $s3, numfact #load numfact into $s3
loop:		rem $t1, $s1, $s2 #calculate remainder of the integer divided by the current value of counter
		bnez $t1, incrementcount #if the current counter is not a factor, increment the counter
		sw $s2, 0($t0) #Store the factor in the array
		addi $t0, $t0, 4 #increment the address by 4 (one integer)
		addi, $s3, $s3, 1 #increment numfact
		

incrementcount:	addi $s2, $s2, 1 #add 1 to the counter
		bne $s2, $s1, loop #if counter not equal to the input, repeat the loop until it is
		
		la $t0, array #load array into $t0
		lw $s2, counter #load counter (1) into $s2
		lw $s4, s #load s (0) into $s4
printloop:	li $v0, 1 #Load syscall to print integer
		lw $a0, 0($t0) #Load the current element of the array into $a0
		syscall #do a syscall to print the current element of the array
		li $v0, 4 #Load syscall to print string
		la $a0, newline #Load newline character into $a0
		syscall #do a syscall to print a newline
		lw $t1, 0($t0) #load current element of array into $t1
		add $s4, $s4, $t1 #add value to sum
		addi $t0, $t0, 4 #increment the address by 4 (one integer)
		addi $s2, $s2, 1 #add 1 to the counter
		ble $s2, $s3, printloop #loop while all of the factors haven't been printed
		
		li $v0, 4 #Load syscall to print string
		la $a0, sum #load the string output for printing the sum into $a0
		syscall #do syscall to print string part of sum line
		li $v0, 1 #Load syscall to print integer
		sw $s4, temp #store the sum in temp
		lw $a0, temp #load temp into $a0
		syscall #do syscall to print the actual sum
		li $v0, 4 #Load syscall to print string
		la $a0, newline #load newline character into $a0
		syscall #do syscall to print newline character
		
		beq $s1, $s4, perfectnum #Jump if sum = input (i.e. if number is perfect)
		li $v0, 4 #Load syscall to print string
		la $a0, imperfect #load the output string for imperfect numbers into $a0
		syscall #do syscall to print the imperfect number string
		j main #jump back to main to allow the user to repeat the process

perfectnum:	li $v0, 4 #Load syscall to print string
		la $a0, perfect #load the output string for perfect numbers into $a0
		syscall #do syscall to print the perfect number string
		
nl:		
		
		j main #jump back to the start of the program
		

#Hardcode in handling for input = 1
one:		li $v0, 4 #Load print string syscall
		la $a0, factors #load the string output for factors into $a0
		syscall #do the syscall to print factors
		li $v0, 1 #load print integer syscall
		li $a0, 1 #load the integer 1 into $a0
		syscall #do syscall to print 1
		li $v0, 4 #load print string syscall
		la $a0, newline #load newline character into $a0
		syscall #do syscall to print newline character
		syscall #do syscall again to print another newline character
		li $v0, 4 #load print string syscall
		la $a0, sum #load the sum label string into $a0
		syscall #do a syscall to print the sum string
		li $v0, 1 #load print integer syscall
		li $a0, 1 #load the integer 1 into $a0
		syscall #do syscall to print 1
		li $v0, 4 #Load print string syscall
		la $a0, newline #load newline character into $a0
		syscall #do syscall to print newline character
		li $v0, 4 #load print string syscall
		la $a0, imperfect #load imperfect number string into $a0
		syscall #do syscall to print imperfect number string
		j main #jump back to start of program
		
badinput:	li $v0, 4 #Load syscall to print string
		la $a0, invalid #Load invalid's address to memory
		syscall #do a syscall to print invalid
		li $v0, 4 #Load syscall to print string
		la $a0, newline #Load newline's address to memory
		syscall #do a syscall to print newline
		j main #jump back to start of program
		
quit:		li $v0, 10 #Load syscall to end program
		syscall #Do a syscall to end program
